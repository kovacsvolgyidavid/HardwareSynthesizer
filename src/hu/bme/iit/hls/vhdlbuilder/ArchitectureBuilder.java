 package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlEntityInstance;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.Const;
import hu.bme.iit.hls.hig.HigModel.InputPort;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.OutputPort;
import hu.bme.iit.hls.hig.HigModel.Port;
import hu.bme.iit.hls.utility.Utility;

public class ArchitectureBuilder {

	VhdlManager vhdlManager;
	List<VhdlEntityInstance> components;
	List<Const> constants;
	Architecture arch;
	Map<Port, Signal> sourcePortMapping;

	public ArchitectureBuilder(VhdlManager vhdlManager) {
		this.vhdlManager = vhdlManager;
	}

	public Architecture buildArchitecture(ComplexNode node) {
		buildArchitectureComponents(node);
		createArchitecture();
		return arch;

	}

	private void buildArchitectureComponents(ComplexNode node) {
		buildConstants(node);
		buildComponentsAndSignalMapping(node);
		buildSelfSignalMapping(node);

	}

	private void buildSelfSignalMapping(ComplexNode node) {
	   		mapPorts(node, this::getSourceForComplex);
	}
	
	private Port getSourceForComplex(Port port){
		if (port instanceof OutputPort) {
			return port.getInEdge().getSourcePort();
		} else {
			return port;
		}
		
	}

	private void buildComponentsAndSignalMapping(ComplexNode node) {		
		components = new ArrayList<>();
		sourcePortMapping = new HashMap<>();
		for (Object obj : Utility.filterList(node.getInnerGraph().getNodes(), Const.class, true)) {
			Node higNode = (Node) obj;
			VhdlEntityInstance instance = new VhdlEntityInstance();
			instance.setEntity((vhdlManager.getVhdl(higNode).getEntity()));
			instance.setPortMap(mapPorts(higNode, this::getSourcePortForEnbedded));
		}

	}

	private Map<VhdlPort, Signal> mapPorts(Node higNode, Function<Port,Port> getSource) {
		VhdlEntity entity = vhdlManager.getVhdl(higNode).getEntity();
		List<VhdlPort> vhdlPorts = vhdlManager.getVhdl(higNode).getEntity().getPorts();
		List<Port> ports = higNode.getPorts();
		Map<VhdlPort, Signal> signalMap = new HashMap<>();
		if(vhdlPorts.size()!=ports.size()){
			throw new IllegalStateException("A "+higNode.getName()+"/"+ entity.getName()+ " node/entity esetén a portszám nem egyezik meg");
					}
		for (int i = 0; i<vhdlPorts.size();i++){			
			mapPort(getSource.apply(ports.get(i)),vhdlPorts.get(i),signalMap);
		}
		return signalMap;
	}

	private void mapPort(Port sourcePort, VhdlPort vhdlPort, Map<VhdlPort, Signal> signalMap) {	
		Signal signal = getSignal(sourcePort, vhdlPort.getBitWidth());
		signalMap.put(vhdlPort, signal);
	}

	private Signal getSignal(Port sourcePort, int bitwidth) {
		Signal signal = sourcePortMapping.get(sourcePort);
		return (signal == null) ? createSignal(sourcePort,bitwidth) : signal;

	}
	
	private Signal createSignal(Port port, int bitwidth) {
		Signal signal = new Signal();
		signal.setBitWidth(bitwidth);
		signal.setName("signal_" + port.getName());
		sourcePortMapping.put(port, signal);
		return signal;
	}

	private Port getSourcePortForEnbedded(Port port) {
		if (port instanceof InputPort) {
 			return port.getInEdge().getSourcePort();
		} else {
			return port;
		}
	}

	private void buildConstants(ComplexNode node) {
		constants = new ArrayList<>();
		Utility.filterList(node.getInnerGraph().getNodes(), Const.class, false).forEach(e -> constants.add((Const) e));
	}

	private void createArchitecture() {
		arch = new Architecture();
		arch.setBody("");
		arch.setConstants(constants);
		arch.setComponents(components);
	}

}
