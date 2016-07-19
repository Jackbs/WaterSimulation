

package Simulation;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import ecs100.UI;

public class Display {
	Map<Block, BlockRender> ImgMap = new HashMap<>();
	Map<Integer, String> TextureMap = new HashMap<Integer, String>();
	Map<Integer, Color> ColorMap = new HashMap<Integer, Color>();
	
	double topXscale = 0.3;
	double topYscale = 0.026;
	
	float hightdarken = 1.9f;
	
	int blksze = 10;
	
	double scale = 1.0;
	
	double xOrg;
	double yOrg;
	
	public Display() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = 1366; //screenSize.getWidth();
		double height = 768; //screenSize.getHeight();

		UI.setWindowSize((int) width, (int) height);
		UI.initialise();
		UI.setDivider(0.15);
		UI.setImmediateRepaint(false);

		CreateBlockRenders();
	}

	private void CreateBlockRenders(){
		try {
			ImgMap.put(new BasicBlock(-1),new BlockRender(ImageIO.read(new File("void_top.png"))));
			ImgMap.put(new BasicBlock(1),new BlockRender(ImageIO.read(new File("stone_top.png"))));
			ImgMap.put(new BasicBlock(2),new BlockRender(ImageIO.read(new File("grass_top.png"))));
			ImgMap.put(new BasicBlock(3),new BlockRender(ImageIO.read(new File("dirt_top.png"))));

			//ImgMap.put(new BasicBlock(-1),new BufferedImage[]{null,ImageIO.read(new File("void_top.png"))});
			//ImgMap.put(new BasicBlock(1),new BufferedImage[]{null,ImageIO.read(new File("stone_top.png"))});
			//ImgMap.put(new BasicBlock(2),new BufferedImage[]{null,ImageIO.read(new File("grass_top.png"))});
			//ImgMap.put(new BasicBlock(3),new BufferedImage[]{null,ImageIO.read(new File("dirt_top.png"))});

		} catch (IOException e) {

		}

		TextureMap.put(-1,"void_top.png");
		TextureMap.put(1,"stone_top.png");
		TextureMap.put(2,"grass_top.png");
		TextureMap.put(3,"dirt_top.png");
	}




	public void preMakeImg(){
		/*
		BufferedImage img = ImgMap.get(id);
		img = ImgMap.get(chunk.getBlock(i,j,t));
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		t = t-1;
		float scaleFactor = (float)((double)(t)*hightdarken/(double)zLevel);

		if(t == 0 || t == -1){
			scaleFactor = (float)((double)(0)+0.5/(double)zLevel);
		}
		t = t+1;

		//UI.println("SF: "+scaleFactor+" t:"+t+" zLevel:"+zLevel+" Actual:"+value);
		//float scaleFactor = (float)Math.pow(0.6, t);
		RescaleOp op = new RescaleOp(scaleFactor, 0, null);
		bimage = op.filter(bimage, null);
		img = bimage;
		*/
	}
	
	public void updateDisplay(Map<Point2D, Chunk> level, int zLevel, double x, double y, double scale) {
		this.xOrg = x;
		this.yOrg = y;		
		this.scale = scale;
		level.forEach( (k,v) -> ShowChunk(v,k, zLevel) );
		
		//ShowChunk(level, zLevel);
		ShowUI(zLevel);
		UI.repaintGraphics();
	}
	
	public void ShowUI(int zLevel){
		int xTopWidth = (int)(UI.getCanvasWidth()*topXscale);
		int yTopWidth = (int)(UI.getCanvasHeight()*topYscale);
		UI.setFontSize(20);
		UI.setColor(Color.WHITE);
		UI.fillRect(5, 5, xTopWidth, yTopWidth);
		UI.fillRect(5, 30, 50, 200);
		UI.setColor(Color.BLACK);
		UI.drawRect(5, 5, xTopWidth, yTopWidth);
		UI.drawRect(5, 30, 50, 200);
		UI.drawString("Level:"+Integer.toString(zLevel), 5, yTopWidth+4);
		UI.drawString("Scale:"+Double.toString((double) Math.round((scale * 100)) / 100), 80, yTopWidth+4);
	}
	
	public void ShowChunk(Chunk chunk, Point2D Point, int zLevel) { //will be changed to 2D array of chunks in future	
		double Xchunkoffset = 16*(scale*blksze*Point.getX());
		double Ychunkoffset = 16*(scale*blksze*Point.getY());
		UI.setColor(Color.BLACK);
		UI.fillRect(xOrg, yOrg,5,5);


		for(int j = 0;j<16;j++){
			for(int i = 0;i<16;i++){			
				Block workingBlock = chunk.getBlock(i,j,zLevel);

				//BufferedImage img = workingBlock.getImage(1); //Get the basic image
				System.out.println("WorkingBlocktype: "+workingBlock.getClass()+" Working Block ID: "+workingBlock.getId()+" Type: "+ImgMap.get(workingBlock));

				BufferedImage img = (ImgMap.get(workingBlock)).GetNormalImage(1);

				boolean foundblock = false;
				if(workingBlock.getId() == 0){
					foundblock = false;
					for(int t = zLevel;t>=0;t--){
						if(chunk.getBlock(i,j,t).getId() != 0){
							foundblock = true;

							img = ImgMap.get(chunk.getBlock(i,j,t)).GetNormalImage(zLevel+1-t);

							break;
						}
					}
					if(!foundblock){
						img = ImgMap.get(new BasicBlock(-1)).GetNormalImage(1);

					}
				}
				
				
				UI.drawImage(img, Xchunkoffset+(scale*i*blksze+xOrg), Ychunkoffset+scale*j*blksze+yOrg, blksze*scale*1.1, blksze*scale*1.1);
				//UI.drawImage(Texture, i*blksze, j*blksze, blksze, blksze);
				//UI.fillRect(i*blksze, j*blksze, blksze, blksze);
			}
		}
		
	}



	
}
