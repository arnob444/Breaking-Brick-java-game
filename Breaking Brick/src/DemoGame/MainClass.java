package DemoGame;

import javax.swing.JFrame;
public class MainClass {

    public static void main(String[] args) {

        JFrame f = new JFrame();                              //JFrame is a class that belongs to the Swing framework.
        f.setTitle("Breaking Brick");
        f.setSize(700, 600);
        f.setLocationRelativeTo(null);                       //JFrame will be displayed in the center of the screen.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //the application should exit and terminate its execution when the user closes the JFrame window.
        f.setVisible(true);                                  //to display the JFrame.
        f.setResizable(false);                               //JFrame will not be resizable by the user.

        GamePlay gamePlay = new GamePlay();
        f.add(gamePlay);
    }
}