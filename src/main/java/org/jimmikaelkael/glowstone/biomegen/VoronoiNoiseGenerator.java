package org.jimmikaelkael.glowstone.biomegen;

import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class VoronoiNoiseGenerator extends NoiseGenerator {

    private static final int CELL_SIZE = 32;
    private static final int NEIGHBOR_RADIUS = 1;
    private static final int MAX_NEIGHBORHOOD = NEIGHBOR_RADIUS * 2 + 1;
    private int[][] seedX = new int[MAX_NEIGHBORHOOD][MAX_NEIGHBORHOOD];
    private int[][] seedZ = new int[MAX_NEIGHBORHOOD][MAX_NEIGHBORHOOD];
    private int cachedCellX, cachedCellZ;
    private final SimplexOctaveGenerator perlin;
    private final SimplexOctaveGenerator perlinX;
    private final SimplexOctaveGenerator perlinZ;

    public VoronoiNoiseGenerator(long seed) {
        perlin = new SimplexOctaveGenerator(seed + 1000, 2);
        perlinX = new SimplexOctaveGenerator(seed + 2000, 2);
        perlinZ = new SimplexOctaveGenerator(seed + 3000, 2);
        cachedCellX = cachedCellZ = Integer.MAX_VALUE;
    }

    @Override
    public double noise(double x, double y, double z) {
        int cellX = (int) x / CELL_SIZE;
        int cellZ = (int) y / CELL_SIZE;
        updateNeighborSeeds(cellX, cellZ);

        double noise = 0;
        int distance = Integer.MAX_VALUE;
        int oldDistance = distance;
        for (int i = 0; i < MAX_NEIGHBORHOOD; i++) {
            for (int j = 0; j < MAX_NEIGHBORHOOD; j++) {
                int dist = (seedX[i][j] - (int) x) * (seedX[i][j] - (int) x) + (seedZ[i][j] - (int) y) * (seedZ[i][j] - (int) y);
                if (dist < distance) {
                    oldDistance = distance;
                    distance = dist;
                    noise = perlin.noise(i + cellX - NEIGHBOR_RADIUS, j + cellZ - NEIGHBOR_RADIUS, 0.8, 0.7, true);
                } else if (dist < oldDistance) {
                    oldDistance = dist;
                }
            }
        }
        return noise;
    }

    private void updateNeighborSeeds(int cellX, int cellZ) {
        if (cellX == cachedCellX && cellZ == cachedCellZ) {
            return;
        }
        int noiseBaseX = cellX - NEIGHBOR_RADIUS;
        int noiseBaseZ = cellZ - NEIGHBOR_RADIUS;
        for (int x = 0; x < MAX_NEIGHBORHOOD; x++) {
            for (int z = 0; z < MAX_NEIGHBORHOOD; z++) {
                int nX = noiseBaseX + x;
                int nZ = noiseBaseZ + z;
                int offsetX = (int) (perlinX.noise(nX, nZ, 0.8, 0.7, true) * CELL_SIZE);
                int offsetZ = (int) (perlinZ.noise(nX, nZ, 0.8, 0.7, true) * CELL_SIZE);
                seedX[x][z] = nX * CELL_SIZE + offsetX;
                seedZ[x][z] = nZ * CELL_SIZE + offsetZ;
            }
        }
        cachedCellX = cellX;
        cachedCellZ = cellZ;
    }
}
