package Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import ecs100.UI;



public class Chunk {
	
	public int[][][] Blocks = new int [16][16][128];
	
	public Chunk(File ChunkFile) throws Exception{
		System.out.println("Initializing chunk of file: "+ChunkFile.getName());
		try {
			int z = 0;
			Scanner scan = new Scanner(ChunkFile);
			while (scan.hasNext()){
				for(int y = 0;y<=15;y++){
					String line = scan.nextLine();
					String[] charBlock = line.split(",");
					if(charBlock.length != 16){
						throw new Exception(); //Line is longer then 16
					}
					//System.out.print("CharBlock is: ");
					for(int x = 0;x<charBlock.length;x++){
						Blocks[x][y][z] = Integer.parseInt(charBlock[x]);
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
	
	public int getBlock(int x,int y,int z){
		return Blocks[x][y][z];
	}
}
