library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity hig8 is                
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        f0 : IN STD_LOGIC_vector(31 downto 0);
                        f0_rdy : IN BOOLEAN;
                        f1 : IN STD_LOGIC_vector(31 downto 0);
                        f1_rdy : IN BOOLEAN;
                        c : OUT STD_LOGIC_vector(31 downto 0);
                        c_rdy : OUT BOOLEAN;
                        y0 : OUT STD_LOGIC_vector(31 downto 0);
                        y0_rdy : OUT BOOLEAN;
                        y1 : OUT STD_LOGIC_vector(31 downto 0);
                        y1_rdy : OUT BOOLEAN;
                        Z0 : OUT STD_LOGIC_vector(31 downto 0);
                        Z0_rdy : OUT BOOLEAN
                    );
end hig8;
architecture Behavioral of hig8 is
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
 constant constant16 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000001";
 constant constant16_rdy : BOOLEAN:=true;
 constant constant15 : STD_LOGIC_VECTOR (31 downto 0):="00000000000000000000000000000101";
 constant constant15_rdy : BOOLEAN:=true;
 signal signal_out20 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out20_rdy : BOOLEAN;
 signal signal_out21 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out21_rdy : BOOLEAN;
 signal signal_f019 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_f019_rdy : BOOLEAN;
 signal signal_out18 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_out18_rdy : BOOLEAN;
 signal signal_f117 : STD_LOGIC_VECTOR (31 downto 0);
 signal signal_f117_rdy : BOOLEAN;
 begin
 lT10: LT port map(rst, loop_rst, clk, signal_f117, signal_f117_rdy, constant15, constant15_rdy, signal_out18, signal_out18_rdy );
 add11: Add port map(rst, loop_rst, clk, signal_f019, signal_f019_rdy, signal_f117, signal_f117_rdy, signal_out20, signal_out20_rdy );
 add12: Add port map(rst, loop_rst, clk, signal_f117, signal_f117_rdy, constant16, constant16_rdy, signal_out21, signal_out21_rdy );
signal_f019<=f0;
signal_f019_rdy<=f0_rdy;
signal_f117<=f1;
signal_f117_rdy<=f1_rdy;
c<=signal_out18;
c_rdy<=signal_out18_rdy;
y0<=signal_out20;
y0_rdy<=signal_out20_rdy;
y1<=signal_out21;
y1_rdy<=signal_out21_rdy;
Z0<=signal_f019;
Z0_rdy<=signal_f019_rdy;
end Behavioral;
