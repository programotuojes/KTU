-- VHDL model created from schematic uzd1.sch -- Apr 01 12:56:54 2019

library IEEE;
use IEEE.std_logic_1164.all;
library xp2;
use xp2.components.all;

entity UZD1 is
      Port (      x2 : In    std_logic;
                  x3 : In    std_logic;
                  x4 : In    std_logic;
             Statinis : Out   std_logic;
                   C : In    std_logic;
               Reset : In    std_logic;
             Dinaminis : Out   std_logic;
             Dvipakopis : Out   std_logic );

end UZD1;

architecture SCHEMATIC of UZD1 is

   SIGNAL gnd : std_logic := '0';
   SIGNAL vcc : std_logic := '1';

   signal     N_27 : std_logic;
   signal     N_28 : std_logic;
   signal Dvipakopis_DUMMY : std_logic;
   signal     N_10 : std_logic;
   signal     N_11 : std_logic;
   signal     N_12 : std_logic;
   signal     N_13 : std_logic;
   signal     N_14 : std_logic;
   signal     N_15 : std_logic;
   signal     N_16 : std_logic;
   signal     N_17 : std_logic;
   signal     N_18 : std_logic;
   signal     N_19 : std_logic;
   signal     N_20 : std_logic;
   signal Dinaminis_DUMMY : std_logic;
   signal     N_21 : std_logic;
   signal     N_22 : std_logic;
   signal     N_23 : std_logic;
   signal     N_24 : std_logic;
   signal        S : std_logic;
   signal        R : std_logic;
   signal     N_25 : std_logic;
   signal     N_26 : std_logic;
   signal      N_8 : std_logic;
   signal      N_9 : std_logic;
   signal Statinis_DUMMY : std_logic;
   signal      N_2 : std_logic;
   signal      N_6 : std_logic;

   component xnor2
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component inv
      Port (       A : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component or2
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component xor2
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component nd3
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component nd2
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   Z : Out   std_logic );
   end component;

begin

   Statinis <= Statinis_DUMMY;
   Dinaminis <= Dinaminis_DUMMY;
   Dvipakopis <= Dvipakopis_DUMMY;

   I11 : xnor2
      Port Map ( A=>N_26, B=>N_25, Z=>R );
   I16 : inv
      Port Map ( A=>C, Z=>N_15 );
   I17 : inv
      Port Map ( A=>S, Z=>N_24 );
   I18 : inv
      Port Map ( A=>R, Z=>N_21 );
   I12 : inv
      Port Map ( A=>x4, Z=>N_26 );
   I2 : inv
      Port Map ( A=>x2, Z=>N_9 );
   I3 : inv
      Port Map ( A=>x3, Z=>N_2 );
   I13 : or2
      Port Map ( A=>x2, B=>N_2, Z=>N_25 );
   I14 : or2
      Port Map ( A=>N_8, B=>N_26, Z=>S );
   I15 : xor2
      Port Map ( A=>N_9, B=>x3, Z=>N_8 );
   I7 : nd3
      Port Map ( A=>Statinis_DUMMY, B=>N_27, C=>Reset, Z=>N_28 );
   I19 : nd3
      Port Map ( A=>Dinaminis_DUMMY, B=>N_11, C=>Reset, Z=>N_10 );
   I20 : nd3
      Port Map ( A=>Dvipakopis_DUMMY, B=>N_14, C=>Reset, Z=>N_16 );
   I21 : nd3
      Port Map ( A=>N_19, B=>N_13, C=>Reset, Z=>N_17 );
   I22 : nd3
      Port Map ( A=>N_12, B=>C, C=>N_22, Z=>N_11 );
   I23 : nd3
      Port Map ( A=>N_23, B=>C, C=>N_11, Z=>N_12 );
   I24 : nd2
      Port Map ( A=>N_12, B=>N_10, Z=>Dinaminis_DUMMY );
   I25 : nd2
      Port Map ( A=>N_18, B=>N_16, Z=>Dvipakopis_DUMMY );
   I26 : nd2
      Port Map ( A=>N_19, B=>N_15, Z=>N_18 );
   I27 : nd2
      Port Map ( A=>N_15, B=>N_17, Z=>N_14 );
   I28 : nd2
      Port Map ( A=>N_20, B=>N_17, Z=>N_19 );
   I29 : nd2
      Port Map ( A=>S, B=>C, Z=>N_20 );
   I30 : nd2
      Port Map ( A=>C, B=>R, Z=>N_13 );
   I31 : nd2
      Port Map ( A=>N_24, B=>N_12, Z=>N_23 );
   I32 : nd2
      Port Map ( A=>N_11, B=>N_21, Z=>N_22 );
   I8 : nd2
      Port Map ( A=>N_6, B=>N_28, Z=>Statinis_DUMMY );
   I9 : nd2
      Port Map ( A=>S, B=>C, Z=>N_6 );
   I10 : nd2
      Port Map ( A=>C, B=>R, Z=>N_27 );

end SCHEMATIC;
