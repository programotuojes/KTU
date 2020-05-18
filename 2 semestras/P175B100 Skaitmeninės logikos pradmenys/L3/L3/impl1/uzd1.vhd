-- VHDL model created from schematic uzd1.sch -- May 02 10:20:04 2019

library IEEE;
use IEEE.std_logic_1164.all;
library xp2;
use xp2.components.all;

entity UZD1 is
      Port (     CLK : In    std_logic;
                 RST : In    std_logic;
                  A0 : In    std_logic;
                  A1 : In    std_logic;
                  Q7 : Out   std_logic;
                  Q6 : Out   std_logic;
                  Q5 : Out   std_logic;
                  Q4 : Out   std_logic;
                  Q0 : Out   std_logic;
                  Q1 : Out   std_logic;
                  Q2 : Out   std_logic;
                  Q3 : Out   std_logic;
                  D7 : In    std_logic;
                  D6 : In    std_logic;
                  D5 : In    std_logic;
                  D4 : In    std_logic;
                  D0 : In    std_logic;
                  D1 : In    std_logic;
                  D2 : In    std_logic;
                  D3 : In    std_logic );

end UZD1;

architecture SCHEMATIC of UZD1 is

   SIGNAL gnd : std_logic := '0';
   SIGNAL vcc : std_logic := '1';

   signal     N_40 : std_logic;
   signal     N_41 : std_logic;
   signal Q3_DUMMY : std_logic;
   signal Q2_DUMMY : std_logic;
   signal Q1_DUMMY : std_logic;
   signal Q0_DUMMY : std_logic;
   signal Q4_DUMMY : std_logic;
   signal Q5_DUMMY : std_logic;
   signal Q6_DUMMY : std_logic;
   signal Q7_DUMMY : std_logic;
   signal     N_39 : std_logic;
   signal     N_19 : std_logic;
   signal     N_20 : std_logic;
   signal     N_22 : std_logic;
   signal     N_15 : std_logic;
   signal     N_16 : std_logic;
   signal     N_17 : std_logic;
   signal     N_18 : std_logic;

   component vhi
      Port (       Z : Out   std_logic );
   end component;

   component mux41
      Port (      D0 : In    std_logic;
                  D1 : In    std_logic;
                  D2 : In    std_logic;
                  D3 : In    std_logic;
                 SD1 : In    std_logic;
                 SD2 : In    std_logic;
                   Z : Out   std_logic );
   end component;

   component fd1s3dx
      Port (      CD : In    std_logic;
                  CK : In    std_logic;
                   D : In    std_logic;
                   Q : Out   std_logic );
   end component;

begin

   Q7 <= Q7_DUMMY;
   Q6 <= Q6_DUMMY;
   Q5 <= Q5_DUMMY;
   Q4 <= Q4_DUMMY;
   Q0 <= Q0_DUMMY;
   Q1 <= Q1_DUMMY;
   Q2 <= Q2_DUMMY;
   Q3 <= Q3_DUMMY;

   I18 : vhi
      Port Map ( Z=>N_40 );
   I17 : vhi
      Port Map ( Z=>N_41 );
   I2 : mux41
      Port Map ( D0=>D2, D1=>Q0_DUMMY, D2=>Q0_DUMMY, D3=>Q3_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_39 );
   I4 : mux41
      Port Map ( D0=>D0, D1=>N_41, D2=>Q6_DUMMY, D3=>Q1_DUMMY, SD1=>A0,
                 SD2=>A1, Z=>N_19 );
   I3 : mux41
      Port Map ( D0=>D1, D1=>N_40, D2=>Q7_DUMMY, D3=>Q2_DUMMY, SD1=>A0,
                 SD2=>A1, Z=>N_20 );
   I1 : mux41
      Port Map ( D0=>D3, D1=>Q1_DUMMY, D2=>Q1_DUMMY, D3=>Q4_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_22 );
   I5 : mux41
      Port Map ( D0=>D4, D1=>Q2_DUMMY, D2=>Q2_DUMMY, D3=>Q5_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_15 );
   I6 : mux41
      Port Map ( D0=>D5, D1=>Q3_DUMMY, D2=>Q3_DUMMY, D3=>Q6_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_16 );
   I7 : mux41
      Port Map ( D0=>D6, D1=>Q4_DUMMY, D2=>Q4_DUMMY, D3=>Q7_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_17 );
   I8 : mux41
      Port Map ( D0=>D7, D1=>Q5_DUMMY, D2=>Q5_DUMMY, D3=>Q7_DUMMY,
                 SD1=>A0, SD2=>A1, Z=>N_18 );
   I12 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_19, Q=>Q0_DUMMY );
   I11 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_20, Q=>Q1_DUMMY );
   I10 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_39, Q=>Q2_DUMMY );
   I9 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_22, Q=>Q3_DUMMY );
   I13 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_15, Q=>Q4_DUMMY );
   I14 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_16, Q=>Q5_DUMMY );
   I15 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_17, Q=>Q6_DUMMY );
   I16 : fd1s3dx
      Port Map ( CD=>RST, CK=>CLK, D=>N_18, Q=>Q7_DUMMY );

end SCHEMATIC;
