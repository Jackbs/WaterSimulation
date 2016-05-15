package Simulation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MinecraftIO {
	public MinecraftIO(){
		
		
	}
	
	public void ReadRegion(){
		byte[] data = null;
		String Region = ("D:/Jack/Minecraft/minecraft/saves/WaterSimTest/region/r.0.0.mca");
		System.out.println("Reading Region at"+Region);
		Path path = Paths.get(Region);
		try {
			data = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Printing all byte's");
		for(int i = 0;i<data.length;i++){
			System.out.println(data[i]);
		}
	}
}
