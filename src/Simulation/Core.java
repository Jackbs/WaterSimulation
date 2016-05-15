package Simulation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.MouseAdapter;

import ecs100.UI;
import ecs100.UIButtonListener;

public class Core extends MouseAdapter{
	
	Display Display;
	public Chunk OnlyChunk; //Will be changed to array of chunks in future 
	public int zLevel = 1;
	
	boolean Dragging = false;;
	
	double scale = 1.0;
	double xOrg = 0.0;
	double yOrg = 0.0;
	double lastX = 0.0;
	double lastY = 0.0;
	double lastxOrg = 0.0;
	double lastyOrg = 0.0;
	
	public Core() {
		Display = new Display();
		UI.addButton("Load Chunks", this::loadChunks);
		UI.addButton("Access random", this::accessrandom);
		UI.addButton("Update Display", this::updateDisplay);
		UI.setKeyListener(this::KeyPressed);
		//UI.setMouseMotionListener(this :: doMouse);

		UI.getFrame().addMouseWheelListener(this);
		System.out.println(UI.getFrame().findComponentAt(600, 300).getClass());
		UI.getFrame().findComponentAt(600, 300).addMouseMotionListener(this);
		UI.getFrame().findComponentAt(600, 300).addMouseListener(this);
		loadChunks();
	}
	
	
	
	public void accessrandom(){

		int x = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int z = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		System.out.println("Access Random: "+x+" "+y+" "+z);
		OnlyChunk.getBlock(x, y, z);
		System.out.println("Got Block: "+OnlyChunk.getBlock(x, y, z));
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		UI.clearGraphics();
		scale = scale - 0.2*e.getWheelRotation();
		if(scale <= 0){
			scale = 0.1;
		}
		updateDisplay();
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
		Display.updateDisplay(OnlyChunk,zLevel, xOrg, yOrg, scale); //Method changed to array of chunks in future
	}
	
	@Override
	 public void mouseDragged(MouseEvent event) {
		if(event.getButton() == 2){
			Dragging = true;
			System.out.println("Started Dragging");
		}
		if(Dragging){
			xOrg = lastxOrg+(event.getX()-lastX);
			yOrg = lastyOrg+(event.getY()-lastY);
			System.out.println("IM DRAGGED");
			updateDisplay();
			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getButton() == 2){
			System.out.println("Stopped Dragging");
			Dragging = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(arg0.getButton() == 2){
			Dragging = true;
			System.out.println("Started Dragging");
		}
		
		if(arg0.getButton() == 2){
			lastX = arg0.getX();
			lastY = arg0.getY();
			lastxOrg = xOrg;
			lastyOrg = yOrg;
			System.out.println("PRESSED");
			updateDisplay();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		UI.clearGraphics();
		updateDisplay();
	}
	
	
	
	
	


	public static void main(String[] args) {
		Core c = new Core();
	}

	

	
	

}
