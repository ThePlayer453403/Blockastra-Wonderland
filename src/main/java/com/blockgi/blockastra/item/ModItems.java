package com.blockgi.blockastra.item;

import com.blockgi.blockastra.BlockastraWonderland;
import com.blockgi.blockastra.item.custom.ElementWand;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;

import java.util.function.Function;

public class ModItems {

    public static class ModItemIds {
        public static final ResourceKey<Item> ELEMENT_WAND = create("element_wand");

        public static ResourceKey<Item> create(String name) {
            return ResourceKey.create(Registries.ITEM, BlockastraWonderland.of(name));
        }
    }

    public static final Item ELEMENT_WAND = register(ModItemIds.ELEMENT_WAND, ElementWand::new, new Item.Properties().sword(ToolMaterial.DIAMOND, 3.0F, -2.4F));

    public static Item register(ResourceKey<Item> itemKey, Function<Item.Properties, Item> itemFactory, Item.Properties settings) {
        Item item = itemFactory.apply(settings.setId(itemKey));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void register() {}
}
