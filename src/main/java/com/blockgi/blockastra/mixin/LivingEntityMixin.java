package com.blockgi.blockastra.mixin;

import com.blockgi.blockastra.attributes.ModAttributes;
import com.blockgi.blockastra.network.DamageNumberPayload;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    // 发生伤害数字数据包
    @Inject(method = "hurtServer", at = @At("RETURN"))
    private void a(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {return;}
        LivingEntity self = (LivingEntity)(Object) this;
        Vec3 position = self.position();
        Vec3 numberPosition = new Vec3(position.x + Math.random() - 0.5, position.y + Math.random() + 1, position.z + Math.random() - 0.5);
        DamageNumberPayload payload = new DamageNumberPayload(numberPosition, (int) damage, 0xffffff);
        PlayerLookup.around(level, position, 64).forEach(player -> ServerPlayNetworking.send(player, payload));
    }

    // 当LivingEntity受击时有概率触发暴击
    @ModifyVariable(method = "hurtServer", at = @At("HEAD"), argsOnly = true, name = "damage")
    private float applyCriticalHit(float damage, @Local(argsOnly = true, name = "source") DamageSource source) {
        if (source.getEntity() instanceof LivingEntity entity){
            if (Math.random() <= entity.getAttributeValue(ModAttributes.CRIT_RATE)) {
                damage = (float) (damage + damage * entity.getAttributeValue(ModAttributes.CRIT_DAMAGE));
            }
        }
        return damage;
    }

    // 为所有LivingEntity添加指定属性
    @ModifyReturnValue(method = "createLivingAttributes", at = @At("RETURN"))
    private static AttributeSupplier.Builder addGlobalAttribute(AttributeSupplier.Builder original) {
        return original
                .add(ModAttributes.CRIT_RATE)
                .add(ModAttributes.CRIT_DAMAGE);
    }
}
