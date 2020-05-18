SetActiveLib -work
comp -include "C:\Users\Gustas\Documents\KTU\Logika\L3\L3\impl1\uzd1.vhd" 
comp -include "$dsn\src\TestBench\uzd1_TB.vhd" 
asim +access +r TESTBENCH_FOR_uzd1 
wave 
wave -noreg RST
wave -noreg D7
wave -noreg D6
wave -noreg D5
wave -noreg D4
wave -noreg D3
wave -noreg D2
wave -noreg D1
wave -noreg D0

wave -noreg A0
wave -noreg A1

wave -noreg CLK

wave -noreg Q7
wave -noreg Q6
wave -noreg Q5
wave -noreg Q4
wave -noreg Q3
wave -noreg Q2
wave -noreg Q1
wave -noreg Q0
wave -noreg DR
wave -noreg DL
# The following lines can be used for timing simulation
# acom <backannotated_vhdl_file_name>
# comp -include "$dsn\src\TestBench\uzd1_TB_tim_cfg.vhd" 
# asim +access +r TIMING_FOR_uzd1 
