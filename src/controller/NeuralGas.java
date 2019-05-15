package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

import model.Neuron;

public class NeuralGas extends Program {
	private double lambda;
	private Vector<Vector<Double>> distance;

	public NeuralGas(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
		lambda = 3.0;
		distance = new Vector<Vector<Double>>();
	}

	private double lambda() {
		if (lambda <= 0.1003)
			return 0.1;
		lambda -= 0.0003;
		return lambda;
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		// TODO Auto-generated method stub

	}

	private void checkDistanceToPoints(Neuron c, double[] point) {
		distance.clear();
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			V.add(0, c.checkDistance(point));
			V.add(1, (double) i);
			distance.add(V);
		}
		distance.sort(new Comparator<Vector<Double>>() {
			@Override
			public int compare(Vector<Double> o1, Vector<Double> o2) {
				return o1.get(0).compareTo(o2.get(0));
			}
		});
	}
}
