package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import model.Neuron;

public abstract class Program {

	protected double[][] points;
	public List<Neuron> neurons;
	protected double alpha;
	protected int shuffle;
	protected double lambda;
	static protected Vector<Vector<Double>> distance;
	protected Vector<Double> errors;

	public Program(int radius, int centerX, int centerY, int noOfCenters, int noOfPoints) throws IOException {
		points = new double[noOfPoints * 2][2];
		neurons = new ArrayList<Neuron>();
		alpha = 0.1;
		lambda = 3.0;
		shuffle = ThreadLocalRandom.current().nextInt(0, noOfPoints *  2);
		generateRandomPointsInCircle(radius, centerX, centerY, noOfPoints);
		savePoints();
		initCenters(noOfCenters);
		distance = new Vector<Vector<Double>>();
		errors = new Vector<>();
	}

	protected abstract void changeCenterCoords(double[] point, int kx);

	public void algorithm() {
		List<Integer> occurs = new ArrayList<Integer>();
		printCentra();
		double error = 0;
		double oldError = 0;

		do {
			oldError = error;
			error = quantisationError();
			for (int i = 0; i < points.length; i++) {
				int centro = 0;
				while (checkIfOccurs(occurs)) {
					shuffle = ThreadLocalRandom.current().nextInt(0, 200);
				}
				occurs.add(shuffle);

				for (int j = 0; j < neurons.size() - 1; j++) {
					if (neurons.get(j).checkDistance(points[shuffle]) < neurons.get(j + 1)
							.checkDistance(points[shuffle])) {
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
				printCentra();
			}
			occurs.clear();
		} while (Math.abs(error - oldError) > 0.0001);
		saveError();
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
			points[noOfPoints + i][0] = r * Math.cos(a) - OX;
			points[noOfPoints + i][1] = r * Math.sin(a) + OY;
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

	public double quantisationError() {
		double sum = 0;
		for (int i = 0; i < points.length; i++) {
			checkDistanceToPoint(points[i]);
			sum += distance.get(0).get(0);
		}
		sum /= points.length;
		errors.add(sum);
		return sum;
	}

	public double lambda() {
		if (lambda <= 0.1003)
			return 0.1;
		lambda -= 0.0003;
		return lambda;
	}

	public double alpha() {
		if (alpha <= 0.05)
			return 0.1;
		alpha -= 0.001;
		return alpha;
	}

	public void printCentra() {
		neurons.forEach(System.out::println);
	}

	protected void checkDistanceToPoint(double[] point) {
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

	public void saveError() {
		File file = new File("error.txt");
		PrintWriter out = null;
		try {
			file.createNewFile();
			out = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Double e : errors) {
			out.println(e);
		}

		out.close();
	}
	public int checkOrder(int t) {
		for (int i = 0; i < distance.size(); i++) {
			if((double) t == distance.get(i).get(1)) return i;
		}
		return 0;
	}
}
