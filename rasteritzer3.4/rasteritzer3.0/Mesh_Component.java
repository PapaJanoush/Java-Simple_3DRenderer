import java.util.ArrayList;
import java.util.List;

public class Mesh_Component extends Component{

    //another component class that can be added to an object in a world in order to add extra functionality and behaviour in this case a class holding the mesh of a object 

    //it stores two meshes the original an the real mesh the original mesh is supposed to hold the information of the mesh but with out ay changes beeing applyed to it by other parts of the programm such as a moved vertex
    // the real mesh holds the mesh but with the posibility to acces it and make changes to it so that other parts of the programm can manipulate the mesh if needed

    // both the real mesh and the original mesh represent the mesh in the LOCAL COORDINATESYSTEM OF THE OBJECT AND NOTIN WORLD SPACE
    private Mesh original_mesh;
    public Mesh real_mesh;
    public Mesh_Component(Object parent, Mesh mesh){
        super(parent);
        original_mesh = mesh;
        real_mesh = mesh.clone();
    }

    public void reassign_mesh(Mesh mesh){
        original_mesh = mesh;
        real_mesh = mesh.clone();
    }

    public void update_real_mesh(){
        real_mesh = original_mesh.clone();
    }
}
