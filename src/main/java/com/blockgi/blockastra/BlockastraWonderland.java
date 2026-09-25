package com.blockgi.blockastra;

import com.blockgi.blockastra.attributes.ModAttributes;
import com.blockgi.blockastra.network.ModNetworkPayloads;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlockastraWonderland implements ModInitializer {
	public static final String MOD_ID = "blockastra";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModAttributes.register();
		ModNetworkPayloads.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
