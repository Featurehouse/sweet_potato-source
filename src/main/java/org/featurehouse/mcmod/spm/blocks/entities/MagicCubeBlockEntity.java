package org.featurehouse.mcmod.spm.blocks.entities;

import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.behavior.ShufflingList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.featurehouse.mcmod.spm.SPMMain;
import org.featurehouse.mcmod.spm.blocks.MagicCubeBlock;
import org.featurehouse.mcmod.spm.items.RawSweetPotatoBlockItem;
import org.featurehouse.mcmod.spm.lib.block.entity.AbstractLockableContainerBlockEntity;
import org.featurehouse.mcmod.spm.resource.magicalenchantment.WeightedStatusEffect;
import org.featurehouse.mcmod.spm.screen.MagicCubeScreenHandler;
import org.featurehouse.mcmod.spm.util.BooleanStateManager;
import org.featurehouse.mcmod.spm.util.effects.StatusEffectInstances;
import org.featurehouse.mcmod.spm.util.iprops.IntMagicCubeProperties;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.world.level.block.Blocks.SOUL_FIRE;

public class MagicCubeBlockEntity extends AbstractLockableContainerBlockEntity implements WorldlyContainer, ExtendedScreenHandlerFactory {
    private static final Logger LOGGER = LogUtils.getLogger();

    //protected StateHelperV1 stateHelper;
    private static final int[] TOP_SLOTS = new int[] { 0, 1, 2 };
    private static final int[] BOTTOM_SLOTS = new int[] { 3, 4, 5 };
    private static final int[] SIDE_SLOTS = new int[] { 6, 7 };

    private boolean activationCache = false;
    private byte fireCountCache = 0;
    private final Random random = this.level != null ? this.level.random : new Random();

    protected BooleanStateManager stateHelper;
    protected short mainFuelTime;
    protected short viceFuelTime;

    protected IntMagicCubeProperties propertyDelegate;

    public MagicCubeBlockEntity(BlockPos pos, BlockState state) {
        this(SPMMain.MAGIC_CUBE_BLOCK_ENTITY_TYPE, 8, pos, state);
    }

    public MagicCubeBlockEntity(BlockEntityType<?> type, int size, BlockPos pos, BlockState state) {
        super(type, pos, state, size);
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
                assert MagicCubeBlockEntity.this.level != null;
                return MagicCubeBlockEntity.this.level.getBlockState(pos).getValue(property) != newOne;
            }

            @Override
            public void run() {
                assert MagicCubeBlockEntity.this.level != null;
                boolean b;
                byte fc = this.fireCount();
                MagicCubeBlockEntity.this.fireCountCache = this.fireCount();
                if (this.shouldChange(b = fc > 0)) {
                    MagicCubeBlockEntity.this.level.setBlockAndUpdate(pos,
                            MagicCubeBlockEntity.this.level.getBlockState(pos)
                                    .setValue(property, b));
                    if (!b) {
                        MagicCubeBlockEntity.this.mainFuelTime = -1;
                        MagicCubeBlockEntity.this.viceFuelTime = 0;
                        MagicCubeBlockEntity.this.level.playSound(null, pos,
                                SPMMain.MAGIC_CUBE_DEACTIVATE, SoundSource.BLOCKS,
                                1.0F, 1.0F);
                    } else {
                        MagicCubeBlockEntity.this.level.playSound(null, pos,
                                SPMMain.MAGIC_CUBE_ACTIVATE, SoundSource.BLOCKS,
                                1.0F, 1.0F);
                    }
                }
                MagicCubeBlockEntity.this.activationCache = b;
            }

            byte fireCount() {
                BlockPos[] blockPosList = calcPos(MagicCubeBlockEntity.this.getBlockPos());

                assert MagicCubeBlockEntity.this.level != null;
                byte b = 0;
                for (BlockPos eachPos: blockPosList) {
                    if (MagicCubeBlockEntity.this.level.getBlockState(eachPos).getBlock() == SOUL_FIRE)
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
    public void tick(Level world, BlockPos pos, BlockState state) {
        boolean shallMarkDirty = false;

        if (!world.isClientSide) {
            if (world.getGameTime() % 10L == 5L) {
                stateHelper.run();
            }
            if (!activationCache) return;

            /*-* * PROPERTIES * *-*/
            if (!this.isProcessing()) {
                // Check inventory
                if (this.inventory.get(6).getItem() == SPMMain.PEEL) {
                    boolean bl = false;
                    for (int i = 0; i < 3; ++i) {
                        if (SPMMain.RAW_SWEET_POTATOES.contains(this.inventory.get(i).getItem())) {
                            this.mainFuelTime = 200;
                            bl = true;
                            break;
                        }
                    }
                    if (bl) {
                        // DECREMENT PEEL, START PROGRESS, MARK DIRTY.
                        this.inventory.get(6).shrink(1);
                        shallMarkDirty = true;
                    }
                }
            }
            // CHECK VICE FUEL
            if (this.shouldUpdateViceFuel()) {
                if (this.inventory.get(7).getItem() == SPMMain.POTATO_POWDER) {
                    this.viceFuelTime = 401;
                    this.inventory.get(7).shrink(1);
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
        if (shallMarkDirty) setChanged();
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.sweet_potato.magic_cube");
    }

    protected void calculateOutput() {
        if (!anyOutputIsClear()) return;
        for (int i = 0; i < 3; ++i) {
            if (!(this.inventory.get(i).isEmpty()))
                calculateOneOutput(i, i + 3);
        }
    }

    private void calculateOneOutput(int inputIndex, int outputIndex) {
        ItemStack inputCopy = this.getItem(inputIndex).copy();
        Item item = inputCopy.getItem();
        int count = inputCopy.getCount();
        this.setItem(inputIndex, ItemStack.EMPTY);

        if (random.nextDouble() <= (withViceFuel() ? 0.4D : 0.3D)) {
            // ENCHANT
            this.setItem(outputIndex, this.enchant(inputCopy));
        } else if (random.nextDouble() <= (withViceFuel() ? 0.5D : 0.4D)) {
            // GENE-WORK
            List<ItemLike> itemSet = new ObjectArrayList<>(2);
            if (item instanceof RawSweetPotatoBlockItem sweetPotato && SPMMain.RAW_SWEET_POTATOES.contains(item)) {
                sweetPotato.asType().getOtherTwo().forEach(sweetPotatoType -> itemSet.add(sweetPotatoType.getRaw()));
                this.setItem(outputIndex, new ItemStack(
                        random.nextBoolean() ? itemSet.get(0) : itemSet.get(1)
                , count));
            }
        } else if (random.nextDouble() > (0.3D - 0.02D * fireCountCache)) {
            this.setItem(outputIndex, inputCopy);
        } // else EATEN
    }

    private ItemStack enchant(ItemStack originRaw) {
        Item item;
        Item element = item = originRaw.getItem();
        if (!SPMMain.RAW_SWEET_POTATOES.contains(element)
                || !(item instanceof RawSweetPotatoBlockItem sweetPotato))
            return originRaw;
        CompoundTag tag = new CompoundTag();
        ListTag listTag = new ListTag();
        List<MobEffectInstance> enchantments = calcEnchantments();
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

    private List<MobEffectInstance> calcEnchantments() {
        //TODO
        List<MobEffectInstance> enchantmentList = new ObjectArrayList<>();
        ShufflingList<MobEffectInstance> weightedList = new ShufflingList<>();
        WeightedStatusEffect.dump2weightedList(weightedList, withViceFuel());
        if (weightedList.stream().findAny().isEmpty()) {
            LOGGER.warn("No effects can be applied: empty weighted list");
            return Collections.emptyList();
        }
        for (byte times = 0; times < 5; ++times) {
            Optional<MobEffectInstance> optional = weightedList.shuffle().stream().findAny();
            enchantmentList.add(optional.orElseThrow());
            if (random.nextBoolean()) break;
        }
        return enchantmentList;
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return new MagicCubeScreenHandler(syncId, playerInventory, level, worldPosition, this, propertyDelegate);
    }

    private boolean anyOutputIsClear() {
        for (int i = 0; i < 3; ++i) {
            if ((!(this.getItem(i + 3).isEmpty())) && (!(this.getItem(i).isEmpty()))) return false;
        } return true;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.mainFuelTime = tag.getShort("EnergyTime");
        this.viceFuelTime = tag.getShort("SublimateTime");
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putShort("EnergyTime", this.mainFuelTime);
        tag.putShort("SublimateTime", this.viceFuelTime);
        //return tag;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return switch (side) {
            case DOWN -> BOTTOM_SLOTS;
            case UP -> TOP_SLOTS;
            default -> SIDE_SLOTS;
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.canPlaceItem(slot, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
        return slot > 2 && slot < 6 && dir == Direction.DOWN;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer serverPlayerEntity, FriendlyByteBuf packetByteBuf) {
        packetByteBuf.writeBlockPos(this.worldPosition);
    }

    @Override
    public boolean canPlaceItem(int slot, ItemStack fromHopperStack) {
        Item item = fromHopperStack.getItem();
        ItemStack toSlotStack = this.getItem(slot);
        if (slot == 6)
            return item == SPMMain.PEEL;
        if (slot == 7)
            return item == SPMMain.POTATO_POWDER;
        if ((slot >= 3 && slot <= 5) || slot > 7 || slot < 0) return false;
        return SPMMain.RAW_SWEET_POTATOES.contains(item) && toSlotStack.isEmpty();
    }

    @Override
    public boolean stillValid(Player player) {
        BlockState blockState;
        return this.level != null && this.level.getBlockEntity(worldPosition) == this
                && (blockState = this.level.getBlockState(worldPosition)).getBlock() instanceof MagicCubeBlock
                && blockState.getValue(MagicCubeBlock.ACTIVATED);
    }
}
