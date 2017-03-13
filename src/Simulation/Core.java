package Simulation;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseAdapter;

import ecs100.UI;

import javax.accessibility.AccessibleContext;

public class Core extends MouseAdapter{
	
	//MinecraftIO MCIO;
	Display display;
	WaterSimulation WaterSim;


	public int zLevel = 3;
	
	private int Dragging = 0; //0 if not dragging, 1 if mouse 1 dragging, 2 if mouse 2 dragging, ect
	private double size = 1.0;

	int currentblock = 1;
	boolean SimRunning = false;

	Level level;
	
	double scale = 3.0;
	double xOrg = 0.0;
	double yOrg = 0.0;
	double lastX = 0.0;
	double lastY = 0.0;
	double lastxOrg = 0.0;
	double lastyOrg = 0.0;

	int overlayMode = 1;

	public Core() {
		UI.addButton("Test", this::dotest);
		dotest();

		/*
		//MCIO = new MinecraftIO();
		//MCIO.ReadRegion();


		level = new Level("world1");
		display = new Display(level);

		//UI.addButton("Load Chunks", this::loadChunks);
		UI.addButton("Update Display", this::updateDisplay);
		UI.addButton("Play/Pause Simulation", this::playPause);
		UI.addButton("Stop Simulation", this::stopSimulation);
		UI.addButton("Do Simulation Tick", this::doWaterSimTick);
		UI.addSlider("Brush Size", 1.0, 9.0,size,this::setBlockSize);
		UI.setKeyListener(this::KeyPressed);
		//UI.setMouseMotionListener(this :: doMouse);

		UI.getFrame().addMouseWheelListener(this);
		//System.out.println(UI.getFrame().findComponentAt(600, 300).getClass());

		AccessibleContext Ac = UI.getFrame().getAccessibleContext();

		//System.out.println("Frame info1: "+(Ac.getAccessibleName()));
		//System.out.println("Frame info2: "+(Ac.getAccessibleDescription()));
		//System.out.println("Frame info3: "+(Ac.getAccessibleChildrenCount()));
		//System.out.println("Frame info4: "+(Ac.getAccessibleChild(0).getAccessibleContext().getAccessibleName()));



		WaterSim = new WaterSimulation();

		updateDisplay();

		UI.getFrame().findComponentAt(600, 300).addMouseMotionListener(this);
		UI.getFrame().findComponentAt(600, 300).addMouseListener(this);

		UI.println("Simulation Stopped");
		*/

	}

	private void dotest(){
		double rectheight = 100;
		double timescale = 0.1;
		double h1 = 1.0,h2 = 1.0,h3 = 0.0;


		double p1 = 0.0;
		double p2 = 0.0;
		double p3 = 0.0;
		double v2_in = 0.0;
		double v3_in = 0.0;
		double v4_in = 0.0;

		for(int i = 0;i<500;i++){
			UI.sleep(10);

			p1 = 9.8*1000*h1; p2 = 9.8*1000*h2; p3 = 9.8*1000*h3;

			double v2_in_temp = Math.sqrt((p1-p2)/500);
			double v3_in_temp = Math.sqrt((p2-p3)/500);
			double v4_in_temp = Math.sqrt((p3)/500);

			v2_in = v2_in_temp*timescale;
			v3_in = v3_in_temp*timescale+v2_in*0.8;
			v4_in = v4_in_temp*timescale;

			double height_removed_v1 = ((Math.pow(v2_in,2)*500)/9800);
			double height_added_v2 = ((Math.pow(v2_in*0.2,2)*500)/9800);
			double height_added_v2_v3 = ((Math.pow(v3_in,2)*500)/9800);
			double height_added_v3_v4 = ((Math.pow(v4_in,2)*500)/9800);

			h1 = h1 - height_removed_v1;
			h2 = h2 + height_added_v2;

			h2 = h2 - height_added_v2_v3;
			h3 = h3 + height_added_v2_v3;

			//h3 = h3 - height_added_v3_v4;

			System.out.print(h1 +"," + h2+","+h3);
			System.out.print(":=:"+v2_in+ "," + v3_in);
			//System.out.print(":=:"+p1+","+p2+ "," + p3);
			System.out.println();

			UI.setImmediateRepaint(false);
			UI.clearGraphics();
			UI.setColor(Color.blue);
			UI.fillRect(0, rectheight-rectheight*h1, 100, rectheight*h1);
			UI.fillRect(102, rectheight-rectheight*h2, 100, rectheight*h2);
			UI.fillRect(204, rectheight-rectheight*h3, 100, rectheight*h3);
			UI.repaintAllGraphics();
		}
	}


	private void playPause() {
		SimRunning = !(SimRunning);
		if(SimRunning){
			UI.println("Simulation started");
		}else{
			UI.println("Simulation paused");
		}
	}

	private void stopSimulation() {
		SimRunning = false;
		UI.println("Simulation stopped");
		WaterSim.Reset();
	}

	public void doWaterSimTick(){
		level = WaterSim.doWaterSimTick(level);
		updateDisplay();
	}

	private void setBlockSize(Double d) {
		size = d;

		//size = Integer.parseInt(s);
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
			return new FluidBlock(currentblock,level);
		}else {
			return new SolidBlock(currentblock,level);
		}
	}

	public void paintBlocks(BlockLocation blkloc){
		level.setBlock(blkloc,getCurrentBlock());

		if(size != 1){
			for(int i = -(int)size+1;i<size;i++){
				for(int j = -(int)size+1;j<size;j++) {
					//System.out.println("Distance: "+blkloc.distance(blkloc.offsetBlkLoc(i, j)));
					if(blkloc.distance(blkloc.offsetBlkLoc(i, j,0))<(0.85*size)) {
						level.setBlock(blkloc.offsetBlkLoc(i, j,0), getCurrentBlock());
					}
				}
			}
		}
		updateDisplay();
	}



	public void setBlockType(String s){
		currentblock = Integer.parseInt(s);
		if(currentblock == 5){
			if(size>2){
				size = 2;
			}
		}
		System.out.println("Current Block is now: "+currentblock);
	}
	
	public void updateDisplay(){
		display.updateDisplay(level,zLevel, xOrg, yOrg, scale,currentblock,overlayMode);
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
		//System.out.println("Key Pressed:: "+Action);
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
		}else if(Action.equals("p")){
			overlayMode = 1;
			System.out.println("On Pressure Overlay mode");
		}else if(Action.equals("v")){
			overlayMode = 2;
			System.out.println("On Velosity Overlay mode");
		}else if(Action.equals("e")){
			overlayMode = 3;
			System.out.println("On E value overlay mode");
		}else if(Action.equals("f")){
			overlayMode = 4;
			System.out.println("On fill level overlay mode");
		}else if(Action.equals("d")){
			overlayMode = 5;
			System.out.println("On depth value overlay mode");
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
        }else if(arg0.getButton() == 3){
            level.getBlock((BlockLocFromMouse(arg0.getX(), arg0.getY()))).printAllData();
		}else if(arg0.getButton() == 2) {
            Dragging = 2;
            lastX = arg0.getX();
            lastY = arg0.getY();
            lastxOrg = xOrg;
            lastyOrg = yOrg;
            //System.out.println("PRESSED");
            updateDisplay();
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
		Core ThisCore = new Core();
	}

}
