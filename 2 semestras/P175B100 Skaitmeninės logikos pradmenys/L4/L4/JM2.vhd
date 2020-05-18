library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity TOP_CNT is port (
	CLK_TOP : in std_logic;			-- Sinchro signalas
	RST_TOP : in std_logic;			-- Reset signalas
	Enable_TOP : in std_logic;		-- Aktyvavimo signalas
	Pernasa_TOP : out std_logic);	-- Pernasa
end TOP_CNT;

architecture struct of TOP_CNT is
signal C, RST_internal, C1, C2, C3 : std_logic;
signal CNT_1_O : std_logic_vector (2 downto 0);
signal CNT_2_O : std_logic_vector (5 downto 0);
signal CNT_3_O : std_logic_vector (5 downto 0);

component M1 port (
		CLK : in std_logic;		-- Sinchro signalas
		RST : in std_logic;		-- Reset signalas
		CNT_CMD : in std_logic;	-- Aktyvavimo signalas
		CNT_C : out std_logic;	-- Pernasa
		CNT_O : out std_logic_vector (2 downto 0));
end component;

component M2 port (
		CLK : in std_logic;		-- Sinchro signalas
		RST : in std_logic;		-- Reset signalas
		CNT_CMD : in std_logic;	-- Aktyvavimo signalas
		CNT_C : out std_logic;	-- Pernasa
		CNT_O : out std_logic_vector (5 downto 0));
end component;

component M3 port (
		CLK : in std_logic;		-- Sinchro signalas
		RST : in std_logic;		-- Reset signalas
		CNT_CMD : in std_logic;	-- Aktyvavimo signalas
		CNT_C : out std_logic;	-- Pernasa
		CNT_O : out std_logic_vector (5 downto 0));
end component;

begin
	CNT_1 : M1 port map (CLK => CLK_TOP,
		RST => RST_internal, CNT_CMD => Enable_TOP,
		CNT_C => C1, CNT_O => CNT_1_O);
	CNT_2 : M2 port map (CLK => C1,
		RST => RST_internal, CNT_CMD => Enable_TOP,
		CNT_C => C2, CNT_O => CNT_2_O);
	CNT_3 : M3 port map (CLK => C2,
		RST => RST_internal, CNT_CMD => Enable_TOP,
		CNT_C => C3, CNT_O => CNT_3_O);
		
	process (CLK_TOP , RST_TOP)
	begin
		if (RST_TOP = '1') then
			RST_internal <= '1';
		elsif CLK_TOP'event and CLK_TOP = '1' then
		
			-- Stabdymas pasiekus JM2 verte
			if ((CNT_1_O = "000")
			and (CNT_2_O = "011110")
			and (CNT_3_O = "001111")) then
				RST_internal <= '1';
				Pernasa_TOP <= '1';
			else
				RST_internal <= '0';
				Pernasa_TOP <= '0';
			end if;
		end if;
	end process;
end struct;