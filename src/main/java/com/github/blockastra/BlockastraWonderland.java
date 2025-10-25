package com.github.blockastra;

import com.github.blockastra.component.ModComponents;
import com.github.blockastra.item.ModCreativeModeTabs;
import com.github.blockastra.item.ModItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;

@Mod(BlockastraWonderland.MOD_ID)
public class BlockastraWonderland {
    public static final String MOD_ID = "blockastra";
    public BlockastraWonderland(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModComponents.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
