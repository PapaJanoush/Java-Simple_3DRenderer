import java.util.ArrayList;
import java.util.List;

public class World {

    // a class meant to store everything about a certain wordl meaning eveything that is in that world and also the inputs that are given to that world
    
    // for ever frame the world is updated and te inputs get reorganised

    //afterwards the world can manipulate itself given the input it has recieved
    public Input input_cache;
    public Input input;
    public Input prev_input;
    public Vector3D test_camera_rotation;

    private List<Object> objects;
    public World(){
        input_cache = new Input(0, 0);
        input = new Input(0, 0);
        prev_input = null;
        objects = new ArrayList<>();
        test_camera_rotation = new Vector3D(0, 180, 0);
    }
    public Object new_object(){
        Object new_onject = new Object(this, Transform.default_Transform());
        objects.add(new_onject);
        return new_onject;
    }

    public List<Object> objects(){
        return objects;
    }

    public void update(){
        // for each frame the world gets updated
        System.out.println("updating world");
        handle_inputs();

        // the camera moves around in the world
        move_and_rotate_the_camera();

        // the camera recalculates its image
        Update_Camera_images();
    }

    public Object get_a_camera_object(){
        for(Object i: objects){
            for(Component t: i.components){
                if(t instanceof Camera_Component){
                    return i;
                }
            }
        }
        return null;
    }

    public void Update_Camera_images(){

        //find the objects that have cameracomponents attached to them and update their images
        for(Object obj: objects){
            for(Component com : obj.components){
                if(com instanceof Camera_Component){
                   Camera_Component cam = (Camera_Component)com;
                   cam.update_image(); 
                }
            }
        }
    }

    public void handle_inputs(){

        // the inputs get reorganised

        prev_input = input;
        input = input_cache;

        //we create a new Inputvariable meant to store all the inputs that are passed to the world during the duratio of the frame
        input_cache = new Input(input_cache.get_mouse_x(), input_cache.get_mouse_y());

        if(prev_input != null){
        input.delta_mouse_x = input.get_mouse_x()- prev_input.get_mouse_x();
        
        input.delta_mouse_y = input.get_mouse_y()- prev_input.get_mouse_y();
        }
    }

    public void move_and_rotate_the_camera(){

        // move the camera arroud in the wordl according to the inputs
        
        Object camera = get_a_camera_object();
        
        if(input.key_pressed("R")){
            test_camera_rotation.x += input.delta_mouse_x;
            test_camera_rotation.z -= input.delta_mouse_y;
        }
        

        if (camera != null){
            camera.transform.rot = Quaternion.eulerToQuaternion(test_camera_rotation.x, test_camera_rotation.y, test_camera_rotation.z);
        }
        if(input.key_pressed("A")){
            camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(-0.1, 0, 0 )));
        }
        if(input.key_pressed("D")){
             camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(0.1, 0, 0)));
        }
        if(input.key_pressed("W")){
             camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(0, 0, 0.1)));
        }
        if(input.key_pressed("S")){
            camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(0, 0, -0.1)));
        }
        if(input.key_pressed("X")){
            camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(0, -0.1, 0)));
       }
       if(input.key_pressed("Y")){
           camera.transform.pos.add(camera.transform.get_transformer3D().transform_matrix.transformed_vector(new Vector3D(0, 0.1, 0)));
       }

    }
}
