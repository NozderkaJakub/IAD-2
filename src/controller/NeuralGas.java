package controller;

import java.io.IOException;
import java.util.Vector;

public class NeuralGas extends Program {
	private Vector<Vector<Double>> distance;

	public NeuralGas(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
		distance = new Vector<Vector<Double>>();
	}

	private double eFunction(int t) {
		return Math.exp(-(t / lambda()));
	}



	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		checkDistanceToPoint(point);
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			Vector<Double> vector = new Vector<Double>();
			vector.add((point[0] - neurons.get(i).weight.get(0)));
			vector.add((point[1] - neurons.get(i).weight.get(1)));
			V.add( (neurons.get(i).weight.get(0) + (alpha() * eFunction(checkOrder(i)) * vector.get(0))) );
			V.add( (neurons.get(i).weight.get(1) + (alpha() * eFunction(checkOrder(i)) * vector.get(1))) );
			neurons.get(i).setXY(V);
		}

	}
	
	private int checkOrder(int t) {
		for (int i = 0; i < distance.size(); i++) {
			if((double) t == distance.get(i).get(1)) return i;
		}
		return 0;
	}
}
