;
;     /   2c + |x|             kai c + x = 0
; y = |   2x - c * c           kai c + x > 0
;     \   (b + a) / (c + x)    kai c + x < 0
;
; Skaiciai su zenklu
; Duomenys a - b, b - w, c - w, x - b, y - w

stekas  SEGMENT STACK
DB 256 DUP(0)
stekas  ENDS

duom    SEGMENT
a		DB	0
b		DW  -32768
c		DW	0
x		DB -1
kiek = $ - x
y		DW kiek dup(0AAh)

out_str DB 'x = ', 6 dup (?), '  y = ', 6 dup (?), 0Dh, 0Ah, '$'
ovrfl   DB 'Overflow', 0Dh, 0Ah, '$'
end_str DB 'Done. Press any key.', 0Dh, 0Ah, '$' 
duom    ENDS

prog    SEGMENT
assume ss:stekas, ds:duom, cs:prog

pr:
MOV     ax, duom
MOV     ds, ax
XOR     si, si
XOR     di, di

MOV     cx, kiek
JCXZ    output

cycle:
MOV     bx, c
MOV     al, x[si]
CBW
NEG     ax
CMP     bx, ax
JG      f2
JL      f3



f1:		; y = 2c + |x|
MOV		bl, x[si]
CMP		bl, 0
JG		f1_cont
NEG		bl

f1_cont:
MOV		ax, 2
IMUL	c
JO		overflow
XOR		bh, bh
ADD		ax, bx
JO		overflow
JMP		done



f2:     ; y = 2x - c * c
MOV     ax, c
IMUL    c
JO      overflow
MOV     bx, ax
MOV     ax, 2
IMUL    x[si]
SUB     ax, bx
JO      overflow
JMP     done



f3:     ; y = (b + a) / (c + x)
MOV     al, x[si]
CBW
ADD     ax, c
JO      overflow
MOV     bx, ax
MOV     al, a
CBW
ADD     ax, b
JO      overflow
;CMP     ax, -32768
;JE      overflow
CWD
IDIV    bx



done:
MOV     y[di], ax
INC     si
INC     di
INC     di
LOOP    cycle

output:
XOR     si, si
XOR     di, di
MOV     cx, kiek
JCXZ    output_end

output_cycle:
MOV     al, x[si]
CBW
PUSH    ax
MOV     bx, offset out_str+4
PUSH    bx
CALL    binasc
MOV     ax, y[di]
PUSH    ax
MOV     bx, offset out_str+16
PUSH    bx
CALL    binasc

MOV     dx, offset out_str
MOV     ah, 9h
INT     21h

INC     si
INC     di
INC     di
LOOP output_cycle

output_end:
LEA     dx, end_str
MOV     ah, 9
INT     21h
MOV     ah, 0
INT     16h
        
MOV     ah, 4Ch
INT     21h
; ============================
; End of program, return to OS
; ============================
	
overflow:
LEA     dx, ovrfl
MOV     ah, 9
INT     21h
XOR     ax, ax
JMP     done

; skaiciu vercia i desimtaine sist. ir issaugo
; ASCII kode. Parametrai perduodami per steka
; Pirmasis parametras ([bp+6])- verciamas skaicius
; Antrasis parametras ([bp+4])- vieta rezultatui

binasc  PROC NEAR
PUSH    bp
MOV     bp, sp
; naudojamu registru issaugojimas
PUSHA
; rezultato eilute uzpildome tarpais
MOV     cx, 6
MOV     bx, [bp+4]

tarp:
MOV     byte ptr[bx], ' '
INC     bx
LOOP    tarp
; skaicius paruosiamas dalybai is 10
MOV     ax, [bp+6]
MOV     si, 10
CMP     ax, 0
JGE     val
; verciamas skaicius yra neigiamas
NEG     ax

val:
XOR     dx, dx
DIV     si
; gauta liekana verciame i ASCII koda
ADD     dx, '0'   ; galima--> ADD dx, 30h
; irasome skaitmeni i eilutes pabaiga
DEC     bx
MOV     [bx], dl
; skaiciuojame pervestu simboliu kieki
INC     cx
; ar dar reikia kartoti dalyba?
CMP     ax, 0
JNZ     val
; gautas rezultatas. Uzrasome zenkla
;	pop ax
MOV     ax, [bp+6]
CMP     ax, 0
JNS     teig
; buvo neigiamas skaicius, uzrasome -
DEC     bx
MOV     byte ptr[bx], '-'
INC     cx
JMP     vepab
; buvo teigiamas skaicius, uzrasau +
teig:
DEC     bx
MOV     byte ptr[bx], '+'
INC     cx

vepab:
POPA
POP     bp
RET
binasc	ENDP

END pr
prog    ENDS
