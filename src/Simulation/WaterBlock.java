package Simulation;

/**
 * Created by Jack on 21-07-16.
 */
public class WaterBlock implements Block {
    private int id = 5;
    public WaterBlock(int i) {

    }

    @Override
    public int getId() {
        return id;
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
