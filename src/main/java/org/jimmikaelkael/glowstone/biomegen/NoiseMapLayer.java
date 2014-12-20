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
                values[j + i * sizeX] = noiseGen.noise(x + j, z + i, 0.25D, 0.8D, true) >= -0.075D ? 1 : 0;
            }
        }
        return values;
    }
}
