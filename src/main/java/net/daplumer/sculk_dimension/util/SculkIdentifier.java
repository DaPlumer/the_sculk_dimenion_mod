package net.daplumer.sculk_dimension.util;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.minecraft.util.Identifier;

public class SculkIdentifier {
    public static Identifier of(String name){
        return Identifier.of(TheSculkDimension.MOD_ID,name);
    }
}
