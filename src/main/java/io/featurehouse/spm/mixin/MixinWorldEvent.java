package io.featurehouse.spm.mixin;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.client.GrindingSoundInstance;
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
public class MixinWorldEvent {
    @Shadow private ClientWorld world;

    @Shadow @Final private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "processWorldEvent", cancellable = true)
    private void spmSounds(PlayerEntity source, int eventId, BlockPos blockPos, int data, CallbackInfo ci) {
        if (eventId == 1044 && data == 8844110) {
            world.playSound(blockPos, SPMMain.AGROFORESTRY_TABLE_FINISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F, false);
            ci.cancel();
        } else if (eventId == 1132119 && data == 805) {
            client.getSoundManager().play(new GrindingSoundInstance(1.0F, 1.0F, world, blockPos, client.player));
            ci.cancel();
        }
    }
}
