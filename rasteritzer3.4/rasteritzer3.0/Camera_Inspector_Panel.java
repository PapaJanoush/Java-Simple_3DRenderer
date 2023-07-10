import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Camera_Inspector_Panel extends Panel implements ActionListener {
    private Camera_Component camera;

    private JButton perspective_button;
    private JButton z_buffer_button;
    private JButton reset_camera_button;

    public Camera_Inspector_Panel(String name, int ID, Camera_Component camera){
        super(name, ID);
        this.camera = camera;

        setLayout(new FlowLayout());

        perspective_button = new JButton("perspective");
        perspective_button.addActionListener(this);
        add(perspective_button);

        z_buffer_button= new JButton("unview z buffer");
        z_buffer_button.addActionListener(this);
        add(z_buffer_button);

        reset_camera_button = new JButton("reset camera" );
        reset_camera_button.addActionListener(this);
        add(reset_camera_button);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == perspective_button) {
            if(camera.perspective){
                camera.perspective = false;
                perspective_button.setText("perspective");
            }
            else if(!camera.perspective){
                camera.perspective = true;
                perspective_button.setText("no perspective/oprthografic");
            }
        } 
        else if (e.getSource() == z_buffer_button) {
            if(camera.view_z_buffer){
                //knowing we are looking at the z_buffer / depth of the image
                
                camera.view_z_buffer = false;
                z_buffer_button.setText("view z buffer");
            }
            else{
                camera.view_z_buffer = true;
                z_buffer_button.setText("unview z buffer");
            }
        }
        else if(e.getSource() == reset_camera_button){
            camera.parent.transform.pos = new Vector3D(0, 0, -3);
            camera.parent.world().test_camera_rotation =new Vector3D(0, 180, 0) ;
        }
        
    }

}
