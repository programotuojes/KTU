function [d,prec,UU,zingNr,minKelias] = deikstra(V,U,kelioPradzia,kelioPabaiga,orgraf,Vkor)
% DEIKSTRA funkcija apskaiciuoja trumpiausius kelius svoriniame
% grafe nuo virsunes "s" iki likusiu grafo virsuniu.
%
%    Formalûs parametrai
% V    - grafo virsuniu aibe,
% U    - grafo briaunu aibe; [u,v,c]- (u,v) - briauna, c - jos ilgis/svoris,
% s    - pradine kelio virsune,
% orgraf = 0,jei grafas neorientuotasis,
%        = 1,jei grafas orientuotasis, 
% Vkor - grafo virsuniu koordinates; parametras nebutinas;
%        jei Vkor nenurodytas arba Vkor =[], tai grafo virsunes
%        isdestomos apskritimu; priesingu atveju - pagal nurodytas
%        koordinates.
% d    - atstumai tarp virsuniu
% prec - is kurios virsunes atejo
% UU   - trumpiausio kelio briaunu aibe
% zingNr - kelio zingsniu kiekis 

% Paruosiamieji veiksmai
n = numel(V); m = numel(U);
dz = zeros(1,n);  % virsuniu dazymo pozymiu masyvas
d = zeros(1,n); prec = zeros(1,n);
svoris = 1;
for i = 1:m
    a = U{i};
    svoris = svoris + a(3);
end
d = d + svoris;
d(kelioPradzia) = 0; prec(kelioPradzia) = kelioPradzia; t = true;

% Gretimumo strukturos apskaiciavimas
GAM = UtoGAM(V,U,orgraf);

zingNr = 0; clear UU;
while (~all(dz) == 1) && t
    minSvoris = min(d(dz == 0));
    
    if minSvoris == svoris
        disp('Grafas G - nejungusis')
        return
    end
    
    ind = find((d == minSvoris) & ~dz);
    k = V(ind(1)); v = prec(k);
    dz(k) = 1;    % nudazome virsune "k"

    % Briaunos dazymas (v,k)
    if k ~= v
        zingNr = zingNr + 1;
        V1 = [v,k];
        UU{zingNr} = V1;
    end
    
    if k == kelioPabaiga
        tempV = kelioPabaiga;
        i = 0;
        while tempV ~= kelioPradzia
            i = i + 1;
            minKelias{i} = [tempV,prec(tempV)];
            tempV = prec(tempV);
        end
        
        return
    end
    
    % Perskaiciuojame masyvu d ir prec elementus
    a = GAM{k};
    [~,nn] = size(a);
    for i = 1:nn
        u = a(1,i);
        if (dz(u) == 0) && (d(u) > d(k) + a(2,i))
            d(u) = d(k) + a(2,i);
            prec(u) = k;
        end
    end  % for
end  %while

return