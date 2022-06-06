package org.featurehouse.mcmod.spm.client;

import org.featurehouse.mcmod.spm.util.MathUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiPredicate;

@Environment(EnvType.CLIENT)
public class KeepPlayingSoundInstance extends SimpleSoundInstance implements TickableSoundInstance {
    public final LocalPlayer player;
    @NotNull
    public ClientLevel world;
    public BlockPos pos;
    protected boolean done = false;
    protected BiPredicate<ClientLevel, BlockPos> playCondition;

    public KeepPlayingSoundInstance(SoundEvent sound, float pitch, @NotNull ClientLevel world, BlockPos pos, LocalPlayer player, BiPredicate<ClientLevel, BlockPos> playCondition) {
        super(sound, SoundSource.BLOCKS, 1.0F, pitch, world.getRandom(), pos);
        this.looping = true;
        this.pos = pos;
        this.world = world;
        this.player = player;
        this.playCondition = playCondition;
    }

    @Override
    public boolean canPlaySound() {
        return playCondition.test(world, pos);
    }

    @Override
    public boolean canStartSilent() {
        return false;
    }

    @Override
    public boolean isStopped() {
        return done;
    }

    @Override
    public void tick() {
        float distance = MathUtils.distance(pos, player.getX(), player.getY(), player.getZ());
        if (distance >= 24.0F)
            this.volume = 0.0F;
        else {
            // 1.0F: distance = 0.0F
            // 0.0F: distance = 24.0F
            this.volume = 1.0F - distance / 24.0F;
        }
        if (!canPlaySound()) setDone();
    }

    protected final void setDone() {
        this.done = true;
        this.looping = false;
    }
}
