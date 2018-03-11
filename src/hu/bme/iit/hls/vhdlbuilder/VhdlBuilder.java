package hu.bme.iit.hls.vhdlbuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		 	Vhdl vhdl= vhdlManager.getVhdl(node);
			vhdl.setIncludes(createIncludes(node));
			vhdl.setArchitecture(buildArchitecture(node));
			vhdlManager.enablePrint(vhdl);
			return vhdl;
		
	}

	private Architecture buildArchitecture(ComplexNode node) {
		ArchitectureBuilder builder = new ArchitectureBuilder(vhdlManager);
		return builder.buildArchitecture(node);		
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
