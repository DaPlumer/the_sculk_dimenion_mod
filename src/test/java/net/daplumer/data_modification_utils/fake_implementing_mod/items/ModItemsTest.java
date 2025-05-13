package net.daplumer.data_modification_utils.fake_implementing_mod.items;

import net.daplumer.data_modification_utils.fake_implementing_mod.FakeMod;
import net.daplumer.data_modification_utils.fake_implementing_mod.items.custom.CustomItem;
import net.daplumer.data_modification_utils.mod_registries.GeneralDataRegisterer;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.item.Item;

import java.util.Objects;

public class ModItemsTest {
    public static GeneralDataRegisterer<Item,Item.Settings> ITEMS = FakeMod.REGISTERER.ITEMS;
    @GameTest
    void basicItemRegistration(){
        Item foo = ITEMS.register("foo");
        assert Objects.equals(foo, ITEMS.getInstance("foo"));

        Item bar = ITEMS.register("bar",new Item.Settings().maxCount(16));
        assert bar.getMaxCount() == 16;

        Item custom = ITEMS.register("custom", CustomItem::new);
        assert custom instanceof CustomItem;
        assert ((CustomItem) custom).give30() == 30;
    }
    public static void registerModItems(){
        ModItemsTest test = new ModItemsTest();
        test.basicItemRegistration();
    }
}
