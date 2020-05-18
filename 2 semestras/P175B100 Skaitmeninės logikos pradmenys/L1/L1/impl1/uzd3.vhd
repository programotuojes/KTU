-- VHDL model created from schematic uzd3.sch -- Mar 14 10:04:31 2019

library IEEE;
use IEEE.std_logic_1164.all;
library xp2;
use xp2.components.all;

entity UZD3 is
      Port (      x2 : In    std_logic;
                  x1 : In    std_logic;
                  x3 : In    std_logic;
                  x4 : In    std_logic;
                  x5 : In    std_logic;
                  x6 : In    std_logic;
                   y : Out   std_logic );

end UZD3;

architecture SCHEMATIC of UZD3 is

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

   component mux41
      Port (      D0 : In    std_logic;
                  D1 : In    std_logic;
                  D2 : In    std_logic;
                  D3 : In    std_logic;
                 SD1 : In    std_logic;
                 SD2 : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component or5
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
                   D : In    std_logic;
                   E : In    std_logic;
                   Z : Out   std_logic );
   end component;

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

   component and3
      Port (       A : In    std_logic;
                   B : In    std_logic;
                   C : In    std_logic;
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

   I35 : mux41
      Port Map ( D0=>N_15, D1=>N_12, D2=>N_11, D3=>N_10, SD1=>x2,
                 SD2=>x1, Z=>y );
   I36 : or5
      Port Map ( A=>N_8, B=>N_7, C=>N_6, D=>N_5, E=>N_4, Z=>N_12 );
   I37 : and2
      Port Map ( A=>x5, B=>N_14, Z=>N_8 );
   I38 : and2
      Port Map ( A=>x4, B=>N_14, Z=>N_6 );
   I39 : or3
      Port Map ( A=>N_18, B=>N_16, C=>N_13, Z=>N_15 );
   I40 : or3
      Port Map ( A=>N_3, B=>N_2, C=>N_1, Z=>N_11 );
   I41 : and3
      Port Map ( A=>N_19, B=>N_17, C=>N_14, Z=>N_18 );
   I42 : and3
      Port Map ( A=>N_19, B=>x4, C=>x5, Z=>N_7 );
   I43 : and3
      Port Map ( A=>x3, B=>x4, C=>N_9, Z=>N_5 );
   I44 : and3
      Port Map ( A=>x3, B=>N_17, C=>N_14, Z=>N_4 );
   I45 : and3
      Port Map ( A=>N_19, B=>x4, C=>N_9, Z=>N_3 );
   I46 : and3
      Port Map ( A=>N_19, B=>x4, C=>x6, Z=>N_2 );
   I47 : and3
      Port Map ( A=>x4, B=>N_9, C=>x6, Z=>N_1 );
   I48 : and4
      Port Map ( A=>x3, B=>x4, C=>N_9, D=>N_14, Z=>N_16 );
   I49 : and4
      Port Map ( A=>x3, B=>N_17, C=>x5, D=>x6, Z=>N_13 );
   I50 : and4
      Port Map ( A=>N_19, B=>x4, C=>N_9, D=>x6, Z=>N_10 );
   I34 : inv
      Port Map ( A=>x3, Z=>N_19 );
   I33 : inv
      Port Map ( A=>x4, Z=>N_17 );
   I31 : inv
      Port Map ( A=>x5, Z=>N_9 );
   I30 : inv
      Port Map ( A=>x6, Z=>N_14 );

end SCHEMATIC;
