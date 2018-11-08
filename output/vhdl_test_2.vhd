library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity vhdl_test_2 is                
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        sum : IN STD_LOGIC_vector(31 downto 0);
                        sum_rdy : IN BOOLEAN;
                        ret : OUT STD_LOGIC_vector(31 downto 0);
                        ret_rdy : OUT BOOLEAN
                    );
end vhdl_test_2;
architecture Behavioral of vhdl_test_2 is
component Assign is
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        input1 : IN STD_LOGIC_vector(31 downto 0);
                        input1_rdy : IN BOOLEAN;
                        output1 : OUT STD_LOGIC_vector(31 downto 0);
                        output1_rdy : OUT BOOLEAN
                    );
end component;             
component loop10 is
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        d0_ : IN STD_LOGIC_vector(31 downto 0);
                        d0__rdy : IN BOOLEAN;
                        d1_ : IN STD_LOGIC_vector(31 downto 0);
                        d1__rdy : IN BOOLEAN;
                        x0_ : IN STD_LOGIC_vector(31 downto 0);
                        x0__rdy : IN BOOLEAN;
                        z1_ : OUT STD_LOGIC_vector(31 downto 0);
                        z1__rdy : OUT BOOLEAN
                    );
end component;             
component Init is
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        input1 : IN STD_LOGIC_vector(31 downto 0);
                        input1_rdy : IN BOOLEAN;
                        output1 : OUT STD_LOGIC_vector(31 downto 0);
                        output1_rdy : OUT BOOLEAN
                    );
end component;             
 constant constant_12 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000000";
 constant constant_12_rdy : BOOLEAN:=true;
 constant constant_11 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000010100";
 constant constant_11_rdy : BOOLEAN:=true;
 signal signal_out_14 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_14_rdy : BOOLEAN;
 signal signal_out_13 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_13_rdy : BOOLEAN;
 signal signal_sum_15 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_sum_15_rdy : BOOLEAN;
 signal signal_z1_16 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_z1_16_rdy : BOOLEAN;
 begin
 init7: Init port map(rst, loop_rst, clk, constant_11, constant_11_rdy, signal_out_13, signal_out_13_rdy );
 assign8: Assign port map(rst, loop_rst, clk, constant_12, constant_12_rdy, signal_out_14, signal_out_14_rdy );
 loop10node0: loop10 port map(rst, loop_rst, clk, signal_sum_15, signal_sum_15_rdy, signal_out_14, signal_out_14_rdy, signal_out_13, signal_out_13_rdy, signal_z1_16, signal_z1_16_rdy );
signal_sum_15<=sum;
signal_sum_15_rdy<=sum_rdy;
ret<=signal_z1_16;
ret_rdy<=signal_z1_16_rdy;
end Behavioral;
