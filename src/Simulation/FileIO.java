package Simulation;

import javax.imageio.ImageIO;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Jack on 09-07-16.
 */


public class FileIO {

    public Map<Point2D, Chunk> loadLevel(String Directory,Level level){

        System.out.println("Loading level From: "+Directory);


        Map<Point2D, Chunk> Level = new HashMap<Point2D, Chunk>();

        File[] files = new File(Directory).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                try {
                    Chunk chunk = new Chunk(file,level);
                    Level.put(chunk.getChunkLoc(), chunk);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
        return Level;
    }




    public Map<Block, BlockRender> getImgMap(Level level) {
        System.out.println("Creating");

        File file = new File("blockinfo");
        Map<Block, BlockRender> ImgMap = new HashMap<>();
        boolean[] blocks = new boolean[20];

        try {
            Scanner scanner = new Scanner(file);
            System.out.println("Creating Imagemap from file: "+file.getName());
            while(scanner.hasNext()){
                String s = scanner.next();
                String[] info = s.split(",");

                //System.out.println("["+info[0]+"] ["+info[1]+"] ["+info[2]+"]");



                Block b = null;
                Arrays.fill(blocks, Boolean.FALSE);

                int id = Integer.parseInt(info[0]);
                if(id != -1) {
                    blocks[id] = true;
                }

                BlockRender temprender = null;

                if(info[1].equals("a")){
                    b = new AirBlock(id, level);
                }else{
                    temprender = new BlockRender(ImageIO.read(new File("textures/"+info[2])));
                }

                if(info[1].equals("s")){
                    b = new SolidBlock(id, level);

                }else if(info[1].equals("f")){
                    b = new FluidBlock(id, level);
                    temprender.GenerateWaterlevels();
                }

                if(b != null && !(info[1].equals("a"))) {
                    ImgMap.put(b, temprender);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        level.setBlocks(blocks);
        return ImgMap;
    }

    public void saveLevel(String Directory){

    }

}
