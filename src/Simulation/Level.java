package Simulation;

import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by Jack on 21-07-16.
 */
public class Level {

    Map<Block, BlockRender> ImgMap;

    Set WaterBlockPos = new HashSet<BlockLocation>();

    private boolean[] blocks = new boolean[20];
    int numblocks = 0;

    private FileIO fileIO;
    private double Atmosphericpressure = 101.325;
    private Map<Point2D, Chunk> level = new HashMap<Point2D, Chunk>();


    public Level(String worldname) {
        fileIO = new FileIO();
        level = fileIO.loadLevel(worldname,this);
        ImgMap = fileIO.getImgMap(this);
        Atmosphericpressure = 0.0;

    }

    public Map<Point2D, Chunk> getLevelmap() {
        return level;
    }


    public Block getBlock(BlockLocation blkloc) {
        if((isValidBlockLoc(blkloc))){

            if(level.get(blkloc.p).getBlock(blkloc) == null) { //Trying to get an air block, therefore set location to air block
                //System.out.println("Adding New Air Block at: "+blkloc.stringBlockInfomation(this));
                setBlock(blkloc, new Block(0, this));
            }
            return (level.get(blkloc.p).getBlock(blkloc));
        }else{
            return null;
        }
    }

    public double getGlobalPressure() {
        return Atmosphericpressure;
    }

    public void setBlockChunk(Point2D p, int x, int y, int z, Block b) {
        level.get(p).setBlock(x, y, z, b);
    }

    public void setBlock(BlockLocation blkloc, Block b) {
        //System.out.println("Setting Block of type: "+b.getId()+" [x:"+blkloc.x+" y:"+ blkloc.y + " z:"+blkloc.z+" ]");
        if (isValidBlockLoc(blkloc)) {
            level.get(blkloc.p).setBlock(blkloc.x, blkloc.y, blkloc.z, b);

            if(getBlock(blkloc).getId() == 5) {
                if (WaterBlockPos.add(blkloc)) {
                    ((FluidBlock)b).updateSideBlocks();
                    //System.out.println("Add Water Block:" + blkloc.stringBlockInfomation(this));
                }
            }else{
                if (WaterBlockPos.remove(blkloc)) {
                    //System.out.println("Remove Water Block:" + blkloc.stringBlockInfomation(this));
                }
            }
        }
    }

    //Get's the pressure of any block, assumes that level.isValidBlockLoc has been called to check that null blocks are air and not out of bounds
    public double getPressure(BlockLocation blkloc){
        if((this.getBlock(blkloc) == null)||(this.getBlock(blkloc).getId() == 0)){
            return Atmosphericpressure; //Block is air
        }else{
            return ((FluidBlock)this.getBlock(blkloc)).getPressure();
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

    public boolean idExsists(int id){
        return blocks[id];
    }

    public void setBlocks(boolean[] blocks){
        System.out.println("Setting blocks");

        this.blocks = blocks;

        numblocks = 0;
        for(int i = 0;i<this.blocks.length;i++){
            if(blocks[1]){
                numblocks++;
            }
        }
    }

    public Map<Block,BlockRender> getImgMap() {
        return ImgMap;
    }
}
