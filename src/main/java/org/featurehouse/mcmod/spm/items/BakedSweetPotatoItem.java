package org.featurehouse.mcmod.spm.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.featurehouse.mcmod.spm.util.inventory.PeelInserter;

public class BakedSweetPotatoItem extends Item implements SweetPotatoProperties {

    private final SweetPotatoType sweetPotatoType;

    public BakedSweetPotatoItem(Properties settings, SweetPotatoType type) {
        super(settings.food(type.getComponent(SweetPotatoStatus.BAKED).asFoodComponent()));
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
        return SweetPotatoStatus.BAKED;
    }

    @Override
    public SweetPotatoType asType() {
        return this.sweetPotatoType;
    }
}
