package net.roymond.BackgroundGenerator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Generator gen = new Generator.Builder().build();
		try {
			gen = new Generator.Builder().setDelta(80).setOctogons(true).setPolygons(true).setMaxObjects(1337).build();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 13; i++){
			gen.generateBackground();
		}
		
		
		
	}

}
