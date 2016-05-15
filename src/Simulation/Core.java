package Simulation;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;

import ecs100.UI;
import ecs100.UIButtonListener;

public class Core extends MouseAdapter{
	
	MinecraftIO MCIO;
	Display Display;
	
	public Chunk OnlyChunk; //Will be changed to array of chunks in future 
	public int zLevel = 3;
	
	boolean Dragging = false;
	
	
	
	Map <Point2D, Chunk> Level = new HashMap<Point2D, Chunk>();
	//List<Chunk> list = new ArrayList<Chunk>();
	
	double scale = 2.0;
	double xOrg = 0.0;
	double yOrg = 0.0;
	double lastX = 0.0;
	double lastY = 0.0;
	double lastxOrg = 0.0;
	double lastyOrg = 0.0;
	
	public Core() {
		
		MCIO = new MinecraftIO();
		MCIO.ReadRegion();
		
		/*
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
		updateDisplay();
		*/
	}
	
	
	
	public void accessrandom(){

		int x = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int y = ThreadLocalRandom.current().nextInt(0, 15 + 1);
		int z = ThreadLocalRandom.current().nextInt(0, 3 + 1);
		System.out.println("Access Random: "+x+" "+y+" "+z);
		OnlyChunk.getBlock(x, y, z);
		System.out.println("Got Block: "+OnlyChunk.getBlock(x, y, z));
	}
	
	
	
	public void loadChunks(){
		Chunk WorkingChunk;
		
		UI.println("Loading Chunks");
		File myfile1 = new File("c1.txt");
		File myfile2 = new File("c2.txt");
		File myfile3 = new File("c3.txt");
		File myfile4 = new File("c4.txt");
		
		try {
			WorkingChunk = new Chunk(myfile1);
			Level.put(WorkingChunk.getChunkLoc(), WorkingChunk);
			
			WorkingChunk = new Chunk(myfile2);
			Level.put(WorkingChunk.getChunkLoc(), WorkingChunk);
			
			WorkingChunk = new Chunk(myfile3);
			Level.put(WorkingChunk.getChunkLoc(), WorkingChunk);
			
			WorkingChunk = new Chunk(myfile4);
			Level.put(WorkingChunk.getChunkLoc(), WorkingChunk);
			
			//OnlyChunk = new Chunk(myfile1);  //in later versions this will be changed to support multiple chunks
			
			//Level.put(key, value)
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void updateDisplay(){
		Display.updateDisplay(Level,zLevel, xOrg, yOrg, scale); //Method changed to array of chunks in future
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
		
		if(Action == "Period"){
			if(zLevel<128){
				zLevel++;
			}
		}
		if(Action == "Comma"){
			if(zLevel>0){
				zLevel--;
			}
		}
		updateDisplay();
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
			//System.out.println("IM DRAGGED");
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
		System.out.println("Mouse Pressed on button: "+arg0.getButton());
		if(arg0.getButton() == 2){
			Dragging = true;
			lastX = arg0.getX();
			lastY = arg0.getY();
			lastxOrg = xOrg;
			lastyOrg = yOrg;
			System.out.println("PRESSED");
			updateDisplay();
			System.out.println("Started Dragging");
		}else{
			Dragging = false;
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
