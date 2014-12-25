package org.jimmikaelkael.glowstone.biomegen;

public class BiomeVariationMapLayer extends MapLayer {

    private final MapLayer belowLayer;

    public BiomeVariationMapLayer(long seed, MapLayer belowLayer) {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int[] values = belowLayer.generateValues(x, z, sizeX, sizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                int val = values[j + i * sizeX];
                if (val > 0) {
                    setCoordsSeed(x + j, z + i);
                    val = nextInt(30) + 2;
                }
                finalValues[j + i * sizeX] = val;
            }
        }
        return finalValues;
    }
}
