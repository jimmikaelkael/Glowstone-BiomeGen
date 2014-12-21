package org.jimmikaelkael.glowstone.biomegen;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.block.Biome;

public class ShoreMapLayer extends MapLayer {

    private static final Set<Integer> OCEANS = new HashSet<Integer>();
    private final MapLayer belowLayer;

    public ShoreMapLayer(long seed, MapLayer belowLayer) {
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
                // - if it's not ocean and it's surrounded by at least 1 ocean cell
                // it turns the center value into beach.
                int upperVal = values[j + 1 + i * gridSizeX];
                int lowerVal = values[j + 1 + (i + 2) * gridSizeX];
                int leftVal = values[j + (i + 1) * gridSizeX];
                int rightVal = values[j + 2 + (i + 1) * gridSizeX];
                int centerVal = values[j + 1 + (i + 1) * gridSizeX];
                setCoordsSeed(x + j, z + i);
                if (!OCEANS.contains(centerVal) && (OCEANS.contains(upperVal) || OCEANS.contains(lowerVal) ||
                        OCEANS.contains(leftVal) || OCEANS.contains(rightVal))) {
                    finalValues[j + i * sizeX] = GlowBiome.getId(Biome.BEACH);
                } else {
                    finalValues[j + i * sizeX] = centerVal;
                }
            }
        }
        return finalValues;
    }

    static {
        OCEANS.add(GlowBiome.getId(Biome.OCEAN));
        OCEANS.add(GlowBiome.getId(Biome.DEEP_OCEAN));
    }
}