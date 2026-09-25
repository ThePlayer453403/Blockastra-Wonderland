package com.blockgi.blockastra.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class ModNetworkPayloads {
    public static void register() {
        PayloadTypeRegistry.clientboundPlay().register(DamageNumberPayload.TYPE, DamageNumberPayload.CODEC);
    }
}
