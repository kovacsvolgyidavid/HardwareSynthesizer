library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.Numeric_STD.all;
entity loop10 is                
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
end loop10;
architecture Behavioral of loop10 is
component hig9 is
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
SIGNAL signal_f1_3 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_f1_3_rdy : BOOLEAN;
SIGNAL signal_x0__4 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_x0__4_rdy : BOOLEAN;
SIGNAL signal_y0_5 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_y0_5_rdy : BOOLEAN;
SIGNAL signal_y1_6 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_y1_6_rdy : BOOLEAN;
SIGNAL signal_d1__2 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_d1__2_rdy : BOOLEAN;
SIGNAL signal_f0_1 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_f0_1_rdy : BOOLEAN;
SIGNAL signal_Z1_7 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_Z1_7_rdy : BOOLEAN;
SIGNAL signal_d0__0 : STD_LOGIC_VECTOR (31 downto 0);
SIGNAL signal_d0__0_rdy : BOOLEAN;
begin
signal_rst <= rst or rst_loop;
signal_clk <= clk;           
loop_component: hig9 PORT MAP(
signal_rst, signal_loop_rst, signal_clk,
signal_f0_1,signal_f0_1_rdy, signal_f1_3,signal_f1_3_rdy, signal_x0__4,signal_x0__4_rdy
, signal_c, signal_c_rdy, 
signal_y0_5, signal_y0_5_rdy, 
signal_y1_6, signal_y1_6_rdy, 
signal_Z1_7, signal_Z1_7_rdy
);
phi_0 : Phi PORT MAP(
signal_rst, signal_loop_rst, signal_clk,
signal_d0__0, signal_d0__0_rdy,
signal_y0_5, signal_y0_5_rdy,
signal_f0_1, signal_f0_1_rdy
);
phi_1 : Phi PORT MAP(
signal_rst, signal_loop_rst, signal_clk,
signal_d1__2, signal_d1__2_rdy,
signal_y1_6, signal_y1_6_rdy,
signal_f1_3, signal_f1_3_rdy
);
);  
 loop_process : PROCESS (clk, signal_rst)
                 VARIABLE is_loop_finished : BOOLEAN := false;
                 VARIABLE buffer_z1_ : STD_LOGIC_vector(31 downto 0)
                 ;
             BEGIN
             IF rising_edge(signal_rst) THEN
                        is_loop_finished:= false;
                     ELSIF falling_edge(signal_rst) THEN
                                 --do nothing
                     ELSIF signal_c_rdy AND signal_y0_5_rdy AND signal_y1_6_rdy AND signal_Z1_7_rdy THEN --loop minden kimenete kész-e?
                     IF signed(signal_c) > to_signed(0, 32) THEN
                         z1__rdy<=false;
                         signal_loop_rst <= '1';
                     ELSE
                         IF not is_loop_finished THEN --kimenetek beállítása a bufferba
                         buffer_z1_ := signal_Z1_7;
                            is_loop_finished:= true;
                         END IF;
                         z1_ <= buffer_z1_;
                         z1__rdy <= true;
                     END IF;
                 ELSE
                     signal_loop_rst <= '0';
                 END IF;
             END PROCESS;
end Behavioral;
