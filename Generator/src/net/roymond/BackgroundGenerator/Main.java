package net.roymond.BackgroundGenerator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Generator gen = new Generator.Builder().build();
		try {
			gen = new Generator.Builder().setOctogons(false).setOctogons(true).setCircles(true).setDelta(40).setMaxObjects(100).build();
		} catch (InvalidValueException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 1; i++){
			gen.generateBackground();
		}
		
		
		
	}

}
