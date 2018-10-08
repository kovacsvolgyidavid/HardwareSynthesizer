package hu.bme.iit.hls.HIGadmittance;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import hu.bme.iit.hls.higmodel.HigModelPackage;
import hu.bme.iit.hls.higmodel.HigModelRoot;
import hu.bme.iit.hls.higprocessor.HIGProcessor;
import hu.bme.iit.hls.vhdlprinter.VhdlPrintManager;

public class Main {
	
	private final static String RESULT_DIR = "output";
	
	public static void main(String[] args) throws IOException {
		
		HigModelPackage p = HigModelPackage.eINSTANCE;
		Logger.getLogger("").log(Level.INFO, "Building HIG from file");
		 PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.higmodel");Files.walk(Paths.get("input"), 1)
	                        .filter(k -> {
	                            return matcher.matches(k);
	                        }).forEach(k->printVhdl(k));			
	}

	private static void printVhdl(Path path) {
		HigModelRoot hig = (HigModelRoot) new FileHandler().loadData(path);		
		Logger.getLogger("").log(Level.INFO, "Building VHDL from HIG for " + path.getFileName());
		HIGProcessor proc = new HIGProcessor();
		hig.getHig().getNodes().forEach(k->{proc.processHig(k);});
		
		Logger.getLogger("").log(Level.INFO, "Printing VHDL for "+ path.getFileName());
		VhdlPrintManager printManager = new VhdlPrintManager(RESULT_DIR);
		printManager.printAllVhdl();
	}

}
