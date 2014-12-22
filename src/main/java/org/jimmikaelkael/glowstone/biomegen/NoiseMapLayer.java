package org.jimmikaelkael.glowstone.biomegen;

import org.bukkit.util.noise.SimplexOctaveGenerator;

public class NoiseMapLayer extends MapLayer {

    private final SimplexOctaveGenerator noiseGen;

    public NoiseMapLayer(long seed) {
        super(seed);
        noiseGen = new SimplexOctaveGenerator(seed, 2);
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int[] values = new int[sizeX * sizeZ]; 
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                double noise = noiseGen.noise(x + j, z + i, 0.2D, 0.8D, true) * 4.0D;
                values[j + i * sizeX] = noise >= -1D ? (double) noise >= 3 ? 1 : (double) noise >= 0.57D ? 3 : 2 : 0;
            }
        }
        return values;
    }
}
