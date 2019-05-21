package controller;

import java.io.IOException;
import java.util.Vector;

public class WTA extends Program {
	

	public WTA(double lengthOfHorizontalSide, double lengthOfVerticalSide, double centerOfRectangleX,
			double centerOfRectangleY, int noOfPoints, int noOfCenters, String typeOfGeneratingCenters)
			throws IOException {
		super(lengthOfHorizontalSide, lengthOfVerticalSide, centerOfRectangleX, centerOfRectangleY, noOfPoints, noOfCenters,
				typeOfGeneratingCenters);
	}

	public WTA(double radius, double centerX, double centerY, int noOfPoints, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(radius, centerX, centerY, noOfPoints, noOfCenters, typeOfGeneratingCenters);
	}

	public WTA(double height, int noOfPoints, double positiveX1, double positiveX2, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(height, noOfPoints, positiveX1, positiveX2, noOfCenters, typeOfGeneratingCenters);
	}

	public WTA(int noOfPoints, double lengthOfSide, double centerOfSquareX, double centerOfSquareY, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		super(noOfPoints, lengthOfSide, centerOfSquareX, centerOfSquareY, noOfCenters, typeOfGeneratingCenters);
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
