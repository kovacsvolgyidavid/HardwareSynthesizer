package hu.bme.iit.hls.entities


import java.io.Serializable
import java.util.List
import java.util.Map
import java.util.Set
import java.util.stream.Collectors
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Architecture implements Serializable{
    List<VhdlEntityInstance> components;
    //List<Const> constants;    
    String body;
    Map<VhdlPort, Signal> signalMap;
    
    def Set<Signal> getSignals(){
        return signalMap.values.stream.collect(Collectors.toSet());
    }
    
    def Set<VhdlEntity> getComponentSet(){
        return components.stream().map([k | k.entity ]).collect(Collectors.toSet());
    }
}