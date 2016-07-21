package Simulation;

import java.awt.geom.Point2D;

/**
 * Created by Jack on 21-07-16.
 */
public class BlockLocation {
    public int x;
    public int y;
    public int z;
    public Point2D p;
    BlockLocation(int x,int y, int z,Point2D p){
        this.x = x;
        this.y = y;
        this.z = z;
        this.p = p;
    }
    public BlockLocation offsetBlkLoc(int x,int y){

        return new BlockLocation(this.x+x,this.y+y,this.z,this.p);

    }
}
