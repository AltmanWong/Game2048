/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student;

/**
 * Abstraction of the 2048 game board.
 *
 * @author Van
 */
import game.v2.Console;
import game.v2.Game;
import java.awt.Color;
import java.awt.Font;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;



public interface Board{
   
    public static int[][] position = new int[4][4]; 
    public static String font = "Myraid Pro";
    public static AtomicBoolean reverse = new AtomicBoolean(false);
    public static AtomicBoolean click = new AtomicBoolean(false);
    public static AtomicBoolean keepgoing = new AtomicBoolean(false);
    public static int[] xcoordinate = {25, 135, 245, 355};
    public static int[] ycoordinate = {185, 295, 405, 515};
    public static int[] movevalue = {0, 0};
         
    static boolean reversecheck(){
        boolean reversecheck = Boolean.valueOf(reverse.toString());
        return reversecheck;
    }
    
    static boolean clickcheck(){
        boolean clickcheck = Boolean.valueOf(click.toString());
        return clickcheck;
    }
    
    static boolean keepgoingcheck(){
        boolean keepgoingcheck = Boolean.valueOf(keepgoing.toString());
        return keepgoingcheck;
    }
    
    static boolean timecheck(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date d = new Date();
        String time = sdf.format(d);
        boolean timecheck = false;
        if("20:48".equals(time)){
            timecheck = true;
        }
        return timecheck;
    }
    
    public static void initialize(){
        keepgoing.set(false);
        for (int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                position[i][j] = 0;
            }
        }
    }


    /**
     * The four directions of move.
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
    

    /**
     * Check if the player has legal moves. There is no legal moves if there are
     * no empty spaces and no adjacent tiles with the same value.
     *
     * @return true if player has legal move; otherwise false
     */
    static boolean hasLegalMove() {
        boolean haslegalmove = true;
        //hcount for horizontal checking
        //vcount for vertical checking
        //count for counting the valid block
        int hcount = 0, vcount = 0, count = 0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
            //Check leftward and rightward
                switch(i){
                    case 0:
                        if(position[i][j]!=position[i+1][j]){
                            hcount++;
                        }
                        break;
                    case 3:
                        if(position[i][j]!=position[i-1][j]){
                            hcount++;
                        }
                        break;
                    default:
                        if(position[i][j]!=position[i+1][j]&&position[i][j]!=position[i-1][j]){
                            hcount++;
                        }
                        break;
                }
                //check upward and downward
                switch(j){
                    case 0:
                        if(position[i][j]!=position[i][j+1]){
                            vcount++;
                        }
                    break;
                    case 3:
                        if(position[i][j]!=position[i][j-1]){
                            vcount++;
                        }
                    break;
                    default:
                        if(position[i][j]!=position[i][j+1]&&position[i][j]!=position[i][j-1]){
                            vcount++;
                        }
                    break;
                }
                if(reversecheck() == false){
                    if(position[i][j] != 0 && position[i][j] != 2048){
                        count++;
                    }
                }
                else
                    {
                        if(position[i][j] != 0 && position[i][j] != 2){
                            count++;
                        }
                    }
                
            }
        }
        if(vcount == 16 && hcount == 16 && count == 16){
            haslegalmove = false;
        }
        return haslegalmove;
    }

    /**
     * Take a move of the tiles in the chosen direction. This call should lead
     * to a resultant grid of the merged tiles. No new tile is generated at this
     * call.
     *
     * @param dir the chosen direction (UP, DOWN, LEFT or RIGHT) of a move
     */
    static void move(Direction dir){
        boolean checkmovement = false;
        switch(dir){
            case UP:
                for(int i=0; i<=3; i++){
                    for(int j=0; j<=3; j++){
                        if(position[i][j]!=0){
                            for(int k=j-1; k>=0; k--){
                                boolean zero = false;
                                if(position[i][j]==position[i][k]){
                                    //Check whether there are 0 between 2 tiles
                                    int dis = j-k-1;
                                    switch(dis){
                                        case 0:
                                            zero = true;
                                            break;
                                        case 1:
                                            if(position[i][j-1]==0){
                                                zero = true;
                                            }
                                        break;
                                        case 2:
                                            if(position[i][j-1]==0 && position[i][j-2]==0){
                                                zero = true;
                                            }
                                        break;
                                        default:
                                            zero = false;
                                            break;
                                    }
                                    //Perform addition
                                    if(zero == true){
                                        if(reversecheck() == false){
                                            position[i][k]+=position[i][j];
                                        }
                                        else
                                        {
                                            position[i][k]/=2;
                                        }
                                            position[i][j]=0;
                                            checkmovement = true;
                                    }
                                }
                            }
                            for(int k=0; k<j; k++){
                                if(position[i][k]==0){
                                    position[i][k] = position[i][j];
                                    position[i][j]=0;
                                    checkmovement = true;  
                                }  
                            }
                        }
                    }
                }
                if(hasLegalMove()==true){
                    if(checkmovement==true){
                        spawnTile();
                    }
                }
                break;
            case DOWN:
                for(int i=0; i<=3; i++){
                    for(int j=3; j>=0; j--){
                        if(position[i][j]!=0){
                            for(int k=0; k<=j-1; k++){
                                boolean zero = false;
                                if(position[i][j]==position[i][k]){
                                    int dis = j-k-1;
                                    switch(dis){
                                        case 0:
                                            zero = true;
                                            break;
                                        case 1:
                                            if(position[i][j-1]==0){
                                                zero = true;
                                            }
                                        break;
                                        case 2:
                                            if(position[i][j-1]==0 && position[i][j-2]==0){
                                                zero = true;
                                            }
                                        break;
                                        default:
                                            zero = false;
                                            break;
                                    }
                                    if(zero == true){
                                        if(reversecheck() == true){
                                            position[i][k]/=2;
                                        }
                                        else
                                        {
                                            position[i][k]+=position[i][j];
                                        }
                                        position[i][j]=0;
                                        checkmovement = true;
                                    }
                                }
                            }
                            for(int k=3; k>j; k--){
                                if(position[i][k]==0){
                                    position[i][k] = position[i][j];
                                    position[i][j]=0;
                                    checkmovement = true;
                                }  
                            }
                        }
                    }
                }
                if(hasLegalMove()==true){
                    if(checkmovement==true){
                        spawnTile();
                    }
                }
            break;
            case LEFT: 
                for(int i=0; i<=3; i++){
                    for(int j=0; j<=3; j++){
                        if(position[i][j]!=0){
                            for(int k=i-1; k>=0; k--){
                                boolean zero = false;
                                if(position[i][j]==position[k][j]){
                                    int dis = i-k-1;
                                    switch(dis){
                                        case 0:
                                            zero = true;
                                            break;
                                        case 1:
                                            if(position[i-1][j]==0){
                                                zero = true;
                                            }
                                        break;
                                        case 2:
                                            if(position[i-1][j]==0 && position[i-2][j]==0){
                                                zero = true;
                                            }
                                        break;
                                        default:
                                            zero = false;
                                            break;
                                    }
                                    if(zero == true){
                                        if(reversecheck()==true){
                                            position[k][j]/=2;
                                        }
                                        else
                                        {
                                            position[k][j]+=position[i][j];
                                        }
                                        position[i][j]=0;
                                        checkmovement = true;
                                    }
                                }
                            }
                            for(int k=0;k<=i;k++){
                                if(position[k][j]==0){
                                    position[k][j] = position[i][j];
                                    position[i][j]=0;
                                    checkmovement = true;
                                }
                            }
                        }
                    }
                }
                if(hasLegalMove()==true){
                    if(checkmovement==true){
                        spawnTile();
                    }
                }
            break;
            case RIGHT: 
                for(int i=3; i>=0; i--){
                    for(int j=0; j<=3; j++){
                        if(position[i][j]!=0){
                            for(int k=0; k<=i-1; k++){
                                boolean zero = false;
                                if(position[i][j]==position[k][j]){
                                    int dis = i-k-1;
                                    switch(dis){
                                        case 0:
                                            zero = true;
                                            break;
                                        case 1:
                                            if(position[i-1][j]==0){
                                                zero = true;
                                            }
                                        break;
                                        case 2:
                                            if(position[i-1][j]==0 && position[i-2][j]==0){
                                                zero = true;
                                            }
                                        break;
                                        default:
                                            zero = false;
                                            break;
                                    }
                                    if(zero == true){
                                        if(reversecheck() == true){
                                            position[k][j]/=2;
                                        }
                                        else
                                        {
                                            position[k][j]+=position[i][j];
                                        }
                                        position[i][j]=0;
                                        checkmovement = true;
                                    }
                                }
                            }
                            for(int k=3;k>=i;k--){
                                if(position[k][j]==0){
                                    position[k][j] = position[i][j];
                                    position[i][j]=0;
                                    checkmovement = true;                            
                                }
                            }
                        }
                    }
                }
                if(hasLegalMove()==true){
                    if(checkmovement==true){
                        spawnTile();
                    }
                }
            break;

        }
    }
  
    //This one not working :o)  
   static void animation(int init, int dest, Direction Dir){
        if(init != dest){
            switch(Dir){
                case UP:
                    movevalue[0] -=1;
                    System.out.println(movevalue[0]);
                    break;
                case DOWN:
                    movevalue[0] +=1;
                    break;
                case LEFT:
                    movevalue[1] -=1;
                    break;
                case RIGHT:
                    movevalue[1] +=1;
                    break;
            }
        }
    }
    
    static void TileMovement(){
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                Color tilecolor;
                Color textcolor = new Color(255, 255, 255);
                int xtextposition;
                int ytextposition = ycoordinate[j]+65;
                if(position[i][j]!=0){
                    switch(position[i][j]){
                        case 2:
                        case 4:
                        case 8:
                            tilecolor = new Color(236, 224, 200);
                            xtextposition = xcoordinate[i]+35;
                            textcolor = new Color(143, 122, 102);
                            break;
                        case 16:
                        case 32:
                        case 64:
                            tilecolor = new Color(239, 176, 122);
                            xtextposition = xcoordinate[i]+30;
                            break;
                        case 128:
                        case 256:
                        case 512:
                            tilecolor = new Color(242, 216, 106);
                            xtextposition = xcoordinate[i]+15;
                            break;
                        default:
                            tilecolor = new Color(226, 185, 19);
                            xtextposition = xcoordinate[i]+6;
                            break;
                    }
                    Console.getInstance().drawRectangle(xcoordinate[i], ycoordinate[j], 100,100, tilecolor, 20);
                    Console.getInstance().drawText(xtextposition, ytextposition, String.valueOf(position[i][j]),  new Font(font, Font.BOLD, 40), textcolor);
                }
            }
        }
    }
    
    
    /**
     * Check if the game is won. The game is won when a tile with a value of
     * 2048 appears on the board.
     *
     * @return true if the game is won; otherwise false
     */
    static boolean isWon(){
        boolean win=false;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(reversecheck() == true){
                    if(position[i][j]==2){
                        win = true;
                    }
                }
                else
                {
                    if(position[i][j]==2048){
                        win=true;
                    }
                }
            }
        }
        return win;
    }

    /**
     * Set the value of the new tile generated subsequently. Once the value is
     * set, all subsequent tiles will be generated with this value.
     *
     * This method could be used to lower the game difficulty by setting a
     * higher new tile value, which will lead to significantly less number of
     * moves required to finish the game.
     *  
     * @return 
     */
    public static int setNewTileValue(){
        //Random out the number spawn
        int n=0;
        double i = Math.random();
        if(reversecheck() == true){
            if(i<0.7){
                n = 2048;
            }
            else
            {
                n = 1024;
            }
        }
        else{
            if(i<0.7){
                n=2;
            }
            else
            {
                n=4;
            }
        }
        return n;
    }

    static void musicplayer(){
        try {
            File yourFile = new File("SleepAway.wav");
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(yourFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Thread.sleep(10000); // looping as long as this thread is alive
            clip.start();
        }
        catch (Exception e) {
            System.out.println("File not found");
        }    
    }

    /**
     * Generate a new tile of the set value at a random empty position of the
     * grid.
     */
    public static void newTile(){
        int i=0, j=0, count=2;
    // for the intialization   
        while (count !=0){
            i = (int)(Math.random()*4);
            j = (int)(Math.random()*4);
            while(position[i][j] == 0){
                position[i][j] = setNewTileValue();
                count--;
            }
        }
    //for testing
        /*position [0][0] = 2048;
        position [1][1] = 2;
        position [2][2] = 16;
        position [3][3] = 128;
        position [1][0] = 2;
        position [1][1] = 64;
        position [1][2] = 128;
        position [1][3] = 256;
        position [2][0] = 2;
        position [2][1] = 1024;
        position [2][2] = 2048;
        position [2][3] = 2;
        position [3][0] = 4;
        position [3][1] = 8;
        position [3][2] = 16;
        position [3][3] = 32;*/
    }
    
    public static void spawnTile(){
        int count = 1;
        while(count ==1){
        int i=(int)(Math.random()*4);
        int j=(int)(Math.random()*4);
            if(position[i][j] == 0){
                position[i][j] = setNewTileValue();
                count--;
            }
        }
    }
    
    /*1*
     * Draw this board to game console. This call should further invoke the
     * console's drawing methods to display the current state of itself on
     * screen, including the 4x4 grid, scoreboard and game messages.
     */
    public static void draw(){
        TileMovement();
       if(hasLegalMove()==false){
            Console.getInstance().drawRectangle(25, 185, 430, 430, new Color(240, 237, 218, 180));
            Console.getInstance().drawText(140, 380, "YOU LOSE", new Font(font, Font.BOLD, 40), new Color(143, 122, 102));
            Console.getInstance().drawRectangle(165, 420, 150, 50, new Color(143, 122, 102));
            Console.getInstance().drawText(176, 455, "New Game", new Font(font, Font.BOLD, 25), new Color(250, 248, 239));
        }
       if(isWon()==true && keepgoingcheck() == false){
            Console.getInstance().drawRectangle(25, 185, 430, 430, new Color(240, 237, 218, 180));
            Console.getInstance().drawText(150, 380, "YOU WIN", new Font(font, Font.BOLD, 40), new Color(143, 122, 102));
            Console.getInstance().drawRectangle(165, 420, 150, 50, new Color(143, 122, 102));
            Console.getInstance().drawText(176, 455, "New Game", new Font(font, Font.BOLD, 25), new Color(250, 248, 239));
            Console.getInstance().drawRectangle(165, 500, 150, 50, new Color(143,122,102));
            Console.getInstance().drawText(170, 535, "Keep Going", new Font(font, Font.BOLD, 25), new Color(250,248,239));
        }
       if(reversecheck()==false && timecheck()==true && clickcheck() == false){
            Console.getInstance().drawRectangle(25, 185, 430, 430, new Color(240, 237, 218, 180));
            Console.getInstance().drawText(150, 380, "REVERSE?", new Font(font, Font.BOLD, 40), new Color(143, 122, 102));
            Console.getInstance().drawRectangle(165, 420, 150, 50, new Color(143, 122, 102));
            Console.getInstance().drawText(213, 455, "Yes", new Font(font, Font.BOLD, 25), new Color(250, 248, 239));
            Console.getInstance().drawRectangle(165, 500, 150, 50, new Color(143,122,102));
            Console.getInstance().drawText(220, 535, "No", new Font(font, Font.BOLD, 25), new Color(250,248,239));
        }             
       int x=0;
       if(getScore()<10){
           x = 25;
       }
       else
           if(getScore()<100){
               x = 15;
           }
           else
               if(getScore()<1000){
                   x=10;
               }
               else
                   if(getScore()<10000){
                       x=0;
                   }
       Console.getInstance().drawText(250+x, 80, String.valueOf(getScore()),  new Font(font, Font.BOLD , 25), Color.white);
        try {
            if(getBestScore()<10){
                x = 25;
            }
            else
                if(getBestScore()<100){
                    x = 15;
                }
                else
                    if(getBestScore()<1000){
                        x=10;
                    }
                    else
                        if(getBestScore()<10000){
                            x=0;
                        }
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("error in get best score");
        }
        try {
            Console.getInstance().drawText(347+x, 80, String.valueOf(getBestScore()),  new Font(font, Font.BOLD , 25), Color.white);
        } catch (IOException ex) {
            Logger.getLogger(Board.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Convert this board to a 4x4 integer 2D-array. The upper left corner of
     * the board is at array position [0,0]. Each tile is represented by an
     * integer value at the corresponding position of the array. An empty cell
     * on the board is represented by a zero value in the array.
     *
     * @return a 2D array representation of the 4x4 grid
     */
    int[][] toArray();

    /**
     * Set tiles by a 4x4 integer 2D-array. Reset all tiles according to the
     * array representation.
     *
     * @param array an 2D array representation of the 4x4 grid
     */
    void setArray(int[][] array);

    /**
     * Get player's current score.
     *
     * @return current score
     */
    public static int getScore(){
        int score =0;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                score += position[i][j];
            }
        }
        //getBestScore();
        return score;
    }

    
    static int getBestScore() throws FileNotFoundException, IOException{
        int bestscore = 0;
        try{
        BufferedReader br = new BufferedReader(new FileReader("score.txt"));  
        //System.out.println(Integer.valueOf(br.readLine()));
        bestscore = Integer.valueOf(br.readLine());
        //System.out.println(bestscore);
        if(bestscore<getScore()){
            bestscore = getScore();
            setScore();
        }
        br.close();
        }
        catch(IOException e){
            File file = new File("score.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(String.valueOf(0));
            out.close();
        }
        return bestscore;
    }    
    
    static void setScore(){
        try 
        {
            FileWriter fw = new FileWriter("score.txt", false);
                BufferedWriter out = new BufferedWriter(fw);
                out.write(String.valueOf(getScore()));
                out.close();
                fw.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
    
    
            
    /**
     * Set player's current score.
     *
     * @param score value of current score
     */
    /*public static void setScore(int score) throws FileNotFoundException, IOException{
        File in = new File("score.txt");
        FileWriter writescore = new FileWriter("score.txt");
        if(hasLegalMove() == false||isWon() == true){
            writescore.write(String.valueOf(getScore()));
        }
      }*/

    /**
     * Get player's best score.
     *
     * @return best score
     */
    /*static int getBestScore() throws FileNotFoundException{
        File in = new File("score.txt");
        Scanner scanscore = new Scanner(in);
                
        int bestscore = scanscore.nextInt();
        if(bestscore<getScore()){
            bestscore = getScore();
        }
        return bestscore;
    }*/
    /**
     * Set player's best score.
     *
     * @param bestScore value of best score
     */
    /*static void setBestScore(int bestScore) throws IOException{
        File in = new File("score.txt");
        FileWriter writescore = new FileWriter("score.txt");
        writescore.write(String.valueOf(getBestScore()));
        
        writescore.close();
    }*/
    
