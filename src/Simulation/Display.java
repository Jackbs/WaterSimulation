

package Simulation;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ecs100.UI;

public class Display {
	Map<Integer, Color> ColorMap = new HashMap<Integer, Color>();
	
	int blksze = 20;
	
	public Display(){
		UI.initialise();
		UI.setImmediateRepaint(false);
		ColorMap.put(0,Color.white);
		ColorMap.put(1,Color.gray);
		ColorMap.put(2,Color.green);
		ColorMap.put(3,new Color(139,69,19));
	}

	public void ShowChunk(Chunk chunk, int zLevel) { //will be changed to 2D array of chunks in future
		for(int j = 0;j<16;j++){
			for(int i = 0;i<16;i++){			
				int id = chunk.getBlock(i,j,zLevel);
				UI.setColor(ColorMap.get(id));
				UI.fillRect(i*blksze, j*blksze, blksze, blksze);
			}
		}
		UI.repaintGraphics();
	}
}
