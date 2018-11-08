package hu.bme.iit.hls.vhdlprinter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.Source;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlConstant;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.higmodel.Component;
import hu.bme.iit.hls.higmodel.DataLink;
import hu.bme.iit.hls.higmodel.HIG;
import hu.bme.iit.hls.higmodel.InputPort;
import hu.bme.iit.hls.higmodel.Node;
import hu.bme.iit.hls.higmodel.Port;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class ComponenetPreprocessor {

	VhdlManager manager = new VhdlManager();
	SourceBuilder sourceBuilder = new SourceBuilder();

	public VhdlEntity getEntity(Component hig) {
		return manager.getVhdl(hig).getEntity();
	}

	public List<VhdlEntity> getComponents(Component hig) {
		return manager.getVhdl(hig).getComponentsList().stream().map(Vhdl::getEntity).collect(Collectors.toList());
	}

	public Map<Port, VhdlConstant> getConstants(HIG hig) {
		Map<Port, VhdlConstant> map = new HashMap<>();
		hig.getNodes().stream().flatMap(this::getInputPortStream).filter(k -> k.getValue() != null)
				.forEach(k -> map.put(k, sourceBuilder.createConstant(k.getValue())));
		return map;
	}

	private Stream<InputPort> getInputPortStream(Node node) {
		return node.getInPorts().stream().filter(InputPort.class::isInstance).map(InputPort.class::cast);
	}

	public Map<Port, Source> getSourceMap(HIG hig) {
		Map<Port, Source> portMap = new HashMap<>(getConstants(hig));
		List<DataLink> datalinks = getDataLinks(hig); 
		for (DataLink dl : datalinks) {
			Port source = dl.getSourcePort();
			Signal signal;
			if (portMap.containsKey(source)) {
				signal = (Signal) portMap.get(source);
			} else {
				signal = sourceBuilder.createSignal(source);
				portMap.put(source, signal);
			}
			portMap.put(dl.getTargetPort(), signal);
		}
		return portMap;
	}

	private List<DataLink> getDataLinks(HIG hig) {
		Stream<Port> higPorts = Stream.concat(hig.getInPorts().stream(),hig.getOutPorts().stream());
		Stream<Port> nodePorts = hig.getNodes().stream()
				.flatMap(k -> Stream.concat(
						k.getInPorts().stream().map(InputPort.class::cast).filter(l -> l.getValue() == null),
						k.getOutPorts().stream()));
		return Stream.concat(nodePorts,higPorts).flatMap(k -> Stream.concat(k.getOutEdges().stream(), Stream.of(k.getInEdge()))).filter(j->j!=null).distinct()
				.collect(Collectors.toList());
	}

	public List<Port> getPorts(Node node) {
		List<Port> ports = new ArrayList<>(node.getInPorts());
		ports.addAll(node.getOutPorts());
		return ports;
	}

}
