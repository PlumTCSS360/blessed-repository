package controller;

import model.About;
import view.InfoFrame;
import view.WelcomeFrame;

import java.awt.*;
import java.io.File;

/**
 * This class initiate the program. It will check for the existence of a file containing basic user information.
 * If it exists, it will open the WelcomeFrame; else, it will open a InfoFrame to solicit this information.
 * @author Devin Peevy
 * @version 0.1
 */
public class Driver {

    public static void main(String[] args) {
        String location = new File(Driver.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
        File data = new File(location + "/data");
        if (!data.exists()) {
            data.mkdirs();
        }

        String path = About.USER_INFO_FILE_PATH;
        File file = new File(path);

        if (file.exists()) {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new WelcomeFrame();
                }
            });
        } else {
            EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new InfoFrame();
                }
            });
        }
    }
}
