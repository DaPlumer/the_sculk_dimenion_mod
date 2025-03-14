package net.daplumer.sculk_dimension.item.custom;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.VibrationParticleEffect;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.*;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.event.listener.Vibration;
import net.minecraft.world.event.listener.VibrationSelector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

import static net.daplumer.sculk_dimension.component.ModDataComponentTypes.USERID;

public class ResonationHelmet extends ArmorItem implements GameEventListener.Holder<Vibrations.VibrationListener>, Vibrations {
    private ListenerData listenerData;
    private VibrationListener listener;
    private VibrationCallback callback;
    public ResonationHelmet(Type type, Settings settings) {
        super(ModArmorMaterials.RESONATION_ARMOR, type, settings);
        this.listenerData = new Vibrations.ListenerData();
        this.listener = new VibrationListener(this);
    }

    @Override
    public void inventoryTick( ItemStack stack,World world,Entity user,int slot, boolean isEquipped) {
        if (user instanceof PlayerEntity) {
            if (this.callback== null) {
                this.callback = new VibrationCallback((PlayerEntity) user);
                TheSculkDimension.LOGGER.info(this.callback.toString());
                TheSculkDimension.LOGGER.info(this.callback.getPositionSource().toString());
            }else {
                if (world instanceof ServerWorld serverWorld) {
                    this.callback.user = (PlayerEntity) user;
                    Vibrations.Ticker.tick(serverWorld, this.getVibrationListenerData(), this.getVibrationCallback());
                    tryListen(serverWorld,listenerData,callback);
                    if( this.listenerData.getVibration() != null) {
                        TheSculkDimension.LOGGER.info(this.listenerData.getVibration().getEntity(serverWorld).toString());
                    }
                }

            }
        }
    }
    @Override
    public ListenerData getVibrationListenerData() {
        return this.listenerData;
    }

    @Override
    public Callback getVibrationCallback() {
        return this.callback;
    }

    @Override
    public VibrationListener getEventListener() {
        return this.listener;
    }

    public class VibrationCallback implements Callback {
        public PlayerEntity user;
        public PlayerEntity getUser(){
            if (this.user == null){
                return null;
            }
            return this.user;
        }
        public static final int RANGE = 16;
        protected BlockPos pos;
        private PositionSource positionSource;

        public VibrationCallback(PlayerEntity user) {
            this.user = user;
            this.positionSource = new EntityPositionSource(this.user,1);
        }


        @Override
        public final int getRange() {
            return 16;
        }

        @Override
        public PositionSource getPositionSource() {
            return this.positionSource;
        }

        @Override
        public boolean accepts(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable GameEvent.Emitter emitter) {
            TheSculkDimension.LOGGER.info("Tried to accept");
            return true;
        }

        @Override
        public void accept(ServerWorld world, BlockPos pos, RegistryEntry<GameEvent> event, @Nullable Entity sourceEntity, @Nullable Entity entity, float distance) {
            TheSculkDimension.LOGGER.info("ACCEPTED!");
        }

        @Override
        public boolean canAccept(RegistryEntry<GameEvent> gameEvent, GameEvent.Emitter emitter) {
            return true;
        }
    }







    private static void tryListen(@NotNull ServerWorld world, Vibrations.@NotNull ListenerData listenerData, Vibrations.Callback callback) {
        listenerData.getSelector().getVibrationToTick(world.getTime()).ifPresent(vibration -> {
            listenerData.setVibration(vibration);
            Vec3d vec3d = vibration.pos();
            listenerData.setDelay(callback.getDelay(vibration.distance()));
            world.spawnParticles(new VibrationParticleEffect(callback.getPositionSource(), listenerData.getDelay()), vec3d.x, vec3d.y, vec3d.z, 1, 0.0, 0.0, 0.0, 0.0);
            callback.onListen();
            listenerData.getSelector().clear();
        });
    }
    
}
