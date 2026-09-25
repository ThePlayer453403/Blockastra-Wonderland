package com.blockgi.blockastra.client;

import com.blockgi.blockastra.client.misc.DamageNumber;
import com.blockgi.blockastra.client.network.ModPayloadReceiver;
import com.blockgi.blockastra.client.render.DamageNumberRender;
import net.fabricmc.api.ClientModInitializer;

public class BlockastraWonderlandClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		DamageNumberRender.register();
		ModPayloadReceiver.register();
	}
}