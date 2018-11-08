library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity hig9 is                
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        f0 : IN STD_LOGIC_vector(31 downto 0);
                        f0_rdy : IN BOOLEAN;
                        f1 : IN STD_LOGIC_vector(31 downto 0);
                        f1_rdy : IN BOOLEAN;
                        X0 : IN STD_LOGIC_vector(31 downto 0);
                        X0_rdy : IN BOOLEAN;
                        c : OUT STD_LOGIC_vector(31 downto 0);
                        c_rdy : OUT BOOLEAN;
                        y0 : OUT STD_LOGIC_vector(31 downto 0);
                        y0_rdy : OUT BOOLEAN;
                        y1 : OUT STD_LOGIC_vector(31 downto 0);
                        y1_rdy : OUT BOOLEAN;
                        Z1 : OUT STD_LOGIC_vector(31 downto 0);
                        Z1_rdy : OUT BOOLEAN
                    );
end hig9;
architecture Behavioral of hig9 is
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
component LT is
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
 constant constant_17 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000101";
 constant constant_17_rdy : BOOLEAN:=true;
 constant constant_18 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000001";
 constant constant_18_rdy : BOOLEAN:=true;
 signal signal_out_23 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_23_rdy : BOOLEAN;
 signal signal_out_24 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_24_rdy : BOOLEAN;
 signal signal_f1_19 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_f1_19_rdy : BOOLEAN;
 signal signal_X0_22 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_X0_22_rdy : BOOLEAN;
 signal signal_out_20 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out_20_rdy : BOOLEAN;
 signal signal_f0_21 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_f0_21_rdy : BOOLEAN;
 begin
 lT11: LT port map(rst, loop_rst, clk, signal_f1_19, signal_f1_19_rdy, constant_17, constant_17_rdy, signal_out_20, signal_out_20_rdy );
 add12: Add port map(rst, loop_rst, clk, signal_f0_21, signal_f0_21_rdy, signal_X0_22, signal_X0_22_rdy, signal_out_23, signal_out_23_rdy );
 add13: Add port map(rst, loop_rst, clk, signal_f1_19, signal_f1_19_rdy, constant_18, constant_18_rdy, signal_out_24, signal_out_24_rdy );
signal_f0_21<=f0;
signal_f0_21_rdy<=f0_rdy;
signal_f1_19<=f1;
signal_f1_19_rdy<=f1_rdy;
signal_X0_22<=X0;
signal_X0_22_rdy<=X0_rdy;
c<=signal_out_20;
c_rdy<=signal_out_20_rdy;
y0<=signal_out_23;
y0_rdy<=signal_out_23_rdy;
y1<=signal_out_24;
y1_rdy<=signal_out_24_rdy;
Z1<=signal_f0_21;
Z1_rdy<=signal_f0_21_rdy;
end Behavioral;
