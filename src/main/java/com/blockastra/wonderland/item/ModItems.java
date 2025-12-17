package com.blockastra.wonderland.item;

import com.blockastra.wonderland.BlockastraWonderland;
import com.blockastra.wonderland.item.custom.CharacterDoll;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister<Item> ITEM = DeferredRegister.createItems(BlockastraWonderland.MOD_ID);
    public static final DeferredHolder<Item, Item> CHARACTER_DOLL = ITEM.register("character_doll", CharacterDoll::new);
    public static void register(IEventBus modEventBus){
        ITEM.register(modEventBus);
    }
}
