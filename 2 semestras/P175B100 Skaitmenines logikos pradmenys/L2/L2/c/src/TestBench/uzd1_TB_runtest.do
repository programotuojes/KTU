SetActiveLib -work
comp -include "C:\Users\Gustas\Documents\KTU\Logika\impl1\uzd1.vhd" 
comp -include "$dsn\src\TestBench\uzd1_TB.vhd" 
asim +access +r TESTBENCH_FOR_uzd1 
wave 
wave -noreg x2
wave -noreg x3
wave -noreg x4
wave -noreg x1
wave -noreg Reset
wave -noreg Statinis	
wave -noreg UUT/S
wave -noreg UUT/R
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\uzd1_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_uzd1 
