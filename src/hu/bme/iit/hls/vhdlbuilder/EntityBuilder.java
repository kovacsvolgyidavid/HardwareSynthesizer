package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.entities.VhdlPortType;
import hu.bme.iit.hls.higmodel.Component;
import hu.bme.iit.hls.higmodel.Port;
import hu.bme.iit.hls.utility.HIGUtility;

public class EntityBuilder {
	public static VhdlEntity buildEntity(Component node) {
		VhdlEntity entity = new VhdlEntity();
		entity.setName(node.getName());
		
		VhdlPort reset = new VhdlPort();
		VhdlPort loopReset = new VhdlPort();
		VhdlPort clock = new VhdlPort();	

		reset.setBitWidth(1);
		loopReset.setBitWidth(1);
		clock.setBitWidth(1);	

		reset.setInOut(InOut.IN);
		loopReset.setInOut(InOut.IN);
		clock.setInOut(InOut.IN);

		reset.setName("rst");
		loopReset.setName("loop_rst");
		clock.setName("clk");

		reset.setVhdlPortType(VhdlPortType.STD_LOGIC);
		loopReset.setVhdlPortType(VhdlPortType.STD_LOGIC);
		clock.setVhdlPortType(VhdlPortType.STD_LOGIC);

		Stream <VhdlPort> inPorts = node.getInPorts().stream().flatMap(k->toVhdlPort(InOut.IN, k.getName()));	
		Stream <VhdlPort> outPorts = node.getOutPorts().stream().flatMap(k->toVhdlPort(InOut.OUT, k.getName()));
		
		List<VhdlPort> vhdlPorts = concatStreams(Stream.of(reset,loopReset,clock),inPorts, outPorts).collect(Collectors.toList());
		entity.setPorts(vhdlPorts);
		
		return entity;
	}

	@SafeVarargs
	private static <T> Stream<T> concatStreams(Stream<T> ... of) {
		Stream<T> mainStream = Stream.empty();
		for (Stream<T> t : of){
			mainStream = Stream.concat(mainStream, t);			
		}
		return mainStream;
	}

	private static Stream<VhdlPort> toVhdlPort(InOut portType, String name) {
		VhdlPort vhdlPort = new VhdlPort();
		VhdlPort vhdlPortReady = new VhdlPort();
		vhdlPort.setBitWidth(HIGUtility.getBitWidth());
		vhdlPortReady.setBitWidth(1);
		vhdlPort.setInOut(portType);
		vhdlPortReady.setInOut(portType);
		vhdlPort.setName(name);
		vhdlPortReady.setName(name+"_rdy");
		vhdlPort.setVhdlPortType(VhdlPortType.STD_LOGIC);
		vhdlPortReady.setVhdlPortType(VhdlPortType.BOOLEAN);
		return Stream.concat(Stream.of(vhdlPort),Stream.of(vhdlPortReady));
	}
}
