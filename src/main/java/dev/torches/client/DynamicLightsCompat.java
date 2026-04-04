package dev.torches.client;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.torches.TorchesConfig;
import dev.torches.TorchesMod;

/**
 * Optional integration with Sodium Dynamic Lights.
 * Only loaded when SDL is present.
 */
public final class DynamicLightsCompat {

    public static final String MOD_ID = "sodiumdynamiclights";

    private static final int TORCH_LUMINANCE = 14;

    private DynamicLightsCompat() {}

    /** Registers entity light handlers. Call only when SDL is on the classpath. */
    public static void register() {
        DynamicLightHandlers.registerDynamicLightHandler(
                TorchesMod.TORCH_ARROW_ENTITY.get(),
                arrow -> xyz.kwahson.core.config.SafeConfig.getBool(TorchesConfig.ENABLE_DYNAMIC_LIGHTS, true) ? TORCH_LUMINANCE : 0);
    }
}
