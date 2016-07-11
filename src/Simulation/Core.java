package Simulation;

import java.awt.*;
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
import javax.accessibility.AccessibleContext;

public class Core extends MouseAdapter{
	
	MinecraftIO MCIO;
	Display display;
	FileIO fileIO;

	public int zLevel = 3;
	
	boolean Dragging = false;
	
	int currentblock = 1;
	
	Map <Point2D, Chunk> Level = new HashMap<Point2D, Chunk>();
	
	double scale = 2.0;
	double xOrg = 0.0;
	double yOrg = 0.0;
	double lastX = 0.0;
	double lastY = 0.0;
	double lastxOrg = 0.0;
	double lastyOrg = 0.0;
	
	public Core() {
		
		//MCIO = new MinecraftIO();
		//MCIO.ReadRegion();
		

		display = new Display();
		fileIO = new FileIO();

		UI.addButton("Load Chunks", this::loadChunks);
		UI.addButton("Update Display", this::updateDisplay);
		UI.addTextField("Painting Block Type", this::setBlockType);
		UI.setKeyListener(this::KeyPressed);
		//UI.setMouseMotionListener(this :: doMouse);



		UI.getFrame().addMouseWheelListener(this);
		System.out.println(UI.getFrame().findComponentAt(600, 300).getClass());

		AccessibleContext Ac = UI.getFrame().getAccessibleContext();

		System.out.println("Frame info1: "+(Ac.getAccessibleName()));
		System.out.println("Frame info2: "+(Ac.getAccessibleDescription()));
		System.out.println("Frame info3: "+(Ac.getAccessibleChildrenCount()));
		System.out.println("Frame info4: "+(Ac.getAccessibleChild(0).getAccessibleContext().getAccessibleName()));


		loadChunks();
		updateDisplay();

		UI.getFrame().findComponentAt(600, 300).addMouseMotionListener(this);
		UI.getFrame().findComponentAt(600, 300).addMouseListener(this);

	}
	
	public void setBlock(int x, int y){
		double Xdiff = (x-xOrg);
		double Ydiff = (y-yOrg);
		Xdiff = (int)(Xdiff/(10*scale));
		Ydiff = (int)(Ydiff/(10*scale));
		int chunkX = (int)(Xdiff/16);
		int chunkY = (int)(Ydiff/16);
		Xdiff = Xdiff%16;
		Ydiff = Ydiff%16;

		Level.get(new Point(chunkX,chunkY));
		System.out.println("Setting Block at Xdiff1: "+Xdiff+" Ydiff: "+Ydiff+"Chunk X: "+chunkX+" Chunk Y: "+chunkY);
	}


	public void loadChunks(){
		Level = fileIO.loadLevel("world1");
	}

	public void setBlockType(String s){
		currentblock = Integer.parseInt(s);
		System.out.println("Current Block is now: "+currentblock);
	}
	
	public void updateDisplay(){
		display.updateDisplay(Level,zLevel, xOrg, yOrg, scale); //Method changed to array of chunks in future
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		UI.clearGraphics();
        double Xdiff1 = (e.getX()-xOrg)-351.0;
        double Ydiff1 = (e.getY()-yOrg)-55.0;



		double Xdiff2 = Xdiff1/scale;
		double Ydiff2 = Ydiff1/scale;

		scale = scale - 0.2*e.getWheelRotation();

		if(scale < 0.1){
			scale = 0.1;
		}

		Xdiff2 = Xdiff2*scale;
		Ydiff2 = Ydiff2*scale;

		//System.out.println("Xdiff1: "+Xdiff1+" Ydiff1: "+Ydiff1+" Xdiff2: "+Xdiff2+" Ydiff2: "+Ydiff2+" xOrg: "+xOrg+" yOrg: "+ yOrg);

		xOrg = xOrg+(Xdiff1-Xdiff2);
		yOrg = yOrg+(Ydiff1-Ydiff2);
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
		if(event.getButton() == 1){
			setBlock(event.getX(),event.getY());
		}
		if(event.getButton() == 2){
			Dragging = true;
			System.out.println("Started Dragging");
		}
		if(Dragging){
			UI.clearGraphics();
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
		//System.out.println("Mouse Pressed on button: "+arg0.getButton());
		if(arg0.getButton() == 1){
			setBlock(arg0.getX(),arg0.getY());
		}
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
