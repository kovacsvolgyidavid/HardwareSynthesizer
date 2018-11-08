package hu.bme.iit.hls.library;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hu.bme.iit.hls.entities.Vhdl;

public class VhdlLibrary {
	private static VhdlLibrary INSTANCE;

	private VhdlLibrary() {

	}

	private Map<String, Vhdl> lib = new HashMap<>();
	private Map<String, Vhdl> elementaryLib = new HashMap<>();

	public boolean addVhdl(String name, Vhdl vhdl) {
		if (lib.containsKey(name)) {
			return true;
		} else {
			lib.put(name, vhdl);
			return false;
		}
	}

	public boolean hasVhdl(String name) {
		return lib.containsKey(name) || elementaryLib.containsKey(name);
	}

	public Vhdl getVhdl(String name) {
		Vhdl vhdl;
		if (lib.containsKey(name)) {
			vhdl = lib.get(name);
		} else {
			vhdl = elementaryLib.get(name);
		}
		return vhdl;
	}

	public static VhdlLibrary getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VhdlLibrary();
			INSTANCE.elementaryLib = BasicEntityReader.getElementaryVhdls().stream()
					.collect(Collectors.toMap(k -> k.getEntity().getName(), k -> k));
		}
		return INSTANCE;
	}

	public List<Vhdl> getAllVhdl() {
		return new ArrayList<Vhdl>(lib.values());
	}

	public void removeVhdl(String name) {
		lib.remove(name);
	}
}
