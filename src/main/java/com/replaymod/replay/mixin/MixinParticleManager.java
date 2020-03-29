package com.replaymod.replay.mixin;

//#if MC>=10904
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {
    @Shadow
    //#if MC>=11400
    private Queue<Particle> newParticles;
    //#else
    //$$ private Queue<Particle> queueEntityFX;
    //#endif

    /**
     * This method additionally clears the queue of particles to be added when the world is changed.
     * Otherwise particles from the previous world might show up in this one if they were spawned after
     * the last tick in the previous world.
     *
     * @param world The new world
     * @param ci Callback info
     */
    @Inject(method = "setWorld", at = @At("HEAD"))
    public void replayModReplay_clearParticleQueue(World world, CallbackInfo ci) {
        //#if MC>=11400
        this.newParticles.clear();
        //#else
        //$$ this.queueEntityFX.clear();
        //#endif
    }
}
//#endif
