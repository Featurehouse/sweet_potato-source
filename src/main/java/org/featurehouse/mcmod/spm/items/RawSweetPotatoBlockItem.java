package org.featurehouse.mcmod.spm.items;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.SweetPotatoType;
import org.featurehouse.mcmod.spm.util.inventory.PeelInserter;
import org.featurehouse.mcmod.spm.util.properties.objects.NullSweetPotatoComponent;
import org.featurehouse.mcmod.spm.util.j9bridge.MObjects;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RawSweetPotatoBlockItem extends /*SweetPotatoItem*/ AliasedBlockItem implements WithStatus {
    @Override
    public boolean isFood() {
        return true;
    }

    private final SweetPotatoType sweetPotatoType;

    public RawSweetPotatoBlockItem(Block block, Item.Settings settings, SweetPotatoType type) {
        super(block, settings.food(MObjects.requireNonNullElse(
                type.getComponent(SweetPotatoStatus.RAW), new NullSweetPotatoComponent())
                .asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            playerEntity.incrementStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((PlayerEntity) user).abilities.creativeMode)
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
