library ieee;
use ieee.std_logic_1164.all;
library xp2;
use xp2.components.all;

	-- Add your library and packages declaration here ...

entity uzd1_tb is
end uzd1_tb;

architecture TB_ARCHITECTURE of uzd1_tb is
	-- Component declaration of the tested unit
	component uzd1
	port(
		D7 : in STD_LOGIC;
		D6 : in STD_LOGIC;
		D5 : in STD_LOGIC;
		D4 : in STD_LOGIC;
		Q7 : out STD_LOGIC;
		Q6 : out STD_LOGIC;
		Q5 : out STD_LOGIC;
		Q4 : out STD_LOGIC;
		CLK : in STD_LOGIC;
		RST : in STD_LOGIC;
		A0 : in STD_LOGIC;
		A1 : in STD_LOGIC;
		D3 : in STD_LOGIC;
		Q3 : out STD_LOGIC;
		D2 : in STD_LOGIC;
		Q2 : out STD_LOGIC;
		D1 : in STD_LOGIC;
		Q1 : out STD_LOGIC;
		D0 : in STD_LOGIC;
		Q0 : out STD_LOGIC );
	end component;

	-- Stimulus signals - signals mapped to the input and inout ports of tested entity
	signal D7 : STD_LOGIC;
	signal D6 : STD_LOGIC;
	signal D5 : STD_LOGIC;
	signal D4 : STD_LOGIC;
	signal CLK : STD_LOGIC;
	signal RST : STD_LOGIC;
	signal A0 : STD_LOGIC;
	signal A1 : STD_LOGIC;
	signal D3 : STD_LOGIC;
	signal D2 : STD_LOGIC;
	signal D1 : STD_LOGIC;
	signal D0 : STD_LOGIC;
	-- Observed signals - signals mapped to the output ports of tested entity
	signal Q7 : STD_LOGIC;
	signal Q6 : STD_LOGIC;
	signal Q5 : STD_LOGIC;
	signal Q4 : STD_LOGIC;
	signal Q3 : STD_LOGIC;
	signal Q2 : STD_LOGIC;
	signal Q1 : STD_LOGIC;
	signal Q0 : STD_LOGIC;

	-- Add your code here ...

begin

	-- Unit Under Test port map
	UUT : uzd1
		port map (
			D7 => D7,
			D6 => D6,
			D5 => D5,
			D4 => D4,
			Q7 => Q7,
			Q6 => Q6,
			Q5 => Q5,
			Q4 => Q4,
			CLK => CLK,
			RST => RST,
			A0 => A0,
			A1 => A1,
			D3 => D3,
			Q3 => Q3,
			D2 => D2,
			Q2 => Q2,
			D1 => D1,
			Q1 => Q1,
			D0 => D0,
			Q0 => Q0
		);

	-- Add your stimulus here ...
	Reset_proc: process begin
		RST <= '1'; wait for 3 ns;
		RST <= '0'; wait;
	end process; 
	
	Clock_proc: process begin
		CLK <= '0'; wait for 10 ns;
		CLK <= '1'; wait for 10 ns;
	end process; 
	
	Reg_proc: process begin
		-- Input
		A0 <= '0'; A1 <= '0';
		D7 <= '1'; D6 <= '0'; D5 <= '0'; D4 <= '0'; D3 <= '0'; D2 <= '0'; D1 <= '1'; D0 <= '0';
		wait for 25 ns;
		
		-- LL2
		A0 <= '1'; A1 <= '0';
		wait for 80 ns;
		
		-- Input
		A0 <= '0'; A1 <= '0';
		wait for 20 ns;
		
		-- CL2
		A0 <= '0'; A1 <= '1';
		wait for 80 ns;
		
		-- AR1
		A0 <= '1'; A1 <= '1';
		wait for 140 ns;
		
		assert false report " Pabaiga " severity failure;
	end process;

end TB_ARCHITECTURE;

configuration TESTBENCH_FOR_uzd1 of uzd1_tb is
	for TB_ARCHITECTURE
		for UUT : uzd1
			use entity work.uzd1(schematic);
		end for;
	end for;
end TESTBENCH_FOR_uzd1;

