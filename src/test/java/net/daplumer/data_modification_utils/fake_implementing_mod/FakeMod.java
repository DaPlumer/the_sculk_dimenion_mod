package net.daplumer.data_modification_utils.fake_implementing_mod;

import net.daplumer.data_modification_utils.fake_implementing_mod.items.ModItemsTest;
import net.daplumer.data_modification_utils.mod_registries.Registerer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gametest.v1.GameTest;

public class FakeMod implements ModInitializer {
    public static final Registerer REGISTERER = Registerer.of("fake_mod");

    /**
     * Runs the mod initializer.
     */
    @GameTest
    @Override
    public void onInitialize() {
        ModItemsTest.registerModItems();
    }
}
