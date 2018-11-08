library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity Init is
Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        input1 : IN STD_LOGIC_vector(31 downto 0);
                        input1_rdy : IN BOOLEAN;
                        output1 : OUT STD_LOGIC_vector(31 downto 0);
                        output1_rdy : OUT BOOLEAN
                    );
end Init;

architecture Behavioral of Init is
begin
output1<=input1;
output1_rdy<=input1_rdy;
end Behavioral;
