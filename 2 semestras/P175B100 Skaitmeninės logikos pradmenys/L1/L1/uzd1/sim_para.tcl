lappend auto_path "C:/Program Files/lscc/diamond/3.10_x64/data/script"
package require simulation_generation
set ::bali::simulation::Para(PROJECT) {uzd1}
set ::bali::simulation::Para(PROJECTPATH) {C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1}
set ::bali::simulation::Para(FILELIST) {"C:/Users/Gustas/Documents/KTU/Skaitmenine logika/L1/L1/impl1/shema.vhd" }
set ::bali::simulation::Para(GLBINCLIST) {}
set ::bali::simulation::Para(INCLIST) {"none"}
set ::bali::simulation::Para(WORKLIBLIST) {"work" }
set ::bali::simulation::Para(COMPLIST) {"VHDL" }
set ::bali::simulation::Para(SIMLIBLIST) {pmi_work ovi_xp2}
set ::bali::simulation::Para(MACROLIST) {}
set ::bali::simulation::Para(SIMULATIONTOPMODULE) {SHEMA}
set ::bali::simulation::Para(SIMULATIONINSTANCE) {}
set ::bali::simulation::Para(LANGUAGE) {VHDL}
set ::bali::simulation::Para(SDFPATH)  {}
set ::bali::simulation::Para(ADDTOPLEVELSIGNALSTOWAVEFORM)  {1}
set ::bali::simulation::Para(RUNSIMULATION)  {1}
set ::bali::simulation::Para(HDLPARAMETERS) {}
set ::bali::simulation::Para(POJO2LIBREFRESH)    {}
set ::bali::simulation::Para(POJO2MODELSIMLIB)   {}
::bali::simulation::ActiveHDL_Run
