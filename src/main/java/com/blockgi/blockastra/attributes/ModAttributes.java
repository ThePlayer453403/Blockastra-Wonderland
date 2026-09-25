package com.blockgi.blockastra.attributes;

import com.blockgi.blockastra.BlockastraWonderland;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class ModAttributes {

    public static final Holder<Attribute> CRIT_RATE = register("critical_hit_rate", 0.05, 0, Double.MAX_VALUE, true);
    public static final Holder<Attribute> CRIT_DAMAGE = register("critical_hit_damage", 0.5, 0, Double.MAX_VALUE, true);

    public static Holder<Attribute> register(String name, double defaultValue, double minValue, double maxValue, boolean syncedWithClient) {
        Identifier identifier = Identifier.fromNamespaceAndPath(BlockastraWonderland.MOD_ID, name);
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, identifier, new RangedAttribute(identifier.toLanguageKey(), defaultValue, minValue, maxValue).setSyncable(syncedWithClient));
    }
    public static void register() {}
}
