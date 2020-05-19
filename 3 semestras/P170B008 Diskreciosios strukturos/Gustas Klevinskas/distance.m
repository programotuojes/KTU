function [atstumai] = distance(Vkor, kelioPabaiga)
n = length(Vkor);
distFactor = 2;
atstumai = zeros(1,n);
for i = 1:n
    xDist = abs(Vkor(kelioPabaiga,1) - Vkor(i,1)) * distFactor;
    yDist = abs(Vkor(kelioPabaiga,2) - Vkor(i,2)) * distFactor;
    atstumai(i) = sqrt(xDist^2 + yDist^2);
end
end
