package io.github.teddyxlandlee.sweet_potato.mixin;

import io.github.teddyxlandlee.sweet_potato.SPMMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinWorldEvent {
    @Shadow private ClientWorld world;

    @Inject(at = @At("HEAD"), method = "processWorldEvent", cancellable = true)
    private void agro1044(PlayerEntity source, int eventId, BlockPos blockPos, int data, CallbackInfo ci) {
        if (eventId == 1044 && data == 8844110) {
            //world.playSound(blockPos.getX(), blockPos.getY(), blockPos.getZ(), SPMMain.AGROFORESTRY_TABLE_FINISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F, false);
            world.playSound(blockPos, SPMMain.AGROFORESTRY_TABLE_FINISH, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F, false);
            ci.cancel();
        }
    }
}
