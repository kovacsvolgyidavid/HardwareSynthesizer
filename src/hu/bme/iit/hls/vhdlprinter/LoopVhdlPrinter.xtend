package hu.bme.iit.hls.vhdlprinter

import hu.bme.iit.hls.entities.InOut
import hu.bme.iit.hls.entities.VhdlEntity
import hu.bme.iit.hls.higmodel.LoopComp
import java.util.stream.Collectors

class LoopVhdlPrinter {
    static ComponenetPreprocessor preprocessor = new ComponenetPreprocessor();
    static LoopPreproccessor loopPreprocessor = new LoopPreproccessor();

    def static String printVhdl(LoopComp loop) {
        var entity = preprocessor.getEntity(loop);
        '''
            «PrintUtils.includes»
            «PrintUtils.printEntity(entity)»
            «printArchitecture(loop,entity)»
        '''
    }

    

    def static printArchitecture(LoopComp loop, VhdlEntity entity) {
        var signalMap = loopPreprocessor.createPortMap(loop);   
        var comp = loop.comp;     
        '''
            architecture Behavioral of «entity.name» is
            «PrintUtils.printComponents(preprocessor.getComponents(loop))» 
            SIGNAL signal_loop_rst : STD_LOGIC;
            SIGNAL signal_rst : STD_LOGIC;
            SIGNAL signal_clk : STD_LOGIC; 
            SIGNAL signal_c : STD_LOGIC_VECTOR (31 DOWNTO 0);
            SIGNAL signal_c_rdy : BOOLEAN;           
            «FOR signal : signalMap.values.toSet»
            SIGNAL «signal.name» : STD_LOGIC_VECTOR («signal.bitWidth-1» downto 0);
            SIGNAL «signal.name»_rdy : BOOLEAN;
            «ENDFOR»
            begin
            signal_rst <= rst or loop_rst;
            signal_clk <= clk;
            «FOR port : loop.inPorts»
            «signalMap.get(port).name»<=«port.name»;
            «signalMap.get(port).name»_rdy<=«port.name»_rdy;                 
            «ENDFOR»
            «FOR port : loop.outPorts»
            «port.name»<=«signalMap.get(port).name»; 
            «port.name»_rdy<=«signalMap.get(port).name»_rdy;               
            «ENDFOR»             
            loop_component: «loop.comp.name» PORT MAP(
            signal_rst, signal_loop_rst, signal_clk,
            «FOR port : comp.inPorts SEPARATOR ", "»«signalMap.get(port).name»,«signalMap.get(port).name»_rdy«ENDFOR»
            «IF comp.inPorts.size>0», «ENDIF»signal_c, signal_c_rdy«IF comp.outPorts.size>1», «ENDIF»
            «FOR port : comp.outPorts.stream.filter(k|!k.name.startsWith("c")).collect(Collectors.toList) SEPARATOR ", "»
            «signalMap.get(port).name», «signalMap.get(port).name»_rdy
            «ENDFOR»);
            «FOR i:0..<loop.inPorts.stream.filter(k|k.name.startsWith("d")).count.intValue»
                phi_«i» : Phi PORT MAP(
                signal_rst, signal_loop_rst, signal_clk,
                «signalMap.get(loop.inPorts.get(i)).name», «signalMap.get(loop.inPorts.get(i)).name»_rdy,
                «signalMap.get(comp.outPorts.get(i+1)).name», «signalMap.get(comp.outPorts.get(i+1)).name»_rdy,
                «signalMap.get(comp.inPorts.get(i)).name», «signalMap.get(comp.inPorts.get(i)).name»_rdy
                );
            «ENDFOR»
            «var entityOutPorts = entity.ports.stream.filter(k|k.inOut.equals(InOut.OUT) && k.bitWidth>1).collect(Collectors.toList)»
            «var outPorts = loop.outPorts.stream.filter(k|k.name.startsWith("z")).collect(Collectors.toList)»           
             loop_process : PROCESS (clk, signal_rst)
                             VARIABLE is_loop_finished : BOOLEAN := false;
                             «FOR port : entityOutPorts»
                             VARIABLE buffer_«port.name» : «PrintUtils.printType(port)»;
                             «ENDFOR»
                         BEGIN
             «var compInputs = comp.outPorts.stream.filter(k|!k.name.toLowerCase.startsWith("c")).collect(Collectors.toList)»
                         IF rising_edge(signal_rst) THEN
                                    is_loop_finished:= false;
                                    «FOR port : outPorts»
                                    «port.name»_rdy<=false;
                                    «ENDFOR»
                                 ELSIF falling_edge(signal_rst) THEN
                                             --do nothing
                                 ELSIF signal_c_rdy«IF compInputs.size>0» AND «ENDIF»«FOR port: compInputs SEPARATOR " AND "»«signalMap.get(port).name»_rdy«ENDFOR» THEN --loop minden kimenete kész-e?
                                 IF signed(signal_c) > to_signed(0, 32) THEN
                                     «FOR port : entityOutPorts»
                                     «port.name»_rdy<=false;
                                     «ENDFOR»
                                     signal_loop_rst <= '1';
                                 ELSE
                                     IF not is_loop_finished THEN --kimenetek beállítása a bufferba
                                     «FOR port : outPorts»
                                            buffer_«port.name» := «signalMap.get(port).name»;
                                     «ENDFOR»
                                        is_loop_finished:= true;
                                     END IF;
                                     «FOR port : entityOutPorts»
                                     «port.name» <= buffer_«port.name»;
                                     «port.name»_rdy <= true;
                                     «ENDFOR»
                                 END IF;
                             ELSE
                                 signal_loop_rst <= '0';
                             END IF;
                         END PROCESS;
            end Behavioral;
        '''
    }

}