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
		CLK_TOP : in STD_LOGIC;
		RST_TOP : in STD_LOGIC;
		Enable_TOP : in STD_LOGIC;
		Pernasa_TOP : out STD_LOGIC );
	end component;

	-- Stimulus signals - signals mapped to the input and inout ports of tested entity
	signal CLK_TOP : STD_LOGIC;
	signal RST_TOP : STD_LOGIC;
	signal Enable_TOP : STD_LOGIC;
	-- Observed signals - signals mapped to the output ports of tested entity
	signal Pernasa_TOP : STD_LOGIC;

	-- Add your code here ...

begin

	-- Unit Under Test port map
	UUT : top_cnt
		port map (
			CLK_TOP => CLK_TOP,
			RST_TOP => RST_TOP,
			Enable_TOP => Enable_TOP,
			Pernasa_TOP => Pernasa_TOP
		);

	-- Add your stimulus here ...
	clock_proc : process begin
		CLK_TOP <= '0';
		wait for 1 ns;
		CLK_TOP <= '1';
		wait for 1 ns;
	end process;	 
	
	reset_process : process begin
		RST_TOP <= '1';
		wait for 1 ns;
		RST_TOP <= '0';
		wait for 10000 ns;	 
		RST_TOP <= '1';
		wait for 1 ns;
		RST_TOP <= '0';
		wait;
	end process;	
	
	enable_process : process begin
		Enable_TOP <= '0';
		wait for 2 ns;
		Enable_TOP <= '1';
		wait for 10000 ns;  
		Enable_TOP <= '0';
		wait for 10 ns;
		Enable_TOP <= '1';
		assert false report " Pabaiga " severity failure;	
	end process;

end TB_ARCHITECTURE;

configuration TESTBENCH_FOR_top_cnt of top_cnt_tb is
	for TB_ARCHITECTURE
		for UUT : top_cnt
			use entity work.top_cnt(struct);
		end for;
	end for;
end TESTBENCH_FOR_top_cnt;

