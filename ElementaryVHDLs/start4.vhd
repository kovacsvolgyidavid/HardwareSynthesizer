LIBRARY IEEE;
USE IEEE.STD_LOGIC_1164.ALL;

ENTITY start2 IS
    PORT (
        rst : IN std_logic;
        rst_loop : IN STD_LOGIC;
        clk : IN std_logic;
        input1 : IN BOOLEAN;
        input2 : IN BOOLEAN;
        input3 : IN BOOLEAN;
        input4 : IN BOOLEAN;
        output1 : OUT BOOLEAN
    );
END start2;

ARCHITECTURE Behavioral OF start2 IS
BEGIN
    proc_clock : PROCESS (clk, rst)
        VARIABLE is_start_sent : BOOLEAN := false;
    BEGIN
        IF rising_edge(rst) or rising_edge(rst_loop) THEN
            is_start_sent := false;
        ELSIF falling_edge(rst) or falling_edge(rst_loop) THEN
        ELSE
            IF rst = '0' AND NOT is_start_sent AND input1 AND input2 AND input3 AND input4 THEN
                output <= true;
                is_start_sent := true; 
            ELSE
                output <= false;
            END IF;
        END IF;
    END PROCESS;
END Behavioral;
