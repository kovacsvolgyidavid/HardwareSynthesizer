package hu.bme.iit.hls.vhdlprinter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.library.VhdlLibraryEntry;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;

public class VhdlPrintManager {
	static VhdlManager vhdlManager = new VhdlManager();
	private final static String FILE_EXT = ".vhd";

	public static void printAllVhdl(String resultDirectory) {
		List<VhdlLibraryEntry> allVhdl = vhdlManager.getAllVhdl();
		for (VhdlLibraryEntry entry : allVhdl) {
			BufferedWriter bw = null;
			try {
				Vhdl vhdl = entry.getVhdl();
				Logger.getLogger(VhdlPrintManager.class.toString()).log(Level.INFO,
						"Printing VHDL named:" + vhdl.getEntity().getName());
				if (!entry.isPrintable()) {
					throw new IllegalArgumentException("Can't print VHDL: " + vhdl.getEntity().getName());
				}
				String fileName = vhdl.getEntity().getName() + FILE_EXT;
				String vhdlContent = VhdlPrinter.printVhdl(vhdl);
				String fileAbsolutePath;
				if ("".equals(resultDirectory) || resultDirectory == null) {
					fileAbsolutePath = fileName;
				} else {
					fileAbsolutePath = resultDirectory + File.pathSeparator + fileName;
				}
				File file = new File(fileAbsolutePath);
				if (!file.exists()) {
					file.createNewFile();
				}

				FileWriter fw = new FileWriter(file);
				bw = new BufferedWriter(fw);
				bw.write(vhdlContent);
				System.out.println("File written Successfully");
			} catch (Exception e) {
				Logger.getLogger("").log(Level.SEVERE, e.toString());
			} finally {
				try {
					if (bw != null) {
						bw.close();
					}
				} catch (Exception ex) {
					System.out.println("Error in closing the BufferedWriter" + ex);
				}
			}
		}
	}

}
