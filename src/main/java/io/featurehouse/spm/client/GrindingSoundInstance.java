package io.featurehouse.spm.client;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.GrinderBlock;
import io.featurehouse.spm.util.MathUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class GrindingSoundInstance extends PositionedSoundInstance implements TickableSoundInstance {
    public final ClientPlayerEntity player;
    public ClientWorld world;
    public BlockPos pos;
    protected boolean done = false;

    public GrindingSoundInstance(float pitch, ClientWorld world, BlockPos pos, ClientPlayerEntity player) {
        super(SPMMain.GRINDER_GRIND, SoundCategory.BLOCKS, 1.0F, pitch, pos);
        this.repeat = true;
        this.pos = pos;
        this.world = world;
        this.player = player;
    }

    @Override
    public boolean canPlay() {
        BlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof GrinderBlock // important
                && world.getBlockState(pos).get(GrinderBlock.GRINDING);
    }

    @Override
    public boolean shouldAlwaysPlay() {
        return false;
    }

    @Override
    public boolean isDone() {
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
        if (!canPlay()) setDone();
    }

    protected final void setDone() {
        this.done = true;
        this.repeat = false;
    }
}
