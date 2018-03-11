library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity  is
    Port (
    );
end ;
architecture Behavioral of  is
component cluster_0 is
Port(
    cluster_0x_0 : IN STD_LOGIC_VECTOR (31 downto 0);
    cluster_0y2_1 : OUT STD_LOGIC_VECTOR (31 downto 0)
       );
end component;
        begin
                                         cluster_0_0: cluster_0 port map(signal_x_0,signal_y2_1);
end Behavioral;
