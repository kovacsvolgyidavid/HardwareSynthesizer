package hu.bme.iit.hls.entities

import java.io.Serializable
import org.eclipse.xtend.lib.annotations.Accessors

enum InOut{
    IN,OUT,INOUT
}
@Accessors
class VhdlPort implements Serializable{
    String name;
    int bitWidth;
    InOut inOut;
    Signal connectedSignal;
}