package controller;

import model.About;
import view.AboutFrame;
import view.WelcomeFrame;

import java.awt.*;
import java.io.File;

/**
 * This class will initiate the program by running a new WelcomeFrame,
 * the initial GUI.
 */
public class Driver {
    public static void main(String[] args) {
        String path = About.USER_INFO_FILE_PATH;
        File file = new File(path);
        if (file.exists()) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new WelcomeFrame();
                    //new AboutFrame();
                }
            });
        }
        else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    //new InfoFrame();
                }
            });
        }
    }
}
