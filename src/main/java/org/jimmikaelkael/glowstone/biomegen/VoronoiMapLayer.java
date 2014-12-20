package org.jimmikaelkael.glowstone.biomegen;

public class VoronoiMapLayer extends MapLayer {

    private final VoronoiNoiseGenerator voronoi;
    
    public VoronoiMapLayer(long seed) {
        super(seed);
        voronoi = new VoronoiNoiseGenerator(seed);
        
    }

    @Override
    public int[] generateValues(int x, int z, int sizeX, int sizeZ) {

        int[] values = new int[sizeX * sizeZ]; 
        for (int i = 0; i < sizeZ; i++) {
            for (int j = 0; j < sizeX; j++) {
                values[j + i * sizeX] = Math.abs((int) ((voronoi.noise(x + j, z + i) + 1 / 2) * Biome.count()));
            }
        }
        return values;
    }
}
