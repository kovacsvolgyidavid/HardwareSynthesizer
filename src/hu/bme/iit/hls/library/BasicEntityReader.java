package hu.bme.iit.hls.library;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.hig.HigModel.Const;

public class BasicEntityReader {
	public List<Vhdl> getBasicOperitons(File source) {
		List<Vhdl> basics = new ArrayList<>();
		Vhdl vhdl1 = new Vhdl();
		Vhdl vhdl2 = new Vhdl();
		basics.add(vhdl1);
		basics.add(vhdl2);
		String includes = "library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nuse ieee.std_logic_unsigned.all;";
		vhdl1.setIncludes(includes);
		vhdl2.setIncludes(includes);
		VhdlEntity entity1 = new VhdlEntity();
		VhdlEntity entity2 = new VhdlEntity();
		entity1.setName("add");
		entity2.setName("mul");
		List<VhdlPort> ports1 = new ArrayList<>();
		VhdlPort port1 = new VhdlPort();
		VhdlPort port2 = new VhdlPort();
		VhdlPort port3 = new VhdlPort();
		port1.setBitWidth(32);
		port1.setInOut(InOut.IN);
		port1.setName("Input1");
		port2.setBitWidth(32);
		port2.setInOut(InOut.IN);
		port2.setName("Input2");
		port3.setBitWidth(32);
		port3.setInOut(InOut.OUT);
		port3.setName("Output1");
		ports1.add(port1);
		ports1.add(port2);
		ports1.add(port3);
		entity1.setPorts(ports1);
		entity2.setPorts(ports1);
		vhdl1.setEntity(entity1);
		vhdl2.setEntity(entity2);
		Architecture arch1 = new Architecture();
		Architecture arch2 = new Architecture();
		arch2.setBody("begin\nout1<=in1*in2;");
		arch1.setBody("begin\nout1<=in1+in2;");
		vhdl1.setArchitecture(arch1);
		vhdl2.setArchitecture(arch2);
		arch1.setComponents(new ArrayList<VhdlEntity>());
		arch1.setSignals(new HashSet<Signal>());
		arch1.setConstants(new ArrayList<Const>());
		arch2.setComponents(new ArrayList<VhdlEntity>());
		arch2.setSignals(new HashSet<Signal>());
		arch2.setConstants(new ArrayList<Const>());
		return basics;
	}
}
