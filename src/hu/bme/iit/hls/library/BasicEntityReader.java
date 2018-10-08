package hu.bme.iit.hls.library;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import hu.bme.iit.hls.entities.Architecture;
import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.entities.VhdlPortType;
import hu.bme.iit.hls.hig.HigModel.Const;

//USE TEMPLATE, GO EOF
public class BasicEntityReader {
	public List<Vhdl> getBasicOperitons(File source) {
		List<Vhdl> basics = new ArrayList<>();
		Vhdl addVhdl = new Vhdl();
		Vhdl phiVhdl = new Vhdl();
		Vhdl mulVhdl = new Vhdl();
		Vhdl start1Vhdl = new Vhdl();
		Vhdl start2Vhdl = new Vhdl();

		basics.add(addVhdl);
		basics.add(phiVhdl);
		basics.add(mulVhdl);
		basics.add(start1Vhdl);
		basics.add(start2Vhdl);

		File addVhdlFile = new File("ElementaryVHDLs/add.vhd");
		File phiVhdlFile = new File("ElementaryVHDLs/phi.vhd");
		File mulVhdlFile = new File("ElementaryVHDLs/mul.vhd");
		File start1VhdlFile = new File("ElementaryVHDLs/start1.vhd");
		File start2VhdlFile = new File("ElementaryVHDLs/start2.vhd");

		addVhdl.setVhdlFile(addVhdlFile);
		phiVhdl.setVhdlFile(phiVhdlFile);
		mulVhdl.setVhdlFile(mulVhdlFile);
		start1Vhdl.setVhdlFile(start1VhdlFile);
		start2Vhdl.setVhdlFile(start2VhdlFile);

		VhdlEntity addVhdlEntity = new VhdlEntity();
		VhdlEntity phiVhdlEntity = new VhdlEntity();
		VhdlEntity mulVhdlEntity = new VhdlEntity();
		VhdlEntity start1VhdlEntity = new VhdlEntity();
		VhdlEntity start2VhdlEntity = new VhdlEntity();

		addVhdl.setEntity(addVhdlEntity);
		phiVhdl.setEntity(phiVhdlEntity);
		mulVhdl.setEntity(mulVhdlEntity);
		start1Vhdl.setEntity(start1VhdlEntity);
		start2Vhdl.setEntity(start2VhdlEntity);

		addVhdlEntity.setName("add");
		phiVhdlEntity.setName("phi");
		mulVhdlEntity.setName("mul");
		start1VhdlEntity.setName("start1");
		start2VhdlEntity.setName("start2");

		List<VhdlPort> addVhdlPorts = new ArrayList<>();
		List<VhdlPort> phiVhdlPorts = new ArrayList<>();
		List<VhdlPort> mulVhdlPorts = new ArrayList<>();
		List<VhdlPort> start1VhdlPorts = new ArrayList<>();
		List<VhdlPort> start2VhdlPorts = new ArrayList<>();

		addVhdlEntity.setPorts(addVhdlPorts);
		phiVhdlEntity.setPorts(phiVhdlPorts);
		mulVhdlEntity.setPorts(mulVhdlPorts);
		start1VhdlEntity.setPorts(start1VhdlPorts);
		start2VhdlEntity.setPorts(start2VhdlPorts);

		VhdlPort addrst = new VhdlPort();
		VhdlPort addloop_rst = new VhdlPort();
		VhdlPort addclk = new VhdlPort();
		VhdlPort addinput1 = new VhdlPort();
		VhdlPort addinput1_rdy = new VhdlPort();
		VhdlPort addinput2 = new VhdlPort();
		VhdlPort addinput2_rdy = new VhdlPort();
		VhdlPort addoutput = new VhdlPort();
		VhdlPort addoutput_rdy = new VhdlPort();

		addVhdlPorts.add(addrst);
		addVhdlPorts.add(addloop_rst);
		addVhdlPorts.add(addclk);
		addVhdlPorts.add(addinput1);
		addVhdlPorts.add(addinput1_rdy);
		addVhdlPorts.add(addinput2);
		addVhdlPorts.add(addinput2_rdy);
		addVhdlPorts.add(addoutput);
		addVhdlPorts.add(addoutput_rdy);

		addrst.setBitWidth(1);
		addloop_rst.setBitWidth(1);
		addclk.setBitWidth(1);
		addinput1.setBitWidth(32);
		addinput1_rdy.setBitWidth(1);
		addinput2.setBitWidth(32);
		addinput2_rdy.setBitWidth(1);
		addoutput.setBitWidth(32);
		addoutput_rdy.setBitWidth(1);

		addrst.setInOut(InOut.IN);
		addloop_rst.setInOut(InOut.IN);
		addclk.setInOut(InOut.IN);
		addinput1.setInOut(InOut.IN);
		addinput1_rdy.setInOut(InOut.IN);
		addinput2.setInOut(InOut.IN);
		addinput2_rdy.setInOut(InOut.IN);
		addoutput.setInOut(InOut.OUT);
		addoutput_rdy.setInOut(InOut.OUT);

		addrst.setName("rst");
		addloop_rst.setName("loop_rst");
		addclk.setName("clk");
		addinput1.setName("input1");
		addinput1_rdy.setName("input1_rdy");
		addinput2.setName("input2");
		addinput2_rdy.setName("input2_rdy");
		addoutput.setName("output");
		addoutput_rdy.setName("output_rdy");

		addrst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addloop_rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addclk.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addinput1.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addinput1_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		addinput2.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addinput2_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		addoutput.setVhdlPortType(VhdlPortType.STD_LOGIC);
		addoutput_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);

		VhdlPort mulrst = new VhdlPort();
		VhdlPort mulloop_rst = new VhdlPort();
		VhdlPort mulclk = new VhdlPort();
		VhdlPort mulinput1 = new VhdlPort();
		VhdlPort mulinput1_rdy = new VhdlPort();
		VhdlPort mulinput2 = new VhdlPort();
		VhdlPort mulinput2_rdy = new VhdlPort();
		VhdlPort muloutput = new VhdlPort();
		VhdlPort muloutput_rdy = new VhdlPort();
		mulVhdlPorts.add(mulrst);
		mulVhdlPorts.add(mulloop_rst);
		mulVhdlPorts.add(mulclk);
		mulVhdlPorts.add(mulinput1);
		mulVhdlPorts.add(mulinput1_rdy);
		mulVhdlPorts.add(mulinput2);
		mulVhdlPorts.add(mulinput2_rdy);
		mulVhdlPorts.add(muloutput);
		mulVhdlPorts.add(muloutput_rdy);
		mulrst.setBitWidth(1);
		mulloop_rst.setBitWidth(1);
		mulclk.setBitWidth(1);
		mulinput1.setBitWidth(32);
		mulinput1_rdy.setBitWidth(1);
		mulinput2.setBitWidth(32);
		mulinput2_rdy.setBitWidth(1);
		muloutput.setBitWidth(32);
		muloutput_rdy.setBitWidth(1);
		mulrst.setInOut(InOut.IN);
		mulloop_rst.setInOut(InOut.IN);
		mulclk.setInOut(InOut.IN);
		mulinput1.setInOut(InOut.IN);
		mulinput1_rdy.setInOut(InOut.IN);
		mulinput2.setInOut(InOut.IN);
		mulinput2_rdy.setInOut(InOut.IN);
		muloutput.setInOut(InOut.OUT);
		muloutput_rdy.setInOut(InOut.OUT);
		mulrst.setName("rst");
		mulloop_rst.setName("loop_rst");
		mulclk.setName("clk");
		mulinput1.setName("input1");
		mulinput1_rdy.setName("input1_rdy");
		mulinput2.setName("input2");
		mulinput2_rdy.setName("input2_rdy");
		muloutput.setName("output");
		muloutput_rdy.setName("output_rdy");
		mulrst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		mulloop_rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		mulclk.setVhdlPortType(VhdlPortType.STD_LOGIC);
		mulinput1.setVhdlPortType(VhdlPortType.STD_LOGIC);
		mulinput1_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		mulinput2.setVhdlPortType(VhdlPortType.STD_LOGIC);
		mulinput2_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		muloutput.setVhdlPortType(VhdlPortType.STD_LOGIC);
		muloutput_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);

		VhdlPort phirst = new VhdlPort();
		VhdlPort philoop_rst = new VhdlPort();
		VhdlPort phiclk = new VhdlPort();
		VhdlPort phiinput1 = new VhdlPort();
		VhdlPort phiinput1_rdy = new VhdlPort();
		VhdlPort phiinput2 = new VhdlPort();
		VhdlPort phiinput2_rdy = new VhdlPort();
		VhdlPort phioutput = new VhdlPort();
		VhdlPort phioutput_rdy = new VhdlPort();
		phiVhdlPorts.add(phirst);
		phiVhdlPorts.add(philoop_rst);
		phiVhdlPorts.add(phiclk);
		phiVhdlPorts.add(phiinput1);
		phiVhdlPorts.add(phiinput1_rdy);
		phiVhdlPorts.add(phiinput2);
		phiVhdlPorts.add(phiinput2_rdy);
		phiVhdlPorts.add(phioutput);
		phiVhdlPorts.add(phioutput_rdy);
		phirst.setBitWidth(1);
		philoop_rst.setBitWidth(1);
		phiclk.setBitWidth(1);
		phiinput1.setBitWidth(32);
		phiinput1_rdy.setBitWidth(1);
		phiinput2.setBitWidth(32);
		phiinput2_rdy.setBitWidth(1);
		phioutput.setBitWidth(32);
		phioutput_rdy.setBitWidth(1);
		phirst.setInOut(InOut.IN);
		philoop_rst.setInOut(InOut.IN);
		phiclk.setInOut(InOut.IN);
		phiinput1.setInOut(InOut.IN);
		phiinput1_rdy.setInOut(InOut.IN);
		phiinput2.setInOut(InOut.IN);
		phiinput2_rdy.setInOut(InOut.IN);
		phioutput.setInOut(InOut.OUT);
		phioutput_rdy.setInOut(InOut.OUT);
		phirst.setName("rst");
		philoop_rst.setName("loop_rst");
		phiclk.setName("clk");
		phiinput1.setName("start");
		phiinput1_rdy.setName("start_rdy");
		phiinput2.setName("input");
		phiinput2_rdy.setName("input_rdy");
		phioutput.setName("output");
		phioutput_rdy.setName("output_rdy");
		phirst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		philoop_rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		phiclk.setVhdlPortType(VhdlPortType.STD_LOGIC);
		phiinput1.setVhdlPortType(VhdlPortType.STD_LOGIC);
		phiinput1_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		phiinput2.setVhdlPortType(VhdlPortType.STD_LOGIC);
		phiinput2_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
		phioutput.setVhdlPortType(VhdlPortType.STD_LOGIC);
		phioutput_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);

		VhdlPort start2rst = new VhdlPort();
		VhdlPort start2loop_rst = new VhdlPort();
		VhdlPort start2clk = new VhdlPort();
		VhdlPort start2input1 = new VhdlPort();
		VhdlPort start2input2 = new VhdlPort();
		VhdlPort start2output = new VhdlPort();
		start2VhdlPorts.add(start2rst);
		start2VhdlPorts.add(start2loop_rst);
		start2VhdlPorts.add(start2clk);
		start2VhdlPorts.add(start2input1);
		start2VhdlPorts.add(start2input2);
		start2VhdlPorts.add(start2output);
		start2rst.setBitWidth(1);
		start2loop_rst.setBitWidth(1);
		start2clk.setBitWidth(1);
		start2input1.setBitWidth(1);
		start2input2.setBitWidth(1);
		start2output.setBitWidth(1);
		start2rst.setInOut(InOut.IN);
		start2loop_rst.setInOut(InOut.IN);
		start2clk.setInOut(InOut.IN);
		start2input1.setInOut(InOut.IN);
		start2input2.setInOut(InOut.IN);
		start2output.setInOut(InOut.OUT);
		start2rst.setName("rst");
		start2loop_rst.setName("loop_rst");
		start2clk.setName("clk");
		start2input1.setName("input1");
		start2input2.setName("input2");
		start2output.setName("output");
		start2rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start2loop_rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start2clk.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start2input1.setVhdlPortType(VhdlPortType.BOOLEAN);
		start2input2.setVhdlPortType(VhdlPortType.BOOLEAN);
		start2output.setVhdlPortType(VhdlPortType.BOOLEAN);

		VhdlPort start1rst = new VhdlPort();
		VhdlPort start1loop_rst = new VhdlPort();
		VhdlPort start1clk = new VhdlPort();
		VhdlPort start1input1 = new VhdlPort();
		VhdlPort start1output = new VhdlPort();
		start1VhdlPorts.add(start1rst);
		start1VhdlPorts.add(start1loop_rst);
		start1VhdlPorts.add(start1clk);
		start1VhdlPorts.add(start1input1);
		start1VhdlPorts.add(start1output);
		start1rst.setBitWidth(1);
		start1loop_rst.setBitWidth(1);
		start1clk.setBitWidth(1);
		start1input1.setBitWidth(1);
		start1output.setBitWidth(1);
		start1rst.setInOut(InOut.IN);
		start1loop_rst.setInOut(InOut.IN);
		start1clk.setInOut(InOut.IN);
		start1input1.setInOut(InOut.IN);
		start1output.setInOut(InOut.OUT);
		start1rst.setName("rst");
		start1loop_rst.setName("loop_rst");
		start1clk.setName("clk");
		start1input1.setName("input1");
		start1output.setName("output");
		start1rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start1loop_rst.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start1clk.setVhdlPortType(VhdlPortType.STD_LOGIC);
		start1input1.setVhdlPortType(VhdlPortType.BOOLEAN);
		start1output.setVhdlPortType(VhdlPortType.BOOLEAN);

		return basics;
	}
}
/*
 * TEMPLATE FOR BASIC OPERATIONS Vhdl vhdl_VHDLNAME = new Vhdl();
 * basics.add(vhdl1); String includes_VHDLNAME =
 * "library IEEE;\nuse IEEE.STD_LOGIC_1164.ALL;\nuse ieee.std_logic_unsigned.all;"
 * ; vhdl_VHDLNAME.setIncludes(includes_VHDLNAME); VhdlEntity entity_VHDLNAME =
 * new VhdlEntity(); entity_VHDLNAME.setName("add"); List<VhdlPort>
 * ports_VHDLNAME = new ArrayList<>(); VhdlPort port_VHDLNAME1 = new VhdlPort();
 * VhdlPort port_VHDLNAME2 = new VhdlPort(); VhdlPort port_VHDLNAME3 = new
 * VhdlPort(); port_VHDLNAME1.setBitWidth(32);
 * port_VHDLNAME1.setInOut(InOut.IN); port_VHDLNAME1.setName("Input1");
 * port_VHDLNAME2.setBitWidth(32); port_VHDLNAME2.setInOut(InOut.IN);
 * port_VHDLNAME2.setName("Input2"); port_VHDLNAME3.setBitWidth(32);
 * port_VHDLNAME3.setInOut(InOut.OUT); port_VHDLNAME3.setName("Output1");
 * ports_VHDLNAME.add(port_VHDLNAME1); ports_VHDLNAME.add(port_VHDLNAME2);
 * ports_VHDLNAME.add(port_VHDLNAME3); entity_VHDLNAME.setPorts(ports_VHDLNAME);
 * vhdl_VHDLNAME.setEntity(entity_VHDLNAME); Architecture arch_VHDLNAME = new
 * Architecture(); arch_VHDLNAME.setBody("");
 * vhdl_VHDLNAME.setArchitecture(arch_VHDLNAME); arch_VHDLNAME.setComponents(new
 * ArrayList<VhdlEntity>()); arch_VHDLNAME.setSignals(new HashSet<Signal>());
 * arch_VHDLNAME.setConstants(new ArrayList<Const>());
 */
