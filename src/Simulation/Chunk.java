package Simulation;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ecs100.UI;



public class Chunk {
	
	private Block[][][] Blocks = new Block [16][16][128];
	private Point2D pos;
	
	public Chunk(File ChunkFile) throws Exception{
		
		try {
			Scanner scan = new Scanner(ChunkFile);
			int z = 0;
			String line = scan.nextLine();
			System.out.println("Initializing chunk: "+ ChunkFile.getName()+" With info: "+line);
			String[] charBlock = line.split(",");
			pos = new Point(Integer.parseInt(charBlock[0]),Integer.parseInt(charBlock[1]));
			while (scan.hasNext()){
				for(int y = 0;y<=15;y++){
					line = scan.nextLine();
					
					charBlock = line.split(",");
					if(charBlock.length != 16){
						throw new Exception(); //Line is longer then 16
					}
					//System.out.print("CharBlock is: ");
					for(int x = 0;x<charBlock.length;x++){
						setBlock(x,y,z,new BasicBlock(Integer.parseInt(charBlock[x])));

						//System.out.print(Blocks[x][y][z]);
					}
					//System.out.println(" At Y,Z: "+y+","+z);
				}
				z++;
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}	
	}

	public Block getBlock(BlockLocation b){
		return Blocks[b.x][b.y][b.z];
	}

	public Block getBlock(int x,int y,int z){
		return Blocks[x][y][z];
	}

	public void setBlock(int x,int y,int z,Block block){Blocks[x][y][z] = block;}

	public Point2D getChunkLoc(){
		return pos;
	}
	
}
