/**
 * Copyright (c) 2012, Van Ting <vanting@gmail.com>
 * All rights reserved. Unauthorized use and redistribution of this file is
 * strictly prohibited.
 */
package demo;

import game.v2.Console;
import game.v2.Game;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Examples to demonstrate the use of Game.
 * The Game class provides an interactive game skeleton. It faciliates the frame rate (fps) control and input interface.
 * 
 * @author vanting
 */
public class GameDemo extends Game {

    /*
    You can declare any data fields here for your game as usual.
    */
    private Image img1 = Console.loadImage("/assets/demo/invader_1.png");
    private Image img2 = Console.loadImage("/assets/demo/invader_2.png");
    private Image[] images = {img1, img2};
    private int i = 0;
    private int x = 300;
    private int y = 300;
    
    /*
    Main method
    */
    public static void main(String[] args) {

        /*
        Customize the console window per your need but do not show it yet.
        */
        Console.getInstance()
                .setTitle("Game Demo")
                .setWidth(1000)
                .setHeight(600)
                .setTheme(Console.Theme.DARK);

        /*
        Similar to the Console class, use the chaining setters to configure the game. Call start() at the end of
        the chain to start the game loop.
        */
        new GameDemo()
                .setFps(50)                                             // set frame rate
                .setShowFps(true)                                       // set to display fps on screen
                .setBackground(Console.loadImage("/assets/demo/bg.png"))       // set background image
                .start();                                               // start game loop
    }

    /************************************************************************************************
    There are three abstract methods must be overriden:
        protected abstract void cycle();
        protected abstract void keyPressed(KeyEvent e);
        protected abstract void mouseClicked(MouseEvent e);
    */
    
    /*
    Called regularly at predefined frame rate (fps).
    */
    @Override
    protected void cycle() {

            console.drawImage(x, y, images[i/10]);
            i = ++i % 20;
        
    }

    /*
    Called when pressing a key.
    You can leave this method empty if you do not use it.
    */
    @Override
    protected void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                x -= 10;
                break;
            case KeyEvent.VK_RIGHT:
                x += 10;
                break;
            case KeyEvent.VK_UP:
                y -= 10;
                break;
            case KeyEvent.VK_DOWN:
                y += 10;
                break;
        }
    }

    /*
    Called when mouse-clicking on window.
    You can leave this method empty if you do not use it.
    */
    @Override
    protected void mouseClicked(MouseEvent e) {
        System.out.println("Click on (" + e.getX() + "," + e.getY() + ")");
    }

}
