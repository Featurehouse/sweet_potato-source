package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.sweet_potato.ExampleMod;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.world.World;

public class RawSweetPotatoBlockItem extends /*SweetPotatoItem*/ AliasedBlockItem {
    @Override
    public boolean isFood() {
        return true;
    }

    //private final SweetPotatoType sweetPotatoType;
    public RawSweetPotatoBlockItem(Block block, Item.Settings settings) {
        super(block, settings);
        //this.sweetPotatoType = sweetPotatoType;
    }

    public static final FoodComponent COMPONENT = new FoodComponent.Builder()
            .hunger(2)
            .saturationModifier(1.6f)
            .build();

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //(PlayerEntity)user.inventory.insertStack(new ItemStack(ExampleMod.PEEL));
        //user.sendPickup(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(ExampleMod.PEEL)), 1);
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            playerEntity.inventory.insertStack(new ItemStack(ExampleMod.PEEL, 1));
        }

        //if (!(user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode))
        //    stack.setCount(stack.getCount() - 1);
        return stack;
    }
}
