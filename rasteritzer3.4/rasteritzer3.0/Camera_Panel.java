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


public class Camera_Panel extends Panel {
    private Camera_Component camera;
    public int WIDTH;
    public int HEIGHT;

    public Camera_Panel(String name, int ID, Camera_Component camera, int WIDTH, int HEIGHT){
        super(name,ID);
        setSize(WIDTH, HEIGHT);
        this.camera = camera;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        camera.request_camera_resolution(WIDTH, HEIGHT);
        addKeyListener(new KeyListener() {
            /** Handle the key typed event from the text field. */
            public void keyTyped(KeyEvent e) {
            }
            public void keyPressed(KeyEvent e) {
                System.out.println("key pressed: "+ e.getKeyText(e.getKeyCode()));
                camera.world.input_cache.add_pressed_key(e.getKeyText(e.getKeyCode()));;
            }
            public void keyReleased(KeyEvent e) {
                camera.world.input_cache.remove_pressed_key(e.getKeyText(e.getKeyCode()));
            }
        });
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
               camera.world.input_cache.set_lmb_pressed(true);
            }
            public void mouseReleased(MouseEvent e) {
               camera.world.input_cache.set_lmb_pressed(true);
                if (SwingUtilities.isMiddleMouseButton(e)) {
                    camera.world.input_cache.set_middle_mouse_button_pressed(true);;
            }
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                camera.world.input_cache.set_mouse_position(e.getX(), e.getY());;
            }
            public void mouseDragged(MouseEvent e) {
                camera.world.input_cache.set_mouse_position(e.getX(), e.getY());;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(camera.finalImage, 0, 0, null);
    }

    @Override
    public void repaint(){
        super.repaint();
        if(camera != null){
            camera.request_camera_resolution(getWidth(), getHeight());
        }
    }
}
