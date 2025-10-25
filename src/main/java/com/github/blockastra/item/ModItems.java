package com.github.blockastra.item;

import com.github.blockastra.BlockastraWonderland;
import com.github.blockastra.item.custom.FragileResin;
import com.github.blockastra.item.custom.OriginalResin;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlockastraWonderland.MOD_ID);
    public static final DeferredItem<Item> ORIGINAL_RESIN = ITEMS.register("original_resin", OriginalResin::new);
    public static final DeferredItem<Item> FRAGILE_RESIN = ITEMS.register("fragile_resin", FragileResin::new);
    public static void register(IEventBus modEventBus) {ITEMS.register(modEventBus);}
}
