package hu.bme.iit.hls.library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.bme.iit.hls.entities.Vhdl;

public class VhdlLibrary {
	private static VhdlLibrary INSTANCE;

	private VhdlLibrary() {
	}

	private Map<String, VhdlLibraryEntry> lib = new HashMap<>();

	public boolean addVhdl(String name, Vhdl vhdl) {
		if (lib.containsKey(name)) {
			return true;
		} else {
			lib.put(name, new VhdlLibraryEntry(vhdl, false));
			return false;
		}
	}

	public boolean hasVhdl(String name) {
		return lib.containsKey(name);
	}

	public Vhdl getVhdl(String name) {
		return lib.get(name).getVhdl();
	}

	public static VhdlLibrary getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VhdlLibrary();
			new BasicEntityReader().getBasicOperitons(null)
					.forEach(k -> INSTANCE.lib.put(k.getEntity().getName(), new VhdlLibraryEntry(k, false)));

		}
		return INSTANCE;
	}

	public List<VhdlLibraryEntry> getAllVhdl() {
		return new ArrayList<VhdlLibraryEntry>(lib.values());
	}

	public void addSimple(String name) {
		if (lib.containsKey(name)) {
			lib.get(name).setIsPrintable(true);
		} else {
			throw new IllegalArgumentException("Operation" + name + " is not supported yet.");
		}
	}

	public boolean enableOperation(String name) {
		if (lib.containsKey(name)) {
			lib.get(name).setIsPrintable(true);
			return true;
		} else {
			return false;
		}

	}

	public void removeVhdl(String name) {
		lib.remove(name);
	}
}
