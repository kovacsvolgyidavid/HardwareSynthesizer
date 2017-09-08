package hu.bme.iit.hls.library

import hu.bme.iit.hls.entities.Vhdl
import org.eclipse.xtend.lib.annotations.Accessors

@Accessors
class VhdlLibraryEntry {
    Vhdl vhdl;
    boolean isPrintable;
    new(Vhdl vhdl,boolean isPrintable){
        this.vhdl=vhdl;
        this.isPrintable=isPrintable;
    }
}