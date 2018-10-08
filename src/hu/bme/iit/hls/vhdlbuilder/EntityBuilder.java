package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.List;

import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.entities.VhdlPortType;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.Port;
import hu.bme.iit.hls.utility.HIGUtility;

public class EntityBuilder {
	public static VhdlEntity buildEntity(Node node) {
		VhdlEntity entity = new VhdlEntity();
		entity.setName(node.getName());

		List<VhdlPort> VhdlPorts = new ArrayList<>();
		VhdlPort reset = new VhdlPort();
		VhdlPort loopReset = new VhdlPort();
		VhdlPort clock = new VhdlPort();	

		VhdlPorts.add(reset);
		VhdlPorts.add(loopReset);
		VhdlPorts.add(clock);

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


		for (Port port : node.getPorts()) {
			VhdlPort vhdlPort = new VhdlPort();
			VhdlPort vhdlPortReady = new VhdlPort();
			VhdlPorts.add(vhdlPort);
			VhdlPorts.add(vhdlPortReady);
			vhdlPort.setBitWidth(HIGUtility.getBitWidth());
			vhdlPortReady.setBitWidth(1);
			vhdlPort.setInOut(HIGUtility.getInorOut(port, node));
			vhdlPortReady.setInOut(HIGUtility.getInorOut(port, node));
			vhdlPort.setName(HIGUtility.getPortName(node,port));
			vhdlPortReady.setName(HIGUtility.getReadyPortName(node,port));
			vhdlPort.setVhdlPortType(VhdlPortType.STD_LOGIC);
			vhdlPortReady.setVhdlPortType(VhdlPortType.BOOLEAN);
		}
		entity.setPorts(VhdlPorts);
		return entity;
	}
}
