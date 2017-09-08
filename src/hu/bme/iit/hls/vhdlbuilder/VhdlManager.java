package hu.bme.iit.hls.vhdlbuilder;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.ElementaryOp;
import hu.bme.iit.hls.hig.HigModel.LoopNode;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.library.VhdlLibrary;
import hu.bme.iit.hls.library.VhdlLibraryEntry;

public class VhdlManager {
	private static VhdlLibrary lib = VhdlLibrary.getInstance();

	public Vhdl getVhdl(Node node) {
		Vhdl vhdl;
		if (!lib.hasVhdl(node.getName())) {
			vhdl = buildVhdlFromNode(node);
			lib.addVhdl(node.getName(),vhdl);
			enablePrint(vhdl);
			Logger.getLogger("").log(Level.INFO, node.getName() + " added to VHDL Library");
		} else {
			vhdl = lib.getVhdl(node.getName());
		}
		return vhdl;
	}

	private Vhdl buildVhdlFromNode(Node node) {
		Vhdl vhdl;
		VhdlBuilder builder=new VhdlBuilder();
		if(node instanceof LoopNode){
			vhdl=builder.buildVhdl((LoopNode)node);
		}else{if(node instanceof ComplexNode){
			vhdl=builder.buildVhdl((ComplexNode)node);
		}else{if(node instanceof ElementaryOp){
			vhdl=builder.buildVhdl((ElementaryOp)node);
		}else{
			throw new IllegalArgumentException(node.getClass().getName()+"Type of node is not supported yet.");
		}}}
		if(vhdl.getEntity()==null){
		vhdl.setEntity(EntityBuilder.buildEntity(node));
		}
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

	public void addSimpleOperation(String name, Node node) {
		if (!lib.enableOperation(name)) {
			throw new IllegalArgumentException("No vhdl found with name:" + name + ".");
		}
		if(!name.equals(node.getName())){
			
		}
	}

	public void removeVhdl(ElementaryOp node) {
		lib.removeVhdl(node.getName());
	}

	public void enableOperation(String name) {
		lib.addSimple(name);
		
	}
	private void enablePrint(Vhdl vhdl) {
		try {
			if (vhdl.getEntity() != null && vhdl.getArchitecture() != null) {

				enableOperation(vhdl.getEntity().getName());
			} else {
				throw new IllegalArgumentException("Vhdl is not printable named: " + vhdl.getEntity().getName());
			}
		} catch (Exception e) {
			Logger.getLogger(this.getClass().toString()).severe(e.toString());
		}
	}
}
