package com.blockgi.blockastra.item;

import com.blockgi.blockastra.misc.Element;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class ElementalItem extends Item {
    public ElementalItem(Properties properties) {
        super(properties);
    }

    public Element applyElementOnDamage(ServerLevel level, LivingEntity entity, DamageSource source, float damage) {
        return Element.PHYSICS;
    }
}
