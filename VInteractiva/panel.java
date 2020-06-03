import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class panel implements ActionListener{
     private JPanel panel;
     private Button run;
     private SpringLayout layout;
     private JLabel name,arrow,docIzq,docDer,macro;
     private int width,heigth;
     private JTextArea rec;
     private JScrollPane scroll; 
     private List<JTextField> listP;
     private JTextField input;
     private window w;
     private tool t;
     private turing maquina;

     panel(int x, int y,window ww){
        width=x;
        w=ww;
        heigth=y;
        listP = new ArrayList<>();
        panel = new JPanel();
        layout = new SpringLayout();
        panel.setLayout(layout);
        arrow = new JLabel();
        rec = new JTextArea(10,width/27);
     }
    public Container createPanel(){
        Color c = Color.decode("#D9D9D9");	
        panel.setBackground(c);
        
        run = new  Button("Run");
        run.setEnabled(false);
        run.addActionListener(this);
        panel.add(run);

        name = new JLabel("No Hay Automata Cargado Aun :)");
        name.setFont(new Font("Nunito", Font.PLAIN, 15));

        macro = new JLabel("No Hay Macro cargada :)");
        macro.setFont(new Font("Nunito", Font.PLAIN, 15));
        
        input = new JTextField();
        input.setColumns(40);
        input.setEditable(false);
        panel.add(input);

        ImageIcon imgArrow = new ImageIcon(new ImageIcon(".\\icons\\arrow.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        arrow.setIcon(imgArrow);
        arrow.setSize(new Dimension(15, 15));
        panel.add(name);
        panel.add(macro);
        panel.add(arrow);

        docIzq = new JLabel();
        ImageIcon imgdocIzq = new ImageIcon(new ImageIcon(".\\icons\\puntos.png").getImage().getScaledInstance(25,20, Image.SCALE_SMOOTH));
        docIzq.setIcon(imgdocIzq);
        docIzq.setSize(new Dimension(15, 15));
        panel.add(name);
        panel.add(docIzq);

        docDer = new JLabel();
        ImageIcon imgdocDer = new ImageIcon(new ImageIcon(".\\icons\\puntos.png").getImage().getScaledInstance(25,20, Image.SCALE_SMOOTH));
        docDer.setIcon(imgdocDer);
        docDer.setSize(new Dimension(15, 15));
        panel.add(name);
        panel.add(docDer);

        for(int i = 0; i<=width/27; i++){
            JTextField p = new JTextField("B");
            p.setColumns(1);
            p.setEditable(false);
            p.setHorizontalAlignment(JTextField.CENTER);
            p.setFont(new Font("SERIF",Font.BOLD,15));
            listP.add(p);
            panel.add(p);

        }

        rec.setEditable(false);
        rec.setLineWrap(true);
        rec.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        scroll = new JScrollPane(rec);

        panel.add(scroll);

        /* System.out.println(width);
        System.out.println(heigth);
        System.out.println(heigth/4);*/

        layout.putConstraint(SpringLayout.NORTH, name, heigth/15, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, name, width/3, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH,  macro, heigth/15, SpringLayout.NORTH, name);
        layout.putConstraint(SpringLayout.WEST, macro, width/3, SpringLayout.NORTH, name);
        layout.putConstraint(SpringLayout.NORTH, docIzq, 50, SpringLayout.NORTH, macro);
        layout.putConstraint(SpringLayout.WEST, docIzq, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH,  arrow, -35, SpringLayout.NORTH, docIzq);
        layout.putConstraint(SpringLayout.WEST, arrow, 48, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, docDer, 50, SpringLayout.NORTH, macro);
        layout.putConstraint(SpringLayout.WEST, docDer, width-50, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, scroll, 85, SpringLayout.NORTH, macro);
        layout.putConstraint(SpringLayout.WEST, scroll, 130, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, input, 280, SpringLayout.NORTH, scroll);
        layout.putConstraint(SpringLayout.WEST, input, 50, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, run, -1, SpringLayout.NORTH, input);
        layout.putConstraint(SpringLayout.WEST, run, 25, SpringLayout.EAST, input);

        
       
        int i=1, anchop=0;
        for(JTextField p: listP){
            anchop=22*i;
            if(anchop == width){
                System.out.println("entre"+i);
            }
            layout.putConstraint(SpringLayout.NORTH, p, -8, SpringLayout.NORTH, docIzq);
            layout.putConstraint(SpringLayout.WEST, p, anchop, SpringLayout.EAST, docIzq);
            i++;
        }

		return panel;
    }
    public void actionPerformed(ActionEvent e){  
         if(e.getSource()==run){
            try {
                String cadena = input.getText();
                setChar(cadena);
                if (cadProc(cadena)) {
                    String aux_text=getTex();
                    setTextArea(aux_text+"\nCadena valida");
                } else {
                    String aux_text=getTex();
                    setTextArea(aux_text+"\nCadena no valida");
                }
            } catch (Exception r) {
                System.out.println("Error Run: "+r.getMessage());    
            }
         }
    }
    private void setTextArea(String s) {
        try {
            rec.setText(s);  
        } catch (Exception e) {
            System.out.println("Error panel> "+e.getMessage());            
        }
    }
    private String getTex(){
        String aux=null;
        //System.out.println("entre");
        try  {
            String text=rec.getText();
            if(text.equals("")){
                aux="";
            }else{
                aux = rec.getText();
            }
        } catch (Exception e) {
            System.out.println("ERROR getText"+ e.getMessage());
        }
        return aux;
    }
    private void setChar(String s){
        for(int i = 0; i<s.length();i++){
            JTextField aux= listP.get(i);
            aux.setText(String.valueOf(s.charAt(i)));
        }
    }
    private void setName(String n) {
        name.setText(n);
    }
    private void setRun(boolean b){
        run.setEnabled(b);
        input.setEditable(b);
    }
    private tool getTool(){
		try {
			Method setName = window.class.getDeclaredMethod("getTool", new Class[0]);
			setName.setAccessible(true);
			t = (tool) setName.invoke(w, new Object[0]);
			return t;
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return t;
    }
    private turing getTuring(){
        try {
			Method setName = tool.class.getDeclaredMethod("getTuring", new Class[0]);
			setName.setAccessible(true);
			maquina = (turing) setName.invoke(getTool(), new Object[0]);
			return maquina;
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
		return maquina;
    }
    private void printMatriz(){
        try {
            Method printMatriz = turing.class.getDeclaredMethod("printMatriz", new Class[0]);
            printMatriz.setAccessible(true);
            printMatriz.invoke(getTuring(), new Object[0]);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
    private boolean cadProc(String cad){
        boolean status= false;
        try {
            Method setCadena = turing.class.getDeclaredMethod("checkCadena", String.class);
            setCadena.setAccessible(true);
            status= (boolean) setCadena.invoke(getTuring(), cad);
            return status;
        } catch (Exception e) {
            System.out.println("Error al procesar la cadena"+ e.getMessage());
        }
        return status;
    }
}