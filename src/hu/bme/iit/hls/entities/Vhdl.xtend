package hu.bme.iit.hls.entities

import java.io.File
import java.io.Serializable
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Vhdl implements Serializable{
    VhdlEntity entity;
    File vhdlFile;
    List<Vhdl> componentsList;
    
    def boolean isEquals(Object o){
        if (o instanceof Vhdl){
        var vhdl = o as Vhdl;
        return this.entity.name.equals(vhdl.entity.name);
        }
        else {
            return false;
        }
    }
    }
