package controller;

import java.io.IOException;

public class WTA extends Program {

	public WTA(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		super(radius, centerX, centerY, noOfCenters, noOfPoints);
	}

	@Override
	protected void changeCenterCoords(double[] point, int kx) {

	}
}
