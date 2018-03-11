package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.EntityMap;
import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.Const;
import hu.bme.iit.hls.hig.HigModel.Edge;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.PortEdge;
import hu.bme.iit.hls.utility.HIGUtility;
import hu.bme.iit.hls.utility.Utility;

public class ArchitectureBuilder {
	
	VhdlManager vhdlManager;
	Set<Signal> signals;
	
	public ArchitectureBuilder(VhdlManager vhdlManager){
		this.vhdlManager = vhdlManager;
		
	}

	public Architecture buildArchitecture(ComplexNode node) {
		
		Architecture arch = new Architecture();
		List<VhdlEntity> components = new ArrayList<>();
		for (Object higNode : Utility.filterList(node.getInnerGraph().getNodes(), Const.class, true)) {
			components.add(vhdlManager.getVhdl((Node) higNode).getEntity());
		}
		arch.setComponents(components);
		List<Const> constants = new ArrayList<>();
		Utility.filterList(node.getInnerGraph().getNodes(), Const.class, false).forEach(e -> constants.add((Const) e));
		arch.setConstants(constants);
		signals = new HashSet<>();
		node.getInnerGraph().getEdges().forEach(k-> 
					signals.add(createSignal(k, HIGUtility.getBitWidth())));
		arch.setSignals(signals);
		arch.setSelfMapping(buildSelfMapping(node));
		arch.setBody(ComplexBuild.buildBody(node));
		return arch;
	}
	
	private EntityMap buildSelfMapping(ComplexNode node) {
		VhdlEntity nodeEntity = vhdlManager.getVhdl(node).getEntity();
		EntityMap map = new EntityMap(nodeEntity);
		nodeEntity.getPorts().forEach(k->{
			
			
		});
		return map;
	}

	private Signal createSignal(Edge k, int bitWidth) {
		PortEdge edge=(PortEdge)k;
		Signal signal = new Signal();
		signal.setBitWidth(bitWidth);
		signal.setName("signal_"+edge.getSourcePort().getName());
		return signal;
	}

}
