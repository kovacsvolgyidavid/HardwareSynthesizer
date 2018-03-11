library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity cluster_0 is
    Port (
    cluster_0x_0 : IN STD_LOGIC_VECTOR (31 downto 0);
    cluster_0y2_1 : OUT STD_LOGIC_VECTOR (31 downto 0)
    );
end cluster_0;
architecture Behavioral of cluster_0 is
component add is
Port(
    Input1 : IN STD_LOGIC_VECTOR (31 downto 0);
    Input2 : IN STD_LOGIC_VECTOR (31 downto 0);
    Output1 : OUT STD_LOGIC_VECTOR (31 downto 0)
       );
end component;
 signal signal_x_0 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_4 : STD_LOGIC_VECTOR (31 downto 0);
        begin
                                         add_0_0: add port map(signal_x_0,signal_x_0,signal_out_4);
                   signal_x_0<=cluster_0x_0;
                   cluster_0y2_1<=signal_out_4;
end Behavioral;
