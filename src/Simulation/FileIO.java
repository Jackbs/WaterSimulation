package Simulation;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jack on 09-07-16.
 */


public class FileIO {



    public FileIO() {

    }

    public Map<Point2D, Chunk> loadLevel(String Directory){

        System.out.println("Loading Level From: "+Directory);


        Map<Point2D, Chunk> Level = new HashMap<Point2D, Chunk>();

        File[] files = new File(Directory).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                try {
                    Chunk chunk = new Chunk(file);
                    Level.put(chunk.getChunkLoc(), chunk);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
        return Level;
    }

    public void saveLevel(String Directory){


    }

}
