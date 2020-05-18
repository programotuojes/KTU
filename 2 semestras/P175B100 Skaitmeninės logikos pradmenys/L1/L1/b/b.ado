setenv SIM_WORKING_FOLDER .
set newDesign 0
if {![file exists "C:/Users/Gustas/Documents/KTU/Logika/L1/L1/b/b.adf"]} { 
	design create b "C:/Users/Gustas/Documents/KTU/Logika/L1/L1"
  set newDesign 1
}
design open "C:/Users/Gustas/Documents/KTU/Logika/L1/L1/b"
cd "C:/Users/Gustas/Documents/KTU/Logika/L1/L1"
designverincludedir -clear
designverlibrarysim -PL -clear
designverlibrarysim -L -clear
designverlibrarysim -PL pmi_work
designverlibrarysim ovi_xp2
designverdefinemacro -clear
if {$newDesign == 0} { 
  removefile -Y -D *
}
addfile "C:/Users/Gustas/Documents/KTU/Logika/L1/L1/impl1/shema.vhd"
vlib "C:/Users/Gustas/Documents/KTU/Logika/L1/L1/b/work"
set worklib work
adel -all
vcom -dbg -work work "C:/Users/Gustas/Documents/KTU/Logika/L1/L1/impl1/shema.vhd"
entity SHEMA
vsim  +access +r SHEMA   -PL pmi_work -L ovi_xp2
add wave *
run 1000ns
