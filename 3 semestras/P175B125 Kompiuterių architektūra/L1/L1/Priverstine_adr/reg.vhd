
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity REG is port (
	
		CLK 			: in std_logic; --Sinchro signalas
		RST 			: in std_logic; -- Reset signalas
		REG_Din 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		REG_CMD  		: in std_logic_vector(2 downto 0); -- Komanda
		
		REG_FLAG_H		: out std_logic; -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		: out std_logic; -- Zemiausios skilties indikatorius
		REG_Dout 		: out std_logic_vector(15 downto 0) -- Rezultato isvestis
		);
end REG;


architecture rtl of REG is
	signal A: std_logic_vector (15 downto  0);
	
begin
	process(CLK, RST, REG_CMD, REG_Din)	
	begin
		if  RST = '1' then
			A <= "0000000000000000";
			REG_Dout <= "0000000000000000";
			REG_FLAG_H <= '0';	
			REG_FLAG_L <= '0';
			
		elsif CLK'event and CLK = '1' then
			
			case REG_CMD(2 downto 0) is
				when "001"		=> 					-- Duomenu ivedimas
					REG_Dout <= REG_Din;
					A <= REG_Din;
					REG_FLAG_H <= REG_Din(15);	
					REG_FLAG_L <= REG_Din(0);
				when "010"		=> 					-- LL1		 	
					REG_Dout <= A(14 downto 0) & '0';
					A <= A(14 downto 0) & '0';	
					REG_FLAG_H <= A(14);	
					REG_FLAG_L <= '0';
				when "011"		=> 					-- LR1		  	
					REG_Dout <= '0' &  A(15 downto 1);
					A <= '0' &  A(15 downto 1);	
					REG_FLAG_H <= '0';	
					REG_FLAG_L <= A(1);					
				when "100"		=> 					-- AL1	 	
					REG_Dout <= A(15) & A(13 downto 0) & A(15);
					A <= A(15) & A(13 downto 0) & A(15); 
					REG_FLAG_H <= A(15);	
					REG_FLAG_L <= A(15);					
				when "101"		=> 					-- AR1	 	
					REG_Dout <= A(15) & A(15) & A(14 downto 1);
					A <= A(15) & A(15) & A(14 downto 1); 
					REG_FLAG_H <= A(15);	
					REG_FLAG_L <= A(1);					
				when "110"		=> 					-- CL1 	
					REG_Dout <= A(14 downto 0) & A(15);
					A <= A(14 downto 0) & A(15);
					REG_FLAG_H <= A(14);	
					REG_FLAG_L <= A(15);					
				when "111"		=> 					-- CR1		 	
					REG_Dout <= A(0) & A(15 downto 1);
					A <= A(0) & A(15 downto 1);	
					REG_FLAG_H <= A(0);	
					REG_FLAG_L <= A(1);					
				when others => 
				A <= A;	  						-- NOP
			end case;
			
			
		end if;
	end process;  
	

	
end rtl;		

