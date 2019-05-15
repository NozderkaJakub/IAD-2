package controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.Vector;

public class NeuralGas extends Program {
	private double lambda;
	private Vector<Vector<Double>> distance;

	public NeuralGas(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
		lambda = 3.0;
		distance = new Vector<Vector<Double>>();
	}

	private double eFunction(int t) {
		return Math.exp(-(t / lambda()));
	}

	private double lambda() {
		if (lambda <= 0.1003)
			return 0.1;
		lambda -= 0.0003;
		return lambda;
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		checkDistanceToPoint(point);
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			Vector<Double> vector = new Vector<Double>();
			vector.add((point[0] - neurons.get(i).weight.get(0)));
			vector.add((point[1] - neurons.get(i).weight.get(1)));
			V.add( (neurons.get(i).weight.get(0) + (alpha * eFunction(checkOrder(i)) * vector.get(0))) );
			V.add( (neurons.get(i).weight.get(1) + (alpha * eFunction(checkOrder(i)) * vector.get(1))) );
			neurons.get(i).setXY(V);
		}

	}
	
	private int checkOrder(int t) {
		for (int i = 0; i < distance.size(); i++) {
			if((double) t == distance.get(i).get(1)) return i;
		}
		return 0;
	}

	private void checkDistanceToPoint(double[] point) {
		distance.clear();
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			V.add(0, neurons.get(i).checkDistance(point));
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
