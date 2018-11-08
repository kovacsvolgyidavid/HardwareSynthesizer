LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;
USE ieee.std_logic_unsigned.ALL;

ENTITY add IS
	PORT (
		rst : IN STD_LOGIC;
		rst_loop : IN STD_LOGIC;
		clk : IN STD_LOGIC;
		input1 : IN STD_LOGIC_VECTOR (31 DOWNTO 0);
		input1_rdy : IN BOOLEAN;
		input2 : IN STD_LOGIC_VECTOR (31 DOWNTO 0);
		input2_rdy : IN BOOLEAN;
		output : OUT STD_LOGIC_VECTOR (31 DOWNTO 0);
		output_rdy : OUT BOOLEAN
	);
END add;
ARCHITECTURE Behavioral OF add IS
	COMPONENT start2
		PORT (
			rst : IN std_logic;
			rst_loop : IN STD_LOGIC;
			clk : IN std_logic;
			input1 : IN BOOLEAN;
			input2 : IN BOOLEAN;
			output1 : OUT BOOLEAN
		);
	END COMPONENT;
	SIGNAL start_signal : BOOLEAN;
BEGIN
	start : start2
	PORT MAP(rst, rst_loop, clk, input1_rdy, input2_rdy, start_signal);	
	proc1 : PROCESS (clk, rst, rst_loop)
	BEGIN
		IF rising_edge(rst) or rising_edge(rst_loop) THEN
			output_rdy <= false;
		ELSIF falling_edge(rst) or falling_edge(rst_loop) THEN --on rst falling edge 
		ELSE
			IF start_signal THEN
				output <= input1 + input2;
				output_rdy <= input1_rdy AND input2_rdy;
			END IF;
		END IF;
	END PROCESS;
END Behavioral;