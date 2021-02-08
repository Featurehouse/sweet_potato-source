package io.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import io.featurehouse.annotation.HardCoded;
import io.featurehouse.annotation.NonMinecraftNorFabric;
import io.featurehouse.spm.SPMMain;
import io.featurehouse.spm.blocks.GrinderBlock;
import io.featurehouse.spm.screen.GrinderScreenHandler;
import io.featurehouse.spm.util.Util;
import io.featurehouse.spm.util.properties.grinder.IntGrinderProperties;
import io.featurehouse.spm.util.properties.state.BooleanStateManager;
import it.unimi.dsi.fastutil.objects.Object2DoubleOpenHashMap;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

/* (not javadoc)
 * <h2>Why canceling implementing ExtendedScreenHandlerFactory?</h2>
 * <p>Because it is already implemented in AbstractLockableContainerBlockEntity!</p>
 */
public class GrinderBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory {
    private int grindTime;
    private int grindTimeTotal;
    private double ingredientData;

    private byte absorbCooldown;
    private static final byte MAX_COOLDOWN = 5;

    //public static final Object2IntOpenHashMap<ItemConvertible> INGREDIENT_DATA_MAP = new Object2IntOpenHashMap<>();
    public static final Object2DoubleOpenHashMap<ItemConvertible> INGREDIENT_DATA_MAP = new Object2DoubleOpenHashMap<>();

    public final IntGrinderProperties properties;
    //protected DefaultedList<ItemStack> inventory;

    protected BooleanStateManager stateHelper;

    public GrinderBlockEntity() {
        this(SPMMain.GRINDER_BLOCK_ENTITY_TYPE);
    }

    protected GrinderBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType, 2);
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
                            GrinderBlockEntity.this.pos,
                            GrinderBlockEntity.this.world.getBlockState(
                                    GrinderBlockEntity.this.pos).with(
                                            property, b));
                GrinderBlockEntity.this.world.syncWorldEvent(1132119, GrinderBlockEntity.this.pos, 805);
            }
        };
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        //this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        //Inventories.fromTag(tag, this.inventory);
        this.grindTime = tag.getShort("GrindTime");
        this.grindTimeTotal = tag.getShort("GrindTimeTotal");
        //this.propertyDelegate.set(2 /*IngredientData*/, tag.getInt("IngredientData"));
        this.ingredientData = tag.getDouble("IngredientData");
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
        tag.putDouble("IngredientData", ingredientData);
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
        return new GrinderScreenHandler(syncId, playerInventory, this.getWorld(), this, this.properties);
    }

    /**
     * @deprecated because:
     * - Line 8: grindTime should NOT be set to zero.
     * This method could be typed before 20th century, hhh
     * @since 2020/10/24
     */
    @Deprecated //@Override
    public void deprecatedSetStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.getStack(slot);
        boolean equal = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack())
            stack.setCount(this.getMaxCountPerStack());

        if (slot == 0 && !equal) {
            //this.grindTime = 0;
            this.markDirty();
        }
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        super.setStack(slot, stack);
    }

    @Override
    public void tick() {
        assert this.world != null;
        boolean shallMarkDirty = false;

        if (!this.world.isClient) {
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
                if (Util.grindable(this.inventory.get(0))) {
                    // Absorb
                    this.ingredientData += INGREDIENT_DATA_MAP.getDouble(this.inventory.get(0).getItem());
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
            if (this.world.getTime() % 50L == 8L)
                stateHelper.run();
        }

        if (shallMarkDirty) markDirty();
    }

    private boolean shallCooldown() {
        return this.absorbCooldown > 0;
    }

    @Deprecated
    protected boolean canContinueGrinding(ItemStack input) {
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

    /*
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        GrinderProperties grinderProperties = new GrinderProperties(this.grindTime, this.grindTimeTotal, this.ingredientData);
        grinderProperties.fillPacketByteBuf(packetByteBuf);
    }*/

    /**
     * @deprecated method's super interface is {@code ExtendedScreenHandlerFactory}, while the
     * screen handler has just changed into simple.
     * @see ExtendedScreenHandlerFactory
     * @see net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
     * @see net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry.ExtendedClientHandlerFactory
     * @see net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType
     */
    @Deprecated // @Override
    public void writeScreenOpeningData(net.minecraft.server.network.ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
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
        if (slot == 1)
            return false;
        else if (slot == 0)
            return Util.grindable(stack);
        return false;
    }

    /*@Deprecated
    static class StateHelper extends BooleanStateManager {
        GrinderBlockEntity blockEntity;
        public StateHelper(GrinderBlockEntity blockEntity) {
            super(blockEntity);
            this.blockEntity = blockEntity;
        }

        @Override
        public void run() {
            assert this.world != null;
            boolean b;
            if (shouldChange(b = blockEntity.isGrinding()))
                world.setBlockState(pos, world.getBlockState(pos).with(GrinderBlock.GRINDING, b));
        }
    }*/
}
