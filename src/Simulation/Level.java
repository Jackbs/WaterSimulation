package Simulation;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Jack on 21-07-16.
 */
public class Level {

    Set WaterBlockPos = new HashSet<BlockLocation>();

    FileIO fileIO;
    private Map<Point2D, Chunk> level = new HashMap<Point2D, Chunk>();

    public Level(String worldname) {
        fileIO = new FileIO();
        level = fileIO.loadLevel(worldname);
    }

    public Map<Point2D, Chunk> getLevelmap() {
        return level;
    }

    public Block getBlock(BlockLocation blkloc) {
        return (level.get(blkloc.p).getBlock(blkloc));
    }

    public void setBlockRaw() {

    }

    public void setBlockChunk(Point2D p, int x, int y, int z, Block b) {
        level.get(p).setBlock(x, y, z, b);
    }

    public void setBlock(BlockLocation blkloc, Block b) {
        //System.out.println("Setting Block of type: "+b.getId()+" [x:"+blkloc.x+" y:"+ blkloc.y + " z:"+blkloc.z+" ]");
        if (level.containsKey(blkloc.p)) {
            level.get(blkloc.p).setBlock(blkloc.x, blkloc.y, blkloc.z, b);
            if (!(b.isSolid())) {
                if (WaterBlockPos.add(blkloc)) {
                    System.out.print("Adding Block:");
                    blkloc.printBlockInfomation(this);
                }
            }else{
                if (WaterBlockPos.remove(blkloc)) {
                    System.out.print("Removing Block:");
                    blkloc.printBlockInfomation(this);
                }
            }

        }
    }
}
