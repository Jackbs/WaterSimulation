

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
	

	
	int blksze = 10;
	
	double scale = 1.0;
	
	double xOrg;
	double yOrg;

	Level level;
	
	public Display(Level level) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = 1366; //screenSize.getWidth();
		double height = 768; //screenSize.getHeight();
		this.level = level;
		UI.setWindowSize((int) width, (int) height);
		UI.initialise();
		UI.setDivider(0.15);
		UI.setImmediateRepaint(false);
		CreateBlockRenders();
	}

	private void CreateBlockRenders(){
		try {
			System.out.println("Putting stuff in imgmap");
			ImgMap.put(new SolidBlock(-1,level),new BlockRender(ImageIO.read(new File("void_top.png"))));
			ImgMap.put(new SolidBlock(1,level),new BlockRender(ImageIO.read(new File("stone_top.png"))));
			ImgMap.put(new SolidBlock(2,level),new BlockRender(ImageIO.read(new File("grass_top.png"))));
			ImgMap.put(new SolidBlock(3,level),new BlockRender(ImageIO.read(new File("dirt_top.png"))));
			ImgMap.put(new WaterBlock(5,level),new BlockRender(ImageIO.read(new File("water_top.png"))));

		} catch (IOException e) {

		}


	}
	
	public void updateDisplay(Level level, int zLevel, double x, double y, double scale,int currentblock) {
		this.xOrg = x;
		this.yOrg = y;		
		this.scale = scale;

		level.getLevelmap().forEach( (k,v) -> ShowChunk(v,k, zLevel) );
		
		//ShowChunk(level, zLevel);
		ShowUI(zLevel, currentblock);
		UI.repaintGraphics();
	}
	
	public void ShowUI(int zLevel, int currentblock){
		int xTopWidth = (int)(UI.getCanvasWidth()*topXscale);
		int yTopWidth = (int)(UI.getCanvasHeight()*topYscale);
		UI.setFontSize(20);
		UI.setLineWidth(3.0);
		UI.setColor(Color.WHITE);
		UI.fillRect(5, 4, xTopWidth, yTopWidth+5);

		UI.setColor(Color.BLACK);
		UI.drawRect(5, 4, xTopWidth, yTopWidth+5);

		UI.drawString("level:"+Integer.toString(zLevel), 8, yTopWidth+4);
		UI.drawString("Scale:"+Double.toString((double) Math.round((scale * 100)) / 100), 80, yTopWidth+4);

		if(currentblock != 0) {
			if(currentblock != 5) {
				UI.drawImage(ImgMap.get(new SolidBlock(currentblock,level)).GetNormalImage(1), 6, 35, 49, 49);
			}else{
				WaterBlock wb = new WaterBlock(currentblock,level);
				//System.out.println(wb.getId()+""+wb.getClass());
				if(ImgMap.get(wb) == null){
					System.out.println("null_stuff");
				}
				UI.drawImage(ImgMap.get(wb).GetNormalImage(1), 6, 35, 49, 49);
			}
		}else{
			UI.setColor(Color.WHITE);
			UI.fillRect(6, 35, 49, 49);
		}
		UI.setColor(Color.BLACK);
		UI.drawRect(6, 35, 49, 49);
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
				//System.out.println("WorkingBlocktype: "+workingBlock.getClass()+" Working Block ID: "+workingBlock.getId()+" Type: "+ImgMap.get(workingBlock));


				boolean foundblock = false;
				BufferedImage img = null;
				if((workingBlock != null) && (workingBlock.getId() != 0)) {
					img = (ImgMap.get(workingBlock)).GetNormalImage(1);

				}else{

					foundblock = false;
					for(int t = zLevel;t>=0;t--){

						if(chunk.getBlock(i,j,t) != null) {
							if(chunk.getBlock(i,j,t).getId() != 0) {
								foundblock = true;
								img = ImgMap.get(chunk.getBlock(i, j, t)).GetNormalImage(zLevel + 1 - t);

								//img = ImgMap.get(chunk.getBlock(i,j,t)).GetNormalImage(1);

								break;
							}
						}
					}
					if(!foundblock){
						img = ImgMap.get(new SolidBlock(-1,level)).GetNormalImage(1);

					}
				}
				
				if(img != null) {
					UI.drawImage(img, Xchunkoffset + (scale * i * blksze + xOrg), Ychunkoffset + scale * j * blksze + yOrg, blksze * scale * 1.1, blksze * scale * 1.1);
					if(((workingBlock != null)) && !workingBlock.isSolid()) {
						UI.setFontSize((int)(6*(scale)));
						UI.drawString(String.valueOf((int)workingBlock.getPressure()),(blksze*scale*0.15)+Xchunkoffset + (scale * (i) * blksze + xOrg), (blksze*scale*0.85)+(Ychunkoffset + scale * (j) * blksze + yOrg));
					}
				}
				//UI.drawImage(Texture, i*blksze, j*blksze, blksze, blksze);
				//UI.fillRect(i*blksze, j*blksze, blksze, blksze);
			}
		}
		
	}



}
