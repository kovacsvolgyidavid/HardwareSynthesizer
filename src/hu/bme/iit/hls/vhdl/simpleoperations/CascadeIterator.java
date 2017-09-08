package hu.bme.iit.hls.vhdl.simpleoperations;

import java.util.Iterator;

public class CascadeIterator implements Iterator<Cascade> {
	private Cascade cascade;

	public CascadeIterator(Cascade cascade) {
		this.cascade = cascade;
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return cascade.hasNext();
	}

	@Override
	public Cascade next() {
		cascade.increaseIteration();
		return cascade;
	}

}
