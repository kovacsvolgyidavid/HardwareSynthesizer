package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.Const;
import hu.bme.iit.hls.hig.HigModel.Edge;
import hu.bme.iit.hls.hig.HigModel.ElementaryOp;
import hu.bme.iit.hls.hig.HigModel.LoopNode;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.PortEdge;
import hu.bme.iit.hls.utility.HIGUtility;
import hu.bme.iit.hls.utility.Utility;
import hu.bme.iit.hls.vhdl.simpleoperations.SimpleOperationVhdlBuilder;
import hu.bme.iit.hls.vhdlprinter.VhdlPrinter;

public class VhdlBuilder {
	private VhdlManager vhdlManager = new VhdlManager();

	public Vhdl buildVhdl(ComplexNode node) {
		 	Vhdl vhdl= new Vhdl();
			vhdl.setIncludes(createIncludes(node));
			vhdl.setArchitecture(createArchitecture(node));
			return vhdl;
		
	}

	private Architecture createArchitecture(ComplexNode node) {
		Architecture arch = new Architecture();
		List<VhdlEntity> components = new ArrayList<>();
		for (Object higNode : Utility.filterList(node.getInnerGraph().getNodes(), Const.class, true)) {
			components.add(vhdlManager.getVhdl((Node) higNode).getEntity());
		}
		arch.setComponents(components);
		List<Const> constants = new ArrayList<>();
		Utility.filterList(node.getInnerGraph().getNodes(), Const.class, false).forEach(e -> constants.add((Const) e));
		arch.setConstants(constants);
		Set<Signal> signals = new HashSet<>();
		node.getInnerGraph().getEdges().forEach(k -> signals.add(createSignal(k, HIGUtility.getBitWidth())));
		arch.setSignals(signals);
		arch.setBody(ComplexBuild.buildBody(node));
		return arch;
	}

	private Signal createSignal(Edge k, int bitWidth) {
		PortEdge edge=(PortEdge)k;
		Signal signal = new Signal();
		signal.setBitWidth(bitWidth);
		signal.setName(edge.getSourcePort().getName());
		return signal;
	}

	private String createIncludes(ComplexNode node) {
		return VhdlPrinter.getIncludes().toString();
	}

	public Vhdl buildVhdl(LoopNode node) {
		throw new UnsupportedOperationException("Not implemented yet.");
		// Vhdl vhdl = new Vhdl();
		// vhdl.setIncludes(createIncludes(node));
		// vhdl.setEntity(createEntity(node));
		// vhdl.setArchitecture(createArchitecture(node));
		// return vhdl;
	}

	// private static Architecture createArchitecture(LoopNode node) {
	// Architecture arch = new Architecture();
	// return arch;
	// }
	//
	// private static String createIncludes(LoopNode node) {
	// return VhdlPrinter.defaultIncludes().toString();
	// }

	public Vhdl buildVhdl(ElementaryOp node) {
		SimpleOperationVhdlBuilder builder = new SimpleOperationVhdlBuilder();
		return builder.buildSimpleOperationVhdl(node);
		
	}
}
