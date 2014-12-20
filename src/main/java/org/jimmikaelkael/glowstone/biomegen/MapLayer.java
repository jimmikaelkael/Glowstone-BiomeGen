package org.jimmikaelkael.glowstone.biomegen;

import java.util.Random;

public abstract class MapLayer {

    private final Random random = new Random();
    private long seed;

    public MapLayer(long seed) {
        this.seed = seed;
    }

    public void setCoordsSeed(int x, int z) {
        random.setSeed(seed);
        random.setSeed(x * random.nextLong() + z * random.nextLong() ^ seed);
    }

    public int nextInt(int max) {
        return random.nextInt(max);
    }

    public abstract int[] generateValues(int x, int z, int sizeX, int sizeZ);
}
