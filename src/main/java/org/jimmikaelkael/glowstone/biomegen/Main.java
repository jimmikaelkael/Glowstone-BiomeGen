package org.jimmikaelkael.glowstone.biomegen;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.jimmikaelkael.glowstone.biomegen.WhittakerMapLayer.ClimateType;
import org.jimmikaelkael.glowstone.biomegen.ZoomMapLayer.ZoomType;

public class Main extends JComponent {

    private static final long serialVersionUID = 4128619461630366918L;
    private static final int ZOOM = 2;
    private static final int CHUNK_SIZE = 16;
    private static final int CHUNK_WIDTH = 110;
    private static final int CHUNK_HEIGHT = 60;
    private static final int WIDTH = CHUNK_SIZE * CHUNK_WIDTH;
    private static final int HEIGHT = CHUNK_SIZE * CHUNK_HEIGHT;
    private static long seed;   
    private BufferedImage image;
    private int[] biomeData = new int[WIDTH * HEIGHT];

    public void initialize() {
        setToolTipText("X: -, Z: -");

        MapLayer layer = new NoiseMapLayer(seed); // this is initial land spread layer
        layer = new WhittakerMapLayer(seed + 1, layer, ClimateType.WARM_WET);
        layer = new WhittakerMapLayer(seed + 1, layer, ClimateType.COLD_DRY);
        layer = new WhittakerMapLayer(seed + 2, layer, ClimateType.LARGER_BIOMES);
        for (int i = 0; i < 2; i++) {
            layer = new ZoomMapLayer(seed + 100 + i, layer, ZoomType.BLURRY);
        }
        for (int i = 0; i < 2; i++) {
            layer = new ErosionMapLayer(seed + 3 + i, layer);
        }
        layer = new DeepOceanMapLayer(seed + 4, layer);

        MapLayer layerVariation = new BiomeVariationMapLayer(seed + 200, layer);
        MapLayer layerMountains = layerVariation;
        for (int i = 0; i < 2; i++) {
            layerMountains = new ZoomMapLayer(seed + 200 + i, layerMountains);
        }

        layer = new BiomeMapLayer(seed + 5, layer);
        for (int i = 0; i < 2; i++) {
            layer = new ZoomMapLayer(seed + 200 + i, layer);
        }
        layer = new BiomeEdgeMapLayer(seed + 200, layer);
        layer = new BiomeVariationMapLayer(seed + 200, layer, layerMountains);
        layer = new RarePlainsMapLayer(seed + 201, layer);
        layer = new ZoomMapLayer(seed + 300, layer);
        layer = new ErosionMapLayer(seed + 6, layer);
        layer = new ZoomMapLayer(seed + 400, layer);
        layer = new BiomeThinEdgeMapLayer(seed + 400, layer);
        layer = new ShoreMapLayer(seed + 7, layer);
        for (int i = 0; i < ZOOM; i++) {
            layer = new ZoomMapLayer(seed + 500 + i, layer);
        }

        MapLayer layerRiver = layerMountains;
        layerRiver = new ZoomMapLayer(seed + 300, layerRiver);
        layerRiver = new ZoomMapLayer(seed + 400, layerRiver);
        for (int i = 0; i < ZOOM; i++) {
            layerRiver = new ZoomMapLayer(seed + 500 + i, layerRiver);
        }
        layerRiver = new RiverMapLayer(seed + 10, layerRiver);
        layer = new RiverMapLayer(seed + 1000, layerRiver, layer);

        layer = new SmoothMapLayer(seed + 10000, layer);

        int[] data = new int[WIDTH * HEIGHT];
        //Random random = new Random();
        for (int i = 0; i < CHUNK_WIDTH; i++) {
            for (int j = 0; j < CHUNK_HEIGHT; j++) {
                int[] ints = layer.generateValues(i * CHUNK_SIZE, j * CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
                //Color c = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                for (int x = 0; x < CHUNK_SIZE; x++) {
                    for (int z = 0; z < CHUNK_SIZE; z++) {
                        data[x + (CHUNK_SIZE * i) + (j * CHUNK_SIZE * CHUNK_WIDTH * CHUNK_SIZE)
                                + (z * CHUNK_SIZE * CHUNK_WIDTH)] = GlowBiome.getColor(ints[z * CHUNK_SIZE + x]);
                        biomeData[x + (CHUNK_SIZE * i) + (j * CHUNK_SIZE * CHUNK_WIDTH * CHUNK_SIZE)
                             + (z * CHUNK_SIZE * CHUNK_WIDTH)] = ints[z * CHUNK_SIZE + x];
                    }
                }
            }
        }

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, WIDTH, HEIGHT, data, 0, WIDTH);
    }

    @Override
    public void paint(Graphics g) {
        if (image == null)
            initialize();
        g.drawImage(image, 0, 0, this);
    }

    @Override
    public Point getToolTipLocation(MouseEvent e) {
        setToolTipText("X: " + (e.getPoint().x - WIDTH / 2) + ", Z: " + (e.getPoint().y - HEIGHT / 2) + " Biome: " + GlowBiome.fromId(biomeData[e.getPoint().x + e.getPoint().y * WIDTH]).name());
        //return new Point(e.getPoint().x + 20, e.getPoint().y);
        return new Point(10, 10);
    }

    public static void main(String[] args) {
        Random random = new Random();
        seed = random.nextLong();
        seed = -6243259992073630573L;

        JFrame f = new JFrame("Seed: " + seed);
        f.getContentPane().add(new Main());
        f.setSize(WIDTH, HEIGHT);
        f.setLocation(100, 100);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.setVisible(true);
    }
}
