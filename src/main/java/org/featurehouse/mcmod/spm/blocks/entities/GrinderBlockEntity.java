package org.featurehouse.mcmod.spm.blocks.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.GrinderBlock;
import org.featurehouse.mcmod.spm.lib.block.entity.AbstractLockableContainerBlockEntity;
import org.featurehouse.mcmod.spm.screen.GrinderScreenHandler;
import org.featurehouse.mcmod.spm.util.BooleanStateManager;
import org.featurehouse.mcmod.spm.util.iprops.IntGrinderProperties;
import org.featurehouse.mcmod.spm.util.registries.GrindingUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GrinderBlockEntity extends AbstractLockableContainerBlockEntity implements SidedInventory {
    private int grindTime;
    private int grindTimeTotal;
    private double ingredientData;

    private byte absorbCooldown;
    private static final byte MAX_COOLDOWN = 5;

    public final IntGrinderProperties properties;
    //protected DefaultedList<ItemStack> inventory;

    protected BooleanStateManager stateHelper;

    public GrinderBlockEntity(BlockPos pos, BlockState state) {
        this(SPMMain.GRINDER_BLOCK_ENTITY_TYPE, pos, state);
    }

    protected GrinderBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state, 2);
        //this.inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);

        this.properties = new IntGrinderProperties() {
            @Override
            public int getGrindTime() {
                return grindTime;
            }

            @Override
            public int getGrindTimeTotal() {
                return grindTimeTotal;
            }

            @Override
            public double getIngredientData() {
                return ingredientData;
            }

            @Override
            public void setGrindTime(int grindTime) {
                GrinderBlockEntity.this.grindTime = grindTime;
            }

            @Override
            public void setGrindTimeTotal(int grindTimeTotal) {
                GrinderBlockEntity.this.grindTimeTotal = grindTimeTotal;
            }

            @Override
            public void setIngredientData(double ingredientData) {
                GrinderBlockEntity.this.ingredientData = ingredientData;
            }
        };

        this.absorbCooldown = -1;
        this.grindTime = -1;
        this.ingredientData = 0.0D;

        this.stateHelper = new BooleanStateManager(GrinderBlock.GRINDING) {
            public boolean shouldChange(boolean newOne) {
                assert GrinderBlockEntity.this.world != null;
                return GrinderBlockEntity.this.world.getBlockState(pos).get(property) != newOne;
            }

            @Override
            public void run() {
                assert GrinderBlockEntity.this.world != null;
                boolean b;
                if (this.shouldChange(b = GrinderBlockEntity.this.isGrinding()))
                    GrinderBlockEntity.this.world.setBlockState(
                            pos, GrinderBlockEntity.this.world.getBlockState(pos).with(property, b)
                    );
                GrinderBlockEntity.this.world.syncWorldEvent(1132119, GrinderBlockEntity.this.pos, 805);
            }
        };
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.grindTime = tag.getShort("GrindTime");
        this.grindTimeTotal = tag.getShort("GrindTimeTotal");
        //this.propertyDelegate.set(2 /*IngredientData*/, tag.getInt("IngredientData"));
        this.ingredientData = tag.getDouble("IngredientData");
        this.absorbCooldown = tag.getByte("absorbCooldown");

    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putShort("GrindTime", (short) grindTime);
        tag.putShort("GrindTimeTotal", (short) grindTimeTotal);
        //Inventories.writeNbt(tag, this.inventory);
        tag.putDouble("IngredientData", ingredientData);
        tag.putByte("absorbCooldown", absorbCooldown);

        //return tag;
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableTextContent("container.sweet_potato.grinding");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new GrinderScreenHandler(syncId, playerInventory, this.getWorld(), this, this.properties);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        super.setStack(slot, stack);
    }

    @Override
    public void tick(@NotNull World world, BlockPos pos, BlockState state) {
        boolean shallMarkDirty = false;

        if (!world.isClient) {
            // Grind Process
            if (this.grindTime >= this.grindTimeTotal && this.grindTimeTotal != 0 && this.canAcceptRecipeOutput()) { // 200+, 200, yesOutput
                // Output
                this.grindTime = -1;
                this.grindTimeTotal = /*this.getGrindTime()*/ 0;
                this.craftRecipe();
                shallMarkDirty = true;
            } else if (this.grindTime >= 0 && this.grindTime < this.grindTimeTotal && this.canAcceptRecipeOutput() /* && this.grindTime not enough*/) {
                // Grind
                ++this.grindTime;
                shallMarkDirty = true;
            } else if (!this.canAcceptRecipeOutput() && this.isGrinding()) {
                // RollBack & Stuck
                // Still grinding, but not continuing
                this.grindTime = net.minecraft.util.math.MathHelper.clamp(this.grindTime - 2, 0, grindTimeTotal);
                shallMarkDirty = true;
            }

            // Cooldown
            if (!this.shallCooldown()) {
                // New round tern
                if (GrindingUtils.grindable(this.inventory.get(0))) {
                    // Absorb
                    //this.ingredientData += INGREDIENT_DATA_MAP.getDouble(this.inventory.get(0).getItem());
                    this.ingredientData += GrindingUtils.ingredientDataMap().getDouble(this.inventory.get(0).getItem());
                    this.inventory.get(0).decrement(1);
                    this.absorbCooldown = MAX_COOLDOWN;
                    shallMarkDirty = true;
                }
            } else {
                --this.absorbCooldown;
            }

            // Operation of ingredientData check
            if (this.ingredientData >= 15 /*instead of 9*/ && this.grindTime < 0 /*not in grind process*/) {
                this.ingredientData -= 15;
                this.grindTime = 0;
                this.grindTimeTotal = this.getGrindTime();
                shallMarkDirty = true;
            }

            // Check State each 50tick
            if (world.getTime() % 50L == 8L)
                stateHelper.run();
        }

        if (shallMarkDirty) markDirty();
    }

    private boolean shallCooldown() {
        return this.absorbCooldown > 0;
    }

    //@HardCoded
    private void craftRecipe() {
        this.craftRecipe(new ItemStack(SPMMain.POTATO_POWDER));
    }

    //@HardCoded
    private void craftRecipe(ItemStack shallOutput) {
        ItemStack invOutput = this.inventory.get(1);

        if (!invOutput.isItemEqualIgnoreDamage(shallOutput))
            this.inventory.set(1, shallOutput.copy());
        else if (invOutput.getItem() == SPMMain.POTATO_POWDER)
            invOutput.increment(1);
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

    //@HardCoded
    protected boolean canAcceptRecipeOutput() {
        return canAcceptRecipeOutput(SPMMain.POTATO_POWDER);
    }

    /**
     * <p>In the past, Grinder should check the first slot
     * (input slot) if it is non-null.<sup>[More info needed]
     * </sup>&nbsp;Now we've found it is a nonsense.
     * <s>Actually, it's a feature.</s></p>
     *
     * <p>Besides, we use the {@link ItemConvertible} interface
     * instead of inline {@link SPMMain#POTATO_POWDER} because
     * {@link GrinderBlockEntity} can be changed upside-down
     * at any time. This method can be a great API.</p>
     *
     * @since beta-1.0.0
     */
    //@HardCoded
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

    //@NonMinecraftNorFabric
    private boolean isGrinding() {
        return this.grindTime > 0;
    }

    //@NonMinecraftNorFabric
    protected int getGrindTime() {
        return 200;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return side == Direction.DOWN ? new int[]{1} : new int[]{0};
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot == 1 && dir == Direction.DOWN;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        return slot == 0 && GrindingUtils.grindable(stack);
    }
}
