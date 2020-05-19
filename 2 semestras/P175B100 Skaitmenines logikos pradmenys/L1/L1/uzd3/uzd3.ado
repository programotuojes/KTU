setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/uzd3/uzd3.adf"]} { 
	design create uzd3 "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/uzd3"
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
addfile "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/uzd3.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/uzd3/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/impl1/uzd3.vhd"
entity UZD3
vsim  +access +r UZD3   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns
