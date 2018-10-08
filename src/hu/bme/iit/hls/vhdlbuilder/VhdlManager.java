package hu.bme.iit.hls.vhdlbuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.ElementaryOp;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.library.VhdlLibrary;
import hu.bme.iit.hls.library.VhdlLibraryEntry;

public class VhdlManager {
	private static VhdlLibrary lib = VhdlLibrary.getInstance();

	public Vhdl getVhdl(Node node) {
		Vhdl vhdl;
		if(node instanceof ElementaryOp){//Elementary operation esetén aktiválja a VHDL-t
			ElementaryOp elem=(ElementaryOp)node;
			vhdl=lib.getVhdl(elem.getOpType().getName());
		}
		else {
		if (!lib.hasVhdl(node.getName())) {	//ha nincs a VHDL Library-ben kreál egy új VHDL-t
			vhdl = createVhdlFromNode(node);
			lib.addVhdl(node.getName(),vhdl);
			Logger.getLogger("").log(Level.INFO, node.getName() + " added to VHDL Library");
		} else {
			vhdl = lib.getVhdl(node.getName());
		}
		}
		return vhdl;
	}

	private Vhdl createVhdlFromNode(Node node) {
		Vhdl vhdl=new Vhdl();
		vhdl.setEntity(EntityBuilder.buildEntity(node));
		ComplexNode n= (ComplexNode) node;
		return vhdl;
	}

	public Vhdl getVhdl(String name) {
		return lib.getVhdl(name);
	}

	public void importVhdl(String name) {
		lib.addSimple(name);
	}

	public List<VhdlLibraryEntry> getAllVhdl() {
		// TODO Auto-generated method stub
		return lib.getAllVhdl();
	}

	public void enableSimpleOperation(String name) {
		if (!lib.enableOperation(name)) {
			throw new IllegalArgumentException("No vhdl found with name:" + name + ".");
		}
	}

	public void removeVhdl(ElementaryOp node) {
		lib.removeVhdl(node.getName());
	}

	public void enableOperation(String name) {
		lib.addSimple(name);
		
	}
	
	public void enablePrint(Vhdl vhdl) {//TODO: Kitörölni
	}

	public VhdlEntity getEntity(Node node) {
	  return lib.getVhdl(node.getName()).getEntity();
	}
}
