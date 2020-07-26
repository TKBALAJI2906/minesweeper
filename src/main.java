import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.time.LocalDate;
import javax.sound.sampled.*;
import javax.swing.text.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
public class main implements Runnable{
    GUI gui=new GUI();
    public static void main(String[] args) {
        new Thread(new main()).start();
    }
    @Override
    public void run() {
        while(true){
           gui.repaint();
           if(gui.Resetter==false && gui.victory!=true){
           gui.checkVictorystatus();
           }   
        if((gui.victory==true||gui.defeat==true)&&gui.inBusy==false&&(gui.checkv==true||gui.checkd==true)&&gui.check==0&&gui.Resetter==false){
        if(gui.contx!=0&&gui.cont!=0)           
        {
           JFrame frame=new JFrame();
           win_r_lose_music();
           int valx = 0;//valx->in file value
        File file=new File("data.txt");
        try{
        boolean createNewFile=file.createNewFile();
        System.out.println(createNewFile);
        }catch(IOException e){
           System.out.println(e);
        }
        try{
        FileInputStream fis=new FileInputStream(file);
        InputStreamReader isr=new InputStreamReader(fis);
        BufferedReader br=new BufferedReader(isr);
        String line = null;
        while((line=br.readLine())!=null)
        {
          System.out.println(line);
          valx=Integer.parseInt(line.trim());
          System.out.println(valx);
        }
        br.close();
        }catch(IOException ex){
        }
        try{
        if(gui.val>valx)
        {
          valx=gui.val;
          String datum=Integer.toString(gui.val);
          FileWriter fw=new FileWriter(file,false);
          BufferedWriter bw=new BufferedWriter(fw);
          bw.write(datum);
          bw.close();
        }
        else System.out.println("not high score");
        }catch(IOException ex)
        {
          ex.printStackTrace();
        }
        String Msg1 = null,Msg2=null;
        ImageIcon icon=new ImageIcon("src/minesweeper.png");
        if(gui.victory==true){ 
            Msg1=" Won";
            Msg2="\tcongratulations friend, you won the game :)\n\tWinner! Winner! chicken Dinner";
        }
        else if(gui.defeat==true){
            Msg1=" Lost";
            Msg2="   Sorry buddy, you lost the game.Better luck next time :(\n";     
        }   
        JTextPane jtp=new JTextPane();
        Document doc=jtp.getDocument();
        LocalDate date=java.time.LocalDate.now();
        try {
            doc.insertString(doc.getLength(), Msg2+"\n\t    time :"+gui.sec+" seconds\n\t   Date:"+date+"\n\t           Score: "+gui.points+"\n"+"\t      High Score: "+valx+"\n\tWanna play Again",new SimpleAttributeSet());
        } catch (BadLocationException ex) {
        }
        JScrollPane jsp=new JScrollPane(jtp);
        jsp.setPreferredSize(new Dimension(324,120));
        jsp.setBorder(null);
        int replay=JOptionPane.showConfirmDialog(null, jsp, "Game"+Msg1, JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE, icon);
        if(replay==0) gui.ResetAll();
        else if(replay==1) System.exit(0);     
        }
     }
    }
  }
  public void win_r_lose_music()
  {
    try{
        URL url=this.getClass().getClassLoader().getResource("minesweeper3.wav");
        AudioInputStream audioIn= AudioSystem.getAudioInputStream(url);
        Clip clip=AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
        }catch(UnsupportedAudioFileException | IOException | LineUnavailableException e)
        {
            System.out.println(e);
        }
    }
}

