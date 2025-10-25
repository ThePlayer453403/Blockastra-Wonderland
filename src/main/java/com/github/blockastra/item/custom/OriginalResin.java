package com.github.blockastra.item.custom;

import com.github.blockastra.component.ModComponents;
import com.github.blockastra.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OriginalResin extends Item {
    HashMap<UUID, Long> PlayerRegenerated = new HashMap<>();
    public OriginalResin(Properties properties) {super(properties);}

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) {
            super.inventoryTick(stack, level, entity, slotId, isSelected);
            return;
        }

        long tick = level.getGameTime();

        int resin_amount = stack.getOrDefault(ModComponents.RESIN_AMOUNT, 0);
        stack.setDamageValue(1024 - 1024 * resin_amount / Config.RESIN_REGENERATES_LIMIT.getAsInt());

        if (tick % 20 == 0) { // 每秒执行一次
            if (PlayerRegenerated.getOrDefault(entity.getUUID(), 0L) != tick) {
                int resin_timer = stack.getOrDefault(ModComponents.RESIN_TIMER, Config.RESIN_REGENERATES_SPEED.getAsInt());

                if (resin_amount >= Config.RESIN_REGENERATES_LIMIT.getAsInt()) {
                    stack.set(ModComponents.RESIN_TIMER, 0);
                } else if (resin_timer <= 1) {
                    stack.set(ModComponents.RESIN_TIMER, Config.RESIN_REGENERATES_SPEED.getAsInt());
                    stack.set(ModComponents.RESIN_AMOUNT, resin_amount + 1);
                } else {
                    stack.set(ModComponents.RESIN_TIMER, resin_timer - 1);
                }

                PlayerRegenerated.put(entity.getUUID(), tick);
            } else {
                stack.set(ModComponents.RESIN_TIMER, -1);
            }
        }
    }

    @Override
    public void appendHoverText
            (ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        int resin_amount = stack.getOrDefault(ModComponents.RESIN_AMOUNT, 0);
        int resin_timer = stack.getOrDefault(ModComponents.RESIN_TIMER, Config.RESIN_REGENERATES_SPEED.getAsInt());

        if (resin_timer < 0) { // 计时器小于0即计时器为-1 说明该树脂无法恢复
            tooltipComponents.add(Component.translatable("original.blockastra.stop_replenished"));
        }else if (resin_amount >= Config.RESIN_REGENERATES_LIMIT.getAsInt()) { // 已全部恢复
            tooltipComponents.add(Component.translatable("original.blockastra.fully_replenished"));
        } else {
            tooltipComponents.add(Component.translatable("original.blockastra.next_replenished_time")
                            .append(" ")
                            .append(String.valueOf(resin_timer)));
            tooltipComponents.add(Component.translatable("original.blockastra.fully_replenished_time")
                            .append(" ")
                            .append(String.valueOf((Config.RESIN_REGENERATES_LIMIT.getAsInt() - resin_amount - 1)
                                    * Config.RESIN_REGENERATES_SPEED.getAsInt() + resin_timer))
            );
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public @NotNull Component getName(ItemStack stack) {
        return Component.translatable("item.blockastra.original_resin")
                .append(" ")
                .append(stack.getOrDefault(ModComponents.RESIN_AMOUNT, 0)
                        + " / " + Config.RESIN_REGENERATES_LIMIT.getAsInt());

    }
}
