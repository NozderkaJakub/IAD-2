package controller;

import java.io.IOException;
import java.util.Vector;

public class WTM extends Program {
	private double lambda;

	public WTM(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
		lambda = 3.0;
	}

	private double theta(int kx, int k) {
		return Math.exp(-(Math.pow((kx - k), 2)) / (2 * Math.pow(lambda(), 2)));
	}

	private double lambda() {
		if (lambda <= 0.1003)
			return 0.1;
		lambda -= 0.0003;
		return lambda;
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			Vector<Double> vector = new Vector<Double>();
			vector.add( (point[0] - neurons.get(i).weight.get(0)) );
			vector.add( (point[1] - neurons.get(i).weight.get(1)) );
			V.add( (neurons.get(i).weight.get(0) + (alpha * theta(kx, i) * vector.get(0))) );
			V.add( (neurons.get(i).weight.get(1) + (alpha * theta(kx, i) * vector.get(1))) );
			neurons.get(i).setXY(V);
		}
	}

}
