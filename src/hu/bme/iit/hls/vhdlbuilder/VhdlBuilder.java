package hu.bme.iit.hls.vhdlbuilder;

import java.io.IOException;
import java.io.OutputStream;

import hu.bme.iit.hls.higmodel.HIG;
import hu.bme.iit.hls.higmodel.LoopComp;
import hu.bme.iit.hls.vhdlprinter.HIGPrinter;
import hu.bme.iit.hls.vhdlprinter.LoopVhdlPrinter;

public class VhdlBuilder {
	
	public void buildVhdl(HIG hig, OutputStream is){
		String vhdlText = HIGPrinter.printVhdl(hig);
		try {
			is.write(vhdlText.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void buildVhdl(LoopComp loop, OutputStream os){
		String vhdlText = LoopVhdlPrinter.printVhdl(loop);
		System.out.println(vhdlText);
		try {
			os.write(vhdlText.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
