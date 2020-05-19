
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity CNT is port (  
		
		CLK : in std_logic; --Sinchro signalas
		RST : in std_logic; -- Reset signalas
		CNT_CMD  : in std_logic; -- Komanda	
		CNT_Flag : out std_logic --Kai pasiekia 0
		);
end CNT;


architecture rtl of CNT is
	signal CNT_A: unsigned (7 downto  0);
begin	
	process(CLK, RST, CNT_CMD)
	begin
		if RST = '1' then
			CNT_A <= "00001000";
			CNT_Flag <= '0';	
		elsif CLK'event and CLK = '1' and CNT_CMD = '1' then 
			CNT_A <= CNT_A - "1";
			if CNT_A < 2 then
				CNT_Flag <= '1';
			end if;
		end if;	
		
	end process; 
	
	
end rtl;		

