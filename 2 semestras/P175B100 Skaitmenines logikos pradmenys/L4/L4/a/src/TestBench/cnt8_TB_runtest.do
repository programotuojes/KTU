SetActiveLib -work
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L4\L4\M1.vhd" 
comp -include "$dsn\src\TestBench\cnt8_TB.vhd" 
asim +access +r TESTBENCH_FOR_cnt8 
wave 
wave -noreg CLK
wave -noreg RST
wave -noreg CNT_CMD
wave -noreg CNT_C
wave -noreg CNT_O
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\cnt8_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_cnt8 
