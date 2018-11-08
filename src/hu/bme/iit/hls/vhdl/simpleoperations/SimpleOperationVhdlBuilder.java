package hu.bme.iit.hls.vhdl.simpleoperations;

import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class SimpleOperationVhdlBuilder {
	VhdlManager manager = new VhdlManager();

//	public Vhdl buildSimpleOperationVhdl(ElementaryOp node) {
//		String operation=node.getOpType().getName();
//		if (manager.getVhdl(operation) == null) {
//			throw new IllegalArgumentException("Not supported operation type:" + operation);
//		}else{
//			return manager.getVhdl(operation);
//		}
////		if (operation.equals("add")) {			
////			manager.enableOperation("add");
////			return cascadeOperation(node);
////		} else {
////			if (operation.equals("mul")) {
////				
////				manager.enableOperation("mul");
////				return cascadeOperation(node);
////			}else{
////				throw new IllegalArgumentException("Not supported operation type:" + operation);
////			}
////		}
//	}
//
////	private Vhdl cascadeOperation(ElementaryOp node) {
////		if (Utility.filterList(node.getPorts(), InputPort.class, false).size() > 2) {
////				Vhdl vhdl = new Vhdl();
////				vhdl.setArchitecture(buildCascadeArchitecture(node, vhdl));
////				vhdl.setIncludes(buildIncludes(node));
////				vhdl.setEntity(EntityBuilder.buildEntity(node));
////				return vhdl;
////			}
////		else{
////			return manager.getVhdl(node.getOpType().getName());
////		}
////	}
////
////	private Architecture buildCascadeArchitecture(ElementaryOp node, Vhdl vhdl) {
////		Architecture arch = new Architecture();
////		Set<Signal> signals = new HashSet<>();
////		Cascade cascade = new Cascade();
////		vhdl.getEntity().getPorts().stream().filter(y -> y.getInOut().equals(InOut.IN))
////				.forEach(a -> cascade.addInput(a.getName()));
////		for (int i = 0; i < cascade.getSignalsNeeded(); i++) {
////			Signal signal = new Signal();
////			signal.setName(cascade.getResultname() + Integer.toString(i + 1));
////			signal.setBitWidth(HIGUtility.getBitWidth());
////			signals.add(signal);
////		}
////		arch.setSignals(signals);
////		List<VhdlEntity> components = new ArrayList<>();
////		components.add(new VhdlManager().getVhdl(node.getOpType().getName()).getEntity());
////		arch.setComponents(components);
////		arch.setBody(SimpleOperationExpander.buildOperationBody(node, cascade));
////		arch.setConstants(new ArrayList<Const>());
////		return arch;
////	}
//
//	private String buildIncludes(ElementaryOp node) {
//		return VhdlPrinter.getIncludes();
//
//	}

}
