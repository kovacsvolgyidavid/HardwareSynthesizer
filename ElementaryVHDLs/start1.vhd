LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY start1 IS
	PORT (
		rst : IN std_logic;
		rst_loop : IN STD_LOGIC;
		clk : IN std_logic;
		input : IN BOOLEAN;
		output : OUT BOOLEAN
	);
END start1;

ARCHITECTURE Behavioral OF start1 IS

BEGIN
	proc_clock : PROCESS (clk, rst)
		VARIABLE is_start_sent : BOOLEAN := false;
	BEGIN
		IF rising_edge(rst) or rising_edge(rst_loop) THEN
			is_start_sent := false;
		ELSIF falling_edge(rst) or falling_edge(rst_loop) THEN
		ELSE
			IF rst = '0' AND NOT is_start_sent AND input THEN
				output <= true;
				is_start_sent := true; 
			ELSE
				output <= false;
			END IF;
		END IF;
	END PROCESS;

END Behavioral;