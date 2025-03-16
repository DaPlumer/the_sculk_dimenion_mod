package net.daplumer.sculk_dimension.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos;

public class VecToBlockPos {
    public static BlockPos createBlockPos(Vec3d vec) {
        int x = (int) Math.floor(vec.x);
        int y = (int) Math.floor(vec.y);
        int z = (int) Math.floor(vec.z);
        return new BlockPos(x, y, z);
    }
}
