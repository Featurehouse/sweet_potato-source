package org.featurehouse.mcmod.spm.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.featurehouse.mcmod.spm.util.inventory.PeelInserter;

public class RawSweetPotatoBlockItem extends /*SweetPotatoItem*/ ItemNameBlockItem implements SweetPotatoProperties {
    @Override
    public boolean isEdible() {
        return true;
    }

    private final SweetPotatoType sweetPotatoType;

    public RawSweetPotatoBlockItem(Block block, Item.Properties settings, SweetPotatoType type) {
        super(block, settings.food(type.getComponent(SweetPotatoStatus.RAW).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        super.finishUsingItem(stack, world, user);
        if (user instanceof Player playerEntity) {
            playerEntity.awardStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((Player) user).getAbilities().instabuild)
                PeelInserter.run(playerEntity);
        }

        return stack;
    }

    @Override
    public SweetPotatoStatus getStatus() {
        return SweetPotatoStatus.RAW;
    }

    @Override
    public SweetPotatoType asType() {
        return this.sweetPotatoType;
    }
}
