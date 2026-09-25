package com.blockgi.blockastra.client.network;

import com.blockgi.blockastra.client.misc.DamageNumber;
import com.blockgi.blockastra.network.DamageNumberPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ModPayloadReceiver {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(DamageNumberPayload.TYPE, (payload, _) -> DamageNumber.addDamageNumberInstance(payload));
    }
}
