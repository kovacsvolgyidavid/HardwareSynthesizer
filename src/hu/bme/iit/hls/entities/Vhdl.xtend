package hu.bme.iit.hls.entities

import java.io.File
import java.io.Serializable
import java.util.Set
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Vhdl implements Serializable {
    VhdlEntity entity;
    File vhdlFile;
    Set<Vhdl> componentsList;

new (){
    
}


    new(VhdlEntity entity) {
        this.entity = entity
    }

    def boolean isEquals(Object o) {
        if(o instanceof Vhdl) {
            var vhdl = o as Vhdl;
            return this.entity.name.equals(vhdl.entity.name);
        } else {
            return false;
        }
    }
    
    def String getName(){        
        return this.entity.name;
    }
}
