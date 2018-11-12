package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.higmodel.SelComp
import hu.bme.iit.hls.entities.VhdlEntity

class SelCompPrinter {
    static ComponenetPreprocessor preprocessor = new ComponenetPreprocessor();
    def static String printVhdl(SelComp sel){        
        var entity = preprocessor.getEntity(sel);
        '''
            «PrintUtils.includes»
            «PrintUtils.printEntity(entity)»
            «printArchitecture(sel,entity)»
        '''
    }
    
    def static printArchitecture(SelComp sel, VhdlEntity entity) {
        '''
        architecture Behavioral of «entity.name» is
        «PrintUtils.printComponents(preprocessor.getComponents(sel))» 
        begin
        end Behavioral;
        '''
    }
    
}