package hu.bme.iit.hls.entities

import java.io.Serializable
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Vhdl implements Serializable{
    String includes;
    VhdlEntity entity;
    Architecture architecture; 
    }
