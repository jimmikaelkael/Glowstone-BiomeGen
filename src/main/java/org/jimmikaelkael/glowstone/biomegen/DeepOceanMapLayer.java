package org.jimmikaelkael.glowstone.biomegen;

public class DeepOceanMapLayer extends MapLayer {

    private final MapLayer belowLayer;

    public DeepOceanMapLayer(long seed, MapLayer belowLayer) {
        super(seed);
        this.belowLayer = belowLayer;
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {
        int gridX = x - 1;
        int gridZ = z - 1;
        int gridSizeX = sizeX + 2;
        int gridSizeZ = sizeZ + 2;
        int[] values = belowLayer.generateValues(gridX, gridZ, gridSizeX, gridSizeZ);

        int[] finalValues = new int[sizeX * sizeZ];
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                // This applies deep oceans using Von Neumann neighborhood
                // it takes a 3x3 grid with a cross shape and analyzes values as follow
                // 0X0
                // XxX
                // 0X0
                // the grid center value decides how we are proceeding:
                // - if it's ocean and it's surrounded by 4 ocean cells there is 19/20 chances
                // it turns the center value into deep ocean and 1/20 chance it stay ocean.
                int upperVal = values[j + 1 + i * gridSizeX];
                int lowerVal = values[j + 1 + (i + 2) * gridSizeX];
                int leftVal = values[j + (i + 1) * gridSizeX];
                int rightVal = values[j + 2 + (i + 1) * gridSizeX];
                int centerVal = values[j + 1 + (i + 1) * gridSizeX];
                setCoordsSeed(x + j, z + i);
                if (centerVal == 0 && upperVal == 0 && lowerVal == 0 && leftVal == 0 && rightVal == 0) {
                    finalValues[j + i * sizeX] = nextInt(20) == 0 ? 0 : Biome.DEEP_OCEAN.id;
                } else {
                    finalValues[j + i * sizeX] = centerVal;
                }
            }
        }
        return finalValues;
    }
}
