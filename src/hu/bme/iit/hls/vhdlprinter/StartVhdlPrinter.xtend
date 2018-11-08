package hu.bme.iit.hls.vhdlprinter

import java.util.stream.Collectors
import java.util.stream.IntStream

class StartVhdlPrinter {
 def static String printStart(int inputs){
     var inputNumberList = IntStream.rangeClosed(1,inputs).boxed.collect(Collectors.toList())
     '''
     LIBRARY IEEE;
     USE IEEE.STD_LOGIC_1164.ALL;
     
     ENTITY start2 IS
         PORT (
             rst : IN std_logic;
             rst_loop : IN STD_LOGIC;
             clk : IN std_logic;
             «FOR int i : inputNumberList»
             input«i» : IN BOOLEAN;
             «ENDFOR»
             output1 : OUT BOOLEAN
         );
     END start2;
     
     ARCHITECTURE Behavioral OF start2 IS
     BEGIN
         proc_clock : PROCESS (clk, rst)
             VARIABLE is_start_sent : BOOLEAN := false;
         BEGIN
             IF rising_edge(rst) or rising_edge(rst_loop) THEN
                 is_start_sent := false;
             ELSIF falling_edge(rst) or falling_edge(rst_loop) THEN
             ELSE
                 IF rst = '0' AND NOT is_start_sent AND «FOR int i : inputNumberList SEPARATOR " AND "»input«i»«ENDFOR» THEN
                     output <= true;
                     is_start_sent := true; 
                 ELSE
                     output <= false;
                 END IF;
             END IF;
         END PROCESS;
     END Behavioral;
     '''
 }
}