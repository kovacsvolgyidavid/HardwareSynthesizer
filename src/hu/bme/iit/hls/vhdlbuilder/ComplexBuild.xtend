package hu.bme.iit.hls.vhdlbuilder

import hu.bme.iit.hls.hig.HigModel.ComplexNode
import hu.bme.iit.hls.hig.HigModel.Const
import hu.bme.iit.hls.hig.HigModel.Edge
import hu.bme.iit.hls.hig.HigModel.InputPort
import hu.bme.iit.hls.hig.HigModel.Node
import hu.bme.iit.hls.hig.HigModel.OutputPort
import hu.bme.iit.hls.hig.HigModel.Port
import java.util.HashSet
import java.util.List
import java.util.stream.Collectors

class ComplexBuild {
    def static buildComplex(ComplexNode comp,int bitwidth){ '''
    «var i=0»
        library IEEE;
        use IEEE.STD_LOGIC_1164.ALL;    
        entity «comp.name» is
            Port ( 
            «FOR port:comp.ports.filter[it instanceof InputPort]»
                «comp.name»_«port.name» : in STD_LOGIC_VECTOR («bitwidth-1» downto 0);
            «ENDFOR»
            «FOR port:comp.ports.filter[it instanceof OutputPort] SEPARATOR ";"»
                «comp.name»_«port.name» : out STD_LOGIC_VECTOR («bitwidth-1» downto 0)
            «ENDFOR»);
        end «comp.name»;
        architecture Behavioral of «comp.name» is
            «FOR node:comp.innerGraph.nodes.filter[!(it instanceof Const)]»
               component «node.name» is
               Port(
               «FOR port:node.ports.filter[it instanceof InputPort]»
                  «node.name»_«port.name» : in STD_LOGIC_VECTOR («bitwidth-1» downto 0);
               «ENDFOR»
               «FOR port:node.ports.filter[it instanceof OutputPort]»
                  «node.name»_«port.name» : out STD_LOGIC_VECTOR («bitwidth-1» downto 0);
               «ENDFOR»);
                end component;
            «ENDFOR»
            «FOR node:comp.innerGraph.nodes.filterList(Const,false)»
              «val const=node as Const»
              constant «const.name» : STD_LOGIC_VECTOR («bitwidth-1» downto 0):="«toBit(Integer.parseInt(const.value),bitwidth)»";
            «ENDFOR»
            «FOR signal:comp.innerGraph.edges.neededSignals»
                signal «signal» : STD_LOGIC_VECTOR («bitwidth-1» downto 0);
            «ENDFOR»
        begin
            «FOR o:comp.innerGraph.nodes.filterList(Const,true)»
                «val node=o as Node»
                «node.name+"_"+i++»: «node.name» port map(«node.mapPort»);
            «ENDFOR»
            «FOR o:comp.ports.filterList(InputPort,false)»
                «val port=o as Port»
                «port.outEdges.get(0).name»<=«comp.name»_«port.name»;
            «ENDFOR»
            «FOR o:comp.ports.filterList(OutputPort,false)»
                «val port=o as Port»
                «comp.name»_«port.name»<=«port.inEdge.name»;
            «ENDFOR»
            «FOR o:comp.innerGraph.nodes.filterList(Const,false)»
                «val const=o as Const»
                «const.ports.get(0).outEdges.get(0).name»<=«const.name»;
            «ENDFOR»
        end
        end Behavioral;
    '''}
    def static String buildBody(ComplexNode comp){'''
    «var i=0»
    begin
                «FOR o:comp.innerGraph.nodes.filterList(Const,true)»
                    «val node=o as Node»
                    «node.name+"_"+i++»: «node.name» port map(«node.mapPort»);
                «ENDFOR»
                «FOR o:comp.ports.filterList(InputPort,false)»
                    «val port=o as Port»
                    «port.outEdges.get(0).name»<=«comp.name»_«port.name»;
                «ENDFOR»
                «FOR o:comp.ports.filterList(OutputPort,false)»
                    «val port=o as Port»
                    «comp.name»_«port.name»<=«port.inEdge.name»;
                «ENDFOR»
                «FOR o:comp.innerGraph.nodes.filterList(Const,false)»
                    «val const=o as Const»
                    «const.ports.get(0).outEdges.get(0).name»<=«const.name»;
                «ENDFOR»
                  '''
        
    }
    def static filterList(List<?> list, Class<?> clazz, boolean isComplementer){
        return list.stream.filter(e | 
            if(!isComplementer){
                clazz.isInstance(e)
            }
            else{
                !clazz.isInstance(e)
            }
        ).collect(Collectors.toList);
        
    }
    def static mapPort(Node node){
        '''«FOR port:node.ports SEPARATOR","»«port.getSignalName»«ENDFOR»'''   
    }
    def static String getSignalName(Port port){
        if(port instanceof InputPort){
            return port.inEdge.sourcePort.name;
        }
        else{
            return port.name;
        }
    }
    def static toBit(int input, int bitwidth){
        val builder=new StringBuilder();
        var i = input;
        var j=bitwidth;
        while(i>0){
            builder.append(i%2);
            i/=2;
            j--;
        }   
        for(;j>0;j--){
            builder.append("0");
        }
        return builder.reverse;
    }
    def static getNeededSignals(List<Edge> edges){
        val names=new HashSet<String>(edges.stream.map(e|e.name).collect(Collectors.toList()));
         return names;
    }
    
    }
	