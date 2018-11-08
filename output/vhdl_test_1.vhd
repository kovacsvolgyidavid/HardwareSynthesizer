library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity vhdl_test_1 is                
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        a : IN STD_LOGIC_vector(31 downto 0);
                        a_rdy : IN BOOLEAN;
                        b : IN STD_LOGIC_vector(31 downto 0);
                        b_rdy : IN BOOLEAN;
                        ret : OUT STD_LOGIC_vector(31 downto 0);
                        ret_rdy : OUT BOOLEAN
                    );
end vhdl_test_1;
architecture Behavioral of vhdl_test_1 is
component vhdl_test_1a is
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        a : IN STD_LOGIC_vector(31 downto 0);
                        a_rdy : IN BOOLEAN;
                        b : IN STD_LOGIC_vector(31 downto 0);
                        b_rdy : IN BOOLEAN;
                        ret : OUT STD_LOGIC_vector(31 downto 0);
                        ret_rdy : OUT BOOLEAN
                    );
end component;             
component Add is
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        input1 : IN STD_LOGIC_vector(31 downto 0);
                        input1_rdy : IN BOOLEAN;
                        input2 : IN STD_LOGIC_vector(31 downto 0);
                        input2_rdy : IN BOOLEAN;
                        output1 : OUT STD_LOGIC_vector(31 downto 0);
                        output1_rdy : OUT BOOLEAN
                    );
end component;             
 signal signal_a_0 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_a_0_rdy : BOOLEAN;
 signal signal_b_1 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_b_1_rdy : BOOLEAN;
 signal signal_out_3 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_3_rdy : BOOLEAN;
 signal signal_out_2 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_2_rdy : BOOLEAN;
 signal signal_out_4 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_4_rdy : BOOLEAN;
 begin
 vhdl_test_1a3: vhdl_test_1a port map(rst, loop_rst, clk, signal_a_0, signal_a_0_rdy, signal_b_1, signal_b_1_rdy, signal_out_2, signal_out_2_rdy );
 add4: Add port map(rst, loop_rst, clk, signal_out_2, signal_out_2_rdy, signal_b_1, signal_b_1_rdy, signal_out_3, signal_out_3_rdy );
 add5: Add port map(rst, loop_rst, clk, signal_out_3, signal_out_3_rdy, signal_a_0, signal_a_0_rdy, signal_out_4, signal_out_4_rdy );
signal_a_0<=a;
signal_a_0_rdy<=a_rdy;
signal_b_1<=b;
signal_b_1_rdy<=b_rdy;
ret<=signal_out_4;
ret_rdy<=signal_out_4_rdy;
end Behavioral;
