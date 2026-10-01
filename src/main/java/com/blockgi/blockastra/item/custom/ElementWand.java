package com.blockgi.blockastra.item.custom;

import com.blockgi.blockastra.item.ElementalItem;
import com.blockgi.blockastra.misc.Element;
import com.blockgi.blockastra.misc.ElementAura;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ElementWand extends ElementalItem {
    public ElementWand(Properties properties) {
        super(properties);
    }

    @Override
    public Element applyElementOnDamage(ServerLevel level, LivingEntity entity, DamageSource source, float damage) {
        return ElementAura.applyElementAura(entity, Element.PYRO, 100f, 1f);
    }
}
