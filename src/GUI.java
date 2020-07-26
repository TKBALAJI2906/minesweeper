import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
public class GUI extends JFrame{
    int spacing=3;
    int neighs=0;
    public int points=0;
    public int val = 0;//val->new score
    public int pointx,pointy;
    public boolean isHelp=false;
    public boolean blink=false;
    public boolean inBusy=false;
    public boolean isUsed=false;
    public boolean checkv=false,checkd=false;
    public long secCpy=0;
    public int check=0;
    public int contx=0;
    public int cont=0;
    public int no_of_mines=0,minecount=0;
    public int index1=0;
    public int index2=0;
    public int mx=-100;
    public int my=-100;
    public long sec=0;
    public int timeX=670;
    public int timeY=6;  
    public int setr=0;
    int[][] mines=new int[16][9];
    int[][]  neighbour=new int[16][9];
    boolean[][] revealed=new boolean[16][9];
    boolean[][] flagged=new boolean[16][9];
    boolean[][] doubt=new boolean[16][9];
    int[][] flag_count=new int[16][9];
    public int smileyX=405;
    public int smileyY=1;
    public boolean Resetter=false;
    Date startDate=new Date();
    Date busyDate=null;
    Date endDate;
    public int helpCount=0;
    public int smileyCenterX=smileyX+35;
    public int smileyCenterY=smileyY+35;
    public boolean happiness=true;
    public boolean victory=false;
    public boolean defeat=false;
    String help="Help!";
    Random rand=new Random();
    public GUI(){
        this.setTitle("Minesweeper");
        this.setSize(815,550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        for(int i=0;i<16;i++){
            for(int j=0;j<9;j++){
                mines[i][j]=0;
                  revealed[i][j]=false;
                  flagged[i][j]=false;
                  flag_count[i][j]=0;
            }
        }
        
        while(no_of_mines<20){
            index1=rand.nextInt(100)%16;
            index2=rand.nextInt(100)%9;
            if(mines[index1][index2]!='-'){
                mines[index1][index2]='-';
            no_of_mines++;
            }
        }
        Neighbours();        
        Board board=new Board();
        this.setContentPane(board);
        Move move=new Move();
        this.addMouseMotionListener(move);
        Click click=new Click();
        this.addMouseListener(click);
        key key=new key();
        this.addKeyListener(key);
    }
    public void Neighbours(){
        int ctr1,ctr2;
        for(int i=0;i<16;i++){
            for(int j=0;j<9;j++){
                minecount=0;
                if(mines[i][j]=='-')
                    neighbour[i][j]='-';
                else
                {
                if(i==0 && j!=0 && j!=8)
                {
                    for(ctr1=0;ctr1<=1;ctr1++){
                        for(ctr2=-1;ctr2<=1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(j==0 && i!=0 && i!=15)
                {
                    for(ctr1=-1;ctr1<=1;ctr1++){
                        for(ctr2=0;ctr2<2;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(j==8 && i!=0 && i!=15)
                {
                    for(ctr1=-1;ctr1<=1;ctr1++){
                        for(ctr2=-1;ctr2<1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(i==15 && j!=0 && j!=8)
                {
                    for(ctr1=-1;ctr1<1;ctr1++){
                        for(ctr2=-1;ctr2<=1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(i==0 && j==0)                        
                {
                    for(ctr1=0;ctr1<=1;ctr1++){
                        for(ctr2=0;ctr2<=1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(i==0 && j==8)                        
                {
                    for(ctr1=0;ctr1<=1;ctr1++){
                        for(ctr2=-1;ctr2<1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(i==15 && j==0)                        
                {
                    for(ctr1=-1;ctr1<1;ctr1++){
                        for(ctr2=0;ctr2<=1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else if(i==15 && j==8)                        
                {
                    for(ctr1=-1;ctr1<1;ctr1++){
                        for(ctr2=-1;ctr2<1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                else
                {
                     for(ctr1=-1;ctr1<=1;ctr1++){
                        for(ctr2=-1;ctr2<=1;ctr2++){
                            if(mines[i+ctr1][j+ctr2]=='-'){
                                minecount++;
                            }
                        }
                    }
                }
                
                }
                neighbour[i][j]=minecount;
                
            }
        }
    }

    public class Board extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 0, 1280, 800);
            g.setFont(new Font("Tahoma",Font.ROMAN_BASELINE,20));
            g.setColor(Color.red);
            g.drawString("F", 7, 35);
            g.setColor(Color.white);
            g.drawString("  : FLAG", 10, 35);
            g.setColor(new Color(72,209,204));
            g.drawString("?",120,35);
            g.setColor(Color.white);
            g.drawString("  : DOUBT", 123, 35);
            for(int i=0;i<16;i++){
                for(int j=0;j<9;j++){
                     g.setColor(Color.gray);
            if(revealed[i][j]==true){
                g.setColor(Color.white);
            }
                    if(mx>=spacing+i*50 && mx<spacing+i*50+50-2*spacing && my>=spacing+j*50+50+26 && my<spacing+j*50+26+50+50-2*spacing){
                    g.setColor(Color.lightGray);
                    }
                    g.fillRect(spacing+i*50,spacing+j*50+50 ,50-2*spacing,50-2*spacing );
                    if(blink==true)
                    {
                        for(int c=0;c<10;c++)
                        revealed[pointx][pointy]=true;
                        blink=false;
                    }
                    if(revealed[i][j]==true){
                        if(mines[i][j]!='-'){
                            if(neighbour[i][j]==1)
                                g.setColor(Color.blue);
                            if(neighbour[i][j]==2)
                                g.setColor(Color.green);
                            if(neighbour[i][j]==3)
                                g.setColor(Color.red);
                            if(neighbour[i][j]==4)
                                g.setColor(new Color(0,0,128));
                            if(neighbour[i][j]==5)
                                g.setColor(new Color(178,34,34));
                            if(neighbour[i][j]==6)
                                g.setColor(new Color(128,0,128));
                            if(neighbour[i][j]==7)
                                g.setColor(Color.PINK);
                            if(neighbour[i][j]==8)
                                g.setColor(Color.darkGray);
                            g.setFont(new Font("Tahoma",Font.BOLD,40));
                            if(neighbour[i][j]!=0)
                            g.drawString(Integer.toString(neighbour[i][j]),i*50+12,j*50+50+40);
                        }else if (mines[i][j]=='-'){
                            g.setColor(Color.red);
                            g.fillRect(spacing+i*50,spacing+j*50+50 ,50-2*spacing,50-2*spacing );
                            g.setColor(Color.black);
                            g.fillRect(i*50+18,j*50+50+7, 15, 35);
                            g.fillRect(i*50+8,j*50+50+17, 35, 15);
                            g.fillRect(i*50+13, j*50+50+12, 25, 25);
                            g.fillRect(i*50+23, j*50+50+4, 5, 42);
                            g.fillRect(i*50+4, j*50+50+23, 42, 5);
                        }
                }
                if(revealed[i][j]==false && flagged[i][j]==true){

                            g.setColor(Color.black);
                            g.fillRect(i*50+23+7, j*50+50+5+4, 4, 33);
                            g.fillRect(i*50+13, j*50+50+35+4, 27, 4);
                            g.setColor(Color.red);
                            g.fillRect(i*50+13, j*50+50+5+4, 17, 15);
                            g.setColor(Color.black);
                            g.fillRect(i*50+13,  j*50+50+5+4, 17, 3);
                            g.fillRect(i*50+13,  j*50+50+5+4, 3, 15);
                            g.fillRect(i*50+13,  j*50+50+20+4, 17, 3);                              
                }
                if(revealed[i][j]==false && doubt[i][j]==true){
                            g.setColor(new Color(72,209,204));
                            g.setFont(new Font("Tahoma",Font.ITALIC,40));
                            g.drawString("?",i*50+12,j*50+50+40);
                }
            }
        }            
//smiley painting
            g.setColor(Color.yellow);
            g.fillOval(smileyX, smileyY+5, 40, 40);
            g.setColor(Color.black);
            g.fillOval(smileyX+7, smileyY+15, 7, 7);
            g.fillOval(smileyX+25, smileyY+15, 7, 7);
            if(happiness==true){
                g.fillRect(smileyX+12, smileyY+35, 18, 3);
                g.fillRect(smileyX+9, smileyY+33, 3, 3);
                g.fillRect(smileyX+30, smileyY+33, 3, 3);
                }
            else{
                g.fillRect(smileyX+12, smileyY+33, 18, 3);
                g.fillRect(smileyX+9, smileyY+35, 3, 3);
                g.fillRect(smileyX+30, smileyY+35, 3, 3);
            }
//timer painting
            g.setColor(Color.black);
            g.fillRect(timeX+18, timeY, 75, 40);
            if(defeat == false && victory == false &&inBusy==false)
            {
            if(isUsed==false)
            sec=(int)((new Date().getTime()-startDate.getTime())/1000);          
            else
            {
                sec=secCpy+(int)((new Date().getTime()-startDate.getTime())/1000);          
            }
            }
            
            g.setColor(Color.green);
            
            if(defeat== true)
                g.setColor(Color.red);
            g.setFont(new Font("Tahoma",Font.ROMAN_BASELINE,45));
            if(sec<10)
                g.drawString("00"+Integer.toString((int) sec),timeX+20,timeY+37);
            else if(sec<100)
                g.drawString("0"+Integer.toString((int) sec),timeX+20,timeY+37);
            else g.drawString(Integer.toString((int) sec),timeX+20,timeY+37);  
            
//help button
            g.setColor(Color.black);
            g.fillRect(timeX+18-100, timeY+8, 52, 25);
            g.setColor(Color.red);
            g.setFont(new Font("Tahoma",Font.HANGING_BASELINE,20));
            g.drawString(help, timeX+18-100, timeY+27);
            }
        
    }
    public class key extends Applet implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
           if(e.getKeyChar()=='f'||e.getKeyChar()=='F'){
               setFlag();
           }
           if((e.getKeyChar()=='?')||(e.getKeyChar()=='/')){
               setDoubt();
           }
        }

        @Override
        public void keyReleased(KeyEvent e) {
           
        }
        
    }
    
    public class Move implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
           mx= e.getX();
            my=e.getY();
        }
    }
         public class Click implements MouseListener{

            @Override
            public void mouseClicked(MouseEvent e) {
                int i=inBoxX();
                int j= inBoxY();

                if(!(i==-1||j==-1)){
                minecount=0;
                revealed[i][j]=true;
                
                }
                else{
                    if(inSmiley()==true){
                        inBusy=true;
                        secCpy=sec;
                        String Msg1="Notification",Msg2="You have resetted the game";
                        ImageIcon icony=new ImageIcon("src/replay.png");
                        JTextPane jtp=new JTextPane();
                        Document doc=jtp.getDocument();
                        try {
                         doc.insertString(doc.getLength(), Msg2+"\nHave a good luck:)",new SimpleAttributeSet());
                         } catch (BadLocationException ex) {
                        }
                        JScrollPane jsp=new JScrollPane(jtp);
                        jsp.setPreferredSize(new Dimension(150,20));
                        jsp.setBorder(null);                        
                        contx=JOptionPane.showConfirmDialog(null, jsp, Msg1, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, icony);
                        if(contx==0)
                        {
                            inBusy=false;
                            startDate=new Date();
                            checkv=false;checkd=false;
                            startDate=new Date();
                            sec=0;check=1;secCpy=0;
                            setr=1;
                            ResetAll();
                            checkv=false;checkd=false;
                        }
                        else
                        {
                            inBusy=false;
                            startDate=new Date();
                            isUsed=true;
                        }
                }
                    if(inHelp()==true)
                    {
                        isHelp=true;
                        int num=0,find=0;
                        while(find==0 &&helpCount<=5)
                        {
                        for(int b=rand.nextInt(rand.nextInt(100))%16;b<16 && find==0;b++)
                        {
                            for(int c=rand.nextInt(rand.nextInt(100))%9 ;c<9 && find==0;c++)
                            {
                                if(mines[b][c]==num && revealed[b][c]==false)
                                {
                                    pointx=b;
                                    pointy=c;
                                    find=1;
                                    helpCount++;
                                    break;
                                }
                            }
                        }
                        if(find==0)
                            num++;
                        }
                        if(find==1)
                            blink=true;
                        if(helpCount>5)
                        {
                        secCpy=sec;
                        inBusy=true;
                        String Msg1="Notification",Msg2="Help count over bro!!!";
                        ImageIcon iconx=new ImageIcon("src/iconx.png");
                        JTextPane jtp=new JTextPane();
                        Document doc=jtp.getDocument();
                        try {
                         doc.insertString(doc.getLength(), Msg2+"\nContinue? or replay",new SimpleAttributeSet());
                         } catch (BadLocationException ex) {
                        }
                        Object[] options={"Continue","replay"};
                        JScrollPane jsp=new JScrollPane(jtp);
                        jsp.setPreferredSize(new Dimension(150,20));
                        jsp.setBorder(null);
                         cont=JOptionPane.showOptionDialog(null, jsp, Msg1, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, iconx, options, options[0]);
                        if(cont==0)
                        {
                            inBusy=false;
                            startDate=new Date();
                            isUsed=true;
                        }
                        else
                        {
                            inBusy=false;
                            startDate=new Date();
                            checkv=false;checkd=false;
                            setr=1;
                            startDate=new Date();check=1;
                            ResetAll();sec=0;secCpy=0;
                            
                            checkv=false;checkd=false;
                        }
                        }
                    }
                }
            }
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
         }
         public void checkVictorystatus(){
             
             if(defeat==false && victory==false){
             for(int i=0;i<16;i++){
                 for(int j=0;j<9;j++){
                     if(revealed[i][j]==true && mines[i][j]=='-'){
                         happiness=false;
                         defeat=true;
                         checkd=true;
                         contx=1;
                         cont=1;
                         endDate=new Date();
                         int num=0;
                         for(int b=0;b<16;b++)  {
                            for(int c=0;c<9;c++)  {
                                if(mines[b][c]=='-' && flagged[b][c]==true)
                                    num++;
                            }
                        }
                        points=(num%no_of_mines)*10;
                        val=points;
                     }
                }
             }
           }
             int count=0,set=0;
             for(int i=0;i<16;i++){
                 for(int j=0;j<9;j++){
                     if(flagged[i][j]==true && mines[i][j]=='-'){
                       count++;   
             }
             }
             }
             for(int i=0;i<16;i++){
                 for(int j=0;j<9;j++){
                     if(flagged[i][j]==true && mines[i][j]!='-' ){
                     set=1;
                     }
                     }
                 }
             if(count==no_of_mines && set==0){
                victory=true;
                contx=1;
                cont=1;
                checkv=true;
                endDate=new Date();
                points=no_of_mines*10;
             }
         }
         public void ResetAll(){
             setr=0;
             startDate=new Date();
             Resetter = true;
             victory=false;
             defeat=false;
             checkv=false;checkd=false;
             happiness=true;
             no_of_mines=0;
             Resetter = false;
             helpCount=0;
             inBusy=false;
             points=0;
             val = 0;
             startDate=new Date();
             sec=0;check=0;secCpy=0;
             for(int i=0;i<16;i++){
             for(int j=0;j<9;j++){
                mines[i][j]=0;
                  revealed[i][j]=false;
                  flagged[i][j]=false;
                  flag_count[i][j]=0;
            }
        }
        Random rand1=new Random();
        while(no_of_mines<20){
            index1=rand1.nextInt(100)%15;
            index2=rand1.nextInt(100)%8;
            if(mines[index1][index2]!='-'){
                mines[index1][index2]='-';
            no_of_mines++;
            }

        }
       Neighbours();
         }
         public boolean inSmiley()
         {
             int diff=(int) Math.sqrt(Math.abs(mx-smileyCenterX)*Math.abs(mx-smileyCenterX)+Math.abs(my-smileyCenterY)*Math.abs(my-smileyCenterY));
             if(diff<35)
                 return true;
             return false;
         }
         public boolean inHelp()
         {
            if(mx>=589 && mx<=641 && my>=38 && my<=60)
                return true;
            return false;
         }
         public void setFlag(){
             
             int i=inBoxX();
             int j=inBoxY();
             if(flagged[i][j]==true) flagged[i][j]=false;
             else if(flagged[i][j]==false) flagged[i][j]=true;
             flag_count[i][j]++;
         }
         public void setDoubt(){
             int i=inBoxX();
             int j=inBoxY();
             if(doubt[i][j]==true) doubt[i][j]=false;
             else if(doubt[i][j]==false) doubt[i][j]=true;
             
             
         }
         public int inBoxX(){
             for(int i=0;i<16;i++){
                 for(int j=0;j<9;j++){
                    if(mx>=spacing+i*50 && mx<spacing+i*50+50-2*spacing && my>=spacing+j*50+50+26 && my<spacing+j*50+26+50+50-2*spacing){
                    return(i);
                    }
                 }
             }
             return -1;
         }
         public int inBoxY(){
             for(int i=0;i<16;i++){
                 for(int j=0;j<9;j++){
                    if(mx>=spacing+i*50 && mx<spacing+i*50+50-2*spacing && my>=spacing+j*50+50+26 && my<spacing+j*50+26+50+50-2*spacing){
                    return(j);
                    }
                 }
             }
             return -1;
         }
}