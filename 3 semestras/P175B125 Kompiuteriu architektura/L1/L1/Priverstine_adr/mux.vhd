
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity MUX is port (
		
		
		MUX_CMD  	: in std_logic_vector(3 downto 0); -- Komanda
		MUX_Din0 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din1 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din2 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din3 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din4 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din5 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din6 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		MUX_Din7 	: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		
		
		MUX_Dout 	: out std_logic_vector(15 downto 0) -- Rezultato isvestis
		);
end MUX;


architecture rtl of MUX is
begin	
	process(MUX_CMD,MUX_Din0,MUX_Din1,MUX_Din2,MUX_Din3,MUX_Din4,MUX_Din5,MUX_Din6,MUX_Din7)
		
	begin
		--if MUX_CMD'event then
			if MUX_CMD(3) = '1' then
				MUX_Dout <= "0000000000000000";-- NOP
			else
				case MUX_CMD(2 downto 0) is
					when "000"		=> MUX_Dout <= MUX_Din0;
					when "001"		=> MUX_Dout <= MUX_Din1;
					when "010"		=> MUX_Dout <= MUX_Din2;
					when "011"		=> MUX_Dout <= MUX_Din3;
					when "100"		=> MUX_Dout <= MUX_Din4;
					when "101"		=> MUX_Dout <= MUX_Din5;
					when "110"		=> MUX_Dout <= MUX_Din6; 
					when "111"		=> MUX_Dout <= MUX_Din7;
					when others 	=> MUX_Dout <= "0000000000000000";-- NOP
				end case; 
			end if;	
		--end if;
	end process;
end rtl;		

