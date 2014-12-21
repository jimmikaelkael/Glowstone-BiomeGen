package org.jimmikaelkael.glowstone.biomegen;

import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class NoiseMapLayer extends MapLayer {

    private static final int[] LANDS = new int[] {GlowBiome.getId(Biome.PLAINS), GlowBiome.getId(Biome.DESERT),
            GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.EXTREME_HILLS), GlowBiome.getId(Biome.FOREST),
            GlowBiome.getId(Biome.FOREST), GlowBiome.getId(Biome.ICE_PLAINS)};
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
                values[j + i * sizeX] = noise >= -0.15D ? LANDS[nextInt(LANDS.length)] : 0;
            }
        }
        return values;
    }
}
