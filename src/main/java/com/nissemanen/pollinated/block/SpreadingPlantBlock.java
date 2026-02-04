package com.nissemanen.pollinated.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class SpreadingPlantBlock extends BushBlock {
    public static final MapCodec<SpreadingPlantBlock> CODEC = simpleCodec(SpreadingPlantBlock::new);

    public static final IntegerProperty MATURITY = IntegerProperty.create("maturity", 0, 5);
    public static final IntegerProperty OFFSPRING_COUNT = IntegerProperty.create("offspring", 0, 3);

    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    public SpreadingPlantBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(MATURITY, 0)
                .setValue(OFFSPRING_COUNT, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(MATURITY, OFFSPRING_COUNT);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int maturity = state.getValue(MATURITY);
        int offspring = state.getValue(OFFSPRING_COUNT);

        if (maturity < 5) {
            if (random.nextInt(10) == 0) {
                level.setBlock(pos, state.setValue(MATURITY, maturity + 1), 2);
            }
        } else if (offspring < getMaxOffspring() && random.nextInt(20) == 0) {
            BlockPos spreadPos = findSpreadingLocation(level, pos, random);
            if (spreadPos != null) {
                level.setBlock(spreadPos, this.defaultBlockState(), 2);
                level.setBlock(pos, state.setValue(OFFSPRING_COUNT, offspring + 1), 2);
            }
        }
    }

    private BlockPos findSpreadingLocation(ServerLevel level, BlockPos origin, RandomSource random) {
        int range = getSpreadingRange();

        for (int attempt = 0; attempt < 10; attempt++) {
            BlockPos target = origin.offset(
                    random.nextInt(range * 2 + 1) - range,
                    random.nextInt(range + 1) - range/2,
                    random.nextInt(range * 2 + 1) - range
            );

            if (canSpreadTo(level, target)) {
                return target;
            }
        }
        return null;
    }

    private boolean canSpreadTo(ServerLevel level, BlockPos pos) {
        BlockState stateAtPos = level.getBlockState(pos);
        BlockState stateBelow = level.getBlockState(pos.below());

        if (!stateAtPos.isAir() && !stateAtPos.canBeReplaced()) {
            return false;
        }

        return this.mayPlaceOn(stateBelow, level, pos.below()) && level.getRawBrightness(pos, 0) >= 8;
    }

    protected int getMaxOffspring() {
        return 3;
    }

    protected int getSpreadingRange() {
        return 4;
    }
}
