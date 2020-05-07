unit Unit1;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, FileUtil, Forms, Controls, Graphics, Dialogs, StdCtrls;

type

  { TForm1 }

  TForm1 = class(TForm)
    Button1: TButton;
    Button2: TButton;
    Edit1: TEdit;
    Label1: TLabel;
    Label2: TLabel;
    procedure Button1Click(Sender: TObject);
    procedure Button2Click(Sender: TObject);
  private

  public

  end;
    Turing = record
      estado : integer;
      caracter : char;
      direccion: integer;
    end;
const
  maxedo = 50;
  minb = -15;
  maxb = 20;
var
  Form1: TForm1;
  cinta : array [minb..maxb] of char;
  delta : array [0..maxedo,0..maxedo] of Turing;
  simbolos: array [0..maxedo] of char;
  n , loncad, nsim, edoinicial, edofinal, cabeza :integer;
  entrada : String;
implementation

{$R *.lfm}

{ TForm1 }

procedure TForm1.Button1Click(Sender: TObject);
var
  i, j : integer;
  f : TextFile;
  c : char;
begin
  //limpiar la cinta
  for i := minb to maxb do
      cinta[i] := ' ';
  AssignFile(f,'maquinaTexto.txt');
  Reset(f);
  readln(f, n, nsim);
  for i := 0 to nsim-1 do
      read(f, simbolos[i]);
  readln(f);
    for i := 0 to n-1 do //lee matriz de transicion (tercias)
        begin
          for j := 0 to nsim-1 do
              begin
                read(f, delta[i, j].estado);
                read(f,c);
                read(f, delta[i, j].caracter);
                read(f, delta[i, j].direccion);
              end;
          readln(f);
        end;
    readln(f, edoinicial);
    readln(f, edofinal);
  CloseFile(f);
end;

function Busqueda(c : char): integer;
var
     i : integer;
  band : boolean;
begin
  band := false;
   i   := 0;
   while ((i<nsim) and (not band)) do begin
         if (c = simbolos[i]) then
            band := true;
         inc(i);
   end;
   if band then
         Busqueda := i-1
   else
         Busqueda := -1;
        //Fin Busqueda
end;

procedure TForm1.Button2Click(Sender: TObject);
var
 i, j, k, lcad, pcad : integer;
 edo : integer;
 cadena : String;
 cadaux : array[1..10] of char;
 f : TextFile;
begin
    AssignFile(f, 'corrida.txt');
    rewrite(f);
    //limpiar la cinta
    for i := minb to maxb do
       cinta[i] := ' ';
    entrada := Edit1.Text;
    lcad := Length(entrada);
    for i:=0 to lcad-1 do begin
       cinta[i] := entrada[i+1];
       write(f, cinta[i]);
    end;
    writeln(f);
    edo := edoinicial;
    cabeza := 0;
    for i :=1 to 14 do begin
        pcad := Busqueda(cinta[cabeza]);
        cinta[cabeza] := delta[edo,pcad].caracter;
        cabeza := cabeza + delta[edo,pcad].direccion;
        if pcad <> -1 then begin
            edo := delta[edo,pcad].estado;
            if (edo <> -1) then begin
                //cinta[cabeza] := delta[edo,pcad].caracter;
                //cabeza := cabeza + delta[edo,pcad].direccion;
                //dep
                k:=0;
                for j := 1 to 5 do begin
                   cadaux[j] := cinta[k];
                   write(f, cinta[k]);
                   //cadena := cadena + string(cinta[k]);
                   inc(k);
                end;
                writeln(f);
                //fin
            end
            else
                ShowMessage('Cadena no aceptada');
        end
        else
            ShowMessage('Eror, símbolo no encontrado');
    end;
    CloseFile(f);
end;

end.
