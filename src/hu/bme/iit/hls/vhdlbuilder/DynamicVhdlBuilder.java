package hu.bme.iit.hls.vhdlbuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hu.bme.iit.hls.entities.Vhdl;
import hu.bme.iit.hls.library.BasicEntityReader;
import hu.bme.iit.hls.vhdlprinter.StartVhdlPrinter;

public class DynamicVhdlBuilder {

	public Optional<Vhdl> createDynamicVhdl(String name) {
		 Pattern p = Pattern.compile("^start[1-9][0-9]*$");
		 Matcher m = p.matcher(name);
		 
		if(m.matches()){			
			return createStartVhdl(name);
		}
		return Optional.empty();
	}

	private Optional<Vhdl> createStartVhdl(String name) {
		int inputs = Integer.valueOf(name.replace("start", ""));
		BasicEntityReader.BasicVhdlBuilder builder = new BasicEntityReader.BasicVhdlBuilder();
		Vhdl vhdl= builder.inPortNum(inputs).outPortNum(1).name(name).build();
		Path path = Paths.get("ElementaryVHDLs",name+".vhd");
		try (OutputStream stream= Files.newOutputStream(path);){
			stream.write(StartVhdlPrinter.printStart(inputs).getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vhdl.setVhdlFile(path.toFile());
		return Optional.of(vhdl);
	}

}
