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
    BufferedImage[] WaterLevels = new BufferedImage[20];
    BufferedImage base;

    public BlockRender(BufferedImage read) {
        base = read;
        GenerateNormalImages();
    }

    private void GenerateNormalImages() {
        NormalImages[1] = base;
        BufferedImage baseImage = NormalImages[1];
        for(int i = 2;i<10;i++){
            BufferedImage bimage = new BufferedImage(baseImage.getWidth(null), baseImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.drawImage(baseImage, 0, 0, null);
            g.dispose();


            float scaleFactor = (float)(((double)hightdarken)/(i)*0.5);

            //System.out.println(i+" :SF: "+scaleFactor);
            //float scaleFactor = (float)Math.pow(0.6, t);
            RescaleOp op = new RescaleOp(scaleFactor, 0, null);
            bimage = op.filter(bimage, null);
            NormalImages[i] = bimage;
        }
    }

    public void GenerateWaterlevels() {
        System.out.println("Generating water levels");
        BufferedImage baseImage = base;
        for(int i = 0;i<20;i++){
            BufferedImage bimage = new BufferedImage(baseImage.getWidth(null), baseImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.drawImage(baseImage, 0, 0, null);
            g.dispose();

            //RescaleOp op = new RescaleOp(0.8f, 0, null);
            double scaleFactor = (((double)(26-i))/20);
            //System.out.println("i, sf"+i+","+scaleFactor);

            RescaleOp op = new RescaleOp((float)scaleFactor, 0, null);
            bimage = op.filter(bimage, null);
            WaterLevels[i] = bimage;


            //RescaleOp brighterOp = new RescaleOp(0.2f, 0, null);
            // = brighterOp.filter(baseImage,null); //filtering

            //WaterLevels[i] = base;
            //WaterLevels[i] = changeBrightness(baseImage, 0.0f);
        }
    }

    public BufferedImage changeBrightness(BufferedImage src,float val){
        RescaleOp brighterOp = new RescaleOp(val, 0, null);
        return brighterOp.filter(src,null); //filtering
    }

    public BufferedImage GetWaterImage(double filllevel){
        int picToGet = (int)(filllevel*19);
        if(picToGet > 19){picToGet = 19;}
        if(picToGet < 0){picToGet = 0;}
        //System.out.println(picToGet);
        return WaterLevels[picToGet];
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
