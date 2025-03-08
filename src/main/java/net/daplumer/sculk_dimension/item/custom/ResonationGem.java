package net.daplumer.sculk_dimension.item.custom;
import net.daplumer.sculk_dimension.util.statistics.Insanity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class ResonationGem extends Item{

    public ResonationGem(Settings settings) {
        super(settings);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        Insanity.add(2, player);
        super.onCraftByPlayer(stack, world, player);
    }
    HitResult ray;

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Entity entity;
        ray = raycastEntity(user, 256);
        if (ray == null){
            return TypedActionResult.fail(user.getStackInHand(hand));
        }
        switch (ray.getType()){
            case ENTITY -> {
                EntityHitResult entityHit = (EntityHitResult) ray;
                entity = entityHit.getEntity();
                world.emitGameEvent(entity, GameEvent.RESONATE_6, entity.getPos());
                user.getStackInHand(hand).damage(1, user, LivingEntity.getSlotForHand(hand));
                entity.playSound(SoundEvents.BLOCK_SCULK_SHRIEKER_FALL,1,1);
                Insanity.increment(user);
                return TypedActionResult.pass(user.getStackInHand(hand));
            }
            case null, default -> {
            }
        }
        return super.use(world, user, hand);
    }
    public static HitResult raycastEntity(PlayerEntity player, double maxDistance) {
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
}