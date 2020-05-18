
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all; 	
--use ieee.std_logic_signed.all;
--use ieee.std_logic_arith.all;	 


entity ALU_s is port (
		
		ALU_CMD  		: in std_logic_vector(4 downto 0); -- Komanda	
		ALU_Din_L 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		ALU_Din_R 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
		
		FLG_ALU_CMD		: out std_logic_vector(4 downto 0); --Instrukcijos FLAG 
		ALU_Dout 		: out std_logic_vector(15 downto 0) -- Rezultato isvestis	 
		
		
		);
end ALU_s;

architecture rtl of ALU_s is   
	signal A : unsigned(16 downto 0) := "00000000000000000";
begin	   
	process(ALU_CMD, ALU_Din_L, ALU_Din_R)	
	begin
		case ALU_CMD(2 downto 0) is
			when "000" => 
				ALU_Dout <= std_logic_vector(unsigned(ALU_Din_L) + unsigned(ALU_Din_R)); -- SUM 
				A(15 downto 0) <= (unsigned(ALU_Din_L) + unsigned(ALU_Din_R));
				FLG_ALU_CMD(3) <= A(16); --Jei po sumos atsirado pernasa irasomas bitas i FLAG registra
			when "001" => 
				ALU_Dout <= not ALU_Din_L; -- NOT L  
			when "010" => 
				ALU_Dout <= not ALU_Din_R; -- NOT R 
			when "011" => 
				ALU_Dout <= std_logic_vector(unsigned(ALU_Din_L) + 1); --L + 1
			when "100" => 
				ALU_Dout <= std_logic_vector(unsigned(ALU_Din_L) - 1); --L - 1
			when "101" => 
				ALU_Dout <= std_logic_vector(unsigned(ALU_Din_R) + 1); --R + 1
			when "110" => 
				ALU_Dout <= std_logic_vector(unsigned(ALU_Din_R) - 1); --R + 1
			when "111" => 
				ALU_Dout <= ALU_Din_L xor ALU_Din_R;
			when others => assert 1=0 report "Neteisinga ALU komanda" severity warning;	
		end case;	
	end process; 					
	FLG_ALU_CMD(4) <= '0';
	FLG_ALU_CMD(2) <= '0';
	FLG_ALU_CMD(1) <= '0';
	FLG_ALU_CMD(0) <= '0';
end rtl;	


