package Simulation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jack on 23-07-16.
 * Unused for now
 */
public class WaterSimulation{
    private Level workingLevel;
    private Set WaterGroup1 = new HashSet<BlockLocation>();
    private double Atmosphericpressure = 101.325;
    private final double kpaPerBlock = 9.807;
    WaterSimulation(){
        Atmosphericpressure = 0.0;
    }

    public Level doWaterSimTick(Level ThisLevel){
        WaterGroup1.clear();
        workingLevel = ThisLevel;
        System.out.println("Doing water simulation tick");
        Iterator iter = workingLevel.WaterBlockPos.iterator();
        BlockLocation highestliq = new BlockLocation(0,0,0,null);
        while (iter.hasNext()) {
            BlockLocation blkloc = ((BlockLocation)iter.next());
            if(highestliq.z<blkloc.z){
                highestliq = blkloc;
            }
        }
        highestliq.printBlockInfomation(workingLevel);

        updatePressure(highestliq,Atmosphericpressure);

        Iterator I = WaterGroup1.iterator();
        while(I.hasNext()){
            WaterBlock w = ((WaterBlock)workingLevel.getBlock(((BlockLocation)I.next())));
            System.out.print("Pressure: "+w.getPressure());
            w.getBlkLoc().printBlockInfomation(workingLevel);
            Block b = w.getBlockright();
            BlockLocation bl = b.getBlkLoc();
            bl.printBlockInfomation(workingLevel);
        }
        return workingLevel;
    }

    //Get's the pressure of any block, assumes that level.isValidBlockLoc has been called to check that null blocks are air and not out of bounds
    public double getPressure(BlockLocation blkloc){
        if((workingLevel.getBlock(blkloc) == null)||(workingLevel.getBlock(blkloc).getId() == 0)){
            return Atmosphericpressure; //Block is air
        }else{
            return ((WaterBlock)workingLevel.getBlock(blkloc)).getPressure();
        }
    }

    public void updatePressure(BlockLocation blkloc,double topPressure){

        if(WaterGroup1.contains(blkloc)){ //WaterBlock has allready been added
            return;
        }else{
            WaterGroup1.add(blkloc);
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)) == null)||(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)).getId() == 0)){ //There is air or null above the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            thisWaterBlock.setPressure(kpaPerBlock*thisWaterBlock.getFillLevel());
        }
        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)) != null)&& !(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)).isSolid())){ //There is a water block above this block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            thisWaterBlock.setPressure(topPressure);
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(1, 0, 0)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(1, 0, 0)).isSolid()))){ //There is a water to the right of the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(1, 0, 0),thisWaterBlock.getPressure());
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 1, 0)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 1, 0)).isSolid()))){ //There is a water to the up of the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(0, 1, 0),thisWaterBlock.getPressure());
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(-1, 0, 0)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(-1, 0, 0)).isSolid()))){ //There is a water to the left of the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(-1, 0, 0),thisWaterBlock.getPressure());
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, -1, 0)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(0, -1, 0)).isSolid()))){ //There is a water to the down of the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(0, -1, 0),thisWaterBlock.getPressure());
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, -1)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, -1)).isSolid()))){ //There is a water block bellow this block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(0, 0, -1),thisWaterBlock.getPressure()+kpaPerBlock);
        }
    }
}
