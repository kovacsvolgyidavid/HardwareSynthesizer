package hu.bme.iit.hls.HIGadmittance;

import java.util.logging.Level;
import java.util.logging.Logger;

import hu.bme.iit.hls.hig.HigModel.ComplexNode;
import hu.bme.iit.hls.hig.HigModel.HigModelPackage;
import hu.bme.iit.hls.hig.HigModel.HigModelRoot;
import hu.bme.iit.hls.hig.HigModel.impl.HigModelFactoryImpl;
import hu.bme.iit.hls.higprocessor.HIGProcessor;
import hu.bme.iit.hls.vhdlprinter.VhdlPrintManager;

public class Main {
	public static void main(String[] args) {
		String resultDir = "";
		HigModelPackage p = HigModelPackage.eINSTANCE;
		Logger.getLogger("").log(Level.INFO, "Building HIG from file");
		HigModelRoot hig = (HigModelRoot) new FileHandler().loadData("test_1.gv.higmodel");
		ComplexNode complex=new HigModelFactoryImpl().createComplexNode();
		complex.setInnerGraph(hig.getHig());
		Logger.getLogger("").log(Level.INFO, "Building VHDL from HIG");
		HIGProcessor proc = new HIGProcessor();
		proc.processHigtoVhdl(complex);
		Logger.getLogger("").log(Level.INFO, "Building VHDLFile from VHDL");
		VhdlPrintManager printManager = new VhdlPrintManager(resultDir);
		printManager.printAllVhdl();
	}

}
