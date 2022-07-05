package org.featurehouse.mcmod.spm.items;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.util.effects.StatusEffectInstances;
import org.featurehouse.mcmod.spm.util.inventory.PeelInserter;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoStatus;
import org.featurehouse.mcmod.spm.util.objsettings.sweetpotato.SweetPotatoType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EnchantedSweetPotatoItem extends EnchantedItem implements SweetPotatoProperties {
    @Override
    public boolean isEdible() {
        return true;
    }

    private final SweetPotatoType sweetPotatoType;

    public EnchantedSweetPotatoItem(Properties settings, SweetPotatoType type) {
        super(settings.food(Objects.requireNonNull(type.getComponent(SweetPotatoStatus.ENCHANTED)).asFoodComponent()));
        this.sweetPotatoType = type;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (user instanceof Player playerEntity) {
            playerEntity.awardStat(SPMMain.SWEET_POTATO_EATEN);
            if (!((Player) user).getAbilities().instabuild)
                PeelInserter.run(playerEntity);
        }

        if (!world.isClientSide) {
            Optional<List<MobEffectInstance>> statusEffectInstances = calcEffect(stack);
            statusEffectInstances.ifPresent(set -> set.forEach(statusEffectInstance -> {
                if (!statusEffectInstance.getEffect().isInstantenous()) {
                    user.addEffect(new MobEffectInstance(statusEffectInstance));
                } else {
                    statusEffectInstance.getEffect().applyInstantenousEffect(user, user, user, statusEffectInstance.getAmplifier(), 1.0D);
                }
            }));
        }

        return super.finishUsingItem(stack, world, user);
    }
    protected static Optional<List<MobEffectInstance>> calcEffect(ItemStack stack) {
        Item item = stack.getItem();
        if (!(item instanceof EnchantedSweetPotatoItem)) return Optional.empty();
        CompoundTag compoundNbtElement = stack.getOrCreateTag();
        if (!compoundNbtElement.contains("statusEffects", NbtType.LIST)) return Optional.empty();
        ListTag statusEffects = compoundNbtElement.getList("statusEffects", NbtType.COMPOUND);

        List<MobEffectInstance> effectInstances = new ObjectArrayList<>();
        for (Tag oneStatusEffect: statusEffects) {
            //if (NbtUtils.notCompoundTag(oneStatusEffect)) continue;
            if (oneStatusEffect.getId() != NbtType.COMPOUND) continue;
            CompoundTag compoundNbtElement1 = (CompoundTag) oneStatusEffect;
            MobEffectInstance statusEffectInstance = StatusEffectInstances.readNbt(compoundNbtElement1);
            if (statusEffectInstance == null) continue;
            effectInstances.add(statusEffectInstance);
        }
        return Optional.of(effectInstances);
    }

    @Deprecated
    @SuppressWarnings("unused")
    private static MobEffectInstance calcEffect() {
        return new MobEffectInstance(MobEffects.LUCK, 200, 1);    // Luck II 10s
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
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);

        CompoundTag root = stack.getOrCreateTag();
        BaseComponent mainTip = new TranslatableComponent("tooltip.sweet_potato.enchanted_sweet_potato.effects");
        tooltip.add(mainTip);

        short index = root.getShort("displayIndex");
        if (index == -1 || root.isEmpty()) {
            mainTip.append(new TranslatableComponent("effect.none").withStyle(ChatFormatting.ITALIC));
            return;
        }
        if (!root.contains("displayIndex", NbtType.NUMBER)) {
            mainTip.append(new TextComponent("???").withStyle(ChatFormatting.ITALIC));
            return;
        }

        Optional<List<MobEffectInstance>> statusEffectInstances = calcEffect(stack);
        if (statusEffectInstances.isEmpty()) {
            mainTip.append(new TextComponent("???").withStyle(ChatFormatting.ITALIC));
            return;
        }
        List<MobEffectInstance> sei = statusEffectInstances.get();
        MobEffectInstance toBeShown = (sei.size() <= index) ? null : sei.get(index);
        if (toBeShown != null) {
            mainTip.append(new TranslatableComponent(toBeShown.getDescriptionId()).withStyle(ChatFormatting.ITALIC));
            mainTip.append(" ").append(new TranslatableComponent("potion.potency." + toBeShown.getAmplifier()));
            mainTip.append(new TextComponent(" ...").withStyle(ChatFormatting.ITALIC));
        } else mainTip.append(new TextComponent("???").withStyle(ChatFormatting.ITALIC));
    }
}
