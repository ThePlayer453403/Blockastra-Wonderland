package com.blockgi.blockastra.mixin;

import com.blockgi.blockastra.attributes.ModAttributes;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class ApplyCriticalHit {
    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true, name = "damage")
    private float applyCriticalHit(float damage, @Local(argsOnly = true, name = "source") DamageSource source) {
        if (source.getEntity() instanceof LivingEntity entity){
            if (Math.random() <= entity.getAttributeValue(ModAttributes.CRIT_RATE)) {
                damage = (float) (damage + damage * entity.getAttributeValue(ModAttributes.CRIT_DAMAGE));
            }
        }
        return damage;
    }
}
