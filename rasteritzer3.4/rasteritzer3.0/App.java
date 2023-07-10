import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;




/* instructions:

- run java App in the console

-a window should open with a bunch of buttons and sliders on the right

- to move and look around in the kanvas:

    press w and s to go forward and backward(when there is no perspective things wont get bigger)

    press a and d to moe left and right

    press x and y to go up and down

    hold r and move the mouse to rotate the camera

-buttons:

    - perspective button:
        activates and deactivates the perspective of the camera

    - z_buffer butto:n
        allows to take a look in to the depth of the picture white meaning close and black meaning far away

    - make x buttons:
        change the mesh of the object in the scene

    -sliders
        allow the rotation of the object 

    

 * 
 * aditional notes:
 * some parts of the programm may not be used by the programm either because their
 * use in not implemented jet or because they got replaced by other ways to do the same thing and 
 * forgotten to delete/ kept just in case it is needed to turn back to them
 * 
 */
public class App {
    public static Window_Manager window_manager = new Window_Manager();
    public static World_manager world_manager = new World_manager();

    public static void main(String[] args) {
        World_manager world_manager = new World_manager();
        Window_Manager window_manager = new Window_Manager();

        World new_world = world_manager.new_world();
        Object plane_one = new_world.new_object();
        plane_one.add_component(new Mesh_Component(plane_one, Mesh.pyramidMesh()));
        plane_one.transform.rot = Quaternion.eulerToQuaternion(0, 0, 0);
        plane_one.add_component(new Renderer_Component(plane_one, "renderer", new Default_Shader()));
        /*
        Object plane_two = new_world.new_object();
        plane_two.add_component(new Mesh_Component(plane_two, Mesh.planeMesh()));
        plane_two.transform.rot = Quaternion.eulerToQuaternion(90, 0, 0);
        plane_two.add_component(new Renderer_Component(plane_one, "renderer",new
        Default_Shader()));
         
        Object plane_three = new_world.new_object();
        plane_three.add_component(new Mesh_Component(plane_three, Mesh.planeMesh()));
        plane_three.transform.rot = Quaternion.eulerToQuaternion(0, 90, 0);
        plane_three.add_component(new Renderer_Component(plane_one, "renderer",new
        Default_Shader()));
        */
        Object camera_object = new_world.new_object();
        Camera_Component camera = new Camera_Component(camera_object);
        camera_object.add_component(camera);
        camera_object.transform.rot = Quaternion.eulerToQuaternion(0, 0, 0);
        camera_object.transform.pos = new Vector3D(0, 0, -3);

        Window x = window_manager.new_window(800, 500, "Test");
        x.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        x.setLayout( new BorderLayout());

        Panel side_panel = new Panel("Panel", 1);
        side_panel.setSize(300, 500);
        side_panel.setPreferredSize(new Dimension(300, 500));
        side_panel.setBackground(new Color(40,40,40));
        side_panel.setLayout( new BorderLayout());

        Camera_Inspector_Panel upper_side_panel = new Camera_Inspector_Panel("upper_side_panel", 2, camera);
        upper_side_panel.setSize(250, 200);
        upper_side_panel.setPreferredSize(new Dimension(250, 200));
        upper_side_panel.setBackground(new Color(80,80,80));

        Object_Inspector_Panel lower_side_panel = new Object_Inspector_Panel( "lower_side_panel", 3, plane_one );
        lower_side_panel.setSize(250, 200);
        lower_side_panel.setPreferredSize(new Dimension(250, 200));
        lower_side_panel.setBackground(new Color(50,50,50));

        x.add(side_panel, BorderLayout.EAST);

        side_panel.add(upper_side_panel, BorderLayout.NORTH);
        side_panel.add(lower_side_panel, BorderLayout.SOUTH);


        Camera_Panel camera_Panel = new Camera_Panel("camera_panel", 666,camera, 500, 500);
        camera_Panel.setPreferredSize(new Dimension(500, 500));
        window_manager.get_window(0).add(camera_Panel, BorderLayout.CENTER);
        camera_Panel.setFocusable(true);
        camera_Panel.requestFocusInWindow();

        x.pack();

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                world_manager.update_worlds();
                x.repaint();
                camera_Panel.requestFocusInWindow();
                System.out.println("  finished cycle");
            }
        }, 0, 100); // Run the update every 16 milliseconds (approximately 60 frames per second)
    }
}