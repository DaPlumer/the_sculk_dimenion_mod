package net.daplumer.sculk_dimension.block;

import net.daplumer.sculk_dimension.util.SculkIdentifier;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModBlockTags {
    public static final TagKey<Block> VIBRATOR = of("vibrators");

    @SuppressWarnings("SameParameterValue")
    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, SculkIdentifier.of(id));
    }
}
