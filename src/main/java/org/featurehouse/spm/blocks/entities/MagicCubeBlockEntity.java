package org.featurehouse.spm.blocks.entities;

import bilibili.ywsuoyi.block.AbstractLockableContainerBlockEntity;
import org.featurehouse.spm.SPMMain;
import org.featurehouse.spm.blocks.MagicCubeBlock;
import org.featurehouse.spm.items.RawSweetPotatoBlockItem;
import org.featurehouse.spm.resource.magicalenchantment.WeightedStatusEffect;
import org.featurehouse.spm.screen.MagicCubeScreenHandler;
import org.featurehouse.spm.util.effects.StatusEffectInstances;
import org.featurehouse.spm.util.properties.magiccube.IntMagicCubeProperties;
import org.featurehouse.spm.util.properties.state.BooleanStateManager;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import static net.minecraft.block.Blocks.SOUL_FIRE;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements Tickable, SidedInventory, ExtendedScreenHandlerFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    //protected StateHelperV1 stateHelper;
    private static final int[] TOP_SLOTS = new int[] { 0, 1, 2 };
    private static final int[] BOTTOM_SLOTS = new int[] { 3, 4, 5 };
    private static final int[] SIDE_SLOTS = new int[] { 6, 7 };

    private boolean activationCache = false;
    private byte fireCountCache = 0;
    private final Random random = this.world != null ? this.world.random : new Random();

    protected BooleanStateManager stateHelper;
    protected short mainFuelTime;
    protected short viceFuelTime;

    protected IntMagicCubeProperties propertyDelegate;

    public MagicCubeBlockEntity() {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, 8);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, int size) {
        super(type, size);
        this.propertyDelegate = new IntMagicCubeProperties() {
            @Override
            public short getMainFuelTime() {
                return mainFuelTime;
            }

            @Override
            public short getViceFuelTime() {
                return viceFuelTime;
            }

            @Override
            public void setMainFuelTime(short time) {
                mainFuelTime = time;
            }

            @Override
            public void setViceFuelTime(short time) {
                viceFuelTime = time;
            }
        };
        this.stateHelper = new BooleanStateManager(MagicCubeBlock.ACTIVATED) {
            public boolean shouldChange(boolean newOne) {
                assert MagicCubeBlockEntity.this.world != null;
                return MagicCubeBlockEntity.this.world.getBlockState(pos).get(property) != newOne;
            }

            @Override
            public void run() {
                assert MagicCubeBlockEntity.this.world != null;
                boolean b;
                byte fc = this.fireCount();
                MagicCubeBlockEntity.this.fireCountCache = this.fireCount();
                if (this.shouldChange(b = fc > 0)) {
                    MagicCubeBlockEntity.this.world.setBlockState(
                            MagicCubeBlockEntity.this.pos,
                            MagicCubeBlockEntity.this.world.getBlockState(
                                    MagicCubeBlockEntity.this.pos
                            ).with(property, b)
                    );
                    if (!b) {
                        MagicCubeBlockEntity.this.mainFuelTime = -1;
                        MagicCubeBlockEntity.this.viceFuelTime = 0;
                        MagicCubeBlockEntity.this.world.playSound(null, MagicCubeBlockEntity.this.pos,
                                SPMMain.MAGIC_CUBE_DEACTIVATE, SoundCategory.BLOCKS,
                                1.0F, 1.0F);
                    } else {
                        MagicCubeBlockEntity.this.world.playSound(null, MagicCubeBlockEntity.this.pos,
                                SPMMain.MAGIC_CUBE_ACTIVATE, SoundCategory.BLOCKS,
                                1.0F, 1.0F);
                    }
                }
                MagicCubeBlockEntity.this.activationCache = b;
            }

            byte fireCount() {
                BlockPos[] blockPosList = calcPos(MagicCubeBlockEntity.this.getPos());

                assert MagicCubeBlockEntity.this.world != null;
                byte b = 0;
                for (BlockPos eachPos: blockPosList) {
                    if (MagicCubeBlockEntity.this.world.getBlockState(eachPos).getBlock() == SOUL_FIRE)
                        ++b;
                }
                return b;
            }
        };
        this.mainFuelTime = -1;
        this.viceFuelTime = 0;
    }

    public boolean isProcessing() {
        return mainFuelTime >= 0;
    }

    public boolean shouldOutput() {
        return mainFuelTime == 0;
    }

    public boolean withViceFuel() {
        return viceFuelTime > 0;
    }

    public boolean shouldUpdateViceFuel() {
        return viceFuelTime == 0;
    }

    @Override
    public void tick() {
        assert this.world != null;
        boolean shallMarkDirty = false;

        if (!world.isClient) {
            if (world.getTime() % 10L == 5L) {
                stateHelper.run();
            }
            if (!activationCache) return;

            /*-* * PROPERTIES * *-*/
            if (!this.isProcessing()) {
                // Check inventory
                if (this.inventory.get(6).getItem() == SPMMain.PEEL) {
                    boolean bl = false;
                    for (int i = 0; i < 3; ++i) {
                        if (this.inventory.get(i).getItem().isIn(SPMMain.RAW_SWEET_POTATOES)) {
                            this.mainFuelTime = 200;
                            bl = true;
                            break;
                        }
                    }
                    if (bl) {
                        // DECREMENT PEEL, START PROGRESS, MARK DIRTY.
                        this.inventory.get(6).decrement(1);
                        shallMarkDirty = true;
                    }
                }
            }
            // CHECK VICE FUEL
            if (this.shouldUpdateViceFuel()) {
                if (this.inventory.get(7).getItem() == SPMMain.POTATO_POWDER) {
                    this.viceFuelTime = 401;
                    this.inventory.get(7).decrement(1);
                    shallMarkDirty = true;
                }
            }

            if (this.shouldOutput()) {
                calculateOutput();
                shallMarkDirty = true;
            }

            if (this.isProcessing() && this.anyOutputIsClear()) {
                --this.mainFuelTime;
                if (withViceFuel())
                    --viceFuelTime;
                shallMarkDirty = true;
            }
        }
        if (shallMarkDirty) markDirty();
    }

    @Override
    protected Text getContainerName() {
        return new TranslatableText("container.sweet_potato.magic_cube");
    }

    protected void calculateOutput() {
        if (!anyOutputIsClear()) return;
        for (int i = 0; i < 3; ++i) {
            if (!(this.inventory.get(i).isEmpty()))
                calculateOneOutput(i, i + 3);
        }
    }

    private void calculateOneOutput(int inputIndex, int outputIndex) {
        ItemStack inputCopy = this.getStack(inputIndex).copy();
        Item item = inputCopy.getItem();
        int count = inputCopy.getCount();
        this.setStack(inputIndex, ItemStack.EMPTY);

        if (random.nextDouble() <= (withViceFuel() ? 0.4D : 0.3D)) {
            // ENCHANT
            this.setStack(outputIndex, this.enchant(inputCopy));
        } else if (random.nextDouble() <= (withViceFuel() ? 0.5D : 0.4D)) {
            // GENE-WORK
            List<ItemConvertible> itemSet = new ObjectArrayList<>(2);
            if (item instanceof RawSweetPotatoBlockItem && item.isIn(SPMMain.RAW_SWEET_POTATOES)) {
                RawSweetPotatoBlockItem sweetPotato = (RawSweetPotatoBlockItem) item;
                sweetPotato.asType().getOtherTwo().forEach(sweetPotatoType -> itemSet.add(sweetPotatoType.getRaw()));
                this.setStack(outputIndex, new ItemStack(
                        random.nextBoolean() ? itemSet.get(0) : itemSet.get(1)
                , count));
            }
        } else if (random.nextDouble() > (0.3D - 0.02D * fireCountCache)) {
            this.setStack(outputIndex, inputCopy);
        } // else EATEN
    }

    private ItemStack enchant(ItemStack originRaw) {
        Item item;
        if (!((item = originRaw.getItem()).isIn(SPMMain.RAW_SWEET_POTATOES)) || !(item instanceof RawSweetPotatoBlockItem))
            return originRaw;
        RawSweetPotatoBlockItem sweetPotato = (RawSweetPotatoBlockItem) item;
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        List<StatusEffectInstance> enchantments = calcEnchantments();
        enchantments.forEach(statusEffectInstance -> listTag.add(StatusEffectInstances.writeNbt(statusEffectInstance)));
        int length = enchantments.size();
        //short randomIndex = (short) (this.world.random.nextDouble() * length);
        short randomIndex = (short) (length == 0 ? -1 : random.nextInt(length));
        tag.put("statusEffects", listTag);
        tag.putShort("displayIndex", randomIndex);
        ItemStack outputStack = new ItemStack(sweetPotato.asType().getEnchanted(), originRaw.getCount());
        outputStack.setTag(tag);
        return outputStack;
    }

    private List<StatusEffectInstance> calcEnchantments() {
        //TODO
        List<StatusEffectInstance> enchantmentList = new ObjectArrayList<>();
        WeightedList<StatusEffectInstance> weightedList = new WeightedList<>();
        WeightedStatusEffect.dump2weightedList(weightedList, WeightedStatusEffect.EFFECTS, withViceFuel());
        if (weightedList.isEmpty()) {
            LOGGER.warn("No effects can be applied: empty weighted list");
            return Collections.emptyList();
        }
        for (byte times = 0; times < 5; ++times) {
            enchantmentList.add(weightedList.pickRandom(random));
            if (random.nextBoolean()) break;
        }
        return enchantmentList;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new MagicCubeScreenHandler(syncId, playerInventory, world, pos, this, propertyDelegate);
    }

    private boolean anyOutputIsClear() {
        for (int i = 0; i < 3; ++i) {
            if ((!(this.getStack(i + 3).isEmpty())) && (!(this.getStack(i).isEmpty()))) return false;
        } return true;
    }

    @Deprecated
    boolean outputIsClear() {
        for (int i = 3; i < 6; ++i) {
            if (!(this.getStack(i).isEmpty())) return false;
        } return true;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.mainFuelTime = tag.getShort("EnergyTime");
        this.viceFuelTime = tag.getShort("SublimateTime");
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("EnergyTime", this.mainFuelTime);
        tag.putShort("SublimateTime", this.viceFuelTime);
        return tag;
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        switch (side) {
            case DOWN:
                return BOTTOM_SLOTS;
            case UP:
                return TOP_SLOTS;
            default:
                return SIDE_SLOTS;
        }
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return slot > 2 && slot < 6 && dir == Direction.DOWN;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity serverPlayerEntity, PacketByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.pos);
    }

    @Override
    public boolean isValid(int slot, ItemStack fromHopperStack) {
        Item item = fromHopperStack.getItem();
        ItemStack toSlotStack = this.getStack(slot);
        if (slot == 6)
            return item == SPMMain.PEEL;
        if (slot == 7)
            return item == SPMMain.POTATO_POWDER;
        if ((slot >= 3 && slot <= 5) || slot > 7 || slot < 0) return false;
        return item.isIn(SPMMain.RAW_SWEET_POTATOES) && toSlotStack.isEmpty();
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        BlockState blockState;
        return this.world != null && this.world.getBlockEntity(pos) == this
                && (blockState = this.world.getBlockState(pos)).getBlock() instanceof MagicCubeBlock
                && blockState.get(MagicCubeBlock.ACTIVATED);
    }
}
