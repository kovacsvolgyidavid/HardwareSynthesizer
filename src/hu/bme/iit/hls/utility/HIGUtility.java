package hu.bme.iit.hls.utility;

import hu.bme.iit.hls.entities.InOut;
import hu.bme.iit.hls.hig.HigModel.InputPort;
import hu.bme.iit.hls.hig.HigModel.Node;
import hu.bme.iit.hls.hig.HigModel.Port;

public class HIGUtility {
	public static final int BIT_WIDTH = 32;
	public static final String SIGNAL_PREFIX = "signal_";
	private static final String READY_APPENDANT = "_rdy";
	public static int getBitWidth() {
		// TODO Auto-generated method stub
		return BIT_WIDTH;
	}

	public static InOut getInorOut(Port port, Node node) {
		if (port instanceof InputPort) {
			return InOut.IN;
		} else {
			return InOut.OUT;
		}
	}

	public static String getPortName(Node node, Port port) {
		return node.getName() +"_"+ port.getName();
	}

	public static String getReadyPortName(Node node, Port port) {
		// TODO Auto-generated method stub
		return getPortName(node,port)+READY_APPENDANT;
	}

}
