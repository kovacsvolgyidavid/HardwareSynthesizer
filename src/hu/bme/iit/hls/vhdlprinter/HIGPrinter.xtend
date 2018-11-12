package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.Signal
import hu.bme.iit.hls.entities.VhdlConstant
import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.entities.VhdlPort
import hu.bme.iit.hls.higmodel.HIG
import hu.bme.iit.hls.higmodel.InputPort
import hu.bme.iit.hls.higmodel.Port
import hu.bme.iit.hls.vhdlbuilder.VhdlManager
import java.util.stream.Collectors

class HIGPrinter {

    static ComponenetPreprocessor preprocessor = new ComponenetPreprocessor();

    def static String printVhdl(HIG hig) {
        var entity = preprocessor.getEntity(hig);
        '''
            «PrintUtils.includes»
            «PrintUtils.printEntity(entity)»
            «printArchitecture(hig,entity.name)»
        '''
    }

    

    def static printArchitecture(HIG hig, String name) {
        '''
            architecture Behavioral of «name» is
            «PrintUtils.printComponents(preprocessor.getComponents(hig))»            
             «var sourceMap = preprocessor.getSourceMap(hig)»
             «FOR con : sourceMap.values.stream.filter(k| k instanceof VhdlConstant).map(k|VhdlConstant.cast(k)).collect(Collectors.toSet())»                
                constant «con.name» : STD_LOGIC_VECTOR («con.bitWidth-1» downto 0):="«toBit(con.constant.intValue,con.bitWidth)»";
                constant «con.name»_rdy : BOOLEAN:=true;
             «ENDFOR»             
             «FOR signal : sourceMap.values.stream.filter(k| k instanceof Signal).collect(Collectors.toSet())»
                signal «signal.name» : STD_LOGIC_VECTOR («signal.bitWidth-1» downto 0);
                signal «signal.name»_rdy : BOOLEAN;
             «ENDFOR»
             begin
             «FOR node : hig.nodes»
                «var ports = preprocessor.getPorts(node)»
                «node.name»: «preprocessor.getEntity(node.component).name» port map(«PrintUtils.vhdlInstanceAppendants»«IF !ports.empty», «ENDIF»«FOR port : ports  SEPARATOR ", "»«sourceMap.get(port)?.name», «sourceMap.get(port)?.name»_rdy«ENDFOR» );
             «ENDFOR»
            «FOR inputPort : hig.inPorts»
            «var source = sourceMap.get(inputPort)»
            «IF source !=null»
                «source.name»<=«inputPort?.name»;
                «sourceMap.get(inputPort).name»_rdy<=«inputPort?.name»_rdy;
            «ENDIF»
            «ENDFOR»
            «FOR outputPort : hig.outPorts»
               «outputPort.name»<=«sourceMap.get(outputPort)?.name»;
               «outputPort.name»_rdy<=«sourceMap.get(outputPort)?.name»_rdy;
            «ENDFOR»
            end Behavioral;
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
