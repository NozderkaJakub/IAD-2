package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import model.Neuron;

public abstract class Program {

	protected double[][] points;
	public List<Neuron> neurons;
	protected double alpha;
	protected int shuffle;

	public Program(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		points = new double[200][2];
		neurons = new ArrayList<Neuron>();
		alpha = 0.1;
		shuffle = ThreadLocalRandom.current().nextInt(0, 200);
		generateRandomPointsInCircle(radius, centerX, centerY, noOfPoints);
		savePoints();
		initCenters(noOfCenters);
	}
	
	protected abstract void changeCenterCoords(double[] point, int kx);
	
	public void algorithm() {
		List<Integer> occurs = new ArrayList<Integer>();

		for (int i = 0; i < points.length; i++) {
			int centro = 0;
			while (checkIfOccurs(occurs)) {
				shuffle = ThreadLocalRandom.current().nextInt(0, 200);
			}
			occurs.add(shuffle);

			for (int j = 0; j < neurons.size() - 1; j++) {
				if (neurons.get(j).checkDistance(points[shuffle]) < neurons.get(j + 1).checkDistance(points[shuffle])) {
					if (neurons.get(j).checkDistance(points[shuffle]) < neurons.get(centro)
							.checkDistance(points[shuffle]))
						centro = j;
				} else {
					if (neurons.get(j + 1).checkDistance(points[shuffle]) < neurons.get(centro)
							.checkDistance(points[shuffle]))
						centro = j + 1;
				}
			}
			changeCenterCoords(points[shuffle], centro);
		}
	}

	protected boolean checkIfOccurs(List<Integer> occur) {
		for (int i = 0; i < occur.size(); i++) {
			if (shuffle == occur.get(i)) {
				return true;
			}
		}
		return false;
	}

	protected void initCenters(int n) {
		for (int i = 0; i < n; i++) {
			neurons.add(i, new Neuron(setCenterCoords()));
		}
	}

	protected Vector<Double> setCenterCoords() {
		double x = 0, y = 0;
		Vector<Double> V = new Vector<Double>();

		x = Math.random() * 10;
		if (Math.random() > 0.5)
			x *= -1;
		y = Math.random() * 10;
		if (Math.random() > 0.5)
			y *= -1;

		V.add(x);
		V.add(y);
		return V;
	}

	protected void generateRandomPointsInCircle(int R, int OX, int OY, int noOfPoints) {
		double a, r;

		for (int i = 0; i < noOfPoints; i++) {
			a = Math.random() * 2 * Math.PI;
			r = R * Math.sqrt(Math.random());
			points[i][0] = r * Math.cos(a) + OX;
			points[i][1] = r * Math.sin(a) + OY;

			a = Math.random() * 2 * Math.PI;
			r = R * Math.sqrt(Math.random());
			points[100 + i][0] = r * Math.cos(a) - OX;
			points[100 + i][1] = r * Math.sin(a) + OY;
		}
	}

	protected void savePoints() throws IOException {
		File file = new File("points.txt");
		PrintWriter out = null;
		try {
			file.createNewFile();
			out = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {
			System.out.println();
		}

		for (int i = 0; i < points.length; i++) {
			out.println(points[i][0] + ";" + points[i][1]);
		}

		out.close();
	}
}
