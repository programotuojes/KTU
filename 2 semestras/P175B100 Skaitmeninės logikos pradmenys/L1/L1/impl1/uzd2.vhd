-- VHDL model created from schematic uzd2.sch -- Mar 14 10:02:10 2019

library IEEE;
use IEEE.std_logic_1164.all;
library xp2;
use xp2.components.all;

entity UZD2 is
      Port (      x1 : In    std_logic;
                  x2 : In    std_logic;
                  x3 : In    std_logic;
                  x4 : In    std_logic;
                  x5 : In    std_logic;
                  x6 : In    std_logic;
                   y : Out   std_logic );

end UZD2;

architecture SCHEMATIC of UZD2 is

   SIGNAL gnd : std_logic := '0';
   SIGNAL vcc : std_logic := '1';

   signal      N_1 : std_logic;
   signal      N_2 : std_logic;
   signal      N_3 : std_logic;
   signal      N_4 : std_logic;
   signal      N_5 : std_logic;
   signal      N_6 : std_logic;
   signal      N_7 : std_logic;
   signal      N_8 : std_logic;
   signal      N_9 : std_logic;
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
   signal     N_21 : std_logic;
   signal     N_22 : std_logic;
   signal     N_23 : std_logic;
   signal     N_24 : std_logic;

   component nd2
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

   component nd4
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component nd5
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   E : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component inv
      Port (       A : In    std_logic;
                   Z : Out   std_logic );
   end component;

begin

   I36 : nd2
      Port Map ( A=>N_9, B=>N_7, Z=>N_19 );
   I37 : nd2
      Port Map ( A=>N_20, B=>N_7, Z=>N_23 );
   I38 : nd3
      Port Map ( A=>x2, B=>x5, C=>N_15, Z=>N_11 );
   I39 : nd3
      Port Map ( A=>N_4, B=>x1, C=>N_1, Z=>N_16 );
   I40 : nd3
      Port Map ( A=>N_5, B=>N_3, C=>N_2, Z=>N_4 );
   I41 : nd3
      Port Map ( A=>N_22, B=>x4, C=>x6, Z=>N_5 );
   I42 : nd3
      Port Map ( A=>x4, B=>N_12, C=>x6, Z=>N_3 );
   I43 : nd3
      Port Map ( A=>N_22, B=>x4, C=>N_12, Z=>N_2 );
   I44 : nd4
      Port Map ( A=>x2, B=>x4, C=>N_12, D=>N_15, Z=>N_6 );
   I45 : nd4
      Port Map ( A=>x2, B=>x3, C=>x4, D=>N_12, Z=>N_8 );
   I46 : nd4
      Port Map ( A=>x2, B=>N_22, C=>x4, D=>x5, Z=>N_10 );
   I47 : nd4
      Port Map ( A=>N_11, B=>N_10, C=>N_8, D=>N_6, Z=>N_9 );
   I48 : nd4
      Port Map ( A=>x2, B=>x3, C=>N_12, D=>N_15, Z=>N_14 );
   I49 : nd4
      Port Map ( A=>x3, B=>x4, C=>N_12, D=>N_15, Z=>N_17 );
   I50 : nd4
      Port Map ( A=>N_23, B=>N_19, C=>N_16, D=>N_13, Z=>y );
   I51 : nd4
      Port Map ( A=>N_24, B=>N_21, C=>N_17, D=>N_14, Z=>N_20 );
   I52 : nd4
      Port Map ( A=>N_1, B=>N_22, C=>N_18, D=>N_15, Z=>N_24 );
   I53 : nd5
      Port Map ( A=>N_1, B=>x3, C=>N_18, D=>x5, E=>x6, Z=>N_21 );
   I54 : nd5
      Port Map ( A=>x1, B=>N_22, C=>x4, D=>N_12, E=>x6, Z=>N_13 );
   I30 : inv
      Port Map ( A=>x6, Z=>N_15 );
   I31 : inv
      Port Map ( A=>x5, Z=>N_12 );
   I32 : inv
      Port Map ( A=>x2, Z=>N_1 );
   I33 : inv
      Port Map ( A=>x4, Z=>N_18 );
   I34 : inv
      Port Map ( A=>x3, Z=>N_22 );
   I35 : inv
      Port Map ( A=>x1, Z=>N_7 );

end SCHEMATIC;
