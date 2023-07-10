import java.awt.*;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    // a class meant to extend the JPanel and provide additional functionality if needed especially in later stages of development

    String name;
    int ID;
    //all the panels that er contained inside the Panel
    List<Panel> content;
    List<Panel> contained_in;

    public Panel( String name, int ID){
        super();
        this.name = name;
        this.ID = ID;
        content = new ArrayList<>();
        contained_in = new ArrayList<>();
    }    

    public void add_Panel(Panel panel){
        panel.ID = content.size();
        content.add(panel);
        add(panel);
    }
}
