package Simulation;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Jack on 21-07-16.
 */
public class Level {

    Set WaterBlockPos = new HashSet<BlockLocation>();

    FileIO fileIO;
    private double Atmosphericpressure = 101.325;
    private Map<Point2D, Chunk> level = new HashMap<Point2D, Chunk>();

    public Level(String worldname) {
        fileIO = new FileIO();
        level = fileIO.loadLevel(worldname,this);
        Atmosphericpressure = 0.0;
    }

    public Map<Point2D, Chunk> getLevelmap() {
        return level;
    }


    public Block getBlock(BlockLocation blkloc) {
        if((isValidBlockLoc(blkloc))){

            if(level.get(blkloc.p).getBlock(blkloc) == null) { //Trying to get an air block, therefore set location to air block
                setBlock(blkloc, new Block(0, this));
            }
            return (level.get(blkloc.p).getBlock(blkloc));
        }else{
            return null;
        }
    }

    public void setBlockRaw() {

    }

    public void setBlockChunk(Point2D p, int x, int y, int z, Block b) {
        level.get(p).setBlock(x, y, z, b);
    }

    public void setBlock(BlockLocation blkloc, Block b) {
        //System.out.println("Setting Block of type: "+b.getId()+" [x:"+blkloc.x+" y:"+ blkloc.y + " z:"+blkloc.z+" ]");
        if (isValidBlockLoc(blkloc)) {
            level.get(blkloc.p).setBlock(blkloc.x, blkloc.y, blkloc.z, b);

            if (!(b.isSolid())) {
                if (WaterBlockPos.add(blkloc)) {
                    System.out.println("Adding Block:"+blkloc.stringBlockInfomation(this));

                }
            }else{
                if (WaterBlockPos.remove(blkloc)) {
                    System.out.println("Removing Block:"+blkloc.stringBlockInfomation(this));
                }
            }

        }
    }

    //Get's the pressure of any block, assumes that level.isValidBlockLoc has been called to check that null blocks are air and not out of bounds
    public double getPressure(BlockLocation blkloc){
        if((this.getBlock(blkloc) == null)||(this.getBlock(blkloc).getId() == 0)){
            return Atmosphericpressure; //Block is air
        }else{
            return ((WaterBlock)this.getBlock(blkloc)).getPressure();
        }
    }

    //Used to determine if a block-location is valid
    public boolean isValidBlockLoc(BlockLocation blkloc){
        if(!level.containsKey(blkloc.p)){
            return false;
        }
        if(blkloc.z<0){
            return false;
        }
        return blkloc.z <= 128;
    }
}
