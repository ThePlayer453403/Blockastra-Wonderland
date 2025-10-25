package com.github.blockastra.component;

import com.github.blockastra.BlockastraWonderland;
import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModComponents {
    public static final DeferredRegister<DataComponentType<?>> COMPONENTS =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, BlockastraWonderland.MOD_ID);
    // 原粹树脂
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RESIN_TIMER =
            register("resin_timer", builder -> builder.persistent(Codec.INT));
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RESIN_AMOUNT =
            register("resin_amount", builder -> builder.persistent(Codec.INT));
    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>>
    register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return COMPONENTS.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
    public static void register(IEventBus modEventBus) {COMPONENTS.register(modEventBus);}
}
