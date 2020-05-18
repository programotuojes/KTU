
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas 


-- FLG_Dout - asinchroninis registras skirtas fiksuoti ALU ir universaliu registru veiksmu rezultatus
-- 0 bitas:  REG A neigiamas skaicius
-- 1 bitas:  REG A(0) yra '1'
-- 2 bitas:  REG B neigiamas skaicius
-- 3 bitas:  REG B(0) yra '1'
-- 4 bitas:  REG C neigiamas skaicius
-- 5 bitas:  REG C(0) yra '1'
-- 6 bitas:  REG D neigiamas skaicius
-- 7 bitas:  REG D(0) yra '1'
-- 8 bitas:  REG E neigiamas skaicius
-- 9 bitas:  REG E(0) yra '1'
-- 10 bitas: REG F neigiamas skaicius
-- 11 bitas: REG F(0) yra '1'
-- 12 bitas: CNT == 0
-- 13 bitas: ALU M isvestyje neigiamas skaicius
-- 14 bitas: ALU po sudeties buvo pernasa
-- 15 bitas: 
-- 16 bitas: 
-- 17 bitas: 


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity FLG is 
	port (
		
		CLK 			: in std_logic; --Sinchro signalas
		RST 			: in std_logic;
		Xin  			: in std_logic_vector(1 to 18);
		
		FLG_Dout 		: out std_logic_vector(1 to 18)
		);
end FLG ;

architecture rtl of FLG is
	
	
begin
	process (CLK, RST, Xin)
	begin  
		
		if RST'event and RST = '1' then
			FLG_Dout <= (others => '0'); 
		elsif CLK'event and CLK = '0' then
			FLG_Dout <= Xin;
		end if;
		
	end process;
	
end rtl;