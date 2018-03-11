package hu.bme.iit.hls.vhdlprinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.library.VhdlLibraryEntry;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class VhdlPrintManager {
	VhdlManager vhdlManager = new VhdlManager();
	private final static String FILE_EXT = ".vhd";
	String resultDirectory;
	private final Logger LOGGER = Logger.getLogger(VhdlPrintManager.class.toString());

	public VhdlPrintManager(String resultDirectory) {
		this.resultDirectory = resultDirectory;
	}

	public void printAllVhdl() {
		List<VhdlLibraryEntry> allVhdl = vhdlManager.getAllVhdl();
		allVhdl.forEach(entry -> {
			try {
				printVHDLLibraryEntryToFile(entry);
			} catch (IOException ex) {
				LOGGER.log(Level.INFO, ex.toString());
			}
		});
	}

	private void printVHDLLibraryEntryToFile(VhdlLibraryEntry entry) throws IOException {
		Vhdl vhdl = entry.getVhdl();
		Logger.getLogger(VhdlPrintManager.class.toString()).log(Level.INFO,
				"Printing VHDL named:" + vhdl.getEntity().getName());
		if (!entry.isPrintable()) {
			LOGGER.log(Level.INFO,"Can't print VHDL: " + vhdl.getEntity().getName());
		}
		else{
			printVhdlToFile(vhdl);
		}
	}

	private void printVhdlToFile(Vhdl vhdl) throws IOException {
		String fileAbsolutePath = getAbsolutePathOfVhdlFile(vhdl);
		File file = getOrCreateFileForPath(fileAbsolutePath);
		String vhdlContent = VhdlPrinter.printVhdl(vhdl);
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(vhdlContent);
		System.out.println("File written Successfully");
		bw.close();		
	}

	private File getOrCreateFileForPath(String fileAbsolutePath) throws IOException {
		File file = new File(fileAbsolutePath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}

	private String getAbsolutePathOfVhdlFile(Vhdl vhdl) {
		String fileName = vhdl.getEntity().getName() + FILE_EXT;
		String fileAbsolutePath;
		if ("".equals(resultDirectory) || resultDirectory == null) {
			fileAbsolutePath = fileName;
		} else {
			fileAbsolutePath = resultDirectory + File.pathSeparator + fileName;
		}
		return fileAbsolutePath;
	}

}
