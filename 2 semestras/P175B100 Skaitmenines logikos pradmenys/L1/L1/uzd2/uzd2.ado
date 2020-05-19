setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/uzd2/uzd2.adf"]} { 
	design create uzd2 "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/uzd2"
cd "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1"
designverincludedir -clear
designverlibrarysim -PL -clear
designverlibrarysim -L -clear
designverlibrarysim -PL pmi_work
designverlibrarysim ovi_xp2
designverdefinemacro -clear
if {$newDesign == 0} { 
  removefile -Y -D *
}
addfile "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/impl1/uzd2.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/uzd2/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/impl1/uzd2.vhd"
entity UZD2
vsim  +access +r UZD2   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns
