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

		if (args[2].equals(new String("LINE"))) {
			if (args[0].equals(new String("WTA"))) {
				program = new WTA(2.0, 100, 2.0, 4.0, 10, args[1]);
			} else if (args[0].equals(new String("WTM"))) {
				program = new WTM(2.0, 100, 2.0, 4.0, 10, args[1]);
			} else {
				program = new NeuralGas(2.0, 100, 2.0, 4.0, 10, args[1]);
			}

		} else if (args[2].equals(new String("SQUARE"))) {
			if (args[0].equals(new String("WTA"))) {
				program = new WTA(100, 4.0, 3.0, 0.0, 10, args[1]);
			} else if (args[0].equals(new String("WTM"))) {
				program = new WTM(100, 4.0, 3.0, 0.0, 10, args[1]);
			} else {
				program = new NeuralGas(100, 4.0, 3.0, 0.0, 10, args[1]);
			}

		} else if (args[2].equals(new String("RECTANGLE"))) {
			if (args[0].equals(new String("WTA"))) {
				program = new WTA(6.0, 2.0, 4.0, 0.0, 100, 10, args[1]);
			} else if (args[0].equals(new String("WTM"))) {
				program = new WTM(6.0, 2.0, 4.0, 0.0, 100, 10, args[1]);
			} else {
				program = new NeuralGas(6.0, 2.0, 4.0, 0.0, 100, 10, args[1]);
			}

		} else if (args[2].contentEquals(new String("CIRCLE"))) {
			if (args[0].equals(new String("WTA"))) {
				program = new WTA(2.0, 3.0, 0, 100, 10, args[1]);
			} else if (args[0].equals(new String("WTM"))) {
				program = new WTM(2.0, 3.0, 0, 100, 10, args[1]);
			} else {
				program = new NeuralGas(2.0, 3.0, 0, 100, 10, args[1]);
			}
		} else {
			System.out.println("Nieprawid³owy parametr args[2]. Mo¿liwe to:\nCIRCLE\nLINE\nSQUARE\nRECTANGLE");
			program = null;
			System.exit(-1);
		}

		program.algorithm();

	}

}
