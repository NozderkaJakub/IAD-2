package controller;

import java.io.IOException;
import java.util.Vector;

public class WTA extends Program {

	public WTA(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		Vector<Double> V = new Vector<Double>();
		Vector<Double> vector = new Vector<Double>();
		vector.add((point[0] - neurons.get(kx).weight.get(0))); // x - cxj
		vector.add((point[1] - neurons.get(kx).weight.get(1))); // y - cyj
		V.add((neurons.get(kx).weight.get(0) + (alpha() * vector.get(0))));
		V.add((neurons.get(kx).weight.get(1) + (alpha() * vector.get(1))));
		neurons.get(kx).setXY(V);
	}
}
