import java.awt.*;
import javax.swing.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


class window  {
    private int ancho=0, alto=0;
    private  JFrame mainFrame;
    private panel contenPanel;
    private  tool menubar;
    private window ww;
 
    public void prepareWindow(window w){
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tpantalla = pantalla.getScreenSize();
        
        ancho = tpantalla.width;
        alto= tpantalla.height;
        ww=w;
        mainFrame = new JFrame();
        menubar = new tool(w);
        contenPanel = new panel(ancho/3,alto/2,w);
        JFrame.setDefaultLookAndFeelDecorated(true);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Proyecto Final Maquina de Turing");
        mainFrame.setBounds(ancho/4, alto/4, ancho/3, alto/2);
        mainFrame.setResizable(false);


        mainFrame.setJMenuBar(menubar.crateTool());
        mainFrame.setContentPane(contenPanel.createPanel());
        mainFrame.setVisible(true);
    }
    private void getMatriz(String path){
		try {
            Method	printMatriz= turing.class.getDeclaredMethod("printMatriz",new Class[0]);
            printMatriz.setAccessible(true);
            //printMatriz.invoke(, new Object[0]);
		} catch (Exception e) {
			System.out.println("Error window "+e.getMessage());
		}
    }
    private window getWindow(){
        return ww;
    }
    private panel getPanel(){
        return contenPanel;
    }
    private tool getTool(){
        return menubar;
    }

}