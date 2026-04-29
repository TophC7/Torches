package dev.torches.client;

import dev.torches.TorchesConfig;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import xyz.kwahson.core.config.ConfigBinding;
import xyz.kwahson.core.ui.binding.ValueBinding;
import xyz.kwahson.core.ui.layout.FormLayout;
import xyz.kwahson.core.ui.screen.TabbedScreen;
import xyz.kwahson.core.ui.widget.ToggleField;

/**
 * Configuration screen for Torches.
 */
public final class TorchesConfigScreen {

    private TorchesConfigScreen() {}

    public static Screen create(Screen parent) {
        return TabbedScreen.builder("Torches", parent)
                .tab("General", TorchesConfigScreen::buildGeneralTab)
                .build();
    }

    private static void buildGeneralTab(FormLayout tab) {
        tab.sections("Gameplay", "Visuals");

        tab.left(ToggleField.of(bool(
                "Fire on Hit", TorchesConfig.ENABLE_FIRE_DAMAGE_TICK, true)));
        tab.right(ToggleField.of(bool(
                "Dynamic Lights", TorchesConfig.ENABLE_DYNAMIC_LIGHTS, true)));
    }

    private static ValueBinding<Boolean> bool(
            String label, ModConfigSpec.BooleanValue value, boolean fallback) {
        return ConfigBinding.saving(
                ConfigBinding.bool(Component.literal(label), value, fallback),
                TorchesConfig.SPEC);
    }
}
