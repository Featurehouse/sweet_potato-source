package org.featurehouse.mcmod.spm.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.GrinderBlock;
import org.featurehouse.mcmod.spm.client.KeepPlayingSoundInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinWorldEventC {
    @Shadow private ClientLevel level;
    @Shadow @Final private Minecraft minecraft;

    /**
     * When {@link ClientLevel#levelEvent(Player, int, BlockPos, int)} calls.
     */
    @Inject(at = @At("HEAD"), method = "levelEvent", cancellable = true)
    private void spmSounds(Player source, int eventId, BlockPos blockPos, int data, CallbackInfo ci) {
        if (eventId == 1132119 && data == 805) {
            minecraft.getSoundManager().play(new KeepPlayingSoundInstance(SPMMain.GRINDER_GRIND, 1.0F, level, blockPos, minecraft.player, (world1, blockPos1) -> {
                BlockState state = world1.getBlockState(blockPos1);
                return state.getBlock() instanceof GrinderBlock // important
                        && state.getValue(GrinderBlock.GRINDING);
            }));
            ci.cancel();
        }
    }
}
