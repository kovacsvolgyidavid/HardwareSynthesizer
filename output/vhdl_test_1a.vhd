library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity vhdl_test_1a is                
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
end vhdl_test_1a;
architecture Behavioral of vhdl_test_1a is
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
 constant constant_5 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000000";
 constant constant_5_rdy : BOOLEAN:=true;
 signal signal_out_9 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_9_rdy : BOOLEAN;
 signal signal_a_7 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_a_7_rdy : BOOLEAN;
 signal signal_out_10 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_10_rdy : BOOLEAN;
 signal signal_b_8 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_b_8_rdy : BOOLEAN;
 signal signal_out_6 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_6_rdy : BOOLEAN;
 begin
 init0: Init port map(rst, loop_rst, clk, constant_5, constant_5_rdy, signal_out_6, signal_out_6_rdy );
 add1: Add port map(rst, loop_rst, clk, signal_a_7, signal_a_7_rdy, signal_b_8, signal_b_8_rdy, signal_out_9, signal_out_9_rdy );
 add2: Add port map(rst, loop_rst, clk, signal_out_6, signal_out_6_rdy, signal_out_9, signal_out_9_rdy, signal_out_10, signal_out_10_rdy );
signal_a_7<=a;
signal_a_7_rdy<=a_rdy;
signal_b_8<=b;
signal_b_8_rdy<=b_rdy;
ret<=signal_out_10;
ret_rdy<=signal_out_10_rdy;
end Behavioral;
