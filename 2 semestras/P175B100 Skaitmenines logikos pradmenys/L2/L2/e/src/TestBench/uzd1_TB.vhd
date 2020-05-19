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
		x2 : in STD_LOGIC;
		x3 : in STD_LOGIC;
		x4 : in STD_LOGIC;
		Statinis : out STD_LOGIC;
		C : in STD_LOGIC;
		Reset : in STD_LOGIC;
		Dinaminis : out STD_LOGIC;
		Dvipakopis : out STD_LOGIC );
	end component;

	-- Stimulus signals - signals mapped to the input and inout ports of tested entity
	signal x2 : STD_LOGIC;
	signal x3 : STD_LOGIC;
	signal x4 : STD_LOGIC;
	signal C : STD_LOGIC;
	signal Reset : STD_LOGIC;
	-- Observed signals - signals mapped to the output ports of tested entity
	signal Statinis : STD_LOGIC;
	signal Dinaminis : STD_LOGIC;
	signal Dvipakopis : STD_LOGIC;

	-- Add your code here ...

begin

	-- Unit Under Test port map
	UUT : uzd1
		port map (
			x2 => x2,
			x3 => x3,
			x4 => x4,
			Statinis => Statinis,
			C => C,
			Reset => Reset,
			Dinaminis => Dinaminis,
			Dvipakopis => Dvipakopis
		);

	-- Add your stimulus here ...
	Reset_proc: process begin
		Reset <= '0'; wait for 3 ns;
		Reset <= '1'; wait;
	end process; 
	
	Clock_proc: process begin
		C <= '0'; wait for 10 ns;
		C <= '1'; wait for 10 ns;
	end process; 
	
	SR_proc: process begin
		x2 <= '1'; x3 <= '0'; x4 <= '1'; wait for 15 ns;	-- Save
		x2 <= '1'; x3 <= '1'; x4 <= '1'; wait for 20 ns;	-- Set 1
		x2 <= '1'; x3 <= '0'; x4 <= '1'; wait for 10 ns;	-- Save
		x2 <= '0'; x3 <= '1'; x4 <= '1'; wait for 20 ns;	-- Set 0
		x2 <= '1'; x3 <= '0'; x4 <= '1'; wait for 10 ns;	-- Save	
		x2 <= '1'; x3 <= '1'; x4 <= '1'; wait for 20 ns;	-- Set 1
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

