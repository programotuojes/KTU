SetActiveLib -work
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L1\L1\impl1\uzd3.vhd" 
comp -include "$dsn\src\TestBench\uzd3_TB.vhd" 
asim +access +r TESTBENCH_FOR_uzd3 
wave 
wave -noreg x2
wave -noreg x1
wave -noreg x3
wave -noreg x4
wave -noreg x5
wave -noreg x6
wave -noreg y
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\uzd3_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_uzd3 