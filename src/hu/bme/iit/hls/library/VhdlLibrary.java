package hu.bme.iit.hls.library;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
	//
	// public static void print(Vhdl vhdl) {
	// BufferedReader br;
	// try {
	// br = new BufferedReader(new FileReader(vhdl.getVhdlFile()));
	// String line = null;
	// while ((line = br.readLine()) != null) {
	// System.out.println(line);
	// }
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

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
		return enableOperation(lib.get(name).getVhdl());		
	}

	public boolean enableOperation(Vhdl vhdl) {
		if (isVhdlContained(vhdl)) {
			setPrintable(vhdl);
			vhdl.getComponentsList().forEach(k -> enableOperation(k));
			return true;
		} else {
			return false;
		}
	}

	private boolean isVhdlContained(Vhdl vhdl) {
		return lib.containsKey(vhdl.getEntity().getName());
	}

	private void setPrintable(Vhdl vhdl) {
		lib.get(vhdl.getEntity().getName()).setIsPrintable(true);
	}

	public void removeVhdl(String name) {
		lib.remove(name);
	}
}
