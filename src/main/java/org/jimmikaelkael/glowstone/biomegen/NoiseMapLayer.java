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
                setCoordsSeed(x + j, z + i);
                double noise = noiseGen.noise(x + j, z + i, 0.25D, 0.8D, true);
                values[j + i * sizeX] = noise >= -0.2D ? nextInt(5) + 1 : 0;
            }
        }
        return values;
    }
}
