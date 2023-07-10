import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JPanel;

public class Window_Manager{

    // the class responsible for managing all the windows in the app
    
    private List<Window> windows;
    public Window_Manager(){
        System.out.println("creating window manager");
        windows = new ArrayList<>();
    }

    public Window new_window(int WIDTH, int HEIGHT, String name){
        Window new_window = new Window(WIDTH, HEIGHT, name); 
        windows.add(new_window);
        return new_window;
    }

    public Window get_window(int index){
        return windows.get(index);
    }

    public Window new_World_Window( World World, Camera_Component camera, String name){
        Window window = new Window(700, 700, name);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());

        windows.add(window);        

        Camera_Panel camera_Panel = new Camera_Panel("Camera Panel" , 1, camera, 500, 500);
        camera_Panel.setFocusable(true);
        camera_Panel.requestFocusInWindow();

        window.add(camera_Panel, BorderLayout.WEST);
        

        JPanel smallerPanel = new JPanel();
        window.add(smallerPanel, BorderLayout.EAST);

        return window;
    }

    public void repaint_windows(){
        for(Window win : windows){
            win.repaint();
        }
    }
}
