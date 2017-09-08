package hu.bme.iit.hls.entities

import java.io.Serializable
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Signal implements Serializable{
    String name;
    int bitWidth;
    override int hashCode(){
        return bitWidth.hashCode()+name.hashCode();
    }
    override boolean equals(Object o){
        if(o!=null&&o instanceof Signal){
            val signal=o as Signal;
        return name.equals(signal.name)&&bitWidth==signal.bitWidth
        }else{
            return false;
        }
    }
}