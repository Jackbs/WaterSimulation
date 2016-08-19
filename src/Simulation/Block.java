package Simulation;

/**
 * Created by Jack on 25-07-16.
 */
public class Block {
    private int id;
    private BlockLocation blkloc;
    public Level currentLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block block = (Block) o;

        return id == block.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Block(int id, Level currentLevel){
        this.id = id;
        this.currentLevel = currentLevel;
    }

    public void setBlkLoc(BlockLocation b){
        blkloc = b;
    }

    public BlockLocation getBlkLoc(){
        return blkloc;
    }

    public int getId() {
        return id;
    }

    public double getPressure(){
        if(id == 0) {
            return currentLevel.getGlobalPressure();
        }else{
            return 6969;
        }
    }

    public boolean isSolid() {
        if(id == 0) {
            return false;
        }else{
            return true;
        }
    }

    public boolean isFluid() {
        return false;
    }

    public void printAllData(){
        System.out.print(blkloc.stringBlockInfomation(currentLevel));
        System.out.format(" [%s,%s] Pressure:[%.1f]", isSolid() ? "Solid" : "Nonsolid", isFluid() ? "Fluid" : "No Fluid",getPressure());
        if(isFluid()) {
            ((FluidBlock)this).FluidInfo();
        }else{
            System.out.println();
        }
    }

    public Block getBlockAbove(){return currentLevel.getBlock(blkloc.offsetBlkLoc(0,0,1)); }

    public Block getBlockBellow(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,0,-1));
    }

    public Block getBlockUp(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,-1,0));
    }

    public Block getBlockDown(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,1,0));
    }

    public Block getBlockLeft(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(-1,0,0));
    }

    public Block getBlockRight(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(1,0,0));
    }
}

