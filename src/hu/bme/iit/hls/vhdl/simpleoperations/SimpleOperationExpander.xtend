package hu.bme.iit.hls.vhdl.simpleoperations

class SimpleOperationExpander {
	def static expandOperation(String name,int inPort){
	'''
	«var Cascade cascade=new Cascade()»
	«val operationName=name+inPort»
	library IEEE;
	use IEEE.STD_LOGIC_1164.ALL;	
	entity «operationName» is
	generic(m: natural :=2);
	    Port ( 
	    «FOR i: 0..<inPort»
	       «cascade.addInput(operationName+"_in"+i)»
	       «operationName»_in«i» : in STD_LOGIC_VECTOR (m-1 downto 0);
	    «ENDFOR»
	    out1 : out STD_LOGIC_VECTOR (m-1 downto 0));
	end «operationName»;
	architecture Behavioral of «operationName» is
	component «name» is
	generic(n: natural :=2);
	    Port ( in1 : in STD_LOGIC_VECTOR (n-1 downto 0);
	           in2 : in STD_LOGIC_VECTOR (n-1 downto 0);
	           out1 : out STD_LOGIC_VECTOR (n-1 downto 0));
	end component;
	«FOR i:0..<cascade.signalsNeeded-1»
	   signal result«i» : STD_LOGIC_VECTOR (m-1 downto 0);
	«ENDFOR»
	begin
	   «FOR c:cascade»
	   «name + cascade.getIteration»: «name» generic map(n=>m) port map(«c.nextInput»,«c.nextInput»,«c.nexOutput»);
	   «ENDFOR»
	out1<=«cascade.nextInput»;
	end Behavioral;
	'''
}
//def static String buildOperationBody(ElementaryOp node,Cascade cascade){
//    '''
//    begin
//       «FOR c:cascade»
//            «node.getOpType().getName() + cascade.getIteration»: «node.getOpType().getName()» port map(«c.nextInput»,«c.nextInput»,«c.nexOutput»);
//       «ENDFOR»
//    out1<=«cascade.nextInput»;
//    '''}
}
