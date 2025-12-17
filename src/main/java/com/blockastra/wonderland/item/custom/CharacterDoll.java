package com.blockastra.wonderland.item.custom;

import com.blockastra.wonderland.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CharacterDoll extends Item {
    public CharacterDoll() {
        super(new Item.Properties().stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        return Component.translatable("character.blockastra." + stack.getOrDefault(ModDataComponents.CHARACTER, "none"));
    }
}
