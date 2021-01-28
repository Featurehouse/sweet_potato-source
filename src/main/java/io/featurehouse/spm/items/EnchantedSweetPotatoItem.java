package io.featurehouse.spm.items;

import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.SweetPotatoStatus;
import io.featurehouse.spm.SweetPotatoType;
import io.featurehouse.spm.util.NbtUtils;
import io.featurehouse.spm.util.inventory.PeelInserter;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class EnchantedSweetPotatoItem extends EnchantedItem implements WithStatus {
    @Override
    public boolean isFood() {
        return true;
    }

    private final SweetPotatoType sweetPotatoType;

    public EnchantedSweetPotatoItem(Settings settings, SweetPotatoType type) {
        super(settings.food(Objects.requireNonNull(type.getComponent(SweetPotatoStatus.ENCHANTED)).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            playerEntity.incrementStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((PlayerEntity) user).getAbilities().creativeMode)
                PeelInserter.run(playerEntity);
        }

        Optional<Set<StatusEffectInstance>> statusEffectInstances = calcEffect(stack);
        statusEffectInstances.ifPresent(set -> set.forEach(user::applyStatusEffect));

        return stack;
    }

    protected static Optional<Set<StatusEffectInstance>> calcEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof EnchantedSweetPotatoItem)) return Optional.empty();
        CompoundTag compoundTag = stack.getOrCreateTag();
        Tag statusEffectsTag = compoundTag.get("statusEffects");
        if (NbtUtils.notListTag(statusEffectsTag)) return Optional.empty();
        ListTag statusEffects = (ListTag) statusEffectsTag;

        Set<StatusEffectInstance> effectInstances = new ObjectOpenHashSet<>();
        for (Tag oneStatusEffect: statusEffects) {
            if (NbtUtils.notCompoundTag(oneStatusEffect)) continue;
            CompoundTag compoundTag1 = (CompoundTag) oneStatusEffect;

            Tag tag = compoundTag1.get("id");
            if (NbtUtils.notStringTag(tag)) continue;
            StringTag id = (StringTag) tag;
            tag = compoundTag1.get("lvl");
            int lvl = NbtUtils.notIntTag(tag) ? 0 : ((IntTag) tag).getInt();
            tag = compoundTag1.get("duration");
            int duration = NbtUtils.notIntTag(tag) ? 0 : ((IntTag) tag).getInt();

            StatusEffect statusEffect = Registry.STATUS_EFFECT.get(new Identifier(id.asString()));
            effectInstances.add(new StatusEffectInstance(statusEffect, duration, lvl));
        }
        return Optional.of(effectInstances);
    }

    @Deprecated
    private static StatusEffectInstance calcEffect() {
        return new StatusEffectInstance(StatusEffects.LUCK, 200, 1);    // Luck II 10s
        // Remember, this is just a trial.
        // The REAL calculation should be added later.
        // teddyxlandlee, please decide the details with your group. 13 Jun 2020 night
    }

    @Override
    public SweetPotatoStatus getStatus() {
        return SweetPotatoStatus.ENCHANTED;
    }

    @Override
    public SweetPotatoType asType() {
        return this.sweetPotatoType;
    }
}
