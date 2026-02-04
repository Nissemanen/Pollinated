package com.nissemanen.pollinated.item;

import com.nissemanen.pollinated.Pollinated;
import com.nissemanen.pollinated.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(Pollinated.MODID);

    public static final DeferredItem<BlockItem> SPREADING_DANDELION = ITEMS.register(
            "spreading_dandelion",
            () -> new BlockItem(ModBlocks.SPREADING_DANDELION.get(), new Item.Properties())
    );
}
