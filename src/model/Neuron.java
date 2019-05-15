package model;

import java.util.Vector;

public class Neuron {
	public Vector<Double> weight;

	public Neuron() {
		this.weight = new Vector<Double>();
	}

	public Neuron(Vector<Double> V) {
		this.weight = new Vector<Double>(V);
	}

	public void setXY(Vector<Double> V) {
		this.weight.set(0, V.get(0));
		this.weight.set(1, V.get(1));
	}

	public double checkDistance(double[] point) {
		return Math.sqrt(Math.pow((this.weight.get(0) - point[0]), 2) + Math.pow((this.weight.get(1) - point[1]), 2));
	}

	public double checkDistance(Neuron neuron) {
		return Math.sqrt(Math.pow((this.weight.get(0) - neuron.weight.get(0)), 2)
				+ Math.pow((this.weight.get(1) - neuron.weight.get(1)), 2));
	}
}
