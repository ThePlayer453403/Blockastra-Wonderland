package com.github.blockastra.item.custom;

import com.github.blockastra.Config;
import com.github.blockastra.component.ModComponents;
import com.github.blockastra.item.ModItems;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class FragileResin extends Item {
    public FragileResin() {
        super(new Item.Properties());
    }

    @Override
    public @NotNull InteractionResult onItemUseFirst(@NotNull ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        if (player != null && player.isCrouching()) {
            int amount = Config.FRAGILE_RESIN_RESTORE_AMOUNT.getAsInt();

            for (ItemStack item : player.getInventory().items) {
                int new_amount = item.getOrDefault(ModComponents.RESIN_AMOUNT, Config.RESIN_STORAGE_LIMIT.getAsInt()) + amount;

                if (item.is(ModItems.ORIGINAL_RESIN) && new_amount <= Config.RESIN_STORAGE_LIMIT.getAsInt()) {
                    item.set(ModComponents.RESIN_AMOUNT, new_amount);
                    System.out.println(new_amount);
                    if (!player.isCreative()) {
                        stack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }

        }
        return InteractionResult.SUCCESS_NO_ITEM_USED;
    }
}
