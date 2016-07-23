package Simulation;

/**
 * Created by Jack on 21-07-16.
 */
public class WaterBlock implements Block {
    private int id;
    private double pressure;
    private double FillLevel;
    public WaterBlock(int id) {
        this.id = id;
        FillLevel = 1.0;
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


    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean isSolid() {
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WaterBlock that = (WaterBlock) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
