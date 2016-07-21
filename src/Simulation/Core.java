package Simulation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;

import ecs100.UI;

import javax.accessibility.AccessibleContext;

public class Core extends MouseAdapter{
	
	//MinecraftIO MCIO;
	Display display;


	public int zLevel = 3;
	
	private int Dragging = 0; //0 if not dragging, 1 if mouse 1 dragging, 2 if mouse 2 dragging, ect
	private int size = 1;

	int currentblock = 1;

	Level level;
	//
	
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

		//UI.addButton("Load Chunks", this::loadChunks);
		UI.addButton("Update Display", this::updateDisplay);
		UI.addTextField("Brush size", this::setBlockSize);
		UI.setKeyListener(this::KeyPressed);
		//UI.setMouseMotionListener(this :: doMouse);



		UI.getFrame().addMouseWheelListener(this);
		System.out.println(UI.getFrame().findComponentAt(600, 300).getClass());

		AccessibleContext Ac = UI.getFrame().getAccessibleContext();

		System.out.println("Frame info1: "+(Ac.getAccessibleName()));
		System.out.println("Frame info2: "+(Ac.getAccessibleDescription()));
		System.out.println("Frame info3: "+(Ac.getAccessibleChildrenCount()));
		System.out.println("Frame info4: "+(Ac.getAccessibleChild(0).getAccessibleContext().getAccessibleName()));


		level = new Level("world1");

		updateDisplay();

		UI.getFrame().findComponentAt(600, 300).addMouseMotionListener(this);
		UI.getFrame().findComponentAt(600, 300).addMouseListener(this);

	}

	private void setBlockSize(String s) {
		size = Integer.parseInt(s);
		System.out.println("Current Brush size is now: "+ size);
	}






	public BlockLocation BlockLocFromMouse(int xMouse, int yMouse){
		double Xdiff = (xMouse-xOrg);
		double Ydiff = (yMouse-yOrg);
		Xdiff = (int)(Xdiff/(10*scale));
		Ydiff = (int)(Ydiff/(10*scale));
		int chunkX = (int)(Xdiff/16);
		int chunkY = (int)(Ydiff/16);
		Xdiff = Xdiff%16;
		Ydiff = Ydiff%16;
		//System.out.println("Setting Block ID: "+currentblock+" Xdiff1: "+Xdiff+" Ydiff: "+Ydiff+"Chunk X: "+chunkX+" Chunk Y: "+chunkY);
		return new BlockLocation((int)Xdiff,(int)Ydiff,zLevel,new Point(chunkX,chunkY));
	}

	public Block getCurrentBlock(){
		if(currentblock == 5){
			return new WaterBlock(currentblock);
		}else {
			return new BasicBlock(currentblock);
		}
	}

	public void paintBlocks(BlockLocation blkloc){
		level.setBlock(blkloc,getCurrentBlock());

		if(size != 1){
			for(int i = -size+1;i<size;i++){
				for(int j = -size+1;j<size;j++) {
					System.out.println("Distance: "+blkloc.distance(blkloc.offsetBlkLoc(i, j)));
					if(blkloc.distance(blkloc.offsetBlkLoc(i, j))<(0.85*size)) {
						level.setBlock(blkloc.offsetBlkLoc(i, j), getCurrentBlock());
					}
				}
			}
		}

		updateDisplay();
	}



	public void setBlockType(String s){
		currentblock = Integer.parseInt(s);
		System.out.println("Current Block is now: "+currentblock);
	}
	
	public void updateDisplay(){
		display.updateDisplay(level,zLevel, xOrg, yOrg, scale); //Method changed to array of chunks in future
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
		System.out.println("Key Pressed:: "+Action);

		if(isInteger(Action)){
			setBlockType(Action);
		}else if(Action == "Period"){
			if(zLevel<128){
				zLevel++;
			}
		}else if(Action == "Comma"){
			if(zLevel>0){
				zLevel--;
			}
		}
		updateDisplay();
	}
	
	@Override
	 public void mouseDragged(MouseEvent event) {
		int button = event.getButton();
		//System.out.println("IM DRAGGED: "+Dragging);
		if(Dragging == 1){
			paintBlocks(BlockLocFromMouse(event.getX(),event.getY()));
		}

		if(Dragging == 2){
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
			Dragging = 0;
		}
	}

	/*
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}
	*/

	@Override
	public void mousePressed(MouseEvent arg0) {
		//System.out.println("Mouse Pressed on button: "+arg0.getButton());
		if(arg0.getButton() == 1) {
			paintBlocks(BlockLocFromMouse(arg0.getX(), arg0.getY()));
			Dragging = 1;
		}else if(arg0.getButton() == 2){
			Dragging = 2;
			lastX = arg0.getX();
			lastY = arg0.getY();
			lastxOrg = xOrg;
			lastyOrg = yOrg;
			//System.out.println("PRESSED");
			updateDisplay();
			System.out.println("Started Dragging");
		}else{
			Dragging = 0;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		UI.clearGraphics();
		updateDisplay();
	}

	public boolean isInteger( String input ) {
		try {
			Integer.parseInt( input );
			return true;
		}
		catch( Exception e ) {
			return false;
		}
	}


	public static void main(String[] args) {
		Core c = new Core();
	}

}
