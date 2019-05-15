package controller;

import java.io.IOException;

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

	}

}
