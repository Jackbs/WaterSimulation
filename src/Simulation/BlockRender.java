package Simulation;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 * Created by Jack on 19/07/2016.
 */
public class BlockRender {

    private float hightdarken = 1.9f;
    BufferedImage[] NormalImages = new BufferedImage[10];

    public BlockRender(BufferedImage read) {
        NormalImages[1] = read;
        GenerateNormalImages();
    }

    private void GenerateNormalImages() {
        BufferedImage baseImage = NormalImages[1];
        for(int i = 2;i<10;i++){
            BufferedImage bimage = new BufferedImage(baseImage.getWidth(null), baseImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.drawImage(baseImage, 0, 0, null);
            g.dispose();


            float scaleFactor = (float)(((double)hightdarken)/(i)*0.5);

            System.out.println(i+" :SF: "+scaleFactor);
            //float scaleFactor = (float)Math.pow(0.6, t);
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            bimage = op.filter(bimage, null);
            NormalImages[i] = bimage;
        }


    }

    public BufferedImage GetNormalImage(int depth){

        if(depth != 1) {
            //System.out.println("Getting an image for depth: " + depth);
        }

        if(depth>9){
            return  NormalImages[9];
        }
        return NormalImages[depth];
    }

}
