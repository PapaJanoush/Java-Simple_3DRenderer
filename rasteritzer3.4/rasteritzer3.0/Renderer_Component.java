public class Renderer_Component extends Component {
    //a component attached to a object in order to make it get drawn on the scrren by the camera

    //for each object the camera calls every Renderer_Component making it upt to the shader in that component to draw what ever the shader is prgrammed to draw
    //this could be literally anything but in most cases the shader interacts with the mesh and draws it on the screen

    public Shader shader;
    public Renderer_Component(Object parent, String name, Shader shader){
        super(parent, name);
        this.shader = shader;
    }

    public void shade_object(Camera_Component camera){
        shader.shade_object( parent, camera);
    }
}
