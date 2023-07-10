public class Pixel_Shader {
    // a class that is meant to shade a pixel on a Rasterizer_image given the interpolated screenvertexs of a triangle
   
    // since hunders of pixels will be shaded via the pixelshader for each triangle drawn instead of passing the 
    // vertecxes the rasterazerimage etc. to the pixelshader directly for every single pixel would be wastfull
    // in performance since all of those parameters are the same for each trianglgle that is drawn 
    //these four parameters are rather supposed to be set before itterating through each pixel so that they dont have to be passed every single time


    public Rasterizer_Image image; 
    public Camera_Component cam;
    public Object obj;
    public Screen_Vertex a,b,c;

    public Pixel_Shader(){
        image = null;
        cam = null;
        obj = null;
        a = null;
        b = null;
        c = null;
    }
    
    public void Shade_Pixel(Screen_Vertex vert){
    }

    public void set_up_shader_for_triangle(Screen_Vertex a, Screen_Vertex b, Screen_Vertex c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
}
