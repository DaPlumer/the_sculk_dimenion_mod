package net.daplumer.sculk_dimension.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class VecToBlockPos {
    public static BlockPos createBlockPos(Vec3d vec) {
        int x = (int) Math.round(vec.x);
        int y = (int) Math.round(vec.y);
        int z = (int) Math.round(vec.z);
        return new BlockPos(x, y, z);
    }
}
