package controller;

import java.io.IOException;
import java.util.Vector;

public class NeuralGas extends Program {

	private double winner;

	public NeuralGas(double lengthOfHorizontalSide, double lengthOfVerticalSide, double centerOfRectangleX,
			double centerOfRectangleY, int noOfPoints, int noOfCenters, String typeOfGeneratingCenters)
			throws IOException {
		super(lengthOfHorizontalSide, lengthOfVerticalSide, centerOfRectangleX, centerOfRectangleY, noOfPoints,
				noOfCenters, typeOfGeneratingCenters);
		winner = -1;
	}

	public NeuralGas(double radius, double centerX, double centerY, int noOfPoints, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(radius, centerX, centerY, noOfPoints, noOfCenters, typeOfGeneratingCenters);
		winner = -1;
	}

	public NeuralGas(double height, int noOfPoints, double positiveX1, double positiveX2,
			int noOfCenters, String typeOfGeneratingCenters) throws IOException {
		super(noOfPoints, height, positiveX1, positiveX2, noOfCenters, typeOfGeneratingCenters);
		winner = -1;
	}

	public NeuralGas(int noOfPoints, double lengthOfSide, double centerOfSquareX, double centerOfSquareY,
			int noOfCenters, String typeOfGeneratingCenters) throws IOException {
		super(noOfPoints, lengthOfSide, centerOfSquareX, centerOfSquareY, noOfCenters, typeOfGeneratingCenters);
		winner = -1;
	}

	private double eFunction(int t) {
		return Math.exp(-(t / lambda()));
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		checkDistanceToPoint(point);
		for (int i = 0; i < neurons.size(); i++) {
			if (i == kx && winner == kx)
				continue;
			Vector<Double> V = new Vector<Double>();
			Vector<Double> vector = new Vector<Double>();
			vector.add((point[0] - neurons.get(i).weight.get(0)));
			vector.add((point[1] - neurons.get(i).weight.get(1)));
			V.add((neurons.get(i).weight.get(0) + (alpha() * eFunction(checkOrder(i)) * vector.get(0))));
			V.add((neurons.get(i).weight.get(1) + (alpha() * eFunction(checkOrder(i)) * vector.get(1))));
			neurons.get(i).setXY(V);
			winner = kx;
		}

	}

}
