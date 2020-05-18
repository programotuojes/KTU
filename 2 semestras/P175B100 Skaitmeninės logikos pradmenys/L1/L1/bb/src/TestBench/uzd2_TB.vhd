library ieee;
use ieee.std_logic_1164.all;
library xp2;
use xp2.components.all;

	-- Add your library and packages declaration here ...

entity uzd2_tb is
end uzd2_tb;

architecture TB_ARCHITECTURE of uzd2_tb is
	-- Component declaration of the tested unit
	component uzd2
	port(
		x1 : in STD_LOGIC;
		x2 : in STD_LOGIC;
		x3 : in STD_LOGIC;
		x4 : in STD_LOGIC;
		x5 : in STD_LOGIC;
		x6 : in STD_LOGIC;
		y : out STD_LOGIC );
	end component;

	-- Stimulus signals - signals mapped to the input and inout ports of tested entity
	signal x1 : STD_LOGIC;
	signal x2 : STD_LOGIC;
	signal x3 : STD_LOGIC;
	signal x4 : STD_LOGIC;
	signal x5 : STD_LOGIC;
	signal x6 : STD_LOGIC;
	-- Observed signals - signals mapped to the output ports of tested entity
	signal y : STD_LOGIC;

	-- Add your code here ...

begin

	-- Unit Under Test port map
	UUT : uzd2
		port map (
			x1 => x1,
			x2 => x2,
			x3 => x3,
			x4 => x4,
			x5 => x5,
			x6 => x6,
			y => y
		);

	-- Add your stimulus here ... 
	x6p: process
	begin
	x6 <= '0'; wait for 10 ns;
	x6 <= '1'; wait for 10 ns;
	end process ;
x5p: process
	begin
	x5 <= '0'; wait for 20 ns;
	x5 <= '1'; wait for 20 ns;
	end process ;
x4p: process
	begin
	x4 <= '0'; wait for 40 ns;
	x4 <= '1'; wait for 40 ns;
	end process ;
x3p: process
	begin
	x3 <= '0'; wait for 80 ns;
	x3 <= '1'; wait for 80 ns;
	end process ;
x2p: process
	begin
	x2 <= '0'; wait for 160 ns;
	x2 <= '1'; wait for 160 ns;
	end process ;
x1p: process
	begin
	x1 <= '0'; wait for 320 ns;
	x1 <= '1'; wait for 320 ns;
	assert false report " Pabaiga " severity failure ;
	end process ;


end TB_ARCHITECTURE;

configuration TESTBENCH_FOR_uzd2 of uzd2_tb is
	for TB_ARCHITECTURE
		for UUT : uzd2
			use entity work.uzd2(schematic);
		end for;
	end for;
end TESTBENCH_FOR_uzd2;

