package hu.bme.iit.hls.vhdl.simpleoperations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cascade implements Iterable<Cascade> {
	private List<String> inputList = new ArrayList<>();
	private List<String> newInputList = new ArrayList<>();
	private final String RESULT_NAME = "result";
	int outputCounter = 0;
	int iteration = 0;

	public void addInput(String name) {
		newInputList.add(name);
	}

	public boolean startIteration() {
		if (newInputList.size() <= 1) {
			return false;
		}
		inputList = newInputList;
		newInputList = new ArrayList<>();
		if (inputList.size() % 2 == 1) {
			newInputList.add(inputList.get(0));
			inputList.remove(0);
		}
		return true;
	}

	public String getNexOutput() {
		String output = RESULT_NAME + Integer.toString(outputCounter++);
		this.addInput(output);
		return output;
	}

	public String getNextInput() {
		String next = inputList.get(0);
		inputList.remove(0);
		return next;
	}

	public boolean hasNext() {
		if (inputList.isEmpty()) {
			inputList = newInputList;
			newInputList = new ArrayList<>();
		}
		if (inputList.size() <= 1) {
			return false;
		}
		if (inputList.size() % 2 == 1) {
			newInputList.add(inputList.get(0));
			inputList.remove(0);
		}
		return !inputList.isEmpty();
	}

	public int getSignalsNeeded() {
		int signalPort = 1;
		int i = newInputList.size();
		int maradek;
		while (i > 1) {
			maradek = 0;
			if (i % 2 == 1) {
				maradek = 1;
			}
			i /= 2;
			signalPort += i + maradek;
		}
		return signalPort;
	}

	@Override
	public Iterator<Cascade> iterator() {
		return new CascadeIterator(this);

	}

	public void increaseIteration() {
		iteration++;
	}

	public int getIteration() {
		return iteration;
	}

	public String getResultname() {
		return RESULT_NAME;
	}
}
