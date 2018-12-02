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
        case«i++»: «manager.getVhdl(cas.comp).name» PORT MAP(«entity.printInputs», «printOutputs(outputMap,cas)»);
        «ENDFOR»
        «var selectionPort = sel.inPorts.get(0)»
        proc : process (clk)
        begin
        if «selectionPort.name»_rdy  then
        if «FOR cas: sel.cases.stream.filter(k|k.selectors.stream.allMatch(p|!"def".equals(p.value))).collect(Collectors.toList) SEPARATOR " elsif "»
        «FOR selector:cas.selectors.stream.filter(k|!"def".equals(k.value)).collect(Collectors.toList)»signed(«selectionPort.name») = «selectorMap.get(selector)?.name»«ENDFOR» then
        «FOR int j : 0..sel.outPorts.size-1»  
        «sel.outPorts.get(j).name»<=«outputMap.get(cas.comp.outPorts.get(j)).name»;
        «sel.outPorts.get(j).name»_rdy<=«outputMap.get(cas.comp.outPorts.get(j)).name»_rdy;
        «ENDFOR»
        «ENDFOR»
        else 
        «var defaultCase= sel.cases.stream.filter(k|k.selectors.stream.anyMatch(p|"def".equals(p.value))).findFirst.orElse(null)»
        «FOR int j : 0..sel.outPorts.size-1»  
                «sel.outPorts.get(j).name»<=«outputMap.get(defaultCase.comp.outPorts.get(j)).name»;
                «sel.outPorts.get(j).name»_rdy<=«outputMap.get(defaultCase.comp.outPorts.get(j)).name»_rdy;
        «ENDFOR»
        end if;
        else
        «FOR port : sel.outPorts»
        «port.name»_rdy<=false;
        «ENDFOR»
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