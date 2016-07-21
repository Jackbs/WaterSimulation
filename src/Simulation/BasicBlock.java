package Simulation;

import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Jack on 16-07-16.
 */
public class BasicBlock implements Block{

    private int id;
    private BufferedImage[] renderedblocks = new BufferedImage[10];

    public BasicBlock(int id){
        this.id = id;
        //this.renderedblocks = renderedblocks;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getImage(int imagenumber) {
        return renderedblocks[imagenumber];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicBlock that = (BasicBlock) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}

