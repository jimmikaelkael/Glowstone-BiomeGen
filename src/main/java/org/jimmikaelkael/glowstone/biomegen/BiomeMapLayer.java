package org.jimmikaelkael.glowstone.biomegen;

import org.bukkit.block.Biome;

public class BiomeMapLayer extends MapLayer {

    private static final int[] WARM = new int[] {GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.DESERT),
            GlowBiome.getId(Biome.DESERT), GlowBiome.getId(Biome.SAVANNA), GlowBiome.getId(Biome.SAVANNA), GlowBiome.getId(Biome.PLAINS)};
    private static final int[] WET = new int[] {GlowBiome.getId(Biome.PLAINS), GlowBiome.getId(Biome.PLAINS),
            GlowBiome.getId(Biome.FOREST), /*GlowBiome.getId(Biome.FOREST),*/ GlowBiome.getId(Biome.BIRCH_FOREST), /*GlowBiome.getId(Biome.BIRCH_FOREST),*/
            GlowBiome.getId(Biome.ROOFED_FOREST), /*GlowBiome.getId(Biome.ROOFED_FOREST),*/ GlowBiome.getId(Biome.EXTREME_HILLS),/* GlowBiome.getId(Biome.EXTREME_HILLS),*/
            GlowBiome.getId(Biome.SWAMPLAND)};
    private static final int[] DRY = new int[] {GlowBiome.getId(Biome.PLAINS), GlowBiome.getId(Biome.FOREST),
            GlowBiome.getId(Biome.TAIGA), GlowBiome.getId(Biome.EXTREME_HILLS)};
    private static final int[] COLD = new int[] {GlowBiome.getId(Biome.ICE_PLAINS), GlowBiome.getId(Biome.ICE_PLAINS),
            GlowBiome.getId(Biome.COLD_TAIGA)};
    private static final int[] WARM_LARGE = new int[] {GlowBiome.getId(Biome.MESA_PLATEAU_FOREST), GlowBiome.getId(Biome.MESA_PLATEAU_FOREST),
            GlowBiome.getId(Biome.MESA_PLATEAU)};
    private static final int[] DRY_LARGE = new int[] {GlowBiome.getId(Biome.MEGA_TAIGA)};
    private static final int[] WET_LARGE = new int[] {GlowBiome.getId(Biome.JUNGLE)};

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
                        finalValues[j + i * sizeX] = DRY[nextInt(DRY.length)];
                        break;
                    case 2:
                        finalValues[j + i * sizeX] = WARM[nextInt(WARM.length)];
                        break;
                    case 3:
                    case 1003:
                        finalValues[j + i * sizeX] = COLD[nextInt(COLD.length)];
                        break;
                    case 4:
                        finalValues[j + i * sizeX] = WET[nextInt(WET.length)];
                        break;
                    case 1001:
                        finalValues[j + i * sizeX] = DRY_LARGE[nextInt(DRY_LARGE.length)];
                        break;
                    case 1002:
                        finalValues[j + i * sizeX] = WARM_LARGE[nextInt(WARM_LARGE.length)];
                        break;
                    case 1004:
                        finalValues[j + i * sizeX] = WET_LARGE[nextInt(WET_LARGE.length)];
                        break;
                    default:
                        finalValues[j + i * sizeX] = val;
                }
            }
        }
        return finalValues;
    }
}
