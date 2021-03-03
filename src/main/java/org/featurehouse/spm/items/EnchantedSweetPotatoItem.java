package org.featurehouse.spm.items;

import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.SweetPotatoStatus;
import org.featurehouse.spm.SweetPotatoType;
import org.featurehouse.spm.util.NbtUtils;
import org.featurehouse.spm.util.inventory.PeelInserter;
import org.featurehouse.spm.util.effects.StatusEffectInstances;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            playerEntity.incrementStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((PlayerEntity) user).abilities.creativeMode)
                PeelInserter.run(playerEntity);
        }

        if (!world.isClient) {
            Optional<List<StatusEffectInstance>> statusEffectInstances = calcEffect(stack);
            statusEffectInstances.ifPresent(set -> set.forEach(statusEffectInstance -> {
                if (!statusEffectInstance.getEffectType().isInstant()) {
                    user.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                } else {
                    statusEffectInstance.getEffectType().applyInstantEffect(user, user, user, statusEffectInstance.getAmplifier(), 1.0D);
                }
            }));
        }

        return super.finishUsing(stack, world, user);
    }
    protected static Optional<List<StatusEffectInstance>> calcEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof EnchantedSweetPotatoItem)) return Optional.empty();
        CompoundTag compoundTag = stack.getOrCreateTag();
        if (!compoundTag.contains("statusEffects", NbtType.LIST)) return Optional.empty();
        ListTag statusEffects = compoundTag.getList("statusEffects", NbtType.COMPOUND);

        List<StatusEffectInstance> effectInstances = new ObjectArrayList<>();
        for (Tag oneStatusEffect: statusEffects) {
            if (NbtUtils.notCompoundTag(oneStatusEffect)) continue;
            CompoundTag compoundTag1 = (CompoundTag) oneStatusEffect;
            StatusEffectInstance statusEffectInstance = StatusEffectInstances.readNbt(compoundTag1);
            if (statusEffectInstance == null) continue;
            effectInstances.add(statusEffectInstance);
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

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        CompoundTag root = stack.getOrCreateTag();
        BaseText mainTip = new TranslatableText("tooltip.sweet_potato.enchanted_sweet_potato.effects");
        tooltip.add(mainTip);

        short index = root.getShort("displayIndex");
        if (index == -1 || root.isEmpty()) {
            mainTip.append(new TranslatableText("effect.none").formatted(Formatting.ITALIC));
            return;
        }
        if (!root.contains("displayIndex", NbtType.NUMBER)) {
            mainTip.append(new LiteralText("???").formatted(Formatting.ITALIC));
            return;
        }

        Optional<List<StatusEffectInstance>> statusEffectInstances = calcEffect(stack);
        if (!statusEffectInstances.isPresent()) {
            mainTip.append(new LiteralText("???").formatted(Formatting.ITALIC));
            return;
        }
        List<StatusEffectInstance> sei = statusEffectInstances.get();
        StatusEffectInstance toBeShown = (sei.size() <= index) ? null : sei.get(index);
        if (toBeShown != null) {
            mainTip.append(new TranslatableText(toBeShown.getTranslationKey()).formatted(Formatting.ITALIC));
            mainTip.append(" ").append(new TranslatableText("potion.potency." + toBeShown.getAmplifier()));
            mainTip.append(new LiteralText(" ...").formatted(Formatting.ITALIC));
        } else mainTip.append(new LiteralText("???").formatted(Formatting.ITALIC));
    }
}
