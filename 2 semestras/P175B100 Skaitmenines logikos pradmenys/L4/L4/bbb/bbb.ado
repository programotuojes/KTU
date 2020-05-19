setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/bbb/bbb.adf"]} { 
	design create bbb "C:/Users/Gustas/Documents/KTU/Logika/L4/L4"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/bbb"
cd "C:/Users/Gustas/Documents/KTU/Logika/L4/L4"
designverincludedir -clear
designverlibrarysim -PL -clear
designverlibrarysim -L -clear
designverlibrarysim -PL pmi_work
designverlibrarysim ovi_xp2
designverdefinemacro -clear
if {$newDesign == 0} { 
  removefile -Y -D *
}
addfile "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M3.vhd"
addfile "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M2.vhd"
addfile "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M1.vhd"
addfile "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/JM2.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/bbb/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M3.vhd"
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M2.vhd"
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/M1.vhd"
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/L4/L4/JM2.vhd"
entity TOP_CNT
vsim  +access +r TOP_CNT   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns
