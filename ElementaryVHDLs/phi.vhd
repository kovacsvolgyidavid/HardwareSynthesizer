LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY phi IS
	PORT (
		rst : IN STD_LOGIC;
		rst_loop : IN STD_LOGIC;
		clk : IN STD_LOGIC;
		start : IN STD_LOGIC_VECTOR (31 DOWNTO 0);
		start_rdy : IN BOOLEAN;
		input : IN STD_LOGIC_VECTOR (31 DOWNTO 0);
		input_rdy : IN BOOLEAN;
		output : OUT STD_LOGIC_VECTOR (31 DOWNTO 0);
		output_rdy : OUT BOOLEAN
	);
END phi;

ARCHITECTURE Behavioral OF phi IS
	COMPONENT start1
		PORT (
			rst : IN std_logic;
			rst_loop : IN STD_LOGIC;
			clk : IN std_logic;
			input : IN BOOLEAN;
			output : OUT BOOLEAN
		);
	END COMPONENT;
	SIGNAL input_observable : BOOLEAN;

BEGIN
	input_obs : start1
	PORT MAP(rst, rst_loop, clk, input_rdy, input_observable);
	proc1 : PROCESS (clk, rst, rst_loop)
		VARIABLE is_first_cyc : BOOLEAN := true;
		VARIABLE buffer_input : STD_LOGIC_VECTOR (31 DOWNTO 0);
		VARIABLE buffer_input_rdy : BOOLEAN := false;
 
	BEGIN
		IF input_rdy THEN
			buffer_input := input;
			buffer_input_rdy := true;
		END IF;
		IF rising_edge(rst) THEN
		is_first_cyc:=TRUE;
		output_rdy <=false;
        ELSIF falling_edge(rst) THEN
                    --do nothing
		ELSIF rising_edge(rst_loop) THEN
			output <= buffer_input;
			output_rdy <= buffer_input_rdy;
			buffer_input_rdy := false;
		ELSIF falling_edge(rst_loop) THEN
			--do nothing
		ELSE
			IF (is_first_cyc AND start_rdy) THEN
				output <= start;
				output_rdy <= start_rdy;
				is_first_cyc := false; 
			END IF;
		END IF;
	END PROCESS;
END Behavioral;