import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Object_Inspector_Panel extends Panel implements ActionListener, ChangeListener {
    //a panel to access and manipulate a given object through the window
    private Object object;

    private JButton plane_button;
    private JButton cube_button;
    private JButton pyramid_button;
    private JSlider x_rotation;
    private JSlider y_rotation;
    private JSlider z_rotation;
    private JSlider x_scale;
    private JSlider y_scale;
    private JSlider z_scale;

    public Object_Inspector_Panel(String name, int ID, Object object){




        super(name, ID);
        this.object = object;

        setLayout(new FlowLayout());

        plane_button = new JButton("make plane");
        plane_button.addActionListener(this);
        add(plane_button);

        cube_button = new JButton("make cube");
        cube_button.addActionListener(this);
        add(cube_button);

        pyramid_button = new JButton("make pyramid");
        pyramid_button.addActionListener(this);
        add(pyramid_button);

        x_rotation = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        x_rotation.addChangeListener(this);
        add(x_rotation);

        y_rotation = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        y_rotation.addChangeListener(this);
        add(y_rotation);

        z_rotation = new JSlider(JSlider.HORIZONTAL, -180, 180, 0);
        z_rotation.addChangeListener(this);
        add(z_rotation);

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == plane_button) {
            for(Component com: object.components){
                if(com instanceof Mesh_Component){
                    Mesh_Component mesh = (Mesh_Component) com;
                    mesh.reassign_mesh(Mesh.planeMesh());
                }
            }
        } 
        else if (e.getSource() == cube_button) {
            for(Component com: object.components){
                if(com instanceof Mesh_Component){
                    Mesh_Component mesh = (Mesh_Component) com;
                    mesh.reassign_mesh(Mesh.cubeMesh());
                }
            }
        }
        else if (e.getSource() == pyramid_button) {
            for(Component com: object.components){
                if(com instanceof Mesh_Component){
                    Mesh_Component mesh = (Mesh_Component) com;
                    mesh.reassign_mesh(Mesh.pyramidMesh());
                }
            }
        }
    }

    public void stateChanged(ChangeEvent e) {
        
        int x_rot = x_rotation.getValue();
        int y_rot = y_rotation.getValue();
        int z_rot = z_rotation.getValue();
        object.transform.rot = Quaternion.eulerToQuaternion(x_rot, y_rot, z_rot);
        System.out.println("X Rotation changed to " + x_rot);
    
    }
}
