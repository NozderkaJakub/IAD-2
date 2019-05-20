package controller;

import java.io.IOException;
import java.util.Vector;

public class WTM extends Program {


	public WTM(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
	}

	private double theta(int kx, int k) {
		return Math.exp(-(Math.pow((kx - k), 2)) / (2 * Math.pow(lambda(), 2)));
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
