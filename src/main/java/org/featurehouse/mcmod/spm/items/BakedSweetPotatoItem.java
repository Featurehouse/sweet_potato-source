package org.featurehouse.mcmod.spm.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.sweetpotato.SweetPotatoType;
import org.featurehouse.mcmod.spm.util.inventory.PeelInserter;

public class BakedSweetPotatoItem extends Item implements SweetPotatoProperties {

    private final SweetPotatoType sweetPotatoType;

    public BakedSweetPotatoItem(Settings settings, SweetPotatoType type) {
        super(settings.food(type.getComponent(SweetPotatoStatus.BAKED).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity playerEntity) {
            playerEntity.incrementStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((PlayerEntity) user).getAbilities().creativeMode)
                PeelInserter.run(playerEntity);
        }

        return stack;
    }

    @Override
    public SweetPotatoStatus getStatus() {
        return SweetPotatoStatus.BAKED;
    }

    @Override
    public SweetPotatoType asType() {
        return this.sweetPotatoType;
    }
}
