package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.List;

import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.Port;
import hu.bme.iit.hls.utility.HIGUtility;

public class EntityBuilder {
	public static VhdlEntity buildEntity(Node node) {
		VhdlEntity entity = new VhdlEntity();
		entity.setName(node.getName());
		entity.setGenerics(null);
		List<VhdlPort> ports = new ArrayList<>();
		for (Port port : node.getPorts()) {
			VhdlPort vhdlPort = new VhdlPort();
			vhdlPort.setBitWidth(HIGUtility.getBitWidth());
			vhdlPort.setName(node.getName() + port.getName());
			vhdlPort.setInOut(HIGUtility.getInorOut(port, node));
			ports.add(vhdlPort);
		}
		entity.setPorts(ports);
		return entity;
	}
}
