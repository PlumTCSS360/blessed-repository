package controller;

import view.WelcomeFrame;

import java.awt.*;

/**
 * This class will initiate the program by running a new WelcomeFrame,
 * the initial GUI.
 */
public class Driver {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeFrame();
            }
        });
    }
}
