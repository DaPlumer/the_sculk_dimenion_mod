package net.daplumer.sculk_dimension.util.statistics;

import net.daplumer.sculk_dimension.TheSculkDimension;
import net.daplumer.sculk_dimension.util.SculkIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
public class ModStatistics {
    public static final Identifier INSANITY = SculkIdentifier.of("insanity");
    public static void registerModStatistics(){
        TheSculkDimension.LOGGER.info("Registering Mod Statistics for " + TheSculkDimension.MOD_ID);
        Registry.register(Registries.CUSTOM_STAT, "insanity", INSANITY);
        Stats.CUSTOM.getOrCreateStat(INSANITY, StatFormatter.DEFAULT);
    }
}
