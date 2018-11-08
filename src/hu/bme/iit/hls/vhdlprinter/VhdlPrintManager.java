package hu.bme.iit.hls.vhdlprinter;

import java.util.logging.Logger;

import hu.bme.iit.hls.higmodel.HIG;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class VhdlPrintManager {
	VhdlManager vhdlManager = new VhdlManager();
	private final static String FILE_EXT = ".vhd";
	String resultDirectory;
	private final Logger LOGGER = Logger.getLogger(VhdlPrintManager.class.toString());

	public VhdlPrintManager(String resultDirectory) {
		this.resultDirectory = resultDirectory;
	}
	
	public String createVhdl(HIG hig){
		return HIGPrinter.printVhdl(hig);
	}


}
