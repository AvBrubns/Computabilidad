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
     private JLabel name,arrow,docIzq,docDer;
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
        rec = new JTextArea(10,44);
     }
    public Container createPanel(){
        Color c = Color.decode("#C4F0FF");	
        panel.setBackground(c);
        
        run = new  Button("Run");
        run.setEnabled(false);
        run.addActionListener(this);
        panel.add(run);

        name = new JLabel("No Hay Automata Cargado Aun :)");
        name.setFont(new Font("Nunito", Font.PLAIN, 15));
        
        input = new JTextField();
        input.setColumns(55);
        input.setEditable(false);
        panel.add(input);

        ImageIcon imgArrow = new ImageIcon(new ImageIcon(".\\icons\\arrow.png").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
        arrow.setIcon(imgArrow);
        arrow.setSize(new Dimension(15, 15));
        panel.add(name);
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

        for(int i = 0; i<=32; i++){
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

        layout.putConstraint(SpringLayout.NORTH, name, heigth/10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, name, width/4, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, input, 35, SpringLayout.NORTH, name);
        layout.putConstraint(SpringLayout.WEST, input, width/9, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, run, -1, SpringLayout.NORTH, input);
        layout.putConstraint(SpringLayout.WEST, run, 25, SpringLayout.EAST, input);
        layout.putConstraint(SpringLayout.NORTH, arrow,heigth/4, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST,arrow, (width/2)-20, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, docIzq, 40, SpringLayout.NORTH, arrow);
        layout.putConstraint(SpringLayout.WEST, docIzq, 25, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, docDer, 40, SpringLayout.NORTH, arrow);
        layout.putConstraint(SpringLayout.WEST, docDer, width-50, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.NORTH, scroll, 40, SpringLayout.NORTH, docIzq);
        layout.putConstraint(SpringLayout.WEST, scroll, 25, SpringLayout.EAST, docIzq);

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
                cadProc(cadena);
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
    private String cadProc(String cad){
        String status= "";
        try {
            Method setCadena = turing.class.getDeclaredMethod("checkCadena", String.class);
            setCadena.setAccessible(true);
            status = (String) setCadena.invoke(getTuring(), cad+":"+0+":"+0);
        } catch (Exception e) {
            System.out.println("Error al procesar la cadena"+ e.getMessage());
        }
        return status;
    }
    private boolean validarStatus(String s){
        if (s.equals("4")) {
            String aux_text=getTex();
            setTextArea(aux_text+"\nCadena valida");
            return false;
        } else if(s.equals("-1")) {
            String aux_text=getTex();
            setTextArea(aux_text+"\nCadena no valida");
            return true;
        }else{
            System.out.println("sigue en ejecucion");
            return false;
        }
    }
}