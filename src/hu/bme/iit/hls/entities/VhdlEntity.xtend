package hu.bme.iit.hls.entities

import java.io.Serializable
import java.util.ArrayList
import java.util.List
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.Data

@Accessors
class VhdlEntity implements Serializable{
    String name;
    List<Generic> generics=new ArrayList<Generic>();
    List<VhdlPort>ports=new ArrayList<VhdlPort>();     
    
    override toString() {
        name + generics.toString + ports.toString()
    }
    
}