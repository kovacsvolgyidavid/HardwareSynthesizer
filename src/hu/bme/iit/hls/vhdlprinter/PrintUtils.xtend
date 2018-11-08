package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.entities.VhdlPort
import java.util.List

class PrintUtils {
    def static getVhdlInstanceAppendants() {
        '''rst, loop_rst, clk'''

    }

    def static String getIncludes() {
        '''
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
'''
    }

    def static printEntity(VhdlEntity entity) {
        '''
            entity «entity.name» is                
                «entity.printPorts»                
            end «entity.name»;
        '''
    }

    def static printPorts(VhdlEntity entity) {
        '''
            Port (
                «FOR port : entity.ports SEPARATOR ";"»
                    «port.printPort»
                «ENDFOR»);
        '''
    }

    def static printPort(VhdlPort port) {
        '''        
            «port.name» : «port.getInOut» «port.printType»
        '''
    }

    def static printType(VhdlPort port) {

        '''
            «IF port.bitWidth==1»
                «port.vhdlPortType»
            «ELSE»
                «port.vhdlPortType»_vector(«port.bitWidth-1» downto 0)
            «ENDIF»
        '''
    }

    def static printComponents(List<hu.bme.iit.hls.entities.VhdlEntity> entities) {
        '''
            «FOR comp : entities»
                component «comp.name» is
                    «comp.printPorts»
                end component;             
            «ENDFOR»
        '''
    }

}
