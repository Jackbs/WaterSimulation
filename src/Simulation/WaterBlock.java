package Simulation;

/**
 * Created by Jack on 21-07-16.
 */
public class WaterBlock extends Block {
    private int id;
    private Block[] sideBlocks;
    private double maxDeltaP;
    private double pressure;
    private double FillLevel = 1.0;
    private final double kpaPerBlock = 9.807;

    public WaterBlock(int id, Level currentLevel) {
        super(id,currentLevel);
        sideBlocks = new Block[6];
    }

    public double getFillLevel(){
        return FillLevel;
    }

    @Override
    public double getPressure(){
        return pressure;
    }

    public void setPressure(double p){
        pressure = p;
    }
    public void setFillLevel(double fl){
        FillLevel = fl;
    }


    @Override
    public boolean isSolid() {
        return false;
    }




    /*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaterBlock that = (WaterBlock) o;
        System.out.println("Calling equalls thisid,thatid: "+id+","+that.id);
        return id == that.id;

    }
    */


    private void updateSideBlocks(){
        sideBlocks[0] = this.getBlockAbove();
        sideBlocks[1] = this.getBlockBellow();
        sideBlocks[2] = this.getBlockUp();
        sideBlocks[3] = this.getBlockDown();
        sideBlocks[4] = this.getBlockLeft();
        sideBlocks[5] = this.getBlockRight();
    }


    public double getMaxPressure() {
        updateSideBlocks();

        maxDeltaP = 0.0;
        for(int i = 0;i<sideBlocks.length;i++){
            if(sideBlocks[i] != null) {
                if (!sideBlocks[i].isSolid()) {
                    double currentBlkP = (getPressure() - (sideBlocks[i].getPressure()));
                    if(i == 0){ //block above
                        currentBlkP = currentBlkP-kpaPerBlock;
                        if(currentBlkP<0.01){
                            currentBlkP = 0.0;
                        }
                    }
                    if(i == 1){ //block bellow
                        currentBlkP = currentBlkP+kpaPerBlock;
                    }
                    if (maxDeltaP < currentBlkP) {
                        maxDeltaP = currentBlkP;
                    }
                }
            }
        }

        //System.out.println("Above,bellow,up,down,left,right["+Above+","+Bellow+","+Up+","+Down+","+Left+","+Right+",");


        return maxDeltaP;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
