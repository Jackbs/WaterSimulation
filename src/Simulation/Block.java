package Simulation;

/**
 * Created by Jack on 25-07-16.
 */
public class Block {
    private int id;
    private BlockLocation blkloc;
    public Level currentLevel;

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

    public boolean isSolid() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Block that = (Block) o;
        //System.out.println("Calling equalls on blk thisid,thatid: "+id+","+that.id);
        return id == that.getId();

    }

    public Block getBlockAbove(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,0,1));
    }

    public Block getBlockBellow(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,0,-1));
    }

    public Block getBlockUp(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,1,0));
    }

    public Block getBlockDown(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(0,1,0));
    }

    public Block getBlockLeft(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(-1,0,0));
    }

    public Block getBlockright(){
        return currentLevel.getBlock(blkloc.offsetBlkLoc(1,0,0));
    }
}

