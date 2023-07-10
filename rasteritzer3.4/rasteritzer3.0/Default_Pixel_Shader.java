import java.awt.Color;

public class Default_Pixel_Shader extends Pixel_Shader {

    // a variant of the pexilshader that shades the pixels n a certain way

    // in this case looking at the normal and determining the color on weather it is facing the viewer or not 

    public Vector3D triangle_normal;
    public Color normal_color;

    public Default_Pixel_Shader(){
        super();
    }
    
    @Override
    public void Shade_Pixel(Screen_Vertex vert){
       
        image.set_RGB(normal_color,(int) vert.screen_pos.x,(int) vert.screen_pos.y, vert.screen_pos.z);
    }

    @Override
    public void set_up_shader_for_triangle(Screen_Vertex a, Screen_Vertex b, Screen_Vertex c){
        super.set_up_shader_for_triangle(a, b, c);
        Vector3D ab = Vector3D.subtractVectos3Ds(b.local_pos, a.local_pos);
        Vector3D ac = Vector3D.subtractVectos3Ds(c.local_pos, a.local_pos);
        triangle_normal = Vector3D.cross_product(ab, ac); 
        triangle_normal.normalize();

        float dot =(float) Math.abs(Vector3D.dot_product(triangle_normal, new Vector3D(0,0,-1)));
        if(dot >1){
            dot = 1;
        }
        normal_color = new Color(dot,dot,dot);



    }
}
