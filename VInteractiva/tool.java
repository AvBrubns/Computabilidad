
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.KeyStroke;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.io.*;
import java.util.regex.*;

class tool implements ActionListener{
	private JMenuBar mainMenuBar;
	private JMenu menu,utilitis,macro;
	private JMenuItem open,exit,save,addMacro,printTable,infoM;
	private turing maquina;
	private window w;
	private tool t;
	private panel p;
	tool(window ww){
		w=ww;
		mainMenuBar = new JMenuBar();
		menu = new JMenu("Archivo");
		utilitis = new JMenu("Utilitis");
		macro = new JMenu("Macro");
		open = new JMenuItem("Abrir");
		infoM = new JMenuItem("InfoTuring");
		exit = new JMenuItem("Salir");
		save = new JMenuItem("Guardar");
		addMacro =  new JMenuItem("Add Macro");
		printTable = new  JMenuItem("Print Tabla");
		maquina = new turing(ww);
	}
    public JMenuBar crateTool(){

		
		//abrir menu con accion alt+a
		menu.setMnemonic(KeyEvent.VK_A);
		mainMenuBar.add(menu);
		//abri macro con accion alt+m
		macro.setMnemonic(KeyEvent.VK_M);
		mainMenuBar.add(macro);

		//abri utilitis con accion alt+u
		utilitis.setMnemonic(KeyEvent.VK_U);
		mainMenuBar.add(utilitis);
		menu.getAccessibleContext().setAccessibleDescription(
		"descripcion Xd");
		//abrir archivo CTRL-A
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.addActionListener(this);
		menu.add(open);
		menu.addSeparator();
		//Guardar CTRL-S
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		menu.add(save);
		menu.addSeparator();
		//Salir CTRL-Q
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		exit.addActionListener(this);
		menu.add(exit);

		// add Macro CTRL-M
		addMacro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		addMacro.addActionListener(this);
		macro.add(addMacro);

		// add Macro CTRL-p
		printTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		printTable.addActionListener(this);
		utilitis.add(printTable);

		// add Macro CTRL-i
		infoM.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		infoM.addActionListener(this);
		utilitis.add(infoM);

		

		return mainMenuBar;
    }
    public void actionPerformed(ActionEvent e) {
		if(e.getSource()  == open){
			String path = getPath();
			boolean res = read_File(path);
			setText(res, path);
		}
		if(e.getSource()  == exit){
			int res = JOptionPane.showConfirmDialog(null, "¿Está Seguro?", "Cerrar Programa!!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			// 0=yes, 1=no, 2=cancel
			if(res == 0){
				System.exit(0);
			}

		}
		if(e.getSource()== save){
			saveFile();
			
		}
		if (e.getSource() == printTable) {
			print_Table();
		}
		if (e.getSource() == infoM) {
			print_Info();
		}
		if (e.getSource() == addMacro){
			String path = getPath();
			boolean res =add_Macro(path);
			setTextM(res, path);
		}
	}
	private turing getTuring(){
		return maquina;
	}
	private panel getPanel(){
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
	private void setRun(boolean s){
		try {
			Method buttonRun = panel.class.getDeclaredMethod("setRun", boolean.class);
			buttonRun.setAccessible(true);
			buttonRun.invoke(getPanel(), s);
		} catch (Exception e) {
			System.out.println("Error:"+ e.getMessage());
		}
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
	private void print_Table(){
		try {
			Method printT = turing.class.getDeclaredMethod("printMatriz", new Class[0]);
			printT.setAccessible(true);
			printT.invoke(maquina, new Object[0]);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
	private void print_Info(){
		try {
			Method printT = turing.class.getDeclaredMethod("infoM", new Class[0]);
			printT.setAccessible(true);
			printT.invoke(maquina, new Object[0]);
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
	private String getPath(){
		String path="";
		JFileChooser file = new JFileChooser();
			file.setDialogTitle("Selecione el Archivo");
			file.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Solo archivos txt", "txt");
			file.addChoosableFileFilter(filter);
			int returnValue = file.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				  path = file.getSelectedFile().getPath();
			}
			   return path;
	}
	private boolean read_File(String path){
		boolean res=false;
		try {
			Method readFile = turing.class.getDeclaredMethod("readFile", String.class);
			readFile.setAccessible(true);
			res = (Boolean) readFile.invoke(maquina, path);
		}catch(Exception e ){
			System.out.println("Error:"+e.getMessage());
			}
		return res;
	}
	private void setText(boolean res,String path ){
		try {
			if(res){
				Method setName = panel.class.getDeclaredMethod("setName", String.class);
				setName.setAccessible(true);
				String[] name = path.split(Pattern.quote(File.separator));
				System.out.println(path);
				setName.invoke(getPanel(), name[name.length-1]);
				setRun(true);
			}else{
				Method setName = panel.class.getDeclaredMethod("setName", String.class);
				setName.setAccessible(true);
				setName.invoke(getPanel(), "Error en el Archivo :,( Comprube que tenga el formato acordado XD");
				setRun(false);
			}
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
	private void setTextM(boolean res,String path ){
		try {
			if(res){
				Method setName = panel.class.getDeclaredMethod("setNameM", String.class);setName.setAccessible(true);
				String[] name = path.split(Pattern.quote(File.separator));
				System.out.println(path);
				setName.invoke(getPanel(), name[name.length-1]);
				setRun(true);
			}else{
				Method setName = panel.class.getDeclaredMethod("setNameM", String.class);
				setName.setAccessible(true);
				setName.invoke(getPanel(), "Error en el Archivo :,( Comprube que tenga el formato acordado XD");
				setRun(false);
			}
		} catch (Exception e) {
			System.out.println("Error:"+e.getMessage());
		}
	}
	private void saveFile(){
		JFileChooser fileSave = new JFileChooser();
		fileSave.setDialogTitle("Donde desea guardar?");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Solo archivos txt", "txt");
		fileSave.addChoosableFileFilter(filter);
		int returnValue = fileSave.showOpenDialog(null);
		if(returnValue == JFileChooser.APPROVE_OPTION){
			File file = fileSave.getSelectedFile();
			try {
				boolean isFile = false;
				if(!file.exists())
				isFile = file.createNewFile();
				 
				FileWriter outFile = new FileWriter(file);
				PrintWriter out = new PrintWriter(outFile,true);
				out.println(getText());
				out.close();
			} catch (Exception w) {
				System.out.println("error:"+w.getMessage());
			}
		}
	}

	private boolean add_Macro(String path){
		boolean res=false;
		try {
			Method addM = turing.class.getDeclaredMethod("addM", String.class);
			addM.setAccessible(true);
			res = (Boolean) addM.invoke(maquina, path);
		}catch(Exception e ){
			System.out.println("Error:"+e.getMessage());
			}
		return res;
	}

}