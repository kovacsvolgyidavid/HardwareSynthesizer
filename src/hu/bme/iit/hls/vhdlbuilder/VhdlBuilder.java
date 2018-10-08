package hu.bme.iit.hls.vhdlbuilder;

import java.io.IOException;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.ElementaryOp;
import hu.bme.iit.hls.hig.HigModel.LoopNode;
import hu.bme.iit.hls.vhdl.simpleoperations.SimpleOperationVhdlBuilder;
import hu.bme.iit.hls.vhdlprinter.VhdlPrintManager;
import hu.bme.iit.hls.vhdlprinter.VhdlPrinter;

public class VhdlBuilder {
	private VhdlManager vhdlManager = new VhdlManager();
	private VhdlPrintManager printManager = new VhdlPrintManager("");

	public Vhdl buildVhdl(ComplexNode node) {
		 	Vhdl vhdl= vhdlManager.getVhdl(node);
		 	vhdl.setEntity(EntityBuilder.buildEntity(node));
		 	try {
				vhdl.setVhdlFile(printManager.createVhdl(node));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		Vhdl vhdl= vhdlManager.getVhdl(node);
	 	vhdl.setEntity(EntityBuilder.buildEntity(node));
	 	try {
			vhdl.setVhdlFile(printManager.createVhdl(node));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vhdlManager.enablePrint(vhdl);
		return vhdl;
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
