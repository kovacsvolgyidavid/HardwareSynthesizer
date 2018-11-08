library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
USE ieee.std_logic_unsigned.ALL;
use ieee.std_logic_arith.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity LT is
Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        input1 : IN STD_LOGIC_VECTOR(31 downto 0);
                        input1_rdy : IN BOOLEAN;
                        input2 : IN STD_LOGIC_VECTOR(31 downto 0);
                        input2_rdy : IN BOOLEAN;
                        output1 : OUT STD_LOGIC_VECTOR(31 downto 0);
                        output1_rdy : OUT BOOLEAN
                    );
end LT;

architecture Behavioral of LT is
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
	PORT MAP(rst, loop_rst, clk, input1_rdy, input2_rdy, start_signal);	
	proc1 : PROCESS (clk, rst, loop_rst)
	BEGIN
		IF rising_edge(rst) or rising_edge(loop_rst) THEN
			output1_rdy <= false;
		ELSIF falling_edge(rst) or falling_edge(loop_rst) THEN --on rst falling edge 
		ELSE
			IF start_signal THEN
				IF signed(input1) < signed(input2) THEN
                                output1(0) <=  '1';
                            else 
                                output1(0) <='0';
                            end if;
				output1(31 downto 1) <= (others => '0');
				output1_rdy <= input1_rdy AND input2_rdy;
			END IF;
		END IF;
	END PROCESS;
end Behavioral;
