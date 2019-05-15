package view;

import java.io.IOException;

import controller.*;

public class Main {
	public static void showCenters(Program program) {
		for (int i = 0; i < program.neurons.size(); i++) {
			System.out.println(program.neurons.get(i).weight.get(0) + "; " + program.neurons.get(i).weight.get(1));
		}
	}

	public static void main(String[] args) throws IOException {
		Program program;
		if (args[0].equals(new String("WTA"))) {
			program = new WTA(2, 3, 0, 2, 100);
		} else if (args[0].equals(new String("WTM"))) {
			program = new WTM(2, 3, 0, 10, 100);
		} else {
			program = new NeuralGas(2, 3, 0, 10, 100);
		}
		System.out.println("to jest to");
		program.algorithm();

	}

}
