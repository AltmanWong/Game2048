/*
 * Date: 
 * Student Name:
 * Student ID:
 * 
 */
package student;

import game.v2.Console;
import game.v2.Game;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Game skeleton of 2048
 *
 * @author Van
 */
public class My2048 extends Game {

    /*
     You can declare any data fields here for your game as usual.
     */

    private Board board;        // initialize it with your own implementation

    /*
     Main method
     */
    public static void main(String[] args) {
        Board.initialize();
        Board.newTile();
        Board.musicplayer();
        /*
         Customize the console window per your need but do not show it yet.
         */
        Console.getInstance()
                .setTitle("Game 2048")
                .setWidth(480)
                .setHeight(640)
                .setTheme(Console.Theme.LIGHT);

        /*
         Similar to the Console class, use the chaining setters to configure the game. Call start() at the end of
         the chain to start the game loop.
         */
        new My2048()
                .setFps(50) // set frame rate
                .setShowFps(true) // set to display fps on screen   
                .setBackground(Console.loadImage("/assets/board.png")) // set background image
                .start();                                               // start game loop
        

    }
    

    /**
     * **********************************************************************************************
     * There are three abstract methods must be overriden: protected abstract
     * void cycle(); protected abstract void keyPressed(KeyEvent e); protected
     * abstract void mouseClicked(MouseEvent e);
     */
    @Override
    protected void cycle() {
        Board.draw();
            
    }

    @Override
    protected void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                Board.move(Board.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                Board.move(Board.Direction.RIGHT);
                break;
            case KeyEvent.VK_UP:
                Board.move(Board.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                Board.move(Board.Direction.DOWN);
                break;
        }
    }

    @Override
    protected void mouseClicked(MouseEvent e) {
    System.out.println("Click on (" + e.getX() + "," + e.getY() + ")");
        int x = e.getX();
        int y = e.getY();
        if (x>=353 && x<=462){
            if(y>=108 && y<=145){
                Board.initialize();
                Board.newTile();
            }
        }
        if(Board.hasLegalMove()==false||Board.isWon()==true){
            if(x>=165 && x<=315){
                if(y>=420 && y<=470){
                    Board.initialize();
                    Board.newTile();
                }
            }
            if(x>=165 && x<=315){
                if(y>=500 && y<=550){
                    Board.keepgoing.set(true);
                    Board.click.set(true);
                }
            }
        }
        if(Board.timecheck()==true){
            if(x>=165 && x<315){
                if(y>=420 && y<=470){
                    Board.reverse.set(true);
                    Board.click.set(true);
                    Board.initialize();
                    Board.newTile();                    
                }
                if(y>=500 && y<=550){
                    Board.reverse.set(false);
                    Board.click.set(true);

                }
            }
        }
    }

}
