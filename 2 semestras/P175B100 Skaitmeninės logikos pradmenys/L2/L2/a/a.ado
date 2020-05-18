setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Logika/a/a.adf"]} { 
	design create a "C:/Users/Gustas/Documents/KTU/Logika"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Logika/a"
cd "C:/Users/Gustas/Documents/KTU/Logika"
designverincludedir -clear
designverlibrarysim -PL -clear
designverlibrarysim -L -clear
designverlibrarysim -PL pmi_work
designverlibrarysim ovi_xp2
designverdefinemacro -clear
if {$newDesign == 0} { 
  removefile -Y -D *
}
addfile "C:/Users/Gustas/Documents/KTU/Logika/impl1/uzd1.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Logika/a/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/impl1/uzd1.vhd"
entity UZD1
vsim  +access +r UZD1   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns
