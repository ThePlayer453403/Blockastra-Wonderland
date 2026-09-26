package com.blockgi.blockastra.client;

import com.blockgi.blockastra.client.network.ModPayloadReceiver;
import com.blockgi.blockastra.client.render.ModRendering;
import net.fabricmc.api.ClientModInitializer;

public class BlockastraWonderlandClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ModRendering.register();
		ModPayloadReceiver.register();
	}
}