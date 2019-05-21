package controller;

import java.io.IOException;
import java.util.Vector;

public class WTM extends Program {

	public WTM(double lengthOfHorizontalSide, double lengthOfVerticalSide, double centerOfRectangleX,
			double centerOfRectangleY, int noOfPoints, int noOfCenters, String typeOfGeneratingCenters)
			throws IOException {
		super(lengthOfHorizontalSide, lengthOfVerticalSide, centerOfRectangleX, centerOfRectangleY, noOfPoints,
				noOfCenters, typeOfGeneratingCenters);
	}

	public WTM(double radius, double centerX, double centerY, int noOfPoints, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(radius, centerX, centerY, noOfPoints, noOfCenters, typeOfGeneratingCenters);
	}

	public WTM(double height, int noOfPoints, double positiveX1, double positiveX2, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(noOfPoints, height, positiveX1, positiveX2, noOfCenters, typeOfGeneratingCenters);
	}

	public WTM(int noOfPoints, double lengthOfSide, double centerOfSquareX, double centerOfSquareY, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(noOfPoints, lengthOfSide, centerOfSquareX, centerOfSquareY, noOfCenters, typeOfGeneratingCenters);
	}

	private double theta(int kx, int k) {
		return Math.exp(-(Math.pow((kx - k), 2)) / (2 * Math.pow(lambda(), 2)));
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {
		for (int i = 0; i < neurons.size(); i++) {
			Vector<Double> V = new Vector<Double>();
			Vector<Double> vector = new Vector<Double>();
			vector.add((point[0] - neurons.get(i).weight.get(0)));
			vector.add((point[1] - neurons.get(i).weight.get(1)));
			V.add((neurons.get(i).weight.get(0) + (alpha() * theta(kx, i) * vector.get(0))));
			V.add((neurons.get(i).weight.get(1) + (alpha() * theta(kx, i) * vector.get(1))));
			neurons.get(i).setXY(V);
		}
	}

}
