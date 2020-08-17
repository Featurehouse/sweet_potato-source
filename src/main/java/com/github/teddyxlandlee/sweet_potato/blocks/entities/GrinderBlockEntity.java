package com.github.teddyxlandlee.sweet_potato.blocks.entities;

import com.github.teddyxlandlee.annotation.HardCoded;
import com.github.teddyxlandlee.annotation.NonMinecraftNorFabric;
import com.github.teddyxlandlee.sweet_potato.ExampleMod;
import com.github.teddyxlandlee.sweet_potato.screen.GrinderScreenHandler;
import com.github.teddyxlandlee.sweet_potato.util.Util;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.Iterator;

public class GrinderBlockEntity extends LockableContainerBlockEntity implements Tickable {
    private int grindTime;
    private int grindTimeTotal;
    private int ingredientData;

    private byte absorbCooldown;
    private static final byte MAX_COOLDOWN = 5;
    private boolean addTimeWhenFinished = true;

    public static final Object2IntOpenHashMap<ItemConvertible> INGREDIENT_DATA_MAP = new Object2IntOpenHashMap<>();

    public PropertyDelegate propertyDelegate;
    protected DefaultedList<ItemStack> inventory;

    //private final Object2IntOpenHashMap<Identifier> recipesUsed;
    //protected final RecipeType<GrinderRecipe> recipeType;

    public GrinderBlockEntity() {
        this(ExampleMod.GRINDER_BLOCK_ENTITY_TYPE);
    }

    protected GrinderBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
        this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
        this.propertyDelegate = new PropertyDelegate() {
            @Override
            public int get(int index) {
                switch (index) {
                    case 0:
                        return GrinderBlockEntity.this.grindTime;
                    case 1:
                        return GrinderBlockEntity.this.grindTimeTotal;
                    case 2:
                        return GrinderBlockEntity.this.ingredientData;
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
                        GrinderBlockEntity.this.ingredientData = value;
                }
            }

            @Override
            public int size() {
                return 3;
            }
        };
        //this.recipesUsed = new Object2IntOpenHashMap<>();
        //this.recipeType = recipeType;

        Util.registerGrindableItems(1, ExampleMod.RAW_SWEET_POTATOES);
        Util.registerGrindableItem(3, ExampleMod.ENCHANTED_SWEET_POTATO);
        this.absorbCooldown = -1;
    }


    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.grinding");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        //return new GrinderScreenHandler(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, syncId, playerInventory, this.propertyDelegate);
        return new GrinderScreenHandler(ExampleMod.GRINDER_SCREEN_HANDLER_TYPE, syncId, playerInventory, this, this.propertyDelegate);
    }

    @Override
    public int size() {
        return this.inventory.size();
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

    @Override
    public void clear() {
        this.inventory.clear();
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

    @Override
    public void tick() {
        boolean canMarkDirty = false;
        assert this.world != null;
        if (!this.world.isClient) {
            if (this.isGrinding()) {
                ++grindTime;
                if (this.grindTime == this.grindTimeTotal) {
                    this.grindTime = 0;
                    this.grindTimeTotal = this.getGrindTime();
                    this.craftRecipe();
                    this.addTimeWhenFinished = false;
                }
            }

            if ((this.ingredientData >= 9 && !this.isGrinding()) && this.canAcceptRecipeOutput()) {
                // Can grind or is grinding
                this.ingredientData -= 9;
                if (this.addTimeWhenFinished){
                    ++this.grindTime;
                }
            }

            if (!this.addTimeWhenFinished)
                this.addTimeWhenFinished = true;

            if (shallCooldown())
                --absorbCooldown;
            else {
                if (Util.grindable(this.inventory.get(0))) {
                    canMarkDirty = true;
                    this.inventory.get(0).decrement(1);
                    this.ingredientData += INGREDIENT_DATA_MAP.getInt(this.inventory.get(0).getItem());
                    this.absorbCooldown = MAX_COOLDOWN;
                }
            }

            if (canMarkDirty)
                markDirty();
        }
    }

    private boolean shallCooldown() {
        return absorbCooldown > 0;
    }

    @Deprecated
    private boolean canContinueGrinding(ItemStack input) {
        if (!(input.getItem().isIn(ExampleMod.RAW_SWEET_POTATOES)) && input.getItem() != ExampleMod.ENCHANTED_SWEET_POTATO)
            //throw new UnsupportedOperationException("[com.github.teddyxlandlee.sweet_potato.blocks.entities.GrinderBlockEntity]
            // A programmer tries to force non-grindable thing be grinded, which is unsupported");
            return false;
        if (input.getItem().isIn(ExampleMod.RAW_SWEET_POTATOES))
            return input.getCount() >= 9;
        else
            return input.getCount() >= 3;
    }

    @HardCoded
    private void craftRecipe() {
        if (this.canAcceptRecipeOutput()) {
            //ItemStack input = this.inventory.get(0);
            ItemStack SHALL_OUTPUT = new ItemStack(ExampleMod.POTATO_POWDER);
            ItemStack invOutput = this.inventory.get(1);

            if (invOutput.isEmpty())
                this.inventory.set(1, SHALL_OUTPUT.copy());
            else if (invOutput.getItem() == ExampleMod.POTATO_POWDER)
                invOutput.increment(1);

            //input.decrement(input.getItem().isIn(ExampleMod.RAW_SWEET_POTATOES) ? 9 : 3);
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
        if (!this.inventory.get(0).isEmpty()) {
            ItemStack shallBeOutput = new ItemStack(ExampleMod.POTATO_POWDER);
            ItemStack outInv = this.inventory.get(1);
            if (outInv.isEmpty())
                return true;
            if (!outInv.isItemEqualIgnoreDamage(shallBeOutput))
                return false;
            if (outInv.getCount() < this.getMaxCountPerStack() && outInv.getCount() < outInv.getMaxCount())
                return true;
            return outInv.getCount() < shallBeOutput.getMaxCount();
        } else {
            return false;
        }
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
        this.grindTime = tag.getShort("GrindTime");
        this.grindTimeTotal = tag.getShort("GrindTimeTotal");
        this.ingredientData = tag.getInt("IngredientData");
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
        Inventories.toTag(tag, this.inventory);
        tag.putInt("IngredientData", ingredientData);
        tag.putByte("absorbCooldown", absorbCooldown);

        //CompoundTag recipeUsed = new CompoundTag();
        //this.recipesUsed.forEach(((identifier, integer) -> recipeUsed.putInt(identifier.toString(), integer)));
        //tag.put("RecipesUsed", recipeUsed);
        return tag;
    }

    @NonMinecraftNorFabric
    private boolean isGrinding() {
        return this.grindTime > 0;
    }

    @NonMinecraftNorFabric
    protected int getGrindTime() {
        return 200;
    }

    @Deprecated
    public int getIngredientData() {
        return this.propertyDelegate.get(2);
    }
}
