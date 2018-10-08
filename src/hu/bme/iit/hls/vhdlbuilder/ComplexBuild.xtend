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

class ComplexBuild <T> {
    
    static Object HIGUtility
    
    def static String buildBody(ComplexNode comp) {'''
    «var i=0»
            «var manager= new VhdlManager()»
            begin
                        «FOR node : new ComplexBuild().filterList(comp.innerGraph.nodes,Const,true)» «««példányosítás, portok összekötése
                    «node.name»_«i++»: «manager.getEntity(node).name» port map(«node.mapPort»);«ENDFOR»
                       «FOR port : new ComplexBuild().filterList(comp.ports,InputPort,false)»
                           signal_«port.name»<=«comp.name»«port.name»;
                       «ENDFOR»
                       «FOR port : new ComplexBuild().filterList(comp.ports,OutputPort,false)»
                           «comp.name»«port.name»<=signal_«port.inEdge.sourcePort.name»;
                       «ENDFOR»
                       «FOR constant : new ComplexBuild().filterList(comp.innerGraph.nodes,Const,false)»              
                           «constant.ports.get(0).name»<=«constant.name»;
                       «ENDFOR»
                '''
        
    }
    def List<T> filterList(List<T> list, Class<? extends T> clazz, boolean isComplementer){
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
    