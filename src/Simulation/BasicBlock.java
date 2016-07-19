package Simulation;

import java.awt.image.BufferedImage;

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
}
