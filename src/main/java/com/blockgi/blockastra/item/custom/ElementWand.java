package com.blockgi.blockastra.item.custom;

import com.blockgi.blockastra.item.ElementalItem;
import com.blockgi.blockastra.misc.Element;
import com.blockgi.blockastra.misc.ElementAura;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ElementWand extends ElementalItem {
//    private int elementID = 0;
    private static final List<Element> ELEMENT = List.of(Element.PYRO, Element.HYDRO, Element.DENDRO, Element.ELECTRO, Element.ANEMO, Element.CRYO, Element.GEO, Element.PHYSICS);
    private static final List<String> ELEMENT_NAME = List.of("火元素 Pyro", "水元素 Hydro", "草元素 Dendro", "雷元素 Electro", "风元素 Anemo", "冰元素 Cryo", "岩元素 Geo", "物理 Physics");

    public ElementWand(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult use(Level level, @NonNull Player player, @NonNull InteractionHand hand) {
        if (!level.isClientSide()) {
            int elementID = player.getItemInHand(hand).getDamageValue();
            if (++elementID > 7) {elementID = 0;}
            player.getItemInHand(hand).setDamageValue(elementID);
        }
        return InteractionResult.PASS;
    }

    @Override
    public @NonNull Component getName(@NonNull ItemStack itemStack) {
        int elementID = itemStack.getDamageValue();
        return Component.literal(String.format("元素法杖 %s", ELEMENT_NAME.get(elementID)));
    }

    @Override
    public Element applyElementOnDamage(ServerLevel level, LivingEntity entity, DamageSource source, float damage) {
        return ElementAura.applyElementAura(entity, ELEMENT.get(source.getWeaponItem().getDamageValue()), 100f, 1f);
    }
}
