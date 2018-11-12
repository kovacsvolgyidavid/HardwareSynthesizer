package hu.bme.iit.hls.vhdlprinter;

import java.math.BigInteger;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlConstant;
import hu.bme.iit.hls.higmodel.Port;

public class SourceBuilder {
	
	private Integer i = 0;

	public Signal createSignal(Port source) {
		return createSignal(source.getName());
	}
	
	public Signal createSignal(String name) {
		Signal signal = new Signal();
		signal.setName("signal_"+name+"_"+i++);
		signal.setBitWidth(32);
		return signal;
	}

	public VhdlConstant createConstant(BigInteger constant) {
		VhdlConstant vc = new VhdlConstant();
		vc.setConstant(constant);
		vc.setName("constant"+"_"+i++);
		vc.setBitWidth(32);
		return vc;
	}
	
	public VhdlConstant createConstant(String constant) {
		return createConstant(new BigInteger(constant));
	}

}
