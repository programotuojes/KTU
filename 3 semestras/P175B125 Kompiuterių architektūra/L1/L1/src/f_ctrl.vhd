
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;	



entity CTRL is port (  
		
		--Signalai
		CLK 		: in std_logic;
		RST 		: in std_logic;
		
		--Duomenys IN
		ROM_Din 	: in std_logic_vector(1 to 80);
		Din 		: in std_logic_vector(15 downto 0);
		
		--Flagai
		FlagV		: in std_logic_vector(1 to 18); --
		
		--Kontroles signalai
		
		CNT_CMD		: out std_logic;
		MUX_CMD		: out std_logic_vector(3 downto 0);
		ALU_CMD		: out std_logic_vector(4 downto 0);
		ROM_CMD		: out std_logic_vector(7 downto 0);
		CMD			: out std_logic_vector(7 downto 0);	 
		
		
		REG_A_CMD 	: out std_logic_vector(2 downto 0);
		REG_B_CMD 	: out std_logic_vector(2 downto 0);
		REG_C_CMD 	: out std_logic_vector(2 downto 0);
		REG_D_CMD 	: out std_logic_vector(2 downto 0);
		REG_E_CMD 	: out std_logic_vector(2 downto 0);
		REG_F_CMD 	: out std_logic_vector(2 downto 0);
		
		
		
		--Reseto signalai
		RST_COMP 	:out std_logic_vector(8 downto 0);
		
		--Duomenys OUT	
		Done		: out std_logic;
		CTRL_Dout 		: out std_logic_vector(15 downto 0)
		
		);
end CTRL;   


architecture rtl of CTRL	is	
	signal current 		: std_logic_vector(1 to 80);	
	signal N_ADDR 		: unsigned(0 to 7);	
	signal C_ADDR 		: unsigned(0 to 7);
	signal LS 			: unsigned(0 to 3);  
	signal Logic_Jump 	: std_logic;
	signal CNT_clk 		: std_logic;
	
begin
	process (CLK, RST, ROM_Din, Din, FlagV)
	begin
		if RST = '1' then
			CNT_clk		<= '0';
			N_ADDR 		<= "00000000";
			C_ADDR 		<= "00000000";
			ALU_CMD 	<= "00000";
			CNT_CMD 	<= '0';
			ROM_CMD 	<= "00000000";
			MUX_CMD 	<= "0000";			
			current 	<= (others => '0'); 
			REG_A_CMD 	<= "000";
			REG_B_CMD 	<= "000";
			REG_C_CMD 	<= "000";
			REG_D_CMD 	<= "000";
			REG_E_CMD 	<= "000";
			REG_F_CMD 	<= "000";	 
			CMD <= "00000000";
			RST_COMP 	<= (others => '1');
			CTRL_Dout <= "0000000000000000";	 	  --Duomenu isvedimas
			Done <= '0';		  --Pabaigos signalas 
			Logic_Jump <= '0';
			
			
			
			
		elsif (rising_edge(CLK)) then 
			
			RST_COMP 	<= (others => '0');
			
			
			--Adresas i ROM'a naujai komandai  
			
			
			
			--------------------------------------------------------------
			--Priverstine adresacija 
			
			ALU_CMD		<= "00000";
			CNT_CMD 	<= '0';
			REG_A_CMD 	<= "000";
			REG_B_CMD 	<= "000";
			REG_C_CMD 	<= "000";
			REG_D_CMD 	<= "000";
			REG_E_CMD 	<= "000";
			REG_F_CMD 	<= "000";
			C_ADDR <= N_ADDR;
			current <= ROM_Din;
			LS 		<= unsigned(ROM_Din(69 to 72));
			N_ADDR 	<= unsigned(ROM_Din(73 to 80));  
			
			
			
			
			-- MUX operacijos:
			if ROM_Din(1) = '1' then	--MUX => DIN
				MUX_CMD 	<= "0000";
			end if;
			
			if ROM_Din(2) = '1' then	--MUX => REG_A_Dout
				MUX_CMD 	<= "0010";
			end if;
			
			if ROM_Din(3) = '1' then	--MUX => REG_B_Dout
				MUX_CMD 	<= "0011";
			end if;
			
			if ROM_Din(4) = '1' then	--MUX => REG_C_Dout
				MUX_CMD 	<= "0100";
			end if;
			
			if ROM_Din(5) = '1' then	--MUX => REG_D_Dout
				MUX_CMD 	<= "0101";
			end if;
			
			if ROM_Din(6) = '1' then	--MUX => REG_E_Dout
				MUX_CMD 	<= "0110";
			end if;
			
			if ROM_Din(7) = '1' then	--MUX => REG_F_Dout
				MUX_CMD 	<= "0111";
			end if;
			
			--REG A Operacijos:
			
			if ROM_Din(8) = '1' then	--Duomenu ikrovimas	
				REG_A_CMD 	<= "001";	
			end if;	
			
			--LL1
			
			if ROM_Din(9) = '1' then
				REG_A_CMD 	<= "010";
			end if;	
			
			--LR1
			
			if ROM_Din(10) = '1' then
				REG_A_CMD 	<= "011";
			end if;	
			
			--AL1
			
			if ROM_Din(11) = '1' then
				REG_A_CMD 	<= "100";
				--AR1
			end if;
			if ROM_Din(12) = '1' then
				REG_A_CMD 	<= "101";
			end if;	
			
			--CL1
			
			if ROM_Din(13) = '1' then
				REG_A_CMD 	<= "110";
			end if;	
			
			--CR1
			
			if ROM_Din(14) = '1' then
				REG_A_CMD 	<= "111";
			end if;	
			--REG B Operacijos:		
			
			if ROM_Din(15) = '1' then	 --Duomenu ikrovimas is Din	
				REG_B_CMD 	<= "001";		
			end if; 
			
			--LL1
			
			if ROM_Din(16) = '1' then
				REG_B_CMD 	<= "010";
			end if;	
			--LR1
			
			if ROM_Din(17) = '1' then
				REG_B_CMD 	<= "011";
			end if;	
			--AL1
			
			if ROM_Din(18) = '1' then
				REG_B_CMD 	<= "100";
			end if;	
			--LR1
			
			if ROM_Din(19) = '1' then
				REG_B_CMD 	<= "101";
			end if;	
			--CL1
			
			if ROM_Din(20) = '1' then
				REG_B_CMD 	<= "110";
			end if;	
			--CR1
			
			if ROM_Din(21) = '1' then
				REG_B_CMD 	<= "111";
			end if;	
			
			--REG C Operacijos:		
			
			if ROM_Din(22) = '1' then	  --Duomenu ikrovimas is Din	
				REG_C_CMD 	<= "001";
			end if;
			
			--LL1
			
			if ROM_Din(23) = '1' then
				REG_C_CMD 	<= "010";
			end if;	
			--LR1
			
			if ROM_Din(24) = '1' then
				REG_C_CMD 	<= "011";
			end if;	
			--AL1
			
			if ROM_Din(25) = '1' then
				REG_C_CMD 	<= "100";
			end if;	
			--LR1
			
			if ROM_Din(26) = '1' then
				REG_C_CMD 	<= "101";
			end if;	
			--CL1
			
			if ROM_Din(27) = '1' then
				REG_C_CMD 	<= "110";
			end if;	
			--CR1
			
			
			if ROM_Din(28) = '1' then
				REG_C_CMD 	<= "111";
			end if;	
			
			--REG D Operacijos:		
			
			if ROM_Din(29) = '1' then	  --Duomenu ikrovimas	
				REG_D_CMD 	<= "001";
			end if;	
			
			--LL1
			
			if ROM_Din(30) = '1' then
				REG_D_CMD 	<= "010";
			end if;	
			--LR1
			
			if ROM_Din(31) = '1' then
				REG_D_CMD 	<= "011";
			end if;	
			--AL1
			
			if ROM_Din(32) = '1' then
				REG_D_CMD 	<= "100";
				
				--LR1
			end if;
			if ROM_Din(33) = '1' then
				REG_D_CMD 	<= "101";
			end if;	
			--CL1
			
			if ROM_Din(34) = '1' then
				REG_D_CMD 	<= "110";
				
				--CR1
			end if;
			if ROM_Din(35) = '1' then
				REG_D_CMD 	<= "111";
			end if;	
			
			
			--REG E Operacijos:		
			
			if ROM_Din(36) = '1' then
				REG_E_CMD 	<= "001";	--Duomenu ikrovimas	
			end if;
			
			--LL1
			
			if ROM_Din(37) = '1' then
				REG_E_CMD 	<= "010";
			end if;	
			--LR1
			
			
			if ROM_Din(38) = '1' then
				REG_E_CMD 	<= "011";
			end if;	
			--AL1
			
			if ROM_Din(39) = '1' then
				REG_E_CMD 	<= "100";
			end if;	
			--LR1
			
			if ROM_Din(40) = '1' then
				REG_E_CMD 	<= "101";
			end if;	
			--CL1
			
			if ROM_Din(41) = '1' then
				REG_E_CMD 	<= "110";
			end if;	
			--CR1
			
			if ROM_Din(42) = '1' then
				REG_E_CMD 	<= "111";
			end if;	
			
			
			--REG F Operacijos:		
			
			if ROM_Din(43) = '1' then	
				REG_F_CMD 	<= "001";  --Duomenu ikrovimas
				--LL1
			end if;
			if ROM_Din(44) = '1' then
				REG_F_CMD 	<= "010";
			end if;	
			--LR1
			
			if ROM_Din(45) = '1' then
				REG_F_CMD 	<= "011";
			end if;	
			--AL1
			
			if ROM_Din(46) = '1' then
				REG_F_CMD 	<= "100";
			end if;	
			--LR1
			
			if ROM_Din(47) = '1' then
				REG_F_CMD 	<= "101";
			end if;	
			--CL1
			
			if ROM_Din(48) = '1' then
				REG_F_CMD 	<= "110";
			end if;	
			--CR1
			
			if ROM_Din(49) = '1' then
				REG_F_CMD 	<= "111";
			end if;	
			
			--ALU operacijos
			--Duomenu ikrovimas	
			--ALU_M = ALU_L + ALU_R
			
			if ROM_Din(50) = '1' then	
				ALU_CMD 	<= "00000";
			end if;	 
			--ALU_M = NOT ALU_L 
			
			if ROM_Din(51) = '1' then	
				ALU_CMD 	<= "00001";		
			end if;	
			
			--ALU_M = NOT ALU_R
			if ROM_Din(52) = '1' then	
				ALU_CMD 	<= "00010";			
			end if;	
			
			--ALU_M = NOT ALU_L + 1
			if ROM_Din(53) = '1' then	
				ALU_CMD 	<= "00011";				
			end if;
			
			--ALU_M = NOT ALU_L - 1
			if ROM_Din(54) = '1' then	
				ALU_CMD 	<= "00100";			
			end if;
			
			--ALU_M = NOT ALU_R + 1
			if ROM_Din(55) = '1' then	
				ALU_CMD 	<= "00101";	 		
			end if;	
			
			--ALU_M = NOT ALU_R - 1
			if ROM_Din(56) = '1' then	
				ALU_CMD 	<= "00110";			
			end if;
			
			--ALU_M = NOT ALU_L xor ALU_R
			if ROM_Din(57) = '1' then	
				ALU_CMD 	<= "00111";			
			end if;	
			
			--Komponentu individualus resetai
			
			if ROM_Din(58) = '1' then --Reset REG_A
				RST_COMP(0) <= '1'; 
			end if;
			if ROM_Din(59) = '1' then --Reset REG_B	 
				RST_COMP(1) <= '1'; 
			end if;
			if ROM_Din(60) = '1' then --Reset REG_C	 
				RST_COMP(2) <= '1';
			end if;
			if ROM_Din(61) = '1' then --Reset REG_D	 
				RST_COMP(3) <= '1';
			end if;
			if ROM_Din(62) = '1' then --Reset REG_E	 
				RST_COMP(4) <= '1';
			end if;
			if ROM_Din(63) = '1' then --Reset REG_F	 
				RST_COMP(5) <= '1';
			end if;
			if ROM_Din(64) = '1' then --Reset CNT	 
				RST_COMP(6) <= '1';
			end if;
			if ROM_Din(65) = '1' then --Reset ROM ADDR	 
				RST_COMP(7) <= '1';
			end if;		
			if ROM_Din(66) = '1' then --Reset FLAG	 
				RST_COMP(8) <= '1';
			end if;
			
			if ROM_Din(67) = '1' then --Skaitliuko pabaiga
				CTRL_Dout <= Din;	 	  --Duomenu isvedimas
				Done <= '1';		  --Pabaigos signalas  	
			end if;	 
			
			if ROM_Din(68) = '1' then --Mazinamas skaitliukas
				CNT_CMD 	<= '1';	 				
			end if;	
			
			if unsigned(ROM_Din(69 to 72)) > "0" then --Tikrinama ar yra logine salyga
				
				if FlagV(to_integer(unsigned(ROM_Din(69 to 72)))) = '1' then							-- Jei ar logine salyga buvo patenkinta,
					Logic_Jump <= '1';
					ROM_CMD <= std_logic_vector(unsigned(ROM_Din(73 to 80))+"1");		-- Pateiktas instrukcijoje inkrementuojamas
					N_ADDR <= unsigned(ROM_Din(73 to 80)) + "1";
					
				else
					Logic_Jump <= '0';
					ROM_CMD <= std_logic_vector(unsigned(ROM_Din(73 to 80)));		-- Jei logine salyga nepatenkinta, adresas nekeiciamas 
					N_ADDR <= unsigned(ROM_Din(73 to 80)); 
				end if;
			else
				Logic_Jump <= '0';
				ROM_CMD <= std_logic_vector(unsigned(ROM_Din(73 to 80)));		-- Jei logines salygos nebuvo, adresas nekeiciamas 
				N_ADDR <= unsigned(ROM_Din(73 to 80));
				
			end if;	
			
			
			
			
			
		end if;	
	end process; 
	
end rtl;