package net.daplumer.sculk_dimension.block.custom;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

@SuppressWarnings("SameParameterValue")
public class ModScreenHandlerTypes {
    public static final ScreenHandlerType<EnchantmentDuplicationScreenHandler> ENCHANTMENT_DUPLICATOR = register("enchantment_duplicator", EnchantmentDuplicationScreenHandler::new);
    static{
        HandledScreens.register(ENCHANTMENT_DUPLICATOR,EnchantmentDuplicationScreen::new);
    }
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, id, new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
    public static void initialize(){

    }
}
