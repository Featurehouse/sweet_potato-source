package io.featurehouse.spm.items;

import io.featurehouse.spm.SweetPotatoStatus;
import io.featurehouse.spm.SweetPotatoType;
import io.featurehouse.spm.util.inventory.PeelInserter;
import io.featurehouse.spm.util.j9bridge.MObjects;
import io.featurehouse.spm.util.properties.objects.NullSweetPotatoComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BakedSweetPotatoItem extends Item implements WithStatus {

    private final SweetPotatoType sweetPotatoType;

    public BakedSweetPotatoItem(Settings settings, SweetPotatoType type) {
        super(settings.food(MObjects.requireNonNullElse(
                type.getComponent(SweetPotatoStatus.BAKED), new NullSweetPotatoComponent())
                .asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //(PlayerEntity)user.inventory.insertStack(new ItemStack(SPMMain.PEEL));
        //user.sendPickup(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(SPMMain.BAKED_PEEL)), 1);
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).getAbilities().creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            PeelInserter.run(playerEntity);
        }

        //if (!(user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode))
        //    stack.setCount(stack.getCount() - 1);
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