package hu.bme.iit.hls.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileHandler {	
	
	public static Path getTempfile(String higName) {
		Path path = null;
		try {
			path = Files.createTempFile(higName, ".vhd");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

}
