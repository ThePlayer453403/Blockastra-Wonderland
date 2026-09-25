package com.blockgi.blockastra.network;

import com.blockgi.blockastra.BlockastraWonderland;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;

public record DamageNumberPayload(Vec3 position, int damage, int color) implements CustomPacketPayload {
    public static final Identifier DAMAGE_NUMBER_PAYLOAD_ID = Identifier.fromNamespaceAndPath(BlockastraWonderland.MOD_ID, "dm");

    public static final CustomPacketPayload.Type<DamageNumberPayload> TYPE = new CustomPacketPayload.Type<>(DAMAGE_NUMBER_PAYLOAD_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, DamageNumberPayload> CODEC = StreamCodec.composite(
            Vec3.STREAM_CODEC,
            DamageNumberPayload::position,
            ByteBufCodecs.VAR_INT,
            DamageNumberPayload::damage,
            ByteBufCodecs.VAR_INT,
            DamageNumberPayload::color,
            DamageNumberPayload::new
    );
    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
