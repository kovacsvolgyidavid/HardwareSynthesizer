package hu.bme.iit.hls.HIGadmittance;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.higmodel.HigModelPackage;
import hu.bme.iit.hls.higmodel.HigModelRoot;
import hu.bme.iit.hls.higprocessor.HIGProcessor;
import hu.bme.iit.hls.vhdlbuilder.VhdlManager;
import hu.bme.iit.hls.vhdlprinter.StartVhdlPrinter;

public class Main {
	
	private final static String RESULT_DIR = "output";	
	
	
	public static void main(String[] args) throws IOException {
		new VhdlManager().getVhdl("start4");
		HigModelPackage p = HigModelPackage.eINSTANCE;
		Logger.getLogger("").log(Level.INFO, "Building HIG from file");
		 PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.higmodel");
		 Files.walk(Paths.get("input"), 1)
	                        .filter(k -> {System.out.println(matcher.matches(k));
	                            return matcher.matches(k);
	                        }).forEach(k->{printVhdl(k);System.out.println(k);});			
	}

	private static void printVhdl(Path path) {
		HigModelRoot higModellRoot = (HigModelRoot) new FileHandler().loadData(path);
		Logger.getLogger("").log(Level.INFO, "Building VHDL from HIG for " + path.getFileName());
		HIGProcessor proc = new HIGProcessor();
		System.out.println(path);
		proc.processHigModellRoot(higModellRoot);
		
		
	}

}
