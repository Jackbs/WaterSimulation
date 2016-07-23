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

    public double distance(BlockLocation blkloc){
        if(this.p == blkloc.p) {
            return (Math.hypot(this.x - blkloc.x, this.y - blkloc.y));
        }else{
            return (Math.hypot(this.x - (blkloc.x)-(blkloc.p.getX()-this.p.getX())*16, this.y - blkloc.y-(blkloc.p.getY()-this.p.getY())*16));
        }

    }

    public void printBlockInfomation(Level l){
        System.out.println("[Blkinfo] ID:"+l.getBlock(this).getId()+" [x,y,z]=["+this.x+","+this.y+","+this.z+"] [Chunk][x,y]=["+(int)this.p.getX()+","+(int)this.p.getY()+"]");
    }

    public BlockLocation offsetBlkLoc(int xOffset,int yOffset, int zOffset){
        Point2D thisp = this.p;
        int thisx = this.x;
        int thisy = this.y;
        int thisz = this.z;
        if((this.x+xOffset)>15){
            thisp = new Point(1+(int)thisp.getX(),(int)thisp.getY());
            thisx = thisx-16;
        }
        if((this.x+xOffset)<0){
            thisp = new Point(((int)thisp.getX())-1,(int)thisp.getY());
            thisx = thisx+16;
        }
        if((this.y+yOffset)>15){
            thisp = new Point((int)thisp.getX(),1+(int)thisp.getY());
            thisy = thisy-16;
        }
        if((this.y+yOffset)<0){
            thisp = new Point((int)thisp.getX(),((int)thisp.getY())-1);
            thisy = thisy+16;
        }
        return new BlockLocation(thisx+xOffset,thisy+yOffset,thisz+zOffset,thisp);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlockLocation that = (BlockLocation) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        if (z != that.z) return false;
        return p != null ? p.equals(that.p) : that.p == null;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + (p != null ? p.hashCode() : 0);
        return result;
    }
}
