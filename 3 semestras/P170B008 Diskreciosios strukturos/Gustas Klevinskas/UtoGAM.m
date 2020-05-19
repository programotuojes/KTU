function GAM = UtoGAM(V,U,orgrafGAM)
% GAM funkcija perskaiciuoja grafo G=(V,U),nusakyto virsuniu V ir 
% briaunu U aibemis, uzrasa i gretimumo struktura GAM.
% Pavyzdziui, tegu grafas nusakytas virsuniu aibe V=[1,2,3,4,5] ir briaunu 
% aibe U={[1,2],[1,3],[1,5],[2,3],[2,4],[3,4],[3,5],[4,5]}.Tada funkcija 
% UtoGAM apskaiciuoja gretimumo struktura
% GAM={[2,3,5],[1,3,4],[1,2,4,5],[2,3,5],[1,3,4]}
% "i-tasis" GAM narys GAM{i} yra vienmatis masyvas, kuriame surasytos
%  virsunes, gretimos virsunei "i"
% 
% Formalus parametrai ------------------------
% V - vienmatis masyvas,- grafo virsuniu aibe,
% U - grafo briaunu (lanku) masyvas (zr. pavyzdi),
% orgrafGAM == 0 - jei grafas neorientuotasis,
% orgrafGAM == 1 - jei grafas orientuotasis.

s = size(V);  nv = s(2);  % grafo eile
s = size(U);  nb = s(2);  % lanku skaicius
GAM = cell(nv);
for i = 1:nv
    nvr = abs(V(i));
    for j = 1:nb
        a1 = U{j};  a = a1(1:2);
        s = size(a1); if s(2)==3, sv = a1(3); else, sv = [];  end %jei grafas svorinis sv briaunos svoris, jei ne tuscia
        if ~orgrafGAM  %jei viens lankas orgrafas
            ind = (abs(a)==nvr);
            if sum(ind), GAM{i} = [GAM{i},[a(find(~ind));sv]];  end
        else %jei du lankai neorgrafas
            if abs(a(1))==nvr, GAM{i} = [GAM{i},[a(2);sv]];  end
        end
    end
end
return