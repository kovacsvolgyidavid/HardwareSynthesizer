package hu.bme.iit.hls.entities

import java.io.Serializable
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class Generic implements Serializable{
    String variableName
    int defaultValue 
    }