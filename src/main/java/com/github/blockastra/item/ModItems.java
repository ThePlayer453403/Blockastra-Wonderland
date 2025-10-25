package com.github.blockastra.item;

import com.github.blockastra.BlockastraWonderland;
import com.github.blockastra.item.custom.OriginalResin;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockastraWonderland.MOD_ID);
    public static final DeferredItem<Item> ORIGINAL_RESIN = ITEMS.register("original_resin", () -> new OriginalResin(
            new Item.Properties()
                    .rarity(Rarity.COMMON)
                    .durability(1024)
                    .setNoRepair()
    ));
    public static void register(IEventBus modEventBus) {ITEMS.register(modEventBus);}
}
