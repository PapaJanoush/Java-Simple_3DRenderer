import java.util.ArrayList;
import java.util.List;

public class Object {

    // a abstract datastructure meant to represent something in the world(most of the time a mesh but also with the option of representing other things)

    //has a world reference in order to be able to acces the world from the object if needed
    //has a transform that captures the rotation location and scale of the object in the worlds
    //has a list of components which are meant to extend and define the functionalty of a given object and are meant to interact either with the camera the world or with the object or any other of its components
    //each object ant its compoents are supposed to be updated every frame but that is not implemented yet
    
    private World world; 
    public Transform transform;
    public List<Component> components;

    public Object(World world, Transform transform){
        this.world = world;
        this.transform = transform;
        components = new ArrayList<>();
    }

    public void add_component(Component component){
        components.add(component);
        component.parent = this;
    }

    public World world(){
        return world;
    }
}
