package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Jack on 19/07/2016.
 */
public class BlockRender {

    BufferedImage[] NormalImages = new BufferedImage[10];

    public BlockRender(BufferedImage read) {
        NormalImages[1] = read;
        GenerateNormalImages();
    }

    private void GenerateNormalImages() {
        BufferedImage baseImage = NormalImages[1];
        for(int i = 2;i<10;i++){
            NormalImages[i] = null;
        }
    }

    public BufferedImage GetNormalImage(int depth){

        return NormalImages[depth];
    }

}
