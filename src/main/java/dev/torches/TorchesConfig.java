package dev.torches;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Configuration for Torches.
 *
 * <p>COMMON type so gameplay values are available on dedicated servers.
 * Stored in {@code torches-common.toml}.
 */
public final class TorchesConfig {

    public static final ModConfigSpec SPEC;

    // -- Gameplay --
    public static final ModConfigSpec.BooleanValue ENABLE_FIRE_DAMAGE_TICK;

    // -- Visuals --
    public static final ModConfigSpec.BooleanValue ENABLE_DYNAMIC_LIGHTS;

    static {
        var builder = new ModConfigSpec.Builder();

        builder.push("gameplay");

        ENABLE_FIRE_DAMAGE_TICK = builder
                .comment("Torch arrows briefly ignite entities on hit (1 fire tick).")
                .define("enableFireDamageTick", true);

        builder.pop();

        builder.push("visuals");

        ENABLE_DYNAMIC_LIGHTS = builder
                .comment("Emit dynamic light from torch arrows in flight.",
                        "Requires a dynamic lights mod (e.g. Sodium Dynamic Lights).")
                .define("enableDynamicLights", true);

        builder.pop();

        SPEC = builder.build();
    }

    private TorchesConfig() {}
}
