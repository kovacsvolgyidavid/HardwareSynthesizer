package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.entities.VhdlPort
import hu.bme.iit.hls.hig.HigModel.ComplexNode
import hu.bme.iit.hls.hig.HigModel.Const
import hu.bme.iit.hls.hig.HigModel.InputPort
import hu.bme.iit.hls.hig.HigModel.Node
import hu.bme.iit.hls.hig.HigModel.OutputPort
import hu.bme.iit.hls.hig.HigModel.Port
import hu.bme.iit.hls.utility.HIGUtility
import hu.bme.iit.hls.vhdlbuilder.ComplexBuild
import hu.bme.iit.hls.vhdlbuilder.VhdlManager

class ComplexNodePrinter {
    def static printVhdl(ComplexNode node) {
        return getVhdlContent(node, NodePreprocessor.presprocessNode(node));
    }

    def static String getVhdlContent(ComplexNode node, PreprocessedNode preNode) {
        '''
            «includes»
            «preNode.entity.printEntity»
            «printArchitecture(node,preNode)»
        '''
    }

    def static String getIncludes() {
        '''
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;'''
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

    def static printArchitecture(ComplexNode node, PreprocessedNode preNode) {
        '''
            architecture Behavioral of «preNode.entity.name» is
            «FOR comp : preNode.components»
                component «comp.name» is
                Port(
                    «comp.printPorts»
                       );
                end component;
             «ENDFOR»
             «FOR con : preNode.constants»
                «val bitwidth=HIGUtility.bitWidth»
                constant «con.name» : STD_LOGIC_VECTOR («bitwidth-1» downto 0):="«toBit(Integer.parseInt(con.value),bitwidth)»";
             «ENDFOR»
             «FOR signal : preNode.signals»
                signal «signal.name» : STD_LOGIC_VECTOR («signal.bitWidth-1» downto 0);
             «ENDFOR»
             «var entityNumber = 0»
             «FOR instance : preNode.instances»
«««                component_«entityNumber++»: «instance.entity.name» port map(«FOR port : instance.entity.ports SEPARATOR " ,"»«instance.portMap.get(port).name»«ENDFOR» )
             «ENDFOR»
             «var i=0»
             «var manager= new VhdlManager()»
            begin
            «FOR comp : new ComplexBuild().filterList(node.innerGraph.nodes,Const,true)» «««példányosítás, portok összekötése
            «comp.name»_«i++»: «manager.getVhdl(comp).entity.name» port map(«comp.mapPort»);«ENDFOR»
            «FOR port : new ComplexBuild().filterList(node.ports,InputPort,false)»
                signal_«port.name»<=«node.name»«port.name»;
            «ENDFOR»
            «FOR port : new ComplexBuild().filterList(node.ports,OutputPort,false)»
               «node.name»«port.name»<=signal_«port.inEdge.sourcePort.name»;
            «ENDFOR»
            «FOR constant : new ComplexBuild().filterList(node.innerGraph.nodes,Const,false)»              
                «constant.ports.get(0).name»<=«constant.name»;
            «ENDFOR»  
            end Behavioral;
        '''
    }
    
    def static mapPort(Node node){
            '''«FOR port:node.ports SEPARATOR","»signal_«port.getSignalName»«ENDFOR»'''   
    }
    
     def static String getSignalName(Port port){
        if(port instanceof InputPort){
            if (port.inEdge==null){
                return port.name;
            }
            else{
             return  port.inEdge.sourcePort.name;   
            }            
        }
        else{
            return port.name;
        }
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
