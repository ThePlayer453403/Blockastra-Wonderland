package com.blockgi.blockastra.misc;

import com.blockgi.blockastra.data.ModDataAttachment;
import net.minecraft.world.entity.LivingEntity;

import java.util.*;

public class ElementAura {
    public static void tick(LivingEntity entity) {
        if (entity.level().isClientSide()) {return;}

        for (Element element : Element.values()) {
            if (element == Element.PHYSICS) {
                return;
            }
            if (entity.getAttachedOrElse(ModDataAttachment.ELEMENT_AURA.get(element).HAS, false)) {
                float amount = entity.getAttachedOrElse(ModDataAttachment.ELEMENT_AURA.get(element).AMOUNT, 0f) - entity.getAttachedOrElse(ModDataAttachment.ELEMENT_AURA.get(element).LOSS_SPEED, 1f);
                if (amount <= 0) {
                    entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).HAS, false);
                    entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).AMOUNT, 0f);
                    entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).LOSS_SPEED, 1f);
                } else {
                    entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).AMOUNT, amount);
                }
            }
        }
    }

    public static Element applyElementAura(LivingEntity entity, Element element, float amount, float lossSpeed, float timer, int counter) {
        entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).AMOUNT, amount);
        entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).LOSS_SPEED, lossSpeed);
        entity.setAttached(ModDataAttachment.ELEMENT_AURA.get(element).HAS, true);
        return element;
    }

    public static Element applyElementAura(LivingEntity entity, Element element, float amount, float lossSpeed) {
        return applyElementAura(entity, element, amount, lossSpeed, 2.5f, 3);
    }
}
