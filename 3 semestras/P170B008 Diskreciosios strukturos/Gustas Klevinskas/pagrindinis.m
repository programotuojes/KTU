clc; close all; clear all 

% Grafo virsuniu koordinates nulinamos, pagal nutylejima virsunes bus isdestomos ratu.
Vkor = [];

% Testas nr. 1 (duotas)
% kelioPradzia = 3;
% kelioPabaiga = 8;
% V = [1 2 3 4 5 6 7 8];
% U = {[1 2 1],[2 3 2],[3 4 4],[3 5 1],[2 5 5],[5 4 1],[4 7 1],[1 6 2],[7 8 1],[5 6 3],[5 8 5], [1 8 15]};

% Testas nr. 2 (tiesi linija)
% kelioPradzia = 5;
% kelioPabaiga = 7;
% V = [1 2 3 4 5 6 7];
% U = {[1 2 3],[2 3 1],[3 4 2],[4 5 2],[5 6 6],[6 7 1]};
% step = 2/(length(V) - 1);
% for i = 1:length(V)
%     Vkor(i,:) = [-1 - step + step * i, 0];
% end

% Testas nr. 3 (grid)
kelioPradzia = 1;
kelioPabaiga = 14;
V = [1 2 3 4 5 6 7 8 9 10 11 12 13 14];
U = {[1 2 1],[2 3 1],[3 4 1],[5 6 1],[6 7 1],[8 9 1],[9 10 1],[11 12 1],...
    [12 13 1],[2 5 1],[3 6 1],[4 7 1],[5 8 1],[6 9 1],[7 10 1],[8 11 1],...
    [9 12 1],[10 13 1],[11 14 1]};
step = 0;
Vkor(1,:) = [-1,0.8];
for i = 1:3:10
    Vkor(i+1,:) = [-0.5, 0.8 - step];
    Vkor(i+2,:) = [0, 0.8 - step];
    Vkor(i+3,:) = [0.5, 0.8 - step];
    step = step + 0.4;
end
Vkor(14,:) = [-0.5,-1];

disp('Darbo pradzia')

orgraf = 0;  % grafas neorientuotasis
% Pradinio grafo brezimas
arc = 0; poz = 0; Fontsize = 10; lstor = 1; spalva = 'b';
figure(1)
title('Duotasis grafas')
Vkor = plotGraphVU(V,U,orgraf,arc,Vkor,poz,Fontsize,lstor,spalva,kelioPabaiga);
hold on; pause(1)

[d,prec,UU,zingNr,minKelias] = astar(V,U,kelioPradzia,kelioPabaiga,orgraf,Vkor);
% [d,prec,UU,zingNr,minKelias] = deikstra(V,U,kelioPradzia,kelioPabaiga,orgraf,Vkor);

disp( ['Kelio pradzia: ',num2str(kelioPradzia), ' virsune']);
disp( ['Kelio pabaiga: ',num2str(kelioPabaiga), ' virsune']);

disp('Atstumai iki kitu virsuniu  (d masyvas)'); disp(d);
disp('Is kur atejo  (prec masyvas)'); disp(prec);

for i = 1:zingNr
    title(sprintf('Algoritmo kelias:  %d  zingsnis ',i));
    V1 = UU{i};
    U1 = {V1};
    V1kor = [Vkor(V1(1),:); Vkor(V1(2),:)];
    plotGraphVU(V1,U1,0,0,V1kor,0,10,3,'r',kelioPabaiga);
    pause(0.5)
end

for i = 1:length(minKelias)
    title(sprintf('Trumpiausias kelias:  %d  zingsnis ',i));
    V1 = minKelias{i};
    U1 = {V1};
    V1kor = [Vkor(V1(1),:);Vkor(V1(2),:)];
    plotGraphVU(V1,U1,0,0,V1kor,0,10,3,'g',kelioPabaiga);
    pause(0.5)
end

disp('Darbo pabaiga')
