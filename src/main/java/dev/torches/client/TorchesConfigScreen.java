package dev.torches.client;

import dev.torches.TorchesConfig;
import net.minecraft.client.gui.screens.Screen;
import xyz.kwahson.core.config.ConfigTab;
import xyz.kwahson.core.config.KwahsConfigScreen;

/**
 * Configuration screen for Torches.
 */
public final class TorchesConfigScreen {

    private TorchesConfigScreen() {}

    public static Screen create(Screen parent) {
        return KwahsConfigScreen.builder("Torches", parent, TorchesConfig.SPEC)
                .tab("General", TorchesConfigScreen::buildGeneralTab)
                .build();
    }

    private static void buildGeneralTab(ConfigTab tab) {
        tab.sections("Gameplay", "Visuals");

        tab.left(tab.toggle("Fire on Hit", TorchesConfig.ENABLE_FIRE_DAMAGE_TICK));
        tab.right(tab.toggle("Dynamic Lights", TorchesConfig.ENABLE_DYNAMIC_LIGHTS));
    }
}
