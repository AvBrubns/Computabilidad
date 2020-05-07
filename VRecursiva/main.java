public class main {
    public static void main(String[] Arg)
    {
    	javax.swing.SwingUtilities.invokeLater(new Runnable(){
        
            public void run() {
                window w = new window();
                w.prepareWindow(w);
                
            }
        });
    }
}
