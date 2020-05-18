SetActiveLib -work
comp -include "$dsn\src\f_top.vhd" 
comp -include "$dsn\src\TestBench\top_TB.vhd" 
asim +access +r TESTBENCH_FOR_top 
wave 
wave -noreg CLK
wave -noreg RST
wave -noreg Din
wave -noreg MAIN_Dout
wave -noreg S_Done 
wave /top_tb/UUT/sREG_A_Dout	 
wave /top_tb/UUT/sREG_B_Dout
wave /top_tb/UUT/sREG_C_Dout
wave /top_tb/UUT/sREG_D_Dout
wave /top_tb/UUT/sREG_E_Dout
wave /top_tb/UUT/sREG_F_Dout 
wave /top_tb/UUT/CTRL1/N_ADDR
wave /top_tb/UUT/CTRL1/C_ADDR
wave /top_tb/UUT/CTRL1/LS
wave /top_tb/UUT/CNT1/CNT_A 
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\top_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_top 
