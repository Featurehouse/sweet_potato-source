package org.featurehouse.spm.mixin;

import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.blocks.GrinderBlock;
import org.featurehouse.spm.client.KeepPlayingSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinWorldEvent {
    @Shadow private ClientWorld world;
    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "processWorldEvent", cancellable = true)
    private void spmSounds(PlayerEntity source, int eventId, BlockPos blockPos, int data, CallbackInfo ci) {
        if (eventId == 1044 && data == 8844110) {
            world.playSound(blockPos, SPMMain.AGROFORESTRY_TABLE_FINISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F, false);
            ci.cancel();
        } else if (eventId == 1132119 && data == 805) {
            client.getSoundManager().play(new KeepPlayingSoundInstance(SPMMain.GRINDER_GRIND, 1.0F, world, blockPos, client.player, (world1, blockPos1) -> {
                BlockState state = world1.getBlockState(blockPos1);
                return state.getBlock() instanceof GrinderBlock // important
                        && state.get(GrinderBlock.GRINDING);
            }));
            ci.cancel();
        }
    }
}
