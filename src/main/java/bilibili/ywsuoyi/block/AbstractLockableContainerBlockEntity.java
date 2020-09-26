//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package bilibili.ywsuoyi.block;

import io.github.teddyxlandlee.annotation.DeprecatedFrom;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.collection.DefaultedList;

public abstract class AbstractLockableContainerBlockEntity extends LockableContainerBlockEntity {
    protected /*private*/ DefaultedList<ItemStack> inventory;
    private final int size;

    public AbstractLockableContainerBlockEntity(BlockEntityType<?> type, int size) {
        super(type);
        this.inventory = DefaultedList.ofSize(size, ItemStack.EMPTY);
        this.size = size;
    }

    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        Inventories.toTag(tag, this.inventory);

        return tag;
    }

    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);

    }

    public int size() {
        return this.size;
    }

    @Deprecated @DeprecatedFrom(LootableContainerBlockEntity.class)
    public DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Deprecated @DeprecatedFrom(LootableContainerBlockEntity.class)
    public void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }
}
