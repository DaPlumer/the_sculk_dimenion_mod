package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.BlockPositionSource;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.PositionSource;
import org.jetbrains.annotations.Nullable;

import static net.daplumer.sculk_dimension.util.VecToBlockPos.createBlockPos;

public class ResonationGem extends Item{
    private int tickSinceLastUse = 0;
    private HitResult lastRay;
    private Vec3d rayHit;

    public ResonationGem(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, PlayerEntity player) {
        ((Insanity) player).addInsanity(2);
        super.onCraftByPlayer(stack, player);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Entity entity;
        HitResult ray = raycastEntity(user, 256);
        this.lastRay = ray;
        this.tickSinceLastUse = 0;
        if (ray == null){
            return ActionResult.FAIL;
        }
        this.rayHit = ray.getPos();
        if (ray.getType() == HitResult.Type.ENTITY){
            EntityHitResult entityHit = (EntityHitResult) ray;
            entity = entityHit.getEntity();
            user.getStackInHand(hand).damage(1, user, LivingEntity.getSlotForHand(hand));
            entity.playSound(SoundEvents.BLOCK_SCULK_SHRIEKER_FALL,1,1);
            ((Insanity) user).incrementInsanity();
            world.emitGameEvent(((EntityHitResult) this.lastRay).getEntity(), GameEvent.RESONATE_6, ((EntityHitResult) this.lastRay).getEntity().getPos());
            user.getItemCooldownManager().set(user.getStackInHand(hand),32);
            return ActionResult.SUCCESS;
        }
        return super.use(world, user, hand);
    }
    public static @Nullable HitResult raycastEntity(PlayerEntity player, double maxDistance) {
        Entity cameraEntity = MinecraftClient.getInstance().cameraEntity;
        if (cameraEntity != null) {
            Vec3d cameraPos = player.getCameraPosVec(1.0f);
            Vec3d rot = player.getRotationVec(1.0f);
            Vec3d rayCastContext = cameraPos.add(rot.x * maxDistance, rot.y * maxDistance, rot.z * maxDistance);
            Box box = cameraEntity.getBoundingBox().stretch(rot.multiply(maxDistance)).expand(1d, 1d, 1d);
            return ProjectileUtil.raycast(cameraEntity, cameraPos, rayCastContext, box, (entity -> !entity.isSpectator() && entity.canHit()), maxDistance);
        }
        return null;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        this.tickSinceLastUse++;
        if (this.lastRay != null){
            if (this.lastRay.getType() == HitResult.Type.ENTITY){
                this.rayHit = ((EntityHitResult) this.lastRay).getEntity().getPos();
            }

            Vec3d vec3d = entity.getPos();
            PositionSource positionSource = new BlockPositionSource(createBlockPos(this.rayHit));
            Vec3d vec3d2 = positionSource.getPos(world).orElse(vec3d);
            int i = this.tickSinceLastUse;
            int j = MathHelper.floor(entity.getPos().distanceTo(this.rayHit));
            double d = 1.0 - (double)i / (double)j;
            if(d > 0) {
                double e = MathHelper.lerp(d, vec3d.x, vec3d2.x);
                double f = MathHelper.lerp(d, vec3d.y, vec3d2.y);
                double g = MathHelper.lerp(d, vec3d.z, vec3d2.z);
                (world).spawnParticles(new VibrationParticleEffect(positionSource, i), e, f, g, 1, 0.0, 0.0, 0.0, 0.0);
                if (d - (1D / (double) j) <= 0 && this.lastRay.getType() == HitResult.Type.ENTITY){
                    world.emitGameEvent(((EntityHitResult) this.lastRay).getEntity(), GameEvent.RESONATE_6, ((EntityHitResult) this.lastRay).getEntity().getPos());
                }
            }
        }
    }
}