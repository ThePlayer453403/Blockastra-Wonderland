package com.blockgi.blockastra.mixin;

import com.blockgi.blockastra.attributes.ModAttributes;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class AddGlobalAttribute {
    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder addGlobalAttribute(AttributeSupplier.Builder original) {
        return original
                .add(ModAttributes.CRIT_RATE)
                .add(ModAttributes.CRIT_DAMAGE);
    }
}
