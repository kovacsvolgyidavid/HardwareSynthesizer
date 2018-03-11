library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use ieee.std_logic_unsigned.all;
entity add is
    Port (
    Input1 : IN STD_LOGIC_VECTOR (31 downto 0);
    Input2 : IN STD_LOGIC_VECTOR (31 downto 0);
    Output1 : OUT STD_LOGIC_VECTOR (31 downto 0)
    );
end add;
architecture Behavioral of add is
begin
out1<=in1+in2;
end Behavioral;
