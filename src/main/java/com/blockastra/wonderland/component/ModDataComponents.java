package com.blockastra.wonderland.component;

import com.blockastra.wonderland.BlockastraWonderland;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BlockastraWonderland.MOD_ID);
public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> CHARACTER = register("character", builder -> builder.persistent(Codec.STRING));
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPE.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
    public static void register(IEventBus modEventBus) {
        DATA_COMPONENT_TYPE.register(modEventBus);
    }
}
