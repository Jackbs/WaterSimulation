

package Simulation;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ecs100.UI;

public class Display {
	Map<Integer, String> TextureMap = new HashMap<Integer, String>();
	Map<Integer, Color> ColorMap = new HashMap<Integer, Color>();
	
	int blksze = 50;
	
	public Display(){
		UI.initialise();
		UI.setImmediateRepaint(false);
		ColorMap.put(0,Color.white);
		ColorMap.put(1,Color.gray);
		ColorMap.put(2,Color.green);
		ColorMap.put(3,new Color(139,69,19));
		
		
		TextureMap.put(-1,"void_top.png");
		TextureMap.put(1,"stone_top.png");
		TextureMap.put(2,"grass_top.png");
		TextureMap.put(3,"dirt_top.png");
	}
	
	public void updateDisplay(Chunk onlyChunk, int zLevel) {
		
		ShowChunk(onlyChunk, zLevel);
		UI.drawString(Integer.toString(zLevel), 20, 20);
		UI.setFontSize(20);
		UI.repaintGraphics();
	}
	

	public void ShowChunk(Chunk chunk, int zLevel) { //will be changed to 2D array of chunks in future
		UI.drawString(Integer.toString(zLevel), 5, 5);
		for(int j = 0;j<16;j++){
			for(int i = 0;i<16;i++){			
				int id = chunk.getBlock(i,j,zLevel);
				String Texture = (TextureMap.get(id));
				UI.setColor(ColorMap.get(id));
				boolean foundblock = false;
				if(id == 0){
					foundblock = false;
					for(int t = zLevel;t>=0;t--){
						if(chunk.getBlock(i,j,t) != 0){
							foundblock = true;
							UI.setColor(ColorMap.get(chunk.getBlock(i,j,t)).darker());
							Texture = (TextureMap.get(chunk.getBlock(i,j,t)));
							break;
						}
					}
					if(!foundblock){
						UI.setColor(Color.black);
						Texture = "void_top.png";
					}
				}
				UI.drawImage(Texture, i*blksze, j*blksze, blksze, blksze);
				//UI.fillRect(i*blksze, j*blksze, blksze, blksze);
			}
		}
		
	}



	
}
