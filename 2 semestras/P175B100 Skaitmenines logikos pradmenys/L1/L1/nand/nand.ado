setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/nand/nand.adf"]} { 
	design create nand "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/nand"
cd "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1"
designverincludedir -clear
designverlibrarysim -PL -clear
designverlibrarysim -L -clear
designverlibrarysim -PL pmi_work
designverlibrarysim ovi_xp2
designverdefinemacro -clear
if {$newDesign == 0} { 
  removefile -Y -D *
}
addfile "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/shema.vhd"
addfile "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/nand.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/nand/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/shema.vhd"
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/nand.vhd"
entity SHEMA
vsim  +access +r SHEMA   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns