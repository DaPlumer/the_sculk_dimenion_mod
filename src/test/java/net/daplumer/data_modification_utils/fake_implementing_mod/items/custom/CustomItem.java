package net.daplumer.data_modification_utils.fake_implementing_mod.items.custom;

import net.minecraft.item.Item;

public class CustomItem extends Item {
    public CustomItem(Settings settings) {
        super(settings);
    }
    public int give30(){
        return 30;
    }
}
