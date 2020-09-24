package io.github.teddyxlandlee.sweet_potato.items;

import io.github.teddyxlandlee.sweet_potato.ExampleMod;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoStatus;
import io.github.teddyxlandlee.sweet_potato.SweetPotatoType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Objects;

public class BakedSweetPotatoItem extends Item implements WithStatus {

    private final SweetPotatoType sweetPotatoType;

    public BakedSweetPotatoItem(Settings settings, SweetPotatoType type) {
        super(settings.food(Objects.requireNonNull(type.getComponent(SweetPotatoStatus.BAKED)).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        //(PlayerEntity)user.inventory.insertStack(new ItemStack(ExampleMod.PEEL));
        //user.sendPickup(new ItemEntity(world, user.getX(), user.getY(), user.getZ(), new ItemStack(ExampleMod.BAKED_PEEL)), 1);
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity && !((PlayerEntity) user).abilities.creativeMode) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            playerEntity.inventory.insertStack(new ItemStack(ExampleMod.PEEL, 1));
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
