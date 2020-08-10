package com.github.teddyxlandlee.sweet_potato.items;

import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EnchantedPotatoItem extends SweetPotatoItem {
    public boolean hasGlint(ItemStack stack) {
        return true;
    }

    public static final FoodComponent COMPONENT = new FoodComponent.Builder()
                        .hunger(6)
                        .saturationModifier(7.6f)
                        .statusEffect(calcEffect(), 1.0f)
                        .build();

    public EnchantedPotatoItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        //(PlayerEntity)user.inventory.insertStack(new ItemStack(ExampleMod.PEEL));
        //user.sendPickup(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(ExampleMod.PEEL)), 1);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            playerEntity.inventory.insertStack(new ItemStack(ExampleMod.PEEL, 1));
        }

        //if (!(user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode))
        //    stack.setCount(stack.getCount() - 1);
        return stack;
    }

    private static StatusEffectInstance calcEffect() {
        return new StatusEffectInstance(StatusEffects.LUCK);
        // Remember, this is just a trial.
        // The REAL calculation should be added later.
        // teddyxlandlee, please decide the details with your group. 13 Jun 2020 night
    }

}
