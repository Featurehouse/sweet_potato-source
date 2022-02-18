package org.featurehouse.mcmod.spm.advancement;

import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.mixin.acc.AdvancementTaskAccessor;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.criterion.ConsumeItemCriterion;
import net.minecraft.item.Item;
import net.minecraft.predicate.entity.EntityPredicate;

import java.util.List;

public final class BalancedDietHelper {
    private BalancedDietHelper() {}

    public static void setupCriteria(Advancement.Builder task) {
        List<Item> itemList = SPMMain.ALL_SWEET_POTATOES.stream().toList(); // todo temporary solution before 2.0
        int itemListSize = itemList.size();

        AdvancementTaskAccessor taskAccessor = (AdvancementTaskAccessor) task;
        String[][] requirementsOld = taskAccessor.getRequirements();
        String[][] requirementsNew = new String[requirementsOld.length + itemListSize][];
        System.arraycopy(requirementsOld, 0, requirementsNew, itemListSize, requirementsOld.length);

        for (int i = 0; i < itemListSize; ++i) {
            String reqId = "sweet_potato:balanced_diet__food$" + itemList.get(i);
            task.criterion(reqId,
                    new ConsumeItemCriterion.Conditions(EntityPredicate.Extended.EMPTY,
                            new SimpleItemPredicate(itemList.get(i))));
            requirementsNew[i] = new String[] {reqId};
        }
        taskAccessor.setRequirements(requirementsNew);
    }
}
