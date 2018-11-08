package hu.bme.iit.hls.library;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.entities.VhdlEntity;
import hu.bme.iit.hls.entities.VhdlPort;
import hu.bme.iit.hls.entities.VhdlPortType;

//USE TEMPLATE, GO EOF
public class BasicEntityReader {

	public static List<Vhdl> getElementaryVhdls() {
		Vhdl start1 = new BasicVhdlBuilder().inPortNum(1).outPortNum(1).name("Start1").build();
		Vhdl start2 = new BasicVhdlBuilder().inPortNum(2).outPortNum(1).name("Start2").build();
		Vhdl add = new BasicVhdlBuilder().inPortNum(2).outPortNum(1).name("Add").components(start2).build();
		Vhdl phi = new BasicVhdlBuilder().inPortNum(2).outPortNum(1).name("Phi").components(start1).build();
		Vhdl gt = new BasicVhdlBuilder().inPortNum(2).outPortNum(1).name("LT").components(start2).build();
		Vhdl lt = new BasicVhdlBuilder().inPortNum(2).outPortNum(1).name("GT").components(start2).build();
		Vhdl init = new BasicVhdlBuilder().inPortNum(1).outPortNum(1).name("Init").build();
		Vhdl assign = new BasicVhdlBuilder().inPortNum(1).outPortNum(1).name("Assign").build();
		List<Vhdl> vhdls = Lists.newArrayList(add, phi, start1, start2,gt,lt,init,assign);

		return vhdls;
	}
	
	public static class BasicVhdlBuilder {
		private int inPortNum;
		private int outPortNum;
		private String name;
		private Vhdl [] components = {};

		public BasicVhdlBuilder inPortNum(int portnum) {
			inPortNum = portnum;
			return this;
		}

		public BasicVhdlBuilder outPortNum(int portnum) {
			outPortNum = portnum;
			return this;
		}

		public BasicVhdlBuilder name(String name) {
			this.name = name;
			return this;
		}

		public BasicVhdlBuilder components(Vhdl... vhdls) {
			components = vhdls;
			return this;
		}

		public Vhdl build() {
			Vhdl vhdl = new Vhdl();
			vhdl.setVhdlFile(new File("ElementaryVHDLs/" + name.toLowerCase() + ".vhd"));
			Set<Vhdl> vhdlSet = new HashSet<>(Arrays.asList(components));
			vhdl.setComponentsList(vhdlSet);
			vhdl.setEntity(createBasicEntity(name, inPortNum, outPortNum));
			return vhdl;
		}

		private VhdlEntity createBasicEntity(String name, int inPortNum, int outPortNum) {
			VhdlEntity entity = new VhdlEntity();
			entity.setName(name);
			entity.setPorts(createBasicPorts(inPortNum, outPortNum));
			return entity;
		}

		private List<VhdlPort> createBasicPorts(int inPortNum, int outPortNum) {
			List<VhdlPort> ports = new ArrayList<>();
			List<VhdlPort> staticPorts = createStaticPorts();
			List<VhdlPort> inOutPorts = createInOutPorts(inPortNum, outPortNum);
			ports.addAll(staticPorts);
			ports.addAll(inOutPorts);
			return ports;
		}

		private List<VhdlPort> createInOutPorts(int inPortNum, int outPortNum) {
			Stream<VhdlPort> inPorts = IntStream.range(0, inPortNum).mapToObj(k -> k+1).flatMap(k -> createRdyAndPort(k, InOut.IN));
			Stream<VhdlPort> outPorts = IntStream.range(0, outPortNum).mapToObj(k -> k+1).flatMap(k -> createRdyAndPort(k, InOut.OUT));
			return Stream.concat(inPorts, outPorts).collect(Collectors.toList());
		}

		public Stream<VhdlPort> createRdyAndPort(Integer k, InOut inOut) {
			String name = "";
			switch (inOut) {
			case IN:
				name = "input" + k;
				break;
			case OUT:
				name = "output" + k;
				break;
			case INOUT:
				name = "inoutput" + k;
				break;
			default:
				name = "NULLLLLLLL";
			}
			VhdlPort input_rdy = new VhdlPort();
			input_rdy.setInOut(inOut);
			input_rdy.setName(name + "_rdy");
			input_rdy.setBitWidth(1);
			input_rdy.setVhdlPortType(VhdlPortType.BOOLEAN);
			VhdlPort input = new VhdlPort();
			input.setInOut(inOut);
			input.setName(name);
			input.setBitWidth(32);
			input.setVhdlPortType(VhdlPortType.STD_LOGIC);
			return Stream.of(input,input_rdy);
		}

		private List<VhdlPort> createStaticPorts() {
			return Stream.of("rst", "loop_rst", "clk").map(this::createStaticPort).collect(Collectors.toList());
		}

		private VhdlPort createStaticPort(String name) {
			VhdlPort port = new VhdlPort();
			port.setInOut(InOut.IN);
			port.setName(name);
			port.setBitWidth(1);
			port.setVhdlPortType(VhdlPortType.STD_LOGIC);
			return port;
		}

	}

}