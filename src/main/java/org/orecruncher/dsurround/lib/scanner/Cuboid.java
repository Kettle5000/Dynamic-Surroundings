package org.orecruncher.dsurround.lib.scanner;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.jetbrains.annotations.Nullable;

public final class Cuboid {

    public static BoundingBox of(BlockPos[] points) {
        return of(points[0], points[1]);
    }

    public static BoundingBox of(BlockPos pos1, BlockPos pos2) {
        return BoundingBox.fromCorners(pos1, pos2);
    }

    public static boolean intersects(BoundingBox box1, BoundingBox box2) {
        var meMin = min(box1);
        var meMax = max(box1);
        var oMin = min(box2);
        var oMax = max(box2);
        return meMin.getX() <= oMax.getX()
                && meMax.getX() >= oMin.getX()
                && meMin.getY() <= oMax.getY()
                && meMax.getY() >= oMin.getY()
                && meMin.getZ() <= oMax.getZ()
                && meMax.getZ() >= oMin.getZ();
    }

    @Nullable
    public static BoundingBox intersection(BoundingBox box1, BoundingBox box2) {
        if (intersects(box1, box2)) {
            var meMin = min(box1);
            var meMax = max(box1);
            var oMin = min(box2);
            var oMax = max(box2);
            int minX = Math.max(meMin.getX(), oMin.getX());
            int minY = Math.max(meMin.getY(), oMin.getY());
            int minZ = Math.max(meMin.getZ(), oMin.getZ());
            int maxX = Math.min(meMax.getX(), oMax.getX());
            int maxY = Math.min(meMax.getY(), oMax.getY());
            int maxZ = Math.min(meMax.getZ(), oMax.getZ());
            return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        }
        return null;
    }

    private static BlockPos min(BoundingBox box) {
        return new BlockPos(box.minX(), box.minY(), box.minZ());
    }

    private static BlockPos max(BoundingBox box) {
        return new BlockPos(box.maxX(), box.maxY(), box.maxZ());
    }
}
