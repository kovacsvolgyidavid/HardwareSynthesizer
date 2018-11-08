library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity loop9 is                
    Port (
        rst : IN STD_LOGIC;
                        loop_rst : IN STD_LOGIC;
                        clk : IN STD_LOGIC;
                        d0_ : IN STD_LOGIC_vector(31 downto 0);
                        d0__rdy : IN BOOLEAN;
                        d1_ : IN STD_LOGIC_vector(31 downto 0);
                        d1__rdy : IN BOOLEAN;
                        z0_ : OUT STD_LOGIC_vector(31 downto 0);
                        z0__rdy : OUT BOOLEAN
                    );
end loop9;
architecture Behavioral of loop9 is
component hig8 is
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
end component;             
component start2 is
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
component Phi is
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
SIGNAL signal_loop_rst : STD_LOGIC;
SIGNAL signal_rst : STD_LOGIC;
SIGNAL signal_clk : STD_LOGIC; 
SIGNAL signal_c : STD_LOGIC_VECTOR (31 DOWNTO 0);
SIGNAL signal_c_rdy : BOOLEAN;           
SIGNAL signal_d1_2 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_d1_2_rdy : BOOLEAN;
SIGNAL signal_Z06 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_Z06_rdy : BOOLEAN;
SIGNAL signal_f01 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_f01_rdy : BOOLEAN;
SIGNAL signal_y15 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_y15_rdy : BOOLEAN;
SIGNAL signal_y04 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_y04_rdy : BOOLEAN;
SIGNAL signal_d0_0 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_d0_0_rdy : BOOLEAN;
SIGNAL signal_f13 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_f13_rdy : BOOLEAN;
begin
signal_rst <= rst or rst_loop;
signal_clk <= clk;           
loop_component: hig8 PORT MAP(
signal_rst, signal_loop_rst, signal_clk,
signal_f01,signal_f01_rdy, signal_f13,signal_f13_rdy
,signal_c, signal_c_rdy,
signal_y04,signal_y04_rdy, 
signal_y15,signal_y15_rdy, 
signal_Z06,signal_Z06_rdy
);
phi_0 : Phi PORT MAP(

);
phi_1 : Phi PORT MAP(

);
);  
