package hu.bme.iit.hls.admittance

import hu.bme.iit.hls.hig.HigModel.DataLink
import hu.bme.iit.hls.hig.HigModel.Port
import java.util.HashMap
import java.util.Map

class SignalMapper {
        Map<Port,String> signalMap=new HashMap<Port,String>();
        int iteration=0;        
        def mapSignal(DataLink edge){
            if(signalMap.containsKey(edge.sourcePort)){
                edge.name=signalMap.get(edge.sourcePort);
            }
            else{
                edge.name="Signal"+iteration++;
                signalMap.put(edge.sourcePort,edge.name);
            }
        }
        
}