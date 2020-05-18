library ieee;
use ieee.std_logic_1164 .all;
use ieee.numeric_std .all;

entity CNT8 is port (
	CLK : in std_logic;		-- Sinchro signalas
	RST : in std_logic;		-- Reset signalas
	CNT_CMD : in std_logic;	-- Komanda
	CNT_C : out std_logic;	-- Pernasa
	CNT_O : out std_logic_vector (2 downto 0)
	);
end CNT8 ;

architecture rtl of CNT8 is
	signal CNT_A : unsigned (3 downto 0);
begin
	process (CLK, RST, CNT_CMD)
	begin
		if RST = '1' then
			CNT_A <= "000" ;
			CNT_C <= '1';
		elsif CLK'event and CLK = '1' and CNT_CMD = '1' then
			if CNT_A < 7 then	-- Pernasos signalas
				CNT_A <= CNT_A + 1;
				if CNT_A = 6 then
					CNT_C <= '0';
				else
					CNT_C <= '1';
				end if;
			else
				CNT_C <= '1';
				CNT_A <= "000";
			end if;
		end if;
	end process ;
CNT_O <= std_logic_vector (CNT_A);
end rtl ;