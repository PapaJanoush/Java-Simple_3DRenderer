import javax.swing.*;
import java.util.*;


public class Window extends JFrame{
    //a extension of the JFrame meant to provide extre functionality if needed

    private List<Panel> panels;

    public Window(int WIDTH, int HEIGHT, String name){
        super(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    public void add_Panel(Panel panel){
        panel.ID = panels.size();
        panels.add(panel);
        add(panel);
    }
    
}
