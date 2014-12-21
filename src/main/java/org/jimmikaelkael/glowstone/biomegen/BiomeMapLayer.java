package org.jimmikaelkael.glowstone.biomegen;

import org.bukkit.block.Biome;

public class BiomeMapLayer extends MapLayer {

    private static final int[] WET = new int[] {GlowBiome.getId(Biome.FOREST), GlowBiome.getId(Biome.BIRCH_FOREST),
        GlowBiome.getId(Biome.ROOFED_FOREST), GlowBiome.getId(Biome.JUNGLE), GlowBiome.getId(Biome.SWAMPLAND)};
    private static final int[] DRY = new int[] {GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.DESERT),
            GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.SAVANNA), GlowBiome.getId(Biome.SAVANNA)};
    private static final int[] ROCK = new int[] {GlowBiome.getId(Biome.EXTREME_HILLS), GlowBiome.getId(Biome.PLAINS),
            GlowBiome.getId(Biome.TAIGA)};
    private static final int[] SNOW = new int[] {GlowBiome.getId(Biome.ICE_PLAINS), GlowBiome.getId(Biome.ICE_PLAINS),
        GlowBiome.getId(Biome.COLD_TAIGA)};
    private static final int[] TUNDRA  = new int[] {GlowBiome.getId(Biome.TAIGA), GlowBiome.getId(Biome.FOREST)};

    private final MapLayer belowLayer;

    public BiomeMapLayer(long seed, MapLayer belowLayer) {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int[] values = belowLayer.generateValues(x, z, sizeX, sizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                setCoordsSeed(x + j, z + i);
                int val = values[j + i * sizeX];
                switch (val) {
                    case 1:
                        finalValues[j + i * sizeX] = WET[nextInt(DRY.length)];
                        break;
                    case 2:
                        finalValues[j + i * sizeX] = DRY[nextInt(WET.length)];
                        break;
                    case 3:
                        finalValues[j + i * sizeX] = ROCK[nextInt(ROCK.length)];
                        break;
                    case 4:
                        finalValues[j + i * sizeX] = SNOW[nextInt(SNOW.length)];
                        break;
                    case 5:
                        finalValues[j + i * sizeX] = TUNDRA[nextInt(TUNDRA.length)];
                        break;
                    default:
                        finalValues[j + i * sizeX] = val;
                }
            }
        }
        return finalValues;
    }
}
