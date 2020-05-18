library ieee;
use ieee.NUMERIC_STD.all;
use ieee.std_logic_1164.all;

	-- Add your library and packages declaration here ...

entity top_cnt_tb is
end top_cnt_tb;

architecture TB_ARCHITECTURE of top_cnt_tb is
	-- Component declaration of the tested unit
	component top_cnt
	port(
		CLK_I : in STD_LOGIC;
		RST_I : in STD_LOGIC;
		ENBL_I : in STD_LOGIC;
		CNT_CO : out STD_LOGIC );
	end component;

	-- Stimulus signals - signals mapped to the input and inout ports of tested entity
	signal CLK_I : STD_LOGIC;
	signal RST_I : STD_LOGIC;
	signal ENBL_I : STD_LOGIC;
	-- Observed signals - signals mapped to the output ports of tested entity
	signal CNT_CO : STD_LOGIC;

	-- Add your code here ...

begin

	-- Unit Under Test port map
	UUT : top_cnt
		port map (
			CLK_I => CLK_I,
			RST_I => RST_I,
			ENBL_I => ENBL_I,
			CNT_CO => CNT_CO
		);

	-- Add your stimulus here ...
	clock_proc:process  begin--sinchrosignala valdantis  procesas
		CLK_I  <='0';
		wait  for 1 ns;
		CLK_I  <='1';
		wait  for 1 ns;
	end  process;	 
	
	reset_process:process  begin--isorini reset signala valdantis  procesas
		RST_I  <='1';
		wait  for 1 ns;
		RST_I  <='0';
		wait for 1100 ns;	 
		RST_I  <='1';
		wait  for 1 ns;
		RST_I  <='0';
		wait;
	end  process;	
	
	enbl_process:process  begin--ijungimo signala valdantis  procesas
		ENBL_I  <='0';
		wait  for 10 ns;
		ENBL_I  <='1';
		wait for 1700 ns;  
		ENBL_I  <='0';
		wait  for 10 ns;
		ENBL_I  <='1';
		assert false report " Pabaiga " severity failure; 	
	end  process;
	

end TB_ARCHITECTURE;

configuration TESTBENCH_FOR_top_cnt of top_cnt_tb is
	for TB_ARCHITECTURE
		for UUT : top_cnt
			use entity work.top_cnt(struct);
		end for;
	end for;
end TESTBENCH_FOR_top_cnt;

