import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.Thread;

public class turing {
    private List<List<String>> matriz;
    private ArrayList<String> cinta;
    private String simIN = "(1|0)+";
    private String simCin = "";
    private String commen = "^//.*";
    private String intSim = "^[^\\d].*";
    private String estStart = "";
    private String estEnd = "";
    private int numEst=0;
    private int numSim=0;
    private String line;
    private File file;
    private Method writeTex,getText;
    Matcher mat;
    private int count;
    private String status;
    private window w;

    turing(window ww){
        w=ww;
    }
    private boolean readFile(String path){
        reset();
        String aux="";
        boolean estado=false;
        try {
            file = new File(path);
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
            Pattern stringComment = Pattern.compile(commen);
            while((line=bf.readLine())!=null){
                mat = stringComment.matcher(line);
                if(line.length()>1 && !mat.matches() ){
                    String[] cadplit= line.split(" ");
                    if(cadplit.length == 2){
                        if(isNumeric(cadplit[0]) &&  isNumeric(cadplit[1])){
                            numEst=Integer.parseInt(cadplit[0]);
                            numSim=Integer.parseInt(cadplit[1]);
                        }else{
                            return estado = false;
                        }
                        
                        matriz = new ArrayList<List<String>>();
                        for(int i = 0; i<=numSim;i++){
                            matriz.add(new ArrayList<String>());
                        }
                    }else if(cadplit.length == 1){
                        char[] charSplit = line.toCharArray();
                        aux="(";
                        for(int i=0; i<numSim;i++ ){
                            matriz.get(i).add(Character.toString(charSplit[i]));
                            aux=aux+Character.toString(charSplit[i])+" ";
                        }
                        aux= aux.trim();
                        aux=aux.replace(" ", "|");
                        aux=aux+")+";
                        simCin=aux;
                    }else{
                        aux="";
                        int index=0;
                        for(String s : cadplit){
                            if (count<2){
                                //System.out.print(s);
                                if(aux==""){
                                    aux=s;
                                }else{
                                    aux=aux+":"+s;
                                }
                                count++;
                            }else{
                                aux=aux+":"+s;
                                //System.out.println(aux);
                                if(index<numSim){
                                    matriz.get(index).add(aux);
                                    index++;
                                }else{
                                    index=0;
                                    matriz.get(index).add(aux);
                                    index++;
                                }
                                aux="";
                                count=0;
                            }

                        }
                    }
                }else{
                    Pattern pat = Pattern.compile(intSim);
                    mat = pat.matcher(line);
                    if(!mat.matches() && isNumeric(line)){
                        System.out.println("no comment 2:"+line);
                        if(estStart == ""){
                            estStart=line;
                        }else{
                            estEnd=line;
                        }

                    }else{
                        System.out.println("comment:"+line);
                    }
                }
               
            }
        bf.close();
        estado = fileCheck();
        } catch (Exception e) {
            System.out.println("Error Lectura archivo:"+ e.getMessage());
            return fileCheck();
        }
        return fileCheck();
    }
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    private void printMatriz(){
        System.out.println(matriz.size());
        System.out.println("-------------------------");
        System.out.println( matriz.get(0).get(0) + " "+ matriz.get(1).get(0) + " " + matriz.get(2).get(0)+" "+ matriz.get(3).get(0)+" "+ matriz.get(4).get(0));
        for (int i = 1; i <= matriz.get(0).size() - 1; i++) {
            System.out.println( matriz.get(0).get(i) + " "+ matriz.get(1).get(i) + " " + matriz.get(2).get(i)+" "+ matriz.get(3).get(i)+" "+ matriz.get(4).get(i));
        }
    }
    private boolean fileCheck(){
        if(estEnd!="" && estStart != "" && numEst!= 0 && numSim!=0){
            return true;
        }
        return false;
    }
    private void reset(){
        try {
            estEnd= "";
            estStart = "";
            numEst = 0;
            numSim = 0;
            if(matriz.size()>1){
                matriz.clear();
            }
        } catch (Exception e) {
            System.out.println("Error en reset:"+ e.getMessage());
        }
    }
    private boolean checkCadena(String cad){
        System.out.println("Llego cadena:"+cad);
        boolean statusCad= false;
       try {
        char[] cadena = cad.toCharArray(), plusCadena={'B','B'};
        status=estStart;
        String aux="";
        int index=0,colum=-1;
        while(!status.equals("-1") && !status.equals("4")){
            if(isAlphabbet(cadena[index])){
                if(lockPosit(cadena[index])!=""){
                    colum=Integer.parseInt(lockPosit(cadena[index]));
                    //System.out.println(matriz.get(colum).get(Integer.parseInt(status)+1));
                    aux=matriz.get(colum).get(Integer.parseInt(status)+1);
                    String[] triada=aux.split(":");
                    System.out.println("Cadena:"+new String(cadena)+"index:"+index+":"+status);
                    System.out.println("Triada:"+converMStringtoString(triada));
                    //Thread.sleep(2000);
                    if(!triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "c" ){
                        //System.out.println("C:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));
                        setChar(triada[1],String.valueOf(index));
                        cadena[index]=triada[1].charAt(0);   
                        setText(processText(getText(), new String(cadena)));
                        index = index+Integer.parseInt(triada[2]);
                    }else if(!triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "b+"){
                        //System.out.println("b+:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));
                        //Ingresar  new cadena a la izq;
                        cadena[index]=triada[1].charAt(0);   
                        setChar(triada[1],String.valueOf(index));
                        index=1;
                        index=index+Integer.parseInt(triada[2]);
                        cadena=plusCadena(cadena, plusCadena, true);
                        setText(processText(getText(), new String(cadena)));
                    } else if(!triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "b-"){
                        //Ingresar  new cadena a la der;
                        //System.out.println("B-:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));
                        cadena[index]=triada[1].charAt(0);  
                        setChar(triada[1],String.valueOf(index));
                        cadena=plusCadena(cadena, plusCadena, false);
                        index=index+Integer.parseInt(triada[2]);
                        setText(processText(getText(), new String(cadena)));
                    }else if(triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "c"){
                        //System.out.println("C:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));
                        setText(processText(getText(), new String(cadena)));
                        index = index+Integer.parseInt(triada[2]);
                    }else if(triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "b+"){
                        //System.out.println("B+:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));
                        //Ingresar  new cadena a la izq sin cambio;
                        index=1;
                        index=index+Integer.parseInt(triada[2]);
                        cadena=plusCadena(cadena, plusCadena, true);
                        setText(processText(getText(), new String(cadena)));
                    }else if(triada[1].equals("-") && checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1) == "b-"){
                        //Ingresar  new cadena a la der;
                        //System.out.println("B-:"+checkIndex(index, Integer.parseInt(triada[2]), cadena.length-1));  
                        cadena=plusCadena(cadena, plusCadena, false);
                        index=index+Integer.parseInt(triada[2]);
                        setText(processText(getText(), new String(cadena)));
                    }
                    status=triada[0];
                }else{
                    System.out.println("No esta :,(");
                    break;
                }

            }else{
                System.out.println("La cadena ingresada no pertenece al alfabeto");
                break;
            }
        }
        if(status.equals("4")){
            statusCad=!statusCad;
        }

       } catch (Exception e) {
        System.out.println("Error en checkCad:"+e.getMessage());
       }
       return statusCad;
    }
    private boolean isAlphabbet(char c){
        try {
            //System.out.println("Entre a isAlphabet");
            String chart = Character.toString(c);
            Pattern pat = Pattern.compile(simCin);
            Matcher mat = pat.matcher(chart);
            if (mat.matches()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Erro en alphabet:"+e.getMessage());
        }
        return false;
    }
    private String lockPosit(char c){
        int colum=0;
        boolean luck=false;
        String p = "-1";
        String chart=Character.toString(c);
        try {
            for (colum= 0; colum <= matriz.get(0).size() - 1; colum++) {
                String aux = matriz.get(colum).get(0);
                //System.out.println(aux+":"+chart);
                if(aux.equals(chart)){
                    //System.out.println("Lo encontre");
                    luck=!luck;
                    break;
                }
            }
            if(luck){
                p=String.valueOf(colum);
                //System.out.println("Columna:"+p);
                return p;
            }
        } catch (Exception e) {
            System.out.println("Error en lockP:"+e.getMessage());
            //System.out.println("Columna:"+colum);
        }
        return p;
    }
    private panel getPanel(){
        panel p= null;
		try {
			Method setName = window.class.getDeclaredMethod("getPanel", new Class[0]);
			setName.setAccessible(true);
			p = (panel) setName.invoke(w, new Object[0]);
			return p;
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return p;
    }
    private String getText(){
        String text="";
		try {
			Method getText = panel.class.getDeclaredMethod("getTex", new Class[0]);
			getText.setAccessible(true);
			text = (String) getText.invoke(getPanel(), new Object[0]);
			return text;
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return text;
    }
    private void setText(String s){
		try {
			Method setText = panel.class.getDeclaredMethod("setTextArea", String.class);
			setText.setAccessible(true);
			setText.invoke(getPanel(),s);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
    }
    private void moveArrow(String s){
		try {
			Method setText = panel.class.getDeclaredMethod("positionArrow", int.class);
			setText.setAccessible(true);
			setText.invoke(getPanel(),Integer.parseInt(s));
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
    }
    private void setChar(String s,String p){
		try {
			Method setText = panel.class.getDeclaredMethod("setChart", String.class,int.class);
			setText.setAccessible(true);
			setText.invoke(getPanel(),s,Integer.parseInt(p));
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
    }
    private String processText(String text, String cad){
        String newTex="";
        if(text.equals("")){
            newTex = cad;
        }else{
            newTex=text+"\n"+cad;
        }
        return newTex;
    }
    private String  checkIndex(int index, int i, int cadSize){
        //System.out.println("Index:"+index+"incremento:"+i+"tamCad:"+cadSize);
        String aux="";
        try {
            if((index<=cadSize && index>0)&& i==-1){
                aux="c";
                return "c";
            }else if((index>=0 && index<cadSize) && i==1){
                aux="c";
                return "c";
            }else if(index == 0 && i==-1 ){
                aux="b-";
                return "b-";
            }else if(index == cadSize && i==1){
                aux="b+";
                return "b+";
            }
            return aux;
            
        } catch (Exception e) {
            System.out.println("Error Chenindex:"+e.getMessage());
        }
        return aux;
    }/*
    true ingresar new cadena a la izq
    false ingresar cadena a la der
    */ 
    private char[] plusCadena(char[] cadena, char[] plusCadena,boolean flag){
         String newCadena="";

         if(flag){
             for(char s: cadena){
                newCadena=newCadena+s;
             }
             for(char s: plusCadena){
                newCadena=newCadena+s;
             }
         }else{
             for(char s: plusCadena){
                newCadena=newCadena+s;
             } 
            for(char s: cadena){
                newCadena=newCadena+s;
             }
         }
        return newCadena.toCharArray();
    }
    private String converMStringtoString(String[] s){
        String aux="";
        for(String ss: s){
            aux=aux+ss;
         }
         return aux;
    }
    /* if((index<=cadSize && index>0)&& i==-1){
            index=index+i;
            return index;
        }else if((index>=0 && index<cadSize) && i==1){
            index=index+i;
            return index;
        }else if(index == && ){

        } */
    
}