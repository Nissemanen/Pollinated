package com.nissemanen.pollinated.block;

import com.nissemanen.pollinated.Pollinated;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Pollinated.MODID);

    public static final DeferredBlock<Block> SPREADING_DANDELION = BLOCKS.register(
            "spreadiong_dandelion",
            () -> new SpreadingPlantBlock(
                    BlockBehaviour.Properties.of()
                            .noCollission()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .offsetType(BlockBehaviour.OffsetType.XZ)
                            .randomTicks()
            )
    );
}
