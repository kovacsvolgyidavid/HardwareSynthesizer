package hu.bme.iit.hls.entities

import hu.bme.iit.hls.entities.VhdlPort

class VhdlClockPort extends VhdlPort {
    
    private final String CLK_NAME="clk";
    
    new(){
        super.bitWidth=1;
        super.inOut=InOut.IN;
        super.name = CLK_NAME;
    }
}