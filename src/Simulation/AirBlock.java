package Simulation;

/**
 * Created by Jack on 27-07-16.
 */
public class AirBlock extends Block {
    public AirBlock(int id, Level currentLevel) {
        super(id, currentLevel);
    }

    @Override
    public boolean isSolid() {
        return false;
    }
}
