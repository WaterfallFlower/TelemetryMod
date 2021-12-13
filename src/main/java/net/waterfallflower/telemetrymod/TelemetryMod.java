package net.waterfallflower.telemetrymod;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TelemetryMod implements ModInitializer {

	public static Logger CORE_LOGGER;

	@Override
	public void onInitialize() {
		CORE_LOGGER = LogManager.getLogger("TelemetryMod");

		CORE_LOGGER.warn("Thank you for using TelemetryMod 1.0!");
	}

}
