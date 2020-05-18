-- VHDL model created from schematic shema.sch -- Feb 21 10:28:04 2019

library IEEE;
use IEEE.std_logic_1164.all;
library xp2;
use xp2.components.all;

entity SHEMA is
      Port (      x1 : In    std_logic;
                  x2 : In    std_logic;
                  x3 : In    std_logic;
                  x4 : In    std_logic;
                  x5 : In    std_logic;
                  x6 : In    std_logic;
                   y : Out   std_logic );

end SHEMA;

architecture SCHEMATIC of SHEMA is

   SIGNAL gnd : std_logic := '0';
   SIGNAL vcc : std_logic := '1';

   signal     N_25 : std_logic;
   signal     N_26 : std_logic;
   signal     N_27 : std_logic;
   signal     N_28 : std_logic;
   signal     N_29 : std_logic;
   signal     N_30 : std_logic;
   signal      N_1 : std_logic;
   signal      N_2 : std_logic;
   signal      N_3 : std_logic;
   signal      N_4 : std_logic;
   signal      N_5 : std_logic;
   signal      N_6 : std_logic;
   signal     N_13 : std_logic;
   signal     N_14 : std_logic;
   signal     N_15 : std_logic;
   signal     N_16 : std_logic;
   signal     N_17 : std_logic;
   signal     N_18 : std_logic;
   signal     N_19 : std_logic;
   signal     N_20 : std_logic;
   signal     N_21 : std_logic;
   signal     N_22 : std_logic;
   signal     N_23 : std_logic;
   signal     N_24 : std_logic;

   component and2
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component or3
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component or4
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component and3
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component and5
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   E : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component and4
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component inv
      Port (       A : In    std_logic;
                   Z : Out   std_logic );
   end component;

begin

   I11 : and2
      Port Map ( A=>N_1, B=>N_25, Z=>N_5 );
   I12 : and2
      Port Map ( A=>N_2, B=>N_25, Z=>N_6 );
   I13 : or3
      Port Map ( A=>N_16, B=>N_15, C=>N_14, Z=>N_13 );
   I14 : or4
      Port Map ( A=>N_6, B=>N_5, C=>N_4, D=>N_3, Z=>y );
   I15 : or4
      Port Map ( A=>N_20, B=>N_19, C=>N_18, D=>N_17, Z=>N_1 );
   I16 : or4
      Port Map ( A=>N_24, B=>N_23, C=>N_22, D=>N_21, Z=>N_2 );
   I17 : and3
      Port Map ( A=>N_13, B=>x1, C=>N_26, Z=>N_4 );
   I18 : and3
      Port Map ( A=>N_27, B=>x4, C=>x6, Z=>N_16 );
   I19 : and3
      Port Map ( A=>x4, B=>N_30, C=>x6, Z=>N_15 );
   I20 : and3
      Port Map ( A=>N_27, B=>x4, C=>N_30, Z=>N_14 );
   I21 : and3
      Port Map ( A=>x2, B=>x5, C=>N_29, Z=>N_20 );
   I22 : and5
      Port Map ( A=>x1, B=>N_27, C=>x4, D=>N_30, E=>x6, Z=>N_3 );
   I23 : and5
      Port Map ( A=>N_26, B=>x3, C=>N_28, D=>x5, E=>x6, Z=>N_23 );
   I24 : and4
      Port Map ( A=>x2, B=>N_27, C=>x4, D=>x5, Z=>N_19 );
   I25 : and4
      Port Map ( A=>x2, B=>x3, C=>x4, D=>N_30, Z=>N_18 );
   I26 : and4
      Port Map ( A=>x2, B=>x4, C=>N_30, D=>N_29, Z=>N_17 );
   I27 : and4
      Port Map ( A=>x2, B=>x3, C=>N_30, D=>N_29, Z=>N_21 );
   I28 : and4
      Port Map ( A=>x3, B=>x4, C=>N_30, D=>N_29, Z=>N_22 );
   I29 : and4
      Port Map ( A=>N_26, B=>N_27, C=>N_28, D=>N_29, Z=>N_24 );
   I30 : inv
      Port Map ( A=>x6, Z=>N_29 );
   I31 : inv
      Port Map ( A=>x5, Z=>N_30 );
   I32 : inv
      Port Map ( A=>x2, Z=>N_26 );
   I33 : inv
      Port Map ( A=>x4, Z=>N_28 );
   I34 : inv
      Port Map ( A=>x3, Z=>N_27 );
   I35 : inv
      Port Map ( A=>x1, Z=>N_25 );

end SCHEMATIC;
