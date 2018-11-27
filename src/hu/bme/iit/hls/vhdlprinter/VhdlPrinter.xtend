package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.Architecture
import hu.bme.iit.hls.entities.Vhdl
import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.utility.HIGUtility

class VhdlPrinter {
    def static String getIncludes() {
        '''
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;'''
    }

    def static String printVhdl(Vhdl vhdl) {
        '''
            «includes»
            «vhdl.printEntity»
            «vhdl.printArchitecture»
        '''
    }

    def static printEntity(Vhdl vhdl) {
        '''
            «val entity=vhdl.entity»
            entity «entity.name» is
                Port (
                «entity.printPorts»
                );
            end «entity.name»;
        '''
    }

    def static printPorts(VhdlEntity entity) {
        '''
            «FOR port : entity.ports SEPARATOR ";"»
                «port.name» : «port.getInOut» STD_LOGIC_VECTOR («port.bitWidth-1» downto 0)
            «ENDFOR»
        '''
    }

    def static printArchitecture(Vhdl vhdl) {
        '''
            «val arch= new Architecture»
            architecture Behavioral of «vhdl.entity.name» is
            «FOR comp : arch.componentSet»
                component «comp.name» is
                Port(
                    «comp.printPorts»
                       );
                end component;
             «ENDFOR»
            «««             «FOR con : arch.components»
«««                «val bitwidth=HIGUtility.bitWidth»
«««                constant «con.name» : STD_LOGIC_VECTOR («bitwidth-1» downto 0):="«toBit(Integer.parseInt(con.value),bitwidth)»";
«««             «ENDFOR»
             «FOR signal : arch.signals»
                signal «signal.name» : STD_LOGIC_VECTOR («signal.bitWidth-1» downto 0);
             «ENDFOR»
             «var entityNumber = 0»
             «FOR instance : arch.components»
                component_«entityNumber++»: «instance.entity.name» port map(«FOR port : instance.entity.ports SEPARATOR " ,"»«instance.portMap.get(port).name»«ENDFOR» )
             «ENDFOR»                
            «arch.body»
            end Behavioral;
        '''
    }

    def static instantiatingEntity(VhdlEntity entity, int entityNumber) {
        '''
        
        '''

    }

    def static toBit(int input, int bitwidth) {
        val inputString = Integer.toBinaryString(input);
        val leadingZerosBuilder = new StringBuilder();
        var j = bitwidth - inputString.length;
        for (; j > 0; j--) {
            leadingZerosBuilder.append("0");
        }
        return leadingZerosBuilder.append(inputString);
    }

}
