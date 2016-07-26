package Simulation;

/**
 * Created by Jack on 21-07-16.
 */
public class WaterBlock extends Block {
    private int id;
    private double pressure;
    private double FillLevel = 1.0;

    public WaterBlock(int id, Level currentLevel) {
            super(id,currentLevel);
    }

    public double getFillLevel(){
        return FillLevel;
    }
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

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
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

    @Override
    public int hashCode() {
        return id;
    }

    public double getMaxPressure() {
        double Above = ((WaterBlock)this.getBlockAbove()).getPressure() - getPressure();
        double Bellow = ((WaterBlock)this.getBlockBellow()).getPressure() - getPressure();
        double Up = ((WaterBlock)this.getBlockUp()).getPressure() - getPressure();
        double Down = ((WaterBlock)this.getBlockDown()).getPressure() - getPressure();
        double Left = ((WaterBlock)this.getBlockLeft()).getPressure() - getPressure();
        double Right = ((WaterBlock)this.getBlockright()).getPressure() - getPressure();
        double Max = Math.max(Math.max(Left,Right),Math.max(Math.max(Above,Bellow),Math.max(Up,Down)));
        return Max;
    }
}
