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
    WaterSimulation(){

    }

    public Level doWaterSimTick(Level ThisLevel){
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

        updatePressure(highestliq,101.325);

        return workingLevel;
    }

    public void updatePressure(BlockLocation blkloc,double topPressure){

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)) == null)||(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)).getId() == 0)){ //There is air or null above the block
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            thisWaterBlock.setPressure(topPressure+10.1325*thisWaterBlock.getFillLevel());
        }
        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)) != null)&&(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, 1)).getId() == 0)){
            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            thisWaterBlock.setPressure(topPressure+10.1325);
        }

        if((workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, -1)) != null)&&(!(workingLevel.getBlock(blkloc.offsetBlkLoc(0, 0, -1)).isSolid()))){

            WaterBlock thisWaterBlock = (WaterBlock)workingLevel.getBlock(blkloc);
            updatePressure(blkloc.offsetBlkLoc(0, 0, -1),thisWaterBlock.getPressure());
        }

        WaterGroup1.add(blkloc);
    }
}
