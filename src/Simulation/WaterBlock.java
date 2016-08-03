package Simulation;

/**
 * Created by Jack on 21-07-16.
 */
public class WaterBlock extends Block {
    private int id;
    private double[] maxDeltaPside;
    private double maxDeltaP;
    private double pressure;
    private double FillLevel = 1.0;

    public WaterBlock(int id, Level currentLevel) {
        super(id,currentLevel);
        maxDeltaPside = new double[6];
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



    public double getMaxPressure() {

        if(!this.getBlockAbove().isSolid()) {
            //Above = getPressure() - (this.getBlockAbove()).getPressure();
        }
        if(!this.getBlockBellow().isSolid()) {
            maxDeltaPside[1] = getPressure() - (this.getBlockBellow()).getPressure();
        }
        if(!this.getBlockUp().isSolid()) {
            maxDeltaPside[2] = getPressure() - (this.getBlockUp()).getPressure();
        }
        if(!this.getBlockDown().isSolid()) {
            maxDeltaPside[3] = getPressure() - (this.getBlockDown()).getPressure();
        }
        if(!this.getBlockLeft().isSolid()) {
            maxDeltaPside[4] = getPressure() - (this.getBlockLeft()).getPressure();
        }
        if(!this.getBlockRight().isSolid()) {
            maxDeltaPside[5] = getPressure() - (this.getBlockRight()).getPressure();
        }
        maxDeltaP = 0.0;
        for(int i = 0;i<maxDeltaPside.length;i++){
            if(maxDeltaP<maxDeltaPside[i]){
                maxDeltaP = maxDeltaPside[i];
            }
        }
        //System.out.println("Above,bellow,up,down,left,right["+Above+","+Bellow+","+Up+","+Down+","+Left+","+Right+",");
        //double Max = Math.max(Math.max(Left,Right),Math.max(Math.max(Above,Bellow),Math.max(Down,Up)));

        return maxDeltaP;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + id;
        return result;
    }
}
