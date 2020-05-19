SetActiveLib -work
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L4\L4\M3.vhd" 
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L4\L4\M2.vhd" 
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L4\L4\M1.vhd" 
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L4\L4\JM2.vhd" 
comp -include "$dsn\src\TestBench\top_cnt_TB.vhd" 
asim +access +r TESTBENCH_FOR_top_cnt 
wave 
wave -noreg CLK_TOP
wave -noreg RST_TOP
wave -noreg Enable_TOP
wave -noreg Pernasa_TOP
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\top_cnt_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_top_cnt 
