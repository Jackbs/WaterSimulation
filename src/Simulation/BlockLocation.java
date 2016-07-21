package Simulation;

import java.awt.*;
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
        Point2D thisp = this.p;
        if((this.x+x)>15){
            thisp = new Point(1+(int)thisp.getX(),(int)thisp.getY());
        }
        if((this.x+x)<0){
            thisp = new Point(((int)thisp.getX())-1,(int)thisp.getY());
        }
        if((this.y+y)>15){
            thisp = new Point((int)thisp.getX(),1+(int)thisp.getY());
        }
        if((this.y+y)<0){
            thisp = new Point((int)thisp.getX(),((int)thisp.getY())-1);
        }
        return new BlockLocation(this.x+x,this.y+y,this.z,thisp);

    }
}
