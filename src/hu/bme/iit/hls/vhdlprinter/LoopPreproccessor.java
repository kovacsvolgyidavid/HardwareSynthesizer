package hu.bme.iit.hls.vhdlprinter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import hu.bme.iit.hls.entities.Signal;
import hu.bme.iit.hls.higmodel.Component;
import hu.bme.iit.hls.higmodel.LoopComp;
import hu.bme.iit.hls.higmodel.Port;

public class LoopPreproccessor {
	
	SourceBuilder builder = new SourceBuilder();
	
	public Map<Port,Signal> createPortMap(LoopComp loop) {
		Map<Port,Signal> map=new HashMap<>();
		Component comp = loop.getComp();
		List<Port> loopInPorts = loop.getInPorts();
		List<Port> compInPorts = comp.getInPorts();
		for(int i = 0; i<loopInPorts.size();i++){
			Port compPort = compInPorts.get(i);
			Port loopPort = loopInPorts.get(i);
			Signal signal = builder.createSignal(loopPort.getName());
			map.put(loopPort, signal);
			if(loopPort.getName().startsWith("x")){				
				map.put(compPort, signal);				
			}else{
				Signal compSignal = builder.createSignal(compPort.getName());
				map.put(compPort, compSignal);
			}
			
		}
		List<Port> loopOutPorts = loop.getOutPorts();
		List<Port> compOutPorts = comp.getOutPorts();
		int j= 1;
		for(int i=1;i<compOutPorts.size();i++){
			Port compPort = compOutPorts.get(i);
			Signal signal = builder.createSignal(compPort.getName());
			map.put(compPort,signal);
			if(compPort.getName().startsWith("y")){				
				j++;
			}else{
				map.put(loopOutPorts.get(i-j), signal);
			}
		}
		return map;
	}

}
