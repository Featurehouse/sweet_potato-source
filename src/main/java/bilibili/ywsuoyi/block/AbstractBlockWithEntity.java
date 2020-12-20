package bilibili.ywsuoyi.block;

import io.github.teddyxlandlee.sweet_potato.util.tick.ITickable;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractBlockWithEntity<E extends BlockEntity & ITickable> extends BlockWithEntity {
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public abstract BlockEntityType<E> getBlockEntityType();

    protected AbstractBlockWithEntity(Settings settings) {
        super(settings);
    }

    public abstract E createTileEntity(BlockPos pos, BlockState state);

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return createTileEntity(pos, state);
    }

    //public abstract Optional<ITickable> getTickableBlockEntity(World world, BlockState state);
    public BlockEntityTicker<E> ticker() {
        return ITickable::iTick;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : checkType(type, getBlockEntityType(), ticker());
    }
}
