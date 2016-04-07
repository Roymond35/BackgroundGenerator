package net.roymond.BackgroundGenerator;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Generator gen = new Generator.Builder().build();
		
		for (int i = 0; i < 10; i++){
			gen.generateBackground();
		}
		
	}

}
