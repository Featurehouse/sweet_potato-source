package io.github.teddyxlandlee.sweet_potato.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.github.teddyxlandlee.annotation.HardCoded;
import io.github.teddyxlandlee.annotation.NeedToConfirm;
import io.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import io.github.teddyxlandlee.debug.Debug;
import io.github.teddyxlandlee.debug.PartType;
import io.github.teddyxlandlee.sweet_potato.SPMMain;
import io.github.teddyxlandlee.sweet_potato.screen.GrinderScreenHandler;
import io.github.teddyxlandlee.sweet_potato.util.FloatIntegerizer;
import io.github.teddyxlandlee.sweet_potato.util.Util;
import io.github.teddyxlandlee.sweet_potato.util.network.GrinderProperties;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Iterator;

/**
 * <h2>Why canceling implementing ExtendedScreenHandlerFactory?</h2>
 * <p>Because it is already implemented in AbstractLockableContainerBlockEntity!</p>
 */
public class GrinderBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, ExtendedScreenHandlerFactory {
    private int grindTime;
    private int grindTimeTotal;
    private float ingredientData;

    private byte absorbCooldown;
    private static final byte MAX_COOLDOWN = 5;
    @Deprecated
    private boolean addTimeWhenFinished = true;

    //public static final Object2IntOpenHashMap<ItemConvertible> INGREDIENT_DATA_MAP = new Object2IntOpenHashMap<>();
    public static final Object2FloatOpenHashMap<ItemConvertible> INGREDIENT_DATA_MAP = new Object2FloatOpenHashMap<>();

    @Deprecated
    public PropertyDelegate propertyDelegate;
    //protected DefaultedList<ItemStack> inventory;

    public GrinderBlockEntity() {
        this(SPMMain.GRINDER_BLOCK_ENTITY_TYPE);
    }

    protected GrinderBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType, 2);
        //this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return GrinderBlockEntity.this.grindTime;
                    case 1:
                        return GrinderBlockEntity.this.grindTimeTotal;
                    case 2:
                        return FloatIntegerizer.fromFloat(GrinderBlockEntity.this.ingredientData);
                    default:
                        return 0;
                }
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0:
                        GrinderBlockEntity.this.grindTime = value;
                    case 1:
                        GrinderBlockEntity.this.grindTimeTotal = value;
                    case 2:
                        GrinderBlockEntity.this.ingredientData = FloatIntegerizer.toFloat(value);
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
        this.absorbCooldown = -1;
        this.grindTime = -1;
        this.ingredientData = 0.0f;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        //this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        //Inventories.fromTag(tag, this.inventory);
        this.grindTime = tag.getShort("GrindTime");
        this.grindTimeTotal = tag.getShort("GrindTimeTotal");
        //this.propertyDelegate.set(2 /*IngredientData*/, tag.getInt("IngredientData"));
        this.ingredientData = tag.getFloat("IngredientData");
        this.absorbCooldown = tag.getByte("absorbCooldown");

        //CompoundTag recipeUsed = new CompoundTag();
        //for (String nextKey : recipeUsed.getKeys()) {
        //    this.recipesUsed.put(new Identifier(nextKey), recipeUsed.getInt(nextKey));
        //}
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("GrindTime", (short) grindTime);
        tag.putShort("GrindTimeTotal", (short) grindTimeTotal);
        //Inventories.toTag(tag, this.inventory);
        tag.putFloat("IngredientData", ingredientData);
        tag.putByte("absorbCooldown", absorbCooldown);

        //CompoundTag recipeUsed = new CompoundTag();
        //this.recipesUsed.forEach(((identifier, integer) -> recipeUsed.putInt(identifier.toString(), integer)));
        //tag.put("RecipesUsed", recipeUsed);
        return tag;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.grinding");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        Debug.debug(this.getClass(), PartType.METHOD, "createScreenHandler", "Creating Screen Handler");
        return new GrinderScreenHandler(SPMMain.GRINDER_SCREEN_HANDLER_TYPE, syncId, playerInventory, this, new GrinderProperties(this.grindTime, this.grindTimeTotal, this.ingredientData));
    }

    @Override
    public boolean isEmpty() {
        Iterator<?> iterator = this.inventory.iterator();

        ItemStack itemStack;
        do {
            if (!iterator.hasNext())
                return true;
            itemStack = (ItemStack) iterator.next();
        } while (itemStack.isEmpty());

        return false;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.getStack(slot);
        boolean equal = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack())
            stack.setCount(this.getMaxCountPerStack());

        if (slot == 0 && !equal) {
            this.grindTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        assert this.world != null;  // Stupid IDEA, let you go
        return this.world.getBlockEntity(this.pos) == this && player.squaredDistanceTo(
                (double) this.pos.getX() + 0.5D,
                (double) this.pos.getY() + 0.5D,
                (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Deprecated
    public void deprecatedTick() {
        boolean shallMarkDirty = false;
        assert this.world != null;  // stupid IDEA
        if (!this.world.isClient) {
            if (!(!this.isGrinding() || this.inventory.get(0).isEmpty())) {
                /*if (!this.isGrinding()) {
                    shallMarkDirty = true;
                    ++this.grindTime;
                    if (this.grindTime == this.grindTimeTotal) {    // Done once
                        this.grindTime = 0;
                        this.grindTimeTotal = this.getGrindTime();
                        this.craftRecipe(recipe);
                    }
                }*/
                if (this.canAcceptRecipeOutput() && this.canContinueGrinding(this.inventory.get(0))) {
                    ++this.grindTime;
                    if (this.grindTime == this.grindTimeTotal) {
                        // once done
                        this.grindTime = 0;
                        this.grindTimeTotal = this.getGrindTime();
                        this.craftRecipe();
                        shallMarkDirty = true;
                    }
                } else {
                    this.grindTime = 0;
                }
            }
            /*else {
                if (!grinding && this.grindTime > 0)
                    this.grindTime = MathHelper.clamp(this.grindTime - 2, 0, this.grindTimeTotal);
            }*/
        }

        if (shallMarkDirty)
            markDirty();
    }

    @Deprecated
    public void deprecatedTick$2() {
        boolean canMarkDirty = false;
        assert this.world != null;
        if (!this.world.isClient) {
            //if (this.inventory.get(0).isEmpty())
            if (!this.canContinueGrinding(this.inventory.get(0)))
                // "QuickBack"
                if (this.grindTime > 0)
                    this.grindTime = MathHelper.clamp(this.grindTime - 2, 0, this.grindTimeTotal);
            else {
                canMarkDirty = true;
                //Recipe<?> recipe = this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);
                if (this.canAcceptRecipeOutput()) {
                    ++this.grindTime;
                    if (this.grindTime == this.grindTimeTotal) {
                        // Should finish a grinding process
                        this.grindTime = 0;
                        this.grindTimeTotal = this.getGrindTime();  // Always 200 now.
                        this.craftRecipe();
                    }
                } else {
                    this.grindTime = 0;
                }
            }

            if (canMarkDirty)
                this.markDirty();
        }
    }

    @Deprecated
    public void deprecatedTick$3() {
        boolean canMarkDirty = false;
        assert this.world != null;
        if (!this.world.isClient) {
            if (this.isGrinding()) {
                if (this.grindTime == this.grindTimeTotal) {
                    this.grindTime = 0;
                    this.grindTimeTotal = this.getGrindTime();
                    this.craftRecipe();
                    this.addTimeWhenFinished = false;
                    canMarkDirty = true;
                } else
                    ++grindTime;
            }

            if ((this.ingredientData >= 9 && !this.isGrinding()) && this.canAcceptRecipeOutput()) {
                // Can grind or is grinding
                this.ingredientData -= 9;
                canMarkDirty = true;
            }

            if (!this.addTimeWhenFinished)
                this.addTimeWhenFinished = true;

            if (shallCooldown())
                --absorbCooldown;
            else {
                if (Util.grindable(this.inventory.get(0))) {
                    canMarkDirty = true;
                    final Item ingredient = this.inventory.get(0).getItem();
                    this.inventory.get(0).decrement(1);
                    this.ingredientData += INGREDIENT_DATA_MAP.getFloat(ingredient);
                    this.absorbCooldown = MAX_COOLDOWN;
                }
            }

            if (canMarkDirty)
                markDirty();
        }
    }

    @Override
    @NeedToConfirm
    public void tick() {
        assert this.world != null;
        boolean shallMarkDirty = false;
        if (!world.isClient) {
            // Grind Process
            if (this.grindTime == this.grindTimeTotal && this.grindTimeTotal != 0 && this.canAcceptRecipeOutput()) {
                // Shall End Grinding
                this.grindTime = -1;
                this.grindTimeTotal = this.getGrindTime();
                this.craftRecipe();
                shallMarkDirty = true;
            } else if (!this.canAcceptRecipeOutput() && this.isGrinding()) {
                this.grindTime = MathHelper.clamp(grindTime - 2, 0, grindTimeTotal);
                shallMarkDirty = true;
            } else if (this.grindTime >= 0 && this.canAcceptRecipeOutput()) {
                ++this.grindTime;
                shallMarkDirty = true;
            }

            // Cooldown
            if (shallCooldown())
                --this.absorbCooldown;
            else if (Util.grindable(this.inventory.get(0))) {
                shallMarkDirty = true;
                final Item ingredient = this.inventory.get(0).getItem();
                this.inventory.get(0).decrement(1);
                this.ingredientData += INGREDIENT_DATA_MAP.getFloat(ingredient);
                this.absorbCooldown = MAX_COOLDOWN - 1;
                this.grindTime = 0;
            }

            // IngredientData Checking
            if (this.ingredientData >= 9 && this.grindTime < 0 /*Usually -1*/) {
                this.ingredientData -= 9;
                this.grindTime = 0;
                shallMarkDirty = true;
            }
        }

        if (shallMarkDirty)
            markDirty();
    }

    private boolean shallCooldown() {
        return this.absorbCooldown > 0;
    }

    @Deprecated
    private boolean canContinueGrinding(ItemStack input) {
        if (!(input.getItem().isIn(SPMMain.RAW_SWEET_POTATOES)) && !(input.getItem().isIn(SPMMain.ENCHANTED_SWEET_POTATOES)))
            //throw new UnsupportedOperationException("[GrinderBlockEntity]
            // A programmer tries to force non-grindable thing be grinded, which is unsupported");
            return false;
        if (input.getItem().isIn(SPMMain.RAW_SWEET_POTATOES))
            return input.getCount() >= 9;
        else
            return input.getCount() >= 3;
    }

    @HardCoded
    private void craftRecipe() {
        this.craftRecipe(false);
    }

    @HardCoded
    private void craftRecipe(boolean checkIfCanOutput) {
        this.craftRecipe(new ItemStack(SPMMain.POTATO_POWDER), checkIfCanOutput);
    }

    @HardCoded
    private void craftRecipe(ItemStack shallOutput, boolean checkIfCanOutput) {
        if (this.canAcceptRecipeOutput(shallOutput.getItem()) || !checkIfCanOutput) {
            //ItemStack input = this.inventory.get(0);
            ItemStack invOutput = this.inventory.get(1);

            if (!invOutput.isItemEqualIgnoreDamage(shallOutput))
                this.inventory.set(1, shallOutput.copy());
            else if (invOutput.getItem() == SPMMain.POTATO_POWDER)
                invOutput.increment(1);

            //input.decrement(input.getItem().isIn(SPMMain.RAW_SWEET_POTATOES) ? 9 : 3);
        }
    }

    @Deprecated
    protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
        if (!this.inventory.get(0).isEmpty() && recipe != null) {
            ItemStack output = recipe.getOutput();
            if (output.isEmpty())
                return false;
            else {
                ItemStack outInv = this.inventory.get(1);
                if (outInv.isEmpty())
                    return true;
                if (!outInv.isItemEqualIgnoreDamage(output))
                    return false;
                if (outInv.getCount() < this.getMaxCountPerStack() && outInv.getCount() < outInv.getMaxCount())
                    return true;
                return outInv.getCount() < output.getMaxCount();
            }
        }
        return false;
    }

    @HardCoded
    protected boolean canAcceptRecipeOutput() {
        return canAcceptRecipeOutput(SPMMain.POTATO_POWDER);
    }

    /**
     * <p>In the past, Grinder should check the first slot
     * (input slot) if it is non-null.<sup>[More info needed]
     * </sup>&nbsp;Now we've found it is a nonsense.
     * <s>Actually, it's a feature.</s></p>
     *
     * @since beta-1.0.0
     */
    @HardCoded
    protected boolean canAcceptRecipeOutput(ItemConvertible item) {
        //ItemStack shallBeOutput = new ItemStack(SPMMain.POTATO_POWDER);
        ItemStack outInv = this.inventory.get(1);
        if (outInv.isEmpty())
            return true;
        if (!outInv.isItemEqualIgnoreDamage(new ItemStack(item.asItem())))
            return false;
        if (outInv.getCount() < this.getMaxCountPerStack() && outInv.getCount() < outInv.getMaxCount())
            return true;
        return outInv.getCount() < item.asItem().getMaxCount();
    }

    @NonMinecraftNorFabric
    private boolean isGrinding() {
        return this.grindTime > 0;
    }

    @NonMinecraftNorFabric
    protected int getGrindTime() {
        return 200;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        GrinderProperties grinderProperties = new GrinderProperties(this.grindTime, this.grindTimeTotal, this.ingredientData);
        grinderProperties.fillPacketByteBuf(packetByteBuf);
    }
}
