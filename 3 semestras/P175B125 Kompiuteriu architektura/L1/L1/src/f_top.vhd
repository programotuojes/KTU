
--KTU 2015
--Informatikos fakultetas
--Kompiuteriu katedra
--Kompiuteriu Architektura [P175B125] 
--Kazimieras Bagdonas


library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;


entity TOP is port ( 							--Testavimo sistemos ivestys ir isvestys
		CLK 				: in std_logic;
		RST 				: in std_logic;
		Din					: in std_logic_vector(15 downto 0);
		
		MAIN_Dout			: out std_logic_vector(15 downto 0);
		S_Done				: out std_logic
		);
end TOP;  
-----------------------------------------------------------------------------------------------------------
--------------------------------Testavimo sistemos komponentu strukturos-----------------------------------
-----------------------------------------------------------------------------------------------------------

architecture struct of top is 					
	
	component CTRL is port (  
			
			--Signalai
			CLK 			: in std_logic;
			RST 			: in std_logic;
			
			--Duomenys IN
			ROM_Din 		: in std_logic_vector(1 to 80);
			Din 			: in std_logic_vector(15 downto 0);
			
			--Flagai
			FlagV			: in std_logic_vector(1 to 18); --
			
			--Kontroles signalai
			
			CNT_CMD			: out std_logic;
			MUX_CMD			: out std_logic_vector(3 downto 0);
			ALU_CMD			: out std_logic_vector(4 downto 0);
			ROM_CMD			: out std_logic_vector(7 downto 0);
			CMD				: out std_logic_vector(7 downto 0);	 
			
			
			REG_A_CMD 		: out std_logic_vector(2 downto 0);
			REG_B_CMD 		: out std_logic_vector(2 downto 0);
			REG_C_CMD 		: out std_logic_vector(2 downto 0);
			REG_D_CMD 		: out std_logic_vector(2 downto 0);
			REG_E_CMD 		: out std_logic_vector(2 downto 0);
			REG_F_CMD 		: out std_logic_vector(2 downto 0);
			
			
			
			--Reseto signalai
			RST_COMP 		:out std_logic_vector(8 downto 0);
			
			--Duomenys OUT
			Done			: out std_logic;
			CTRL_Dout 		: out std_logic_vector(15 downto 0)
			
			
			);
	end component;	
	
	component ALU_s is port ( 
			
			ALU_CMD  		: in std_logic_vector(4 downto 0); -- Komanda	
			ALU_Din_L 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			ALU_Din_R 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			
			FLG_ALU_CMD		: out std_logic_vector(4 downto 0); --Instrukcijos FLAG 
			ALU_Dout 		: out std_logic_vector(15 downto 0) -- Rezultato isvestis	 
			);
	end component;	
	
	
	component FLG is port (
			CLK 			: in std_logic;
			RST 			: in std_logic;
			Xin  			: in std_logic_vector(1 to 18);
			
			FLG_Dout 		: out std_logic_vector(1 to 18)
			);
	end component;	
	
	
	
	
	component REG is port (	 
			CLK 			: in std_logic;
			RST 			: in std_logic; -- Reset signalas
			REG_Din 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			REG_CMD  		: in std_logic_vector(2 downto 0); -- Komanda
			
			REG_FLAG_H		: out std_logic; -- Neigiamo skaiciaus indikatorius 
			REG_FLAG_L		: out std_logic; -- Zemiausios skilties indikatorius
			REG_Dout 		: out std_logic_vector(15 downto 0) -- Rezultato isvestis
			);
	end component;	
	
	component MUX is port (
			MUX_CMD  		: in std_logic_vector(3 downto 0); -- Komanda
			MUX_Din0 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din1 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din2 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din3 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din4 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din5 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din6 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			MUX_Din7 		: in std_logic_vector(15 downto 0); -- Duomenu ivestis
			
			
			MUX_Dout 		: out std_logic_vector(15 downto 0) -- Rezultato isvestis
			);
	end component;	
	
	component CNT is port (	 
			CLK 			: in std_logic;
			RST 			: in std_logic; -- Reset signalas
			CNT_CMD  		: in std_logic; -- Komanda	
			CNT_Flag 		: out std_logic --Kai pasiekia 0
			
			);
	end component;	
	
	component ROM is port (	
			RST_ROM 		: in std_logic;
			ROM_CMD 		: in std_logic_vector(7 downto 0);	  
			ROM_Dout 		: out std_logic_vector(1 to 80)
			);
	end component;	
	
	
	
	-----------------------------------------------------------------------------------------------------------
	--------------------------------Testavimo sistemos komponentu sarysiai-------------------------------------
	-----------------------------------------------------------------------------------------------------------
	
	
	
	
	
	
	--Kontroles signalai
	signal sCNT_CMD			:  std_logic;
	signal sALU_CMD			:  std_logic_vector(4 downto 0);
	signal sFLG_CMD 		:  std_logic_vector(15 downto 0);
	signal sROM_CMD			:  std_logic_vector(7 downto 0);
	signal sCMD				:  std_logic_vector(7 downto 0); 
	signal sRST_REG_A, sRST_REG_B, sRST_REG_C, sRST_REG_D, sRST_REG_E, sRST_REG_F, sRST_ROM, sRST_CNT, sRST_FLG : std_logic;
	
	signal sREG_A_CMD, sREG_B_CMD, sREG_C_CMD, sREG_D_CMD, sREG_E_CMD, sREG_F_CMD			 		: std_logic_vector(2 downto 0);	 
	signal sMUX_A_CMD		: std_logic_vector(3 downto 0);	 
	
	--Duomenu magistrales
	
	signal sData 			: std_logic_vector(15 downto 0);
	signal sALU_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_A_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_B_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_C_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_D_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_E_Dout 		: std_logic_vector(15 downto 0);
	signal sREG_F_Dout 		: std_logic_vector(15 downto 0);
	signal s_CTRL_Data	    : std_logic_vector(15 downto 0);
	signal sROM_Dout		:  std_logic_vector(1 to 80); 
	
	--Flag signalai
	signal s_C_Flag 		: std_logic;
	signal s_ALU_Flags 		: std_logic_vector(0 to 4);
	signal sFLG_Din			: std_logic_vector(1 to 18);	-- Flag registro ivestis
	signal sFLG_Dout		: std_logic_vector(1 to 18);    -- Flag registro isvestys
	
	
	
begin
	
	CTRL1: CTRL port map ( 	
		
		
		
		CLK 			=> CLK, 
		RST 			=> RST,
		
		Din 			=> sData,
		ROM_Din 		=> sROM_Dout,
		
		CTRL_Dout 		=> MAIN_Dout, 
		Done			=> S_Done,
		ALU_CMD 		=> sALU_CMD,
		CNT_CMD 		=> sCNT_CMD,
		ROM_CMD 		=> sROM_CMD,
		MUX_CMD 		=> sMUX_A_CMD,
		CMD 			=> sCMD, 
		
		--Registru kontrole
		REG_A_CMD 		=> 	sREG_A_CMD,
		REG_B_CMD 		=>  sREG_B_CMD,
		REG_C_CMD 		=>  sREG_C_CMD,
		REG_D_CMD 		=>  sREG_D_CMD,
		REG_E_CMD 		=> 	sREG_E_CMD,
		REG_F_CMD 		=> 	sREG_F_CMD,
		
		
		FlagV 			=> sFLG_Dout, 
		
		
		RST_COMP(0) 	=> sRST_REG_A, 
		RST_COMP(1) 	=> sRST_REG_B, 
		RST_COMP(2) 	=> sRST_REG_C, 
		RST_COMP(3) 	=> sRST_REG_D, 
		RST_COMP(4) 	=> sRST_REG_E, 
		RST_COMP(5) 	=> sRST_REG_F, 
		RST_COMP(6) 	=> sRST_CNT,
		RST_COMP(7) 	=> sRST_ROM, 
		RST_COMP(8)		=> sRST_FLG
		
		
		
		);
	
	MUXA: MUX port map ( --+
		MUX_CMD 		=> sMUX_A_CMD,
		MUX_Din0 		=> Din,
		MUX_Din1 		=> Din,
		MUX_Din2 		=> sREG_A_Dout,
		MUX_Din3 		=> sREG_B_Dout,
		MUX_Din4 		=> sREG_C_Dout,
		MUX_Din5 		=> sREG_D_Dout,
		MUX_Din6 		=> sREG_E_Dout,
		MUX_Din7 		=> sREG_F_Dout,
		MUX_Dout 		=> sData
		);
	
	ALU1: ALU_s port map ( 
		
		ALU_Din_L 		=> sData,
		ALU_Din_R		=> sREG_A_Dout, -- ALU_R iejimas prijungtas tiesiogiai prie REG_A isejimo
		ALU_CMD 		=> sALU_CMD,
		ALU_Dout 		=> sALU_Dout,
		FLG_ALU_CMD		=> sFLG_Din(14 to 18)
		);
	
	ROM1: ROM port map ( 
		RST_ROM 		=> sRST_ROM,
		ROM_CMD 		=> sROM_CMD,
		ROM_Dout 		=> sROM_Dout
		);
	
	CNT1: CNT port map ( 
		CLK 			=> CLK, 
		RST 			=> sRST_CNT,
		CNT_CMD 		=> sCNT_CMD,
		CNT_Flag 		=> sFLG_Din(13)
		);
	
	REG_A: REG port map(  
		CLK 			=> CLK, 
		RST 			=> sRST_REG_A,
		REG_Din 		=> sALU_Dout, --REG A iejimas prijungtas tiesiogiai prie ALU isejimo
		REG_CMD 		=> sREG_A_CMD,				  
		
		
		
		REG_FLAG_H		=> sFLG_Din(1), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(2), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_A_Dout
		);
	
	REG_B: REG port map( 
		CLK 			=> CLK, 
		RST 			=> sRST_REG_B,
		REG_Din 		=> sData,
		REG_CMD 		=> sREG_B_CMD,
		
		REG_FLAG_H		=> sFLG_Din(3), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(4), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_B_Dout
		);
	
	REG_C: REG port map(
		CLK 			=> CLK, 
		RST 			=> sRST_REG_C,
		REG_Din 		=> sData,
		REG_CMD 		=> sREG_C_CMD,
		
		REG_FLAG_H		=> sFLG_Din(5), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(6), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_C_Dout
		);
	
	REG_D: REG port map(   
		CLK 			=> CLK, 
		RST 			=> sRST_REG_D,
		REG_Din 		=> sData,
		REG_CMD 		=> sREG_D_CMD,
		
		REG_FLAG_H		=> sFLG_Din(7), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(8), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_D_Dout
		);	
	
	REG_E: REG port map( 
		CLK 			=> CLK, 
		RST 			=> sRST_REG_E,
		REG_Din 		=> sData,
		REG_CMD 		=> sREG_E_CMD,
		
		REG_FLAG_H		=> sFLG_Din(9), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(10), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_E_Dout
		);	
	
	REG_F: REG port map(	 
		CLK 			=> CLK, 
		RST 			=> sRST_REG_F,
		REG_Din 		=> sData,
		REG_CMD 		=> sREG_F_CMD,
		
		REG_FLAG_H		=> sFLG_Din(11), -- Neigiamo skaiciaus indikatorius 
		REG_FLAG_L		=> sFLG_Din(12), -- Zemiausios skilties indikatorius	
		REG_Dout 		=> sREG_F_Dout
		);
	
	FLG_A: FLG port map( 
		
		CLK 			=> CLK, 
		RST 			=>	sRST_FLG,
		Xin  			=>	sFLG_Din,
		
		FLG_Dout 		=>	sFLG_Dout
		);
	
	
end struct;
