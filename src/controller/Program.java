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

	protected abstract void changeCenterCoords(double[] point, int kx);

	public Program(double lengthOfHorizontalSide, double lengthOfVerticalSide, double centerOfRectangleX,
			double centerOfRectangleY, int noOfPoints, int noOfCenters, String typeOfGeneratingCenters)
			throws IOException {
		init(noOfPoints);
		generateRandomPointsInRectangle(lengthOfHorizontalSide, lengthOfVerticalSide, centerOfRectangleX,
				centerOfRectangleY, noOfPoints);
		initCenters(noOfCenters, typeOfGeneratingCenters);
	}

	public Program(double radius, double centerX, double centerY, int noOfPoints, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		init(noOfPoints);
		generateRandomPointsInCircle(radius, centerX, centerY, noOfPoints);
		initCenters(centerX, centerY, radius, noOfCenters, typeOfGeneratingCenters);
	}

	public Program(int noOfPoints, double lengthOfSide, double centerOfSquareX, double centerOfSquareY, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		init(noOfPoints);
		generateRandomPointsInSquare(lengthOfSide, centerOfSquareX, centerOfSquareY, noOfPoints);
		initCenters(noOfCenters, typeOfGeneratingCenters);
	}

	public Program(double height, int noOfPoints, double positiveX1, double positiveX2, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		init(noOfPoints);
		generateRandomPointsInLine(height, positiveX1, positiveX2, noOfPoints);
		initCenters(noOfCenters, typeOfGeneratingCenters);
	}

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
					shuffle = ThreadLocalRandom.current().nextInt(0, points.length);
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

	protected void init(int noOfPoints) {
		points = new double[noOfPoints * 2][2];
		neurons = new ArrayList<Neuron>();
		distance = new Vector<Vector<Double>>();
		alpha = 0.1;
		lambda = 3.0;
		shuffle = ThreadLocalRandom.current().nextInt(0, points.length);
		errors = new Vector<>();
	}

	protected void initCenters(double centerX, double centerY, double radius, int noOfCenters,
			String typeOfGeneratingCenters) throws IOException {
		savePoints();
		switch (typeOfGeneratingCenters) {
		case "CIRCLE":
			generateCentersInCircles(centerX, centerY, radius, noOfCenters);
			break;

		case "NO_DEAD":
			generateCentersNoDead(noOfCenters);
			break;

		case "MOVED":
			generateCentersMovedFromRandomPoints(noOfCenters);
			break;
			
		case "RANDOM":
			generateCentersRandomly(noOfCenters);
			break;

		default:
			System.out.println("Nieprawid³owy argument args[1]. Mo¿liwe to:\nNO_DEAD\nMOVED\nRANDOM\noraz CIRCLE, gdy args[2] to CIRCLE.");
			System.exit(-2);
		}
	}

	protected void initCenters(int noOfCenters, String typeOfGeneratingCenters) throws IOException {
		savePoints();
		switch (typeOfGeneratingCenters) {
		case "NO_DEAD":
			generateCentersNoDead(noOfCenters);
			break;

		case "MOVED":
			generateCentersMovedFromRandomPoints(noOfCenters);
			break;

		case "RANDOM":
			generateCentersRandomly(noOfCenters);
			break;
			
		default:
			System.out.println("Nieprawid³owy argument args[1]. Mo¿liwe to:\nNO_DEAD\nMOVED\nRANDOM\noraz CIRCLE, gdy args[2] to CIRCLE.");
			System.exit(-2);
		}
	}

	protected void generateCentersRandomly(int n) {
		for (int i = 0; i < n; i++) {
			neurons.add(i, new Neuron(setCenterCoords()));
		}
	}

	protected void generateCentersNoDead(int noOfCenters) {
		generateCentersRandomly(noOfCenters);
		Vector<Integer> tab;

		do {
			Vector<Vector<Double>> V = new Vector<Vector<Double>>();
			checkDead(noOfCenters, V);
			tab = new Vector<Integer>();
			for (int i = 0; i < V.size(); i++) {
				if (V.get(i).size() == 0) {
					tab.add(i);
					neurons.get(i).setXY(setCenterCoords());
				}
			}
		} while (tab.size() != 0);

	}

	protected void checkDead(int noOfCenters, Vector<Vector<Double>> V) {
		for (int i = 0; i < noOfCenters; i++) {
			V.add(i, new Vector<Double>());
		}
		for (int i = 0; i < points.length; i++) {
			checkDistanceToPoint(points[i]);
			V.get(distance.get(0).get(1).intValue()).add(1.0);
		}
	}

	protected void generateCentersMovedFromRandomPoints(int noOfCenters) {
		for (int i = 0; i < noOfCenters; i++) {
			shuffle = ThreadLocalRandom.current().nextInt(0, points.length);
			Vector<Double> V = new Vector<Double>();
			double sign = Math.random();
			if (sign < 0.5)
				V.add(points[shuffle][0] + Math.random() * 2);
			else
				V.add(points[shuffle][0] - Math.random() * 2);

			sign = Math.random();
			if (sign < 0.5)
				V.add(points[shuffle][1] + Math.random() * 2);
			else
				V.add(points[shuffle][1] - Math.random() * 2);
		}
	}

	protected void generateCentersInCircles(double centerX, double centerY, double radius, int noOfCenters) {
		double a, r, x;

		for (int i = 0; i < noOfCenters / 2; i++) {
			Vector<Double> V = new Vector<Double>();
			a = Math.random() * 2 * Math.PI;
			r = radius * Math.sqrt(Math.random());
			x = Math.random();
			if (x < 0.5) {
				V.add((r * Math.cos(a) + centerX));
			} else {
				V.add((r * Math.cos(a) - centerX));
			}
			V.add((r * Math.sin(a) + centerY));
			neurons.add(i, new Neuron(V));
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

	protected void generateRandomPointsInCircle(double R, double OX, double OY, int noOfPoints) {
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

	protected void generateRandomPointsInLine(double height, double positiveX1, double positiveX2, int noOfPoints) {
		for (int i = 0; i < noOfPoints; i++) {
			points[i][0] = ThreadLocalRandom.current().nextDouble(positiveX1, positiveX2);
			points[i][1] = height;
			
			points[noOfPoints + i][0] = -ThreadLocalRandom.current().nextDouble(positiveX1, positiveX2);
			points[noOfPoints + i][1] = height;
		}
	}

	protected void generateRandomPointsInSquare(double lengthOfSide, double centerOfSquareX, double centerOfSquareY,
			int noOfPoints) {
		double rand;

		for (int i = 0; i < noOfPoints; i++) {
			rand = Math.random();
			if (rand < 0.5) {
				points[i][0] = centerOfSquareX - (Math.random() * lengthOfSide / 2);
			} else {
				points[i][0] = centerOfSquareX + (Math.random() * lengthOfSide / 2);
			}

			rand = Math.random();
			if (rand < 0.5) {
				points[i][1] = centerOfSquareY - (Math.random() * lengthOfSide / 2);
			} else {
				points[i][1] = centerOfSquareY + (Math.random() * lengthOfSide / 2);
			}
			
			rand = Math.random();
			if (rand < 0.5) {
				points[noOfPoints + i][0] = -centerOfSquareX - (Math.random() * lengthOfSide / 2);
			} else {
				points[noOfPoints + i][0] = -centerOfSquareX + (Math.random() * lengthOfSide / 2);
			}

			rand = Math.random();
			if (rand < 0.5) {
				points[noOfPoints + i][1] = centerOfSquareY - (Math.random() * lengthOfSide / 2);
			} else {
				points[noOfPoints + i][1] = centerOfSquareY + (Math.random() * lengthOfSide / 2);
			}
		}
	}

	protected void generateRandomPointsInRectangle(double lengthOfHorizontalSide, double lengthOfVerticalSide,
			double centerOfSquareX, double centerOfSquareY, int noOfPoints) {
		double rand;

		for (int i = 0; i < noOfPoints; i++) {
			rand = Math.random();
			if (rand < 0.5) {
				points[i][0] = centerOfSquareX - (Math.random() * lengthOfHorizontalSide / 2);
			} else {
				points[i][0] = centerOfSquareX + (Math.random() * lengthOfHorizontalSide / 2);
			}

			rand = Math.random();
			if (rand < 0.5) {
				points[i][1] = centerOfSquareY - (Math.random() * lengthOfVerticalSide / 2);
			} else {
				points[i][1] = centerOfSquareY + (Math.random() * lengthOfVerticalSide / 2);
			}
			
			rand = Math.random();
			if (rand < 0.5) {
				points[noOfPoints + i][0] = -centerOfSquareX - (Math.random() * lengthOfHorizontalSide / 2);
			} else {
				points[noOfPoints + i][0] = -centerOfSquareX + (Math.random() * lengthOfHorizontalSide / 2);
			}

			rand = Math.random();
			if (rand < 0.5) {
				points[noOfPoints + i][1] = centerOfSquareY - (Math.random() * lengthOfVerticalSide / 2);
			} else {
				points[noOfPoints + i][1] = centerOfSquareY + (Math.random() * lengthOfVerticalSide / 2);
			}
		}
	}

	public void printCentra() {
		neurons.forEach(System.out::println);
	}

	public void saveError() {
		File file = new File("error.txt");
		PrintWriter out = null;
		try {
			out = new PrintWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}

//		for (Double e : errors) {
//			out.println(e);
//		}
		out.println(errors.lastElement());

		out.close();
	}

	protected void savePoints() throws IOException {
		File file = new File("points.txt");
		PrintWriter out = null;
		try {
//			file.createNewFile();
			out = new PrintWriter(new FileWriter(file));
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < points.length; i++) {
			out.println(points[i][0] + ";" + points[i][1]);
		}

		out.close();
	}

	public int checkOrder(int t) {
		for (int i = 0; i < distance.size(); i++) {
			if ((double) t == distance.get(i).get(1))
				return i;
		}
		return 0;
	}
}
