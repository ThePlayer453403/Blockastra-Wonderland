package com.blockgi.blockastra.data;

import com.blockgi.blockastra.BlockastraWonderland;
import com.blockgi.blockastra.misc.Element;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.Map;

public class ModDataAttachment {
    public static final AttachmentType<Boolean> HAS_PYRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_pyro_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_HYDRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_hydro_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_DENDRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_dendro_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_ELECTRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_electro_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_ANEMO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_anemo_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_CRYO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_cryo_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));
    public static final AttachmentType<Boolean> HAS_GEO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("has_geo_aura"), builder -> builder.syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all()).persistent(Codec.BOOL));

    public static final AttachmentType<Float> PYRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("pyro_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> HYDRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("hydro_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> DENDRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("dendro_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> ELECTRO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("electro_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> ANEMO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("anemo_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> CRYO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("cryo_aura"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> GEO_AURA = AttachmentRegistry.create(BlockastraWonderland.of("geo_aura"), builder -> builder.persistent(Codec.FLOAT));

    public static final AttachmentType<Float> PYRO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("pyro_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> HYDRO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("hydro_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> DENDRO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("dendro_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> ELECTRO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("electro_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> ANEMO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("anemo_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> CRYO_AURA_LOSS_SPEED = AttachmentRegistry.create(BlockastraWonderland.of("cryo_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    public static final AttachmentType<Float> GEO_AURA_LOSS_SPEED  = AttachmentRegistry.create(BlockastraWonderland.of("geo_aura_loss_speed"), builder -> builder.persistent(Codec.FLOAT));
    
    public static class ElementAura {
        public AttachmentType<Boolean> HAS;
        public AttachmentType<Float> AMOUNT;
        public AttachmentType<Float> LOSS_SPEED;
        public ElementAura(AttachmentType<Boolean> has, AttachmentType<Float> amount, AttachmentType<Float> lossSpeed) {
            HAS = has;
            AMOUNT = amount;
            LOSS_SPEED = lossSpeed;
        }
    }
    public static Map<Element, ElementAura> ELEMENT_AURA = Map.of(
            Element.PYRO, new ElementAura(HAS_PYRO_AURA, PYRO_AURA, PYRO_AURA_LOSS_SPEED),
            Element.HYDRO, new ElementAura(HAS_HYDRO_AURA, HYDRO_AURA, HYDRO_AURA_LOSS_SPEED),
            Element.DENDRO, new ElementAura(HAS_DENDRO_AURA, DENDRO_AURA, DENDRO_AURA_LOSS_SPEED),
            Element.ELECTRO, new ElementAura(HAS_ELECTRO_AURA, ELECTRO_AURA, ELECTRO_AURA_LOSS_SPEED),
            Element.ANEMO, new ElementAura(HAS_ANEMO_AURA, ANEMO_AURA, ANEMO_AURA_LOSS_SPEED),
            Element.CRYO, new ElementAura(HAS_CRYO_AURA, CRYO_AURA, CRYO_AURA_LOSS_SPEED),
            Element.GEO, new ElementAura(HAS_GEO_AURA, GEO_AURA, GEO_AURA_LOSS_SPEED)
    );

    public static void register() {}
}
