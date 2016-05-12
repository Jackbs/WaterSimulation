package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import ecs100.UI;
import ecs100.UIButtonListener;

public class Core {
	
	Display Display;
	public Chunk OnlyChunk; //Will be changed to array of chunks in future 
	public int zLevel = 1;
	
	public Core(){
		Display = new Display();
		UI.addButton("Load Chunks", this::loadChunks);
		UI.addButton("Access random", this::accessrandom);
		UI.addButton("Update Display", this::updateDisplay);
		UI.setKeyListener(this::KeyPressed);
	}
	
	public void accessrandom(){

		int x = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int z = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		System.out.println("Access Random: "+x+" "+y+" "+z);
		OnlyChunk.getBlock(x, y, z);
		System.out.println("Got Block: "+OnlyChunk.getBlock(x, y, z));
	}
	
	public void KeyPressed(String Action){
		if(Action == "Comma"){
			if(zLevel<128){
				zLevel++;
			}
		}
		if(Action == "Period"){
			if(zLevel>0){
				zLevel--;
			}
		}
		updateDisplay();
	}
	
	public void loadChunks(){
		UI.println("Loading Chunks");
		File myfile = new File("c1.txt");
		try {
			OnlyChunk = new Chunk(myfile);  //in later versions this will be changed to support multiple chunks
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void updateDisplay(){
		Display.updateDisplay(OnlyChunk,zLevel); //Method changed to array of chunks in future
	}


	public static void main(String[] args) {
		Core c = new Core();
	}

}
