package net.roymond.BackgroundGenerator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Generator gen = new Generator.Builder().build();
		try {
			gen = new Generator.Builder().setDelta(25).setMaxObjects(1000).setRGB(0,125,0).freezeColor("rb").build();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 13; i++){
			gen.generateBackground();
		}
		
		
		
	}

}