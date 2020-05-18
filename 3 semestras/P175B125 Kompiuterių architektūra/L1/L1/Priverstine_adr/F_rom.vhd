
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity ROM is 
	port (
		RST_ROM 	: in std_logic;
		ROM_CMD 	: in std_logic_vector(7 downto 0);	  
		ROM_Dout 	: out std_logic_vector(1 to 80)
		);
end ROM ;

architecture rtl of ROM is
	
	type memory is array (0 to 255) of std_logic_vector(1 to 80) ; 	
	
	constant ROM_CMDln : memory := (	  
--                     1         2         3         4         5         6         7         8 Dvi komentaro eilutes duoda bitu numerius   
--            12345678901234567890123456789012345678901234567890123456789012345678901234567890    (nuo 1 iki 80)
	0 => "10000000000000100000000000000000000000000000000000000000000000000000000000000001",
	1 => "10000000000000000000010000000000000000000000000000000000000000000000000000000111",
	2 => "01000000000000000000000000000000000000000000000000000000000000000000110100000101",
	3 => "00000001000000000000000000000000000000000000000001000000000000000000000000000100",
	4 => "00000000000000000000000000000000000000000000000000000000000000000000000000000010",
	5 => "00000000100000010000000000000000000000000000000000000000000000000001000000000111",
	6 => "00000000000000000000000000000000000000000000000000000000001100010110000000000110",	
	7 => "00010000000000000000000000000000000000000000000000000000000000000000001100000010",	
	others => (others => '0') );   
	
	
	
	
begin
	process (RST_ROM, ROM_CMD) 
		
	begin
		if 	RST_ROM'event and RST_ROM = '1'	 then 
			ROM_Dout <= ROM_CMDln(0);
		elsif ROM_CMD'event then
			ROM_Dout <= ROM_CMDln(to_integer(unsigned(ROM_CMD))); 
		end if;
		
	end process;
	
end rtl;