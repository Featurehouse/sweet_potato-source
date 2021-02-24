package io.featurehouse.spm.resource;

import io.featurehouse.spm.resource.magicalenchantment.MagicalEnchantmentLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public final class SPMDataPackFormats {
    private static final ResourceManagerHelper DATA_PACK_HELPER = ResourceManagerHelper.get(ResourceType.SERVER_DATA);

    public static void init() {
        DATA_PACK_HELPER.registerReloadListener(new MagicalEnchantmentLoader());
    }
}
