package hu.bme.iit.hls.vhdlbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.higmodel.CompRef;
import hu.bme.iit.hls.higmodel.Component;
import hu.bme.iit.hls.library.VhdlLibrary;

public class VhdlManager {
	private static VhdlLibrary lib = VhdlLibrary.getInstance();

	public Vhdl getVhdl(Component comp) {
		Component workingComp = comp;
		if (comp instanceof CompRef) {
			workingComp = ((CompRef) comp).getRef();
		}
		String name = workingComp.getName();
		if (lib.hasVhdl(name)) {
			return lib.getVhdl(name);
		} else {
			Vhdl vhdl = createVhdl(name, workingComp);
			lib.addVhdl(name, vhdl);
			return vhdl;
		}

	}

	private Vhdl createVhdl(String name, Component workingComp) {
		Optional<Vhdl> dynamicVhdl = new DynamicVhdlBuilder().createDynamicVhdl(name);
		Vhdl vhdl = dynamicVhdl.orElse(createBasicVhdl(workingComp, name));
		vhdl.setVhdlFile(createTempFile(name));
		return vhdl;
	}

	private File createTempFile(String name) {	
		return Paths.get("output",name+ ".vhd").toFile();
	}

	private Vhdl createBasicVhdl(Component comp, String name) {
		Vhdl vhdl = new Vhdl(EntityBuilder.buildEntity(comp));
		return vhdl;
	}

	public Vhdl getVhdl(String name) {
		if (lib.hasVhdl(name)) {
			return lib.getVhdl(name);
		} else {
			Optional<Vhdl> dynamicVhdl = new DynamicVhdlBuilder().createDynamicVhdl(name);
			dynamicVhdl.ifPresent(k->lib.addVhdl(name, k));			
			return dynamicVhdl.get();
		}
	}

}
