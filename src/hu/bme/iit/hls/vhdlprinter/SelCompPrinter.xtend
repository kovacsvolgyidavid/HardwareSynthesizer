package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.InOut
import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.higmodel.SelComp
import hu.bme.iit.hls.vhdlbuilder.VhdlManager
import java.util.stream.Collectors
import java.util.Map
import hu.bme.iit.hls.higmodel.Port
import hu.bme.iit.hls.entities.Signal
import hu.bme.iit.hls.higmodel.SelCase

class SelCompPrinter {
    static ComponenetPreprocessor preprocessor = new ComponenetPreprocessor();
    static SelectionPreprocessor selectionPreprocessor= new SelectionPreprocessor();
    static VhdlManager manager = new VhdlManager();
    
    def static String printVhdl(SelComp sel){        
        var entity = preprocessor.getEntity(sel);
        '''
            «PrintUtils.includes»
            «PrintUtils.printEntity(entity)»
            «printArchitecture(sel,entity)»
        '''
    }
    
    def static printArchitecture(SelComp sel, VhdlEntity entity) {
        var selectorMap = selectionPreprocessor.createSelectorMap(sel)
        var outputMap = selectionPreprocessor.createOutputMap(sel)
        '''
        architecture Behavioral of «entity.name» is
        «PrintUtils.printComponents(preprocessor.getComponents(sel))» 
        «var i = 0»
        «FOR selector:sel.cases.stream.flatMap(k|k.selectors.stream).filter(k|!"def".equals(k.value.toLowerCase)).collect(Collectors.toList)»
            «var constant= selectorMap.get(selector)»
             constant «constant.name» : signed («constant.bitWidth-1» downto 0 ) := to_signed(«constant.constant»,32);
        «ENDFOR»
        «FOR signal : outputMap.values»
            SIGNAL «signal.name» : STD_LOGIC_VECTOR («signal.bitWidth-1» downto 0);
            SIGNAL «signal.name»_rdy : BOOLEAN; 
        «ENDFOR»
        begin
        «FOR cas:sel.cases»
        case«i++»: «manager.getVhdl(cas.comp).name» PORT MAP(rst, loop_rst, clk, «entity.printInputs», «printOutputs(outputMap,cas)»);
        «ENDFOR»
        «var selectionPort = sel.inPorts.get(0)»
        proc : process (clk)
        begin
        if «selectionPort.name»_rdy  then
        «FOR cas: sel.cases»
        if «FOR selector:cas.selectors»signed(«selectionPort.name») = «selectorMap.get(selector)?.name»«ENDFOR» then
        output6<=signal_out1;
        output6_rdy<=signal_out1_rdy;
        «ENDFOR»
        else 
        output6<=signal_out2;
        output6_rdy<=signal_out2_rdy;
        end if;
        else
        output6_rdy<=false;
        end if;
        end process;
        end Behavioral;
        '''
    }
    
    def static printOutputs(Map<Port, Signal> map, SelCase cas) {
        '''«FOR outPort: cas.comp.outPorts SEPARATOR ", "»«map.get(outPort).name», «map.get(outPort).name»_rdy«ENDFOR»'''
        
    }
    
    def static printInputs(VhdlEntity entity){
       '''«FOR inPort: entity.ports.stream.filter(k|InOut.IN.equals(k.inOut)).collect(Collectors.toList) SEPARATOR ", "»«inPort.name»«ENDFOR»''' 
        
    }
    
}