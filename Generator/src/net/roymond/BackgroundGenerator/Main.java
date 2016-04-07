package net.roymond.BackgroundGenerator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Generator gen = new Generator.Builder().build();
		try {
			gen = new Generator.Builder().setSquares(true).setDelta(40).setRGB(133, 175, 110).build();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 10; i++){
			gen.generateBackground();
		}
		
		
		
	}

}
