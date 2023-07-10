import javax.security.auth.x500.X500Principal;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Function;
import java.util.function.BiFunction;

public class Camera_Component extends Component {

    // a class that is atached to a Object in a wordl in order to utilize it as a camera
    // this class is respnsible for drawing and saving the image that the camera sees from the point of view of the object it is attached to and is usuall accessed by the Camer_panel to draw that image
    public boolean view_z_buffer; 
    public boolean perspective = false;
    public BufferedImage finalImage;
    public World world;
    public double fov_x = 500;
    public double fov_y = 500;
    public int width = 100;
    public int height = 100;
    public Rasterizer_Image image;
    public Transformer3D camera_transformer_inverse;
    public int center_x = 500;
    public int center_y = 500;
    public Pixel_Shader current_pixel_shader;

    public Camera_Component(Object parent) {
        super(parent);
        if (parent != null) {
            world = parent.world();
        }
        image = new Rasterizer_Image(1, 1);
        finalImage = new BufferedImage(100,100, BufferedImage.TYPE_4BYTE_ABGR);
        view_z_buffer = true;
    }

    public static void main(String[] args) {
        JFrame test = new JFrame();
        JPanel panel = new JPanel();
        Camera_Component cam = new Camera_Component(null);
        Vector3D a = new Vector3D(100, 1000, 3);
        a.print("");
        Vector3D b = cam.vector_from_local_to_screen(a);
        b.print("");
        Vector3D c = cam.vector_from_screen_to_local(b);
        c.print("");

    }

    public void update_image() {
        // the method for updating the image of a camera

        // first set up the variables needed
        System.out.println("updating image");
        image = new Rasterizer_Image(width, height);

        // a quick fix to skip handling the fov for now due to time constraints
        if(perspective == true){
            fov_x = 500;
            fov_y = 500;
        }

        else{
            fov_x = 100;
            fov_y = 100;

        }


        center_x = width / 2;
        center_y = height / 2;
        World world = parent.world();
        camera_transformer_inverse = parent.transform.get_transformer3D().inverse();
        if (parent == null) {
            System.out.println("CAMERA IS NULLLL");
            return;
        }

        // the main part of the method

        //each object in the world gets scanned for having a renderer, a component that is supposed to draw things to the screen

        //once a renderer is found it is called in order to make that component draw on the image of the camera


        for (Object i : world.objects()) {
            Transformer3D transformer = i.transform.get_transformer3D();
            for (Component k : i.components) {
                if (k instanceof Renderer_Component) {
                    Renderer_Component r = (Renderer_Component) k;
                    r.shade_object(this);
                }
            }
        }

        image = image;
        if(view_z_buffer){
            finalImage = image.z_buffer_to_image();
        }
        else{
            finalImage = image.image_buffer;
        }
    }

    public Screen_Vertex Vertex3D_to_Screen_Vertex(Vertex3D vert, Transformer3D transformer) {
        Vector3D vert_local = camera_transformer_inverse
                .get_inverse_transformed_vector(transformer.get_transformed_vector(vert.pos));

        Vector3D vert_nrm = camera_transformer_inverse.unscaled_transform_matrix
                .transformed_vector(transformer.unscaled_transform_matrix.transformed_vector((vert.normal)));

        Vector3D vert_screen = vector_from_local_to_screen(vert_local);

        return new Screen_Vertex(vert_local, vert_screen, vert_nrm, vert);
    }

    public void Draw_Triangle(Screen_Vertex a, Screen_Vertex b, Screen_Vertex c, Pixel_Shader pixel_Shader) {
        // this is the method where the triangles get drawn to the screen

        // given the vertexes of a triangle and a shader this part of the programm iterattes through each
        // pixel on the screen that is contained by the triangle taking into account if there is perspective or not

        // for each pixel the vertexshader gets call an it is that shaders job to shade in the given pixel at x and
        // y given an interpolated vertex that coresponds to that pixel ( the interpolation currently only interpolates the screen coordinates)

        current_pixel_shader = pixel_Shader;
        Screen_Vertex[] screen_verts = sort_Screen_vertexes_with_lowest_screen_y_first(a, b, c);

        pixel_Shader.set_up_shader_for_triangle(a, b, c);

        if (perspective == false) {
            if (is_leftsided_Triangle(screen_verts)) {
                /*  we know that the triangle bends to the right
                and that therfore that the x values of the right line will be bigger than the ones on the left

                                    ______
                                ______|      |
                        ____|            |
                        |_______________|
                            |___       |
                                |___  |             
                                    */ 



                /*  draw the lower part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                            ____________
                            |___       |
                                |___  |             
                                    */      



                // in order to prevent iterating throgh y coordinates that are not on the sceen the start and endpoints
                // get callculated taking in to account if the screenvertexes are out of the bounds of the screen
                
                int clamped_y_start1 = (int)- Math.min(screen_verts[0].screen_pos.y+center_y,0);
                int clamped_y_end1 = (int )(screen_verts[1].screen_pos.y - screen_verts[0].screen_pos.y)-(int)Math.max(0, screen_verts[1].screen_pos.y - center_y);

                Screen_Vertex lower_x = screen_verts[0].clone();
                Screen_Vertex higher_x = screen_verts[0].clone(); 
                for (int y = clamped_y_start1; y <= clamped_y_end1 ; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(lower_x, screen_verts[0], screen_verts[1], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(higher_x, screen_verts[0], screen_verts[2], y);
                    draw_horizontal_line_orthografic(lower_x, higher_x);
                }

                /*  draw the top part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                                ______
                        ______|     |
                    _____|           |
                    |______________*/       


                int clamped_y_start2 = (int)- Math.min(screen_verts[1].screen_pos.y+center_y,0);
                int clamped_y_end2 = (int)(screen_verts[2].screen_pos.y - screen_verts[1].screen_pos.y)-(int)Math.max(0, screen_verts[2].screen_pos.y - center_y);

                for (int y = clamped_y_start2; y <= clamped_y_end2; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(lower_x, screen_verts[1], screen_verts[2], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(higher_x, screen_verts[0], screen_verts[2], y+screen_verts[1].screen_pos.y-screen_verts[0].screen_pos.y);
                    draw_horizontal_line_orthografic(lower_x, higher_x);
                }
                
            }

            else {
                
                //knowing the THE triangle is rightsided (the bend is on the right)

                /*  
        
                _______
                |      |______
                |            |______
                |__________________|
                |        ____|
                    |  ____|
                    */
        


                /*  draw the lower part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                ______________
                |        ____|
                    |  ____|
                    */    
        

                int clamped_y_start1 = (int)- Math.min(screen_verts[0].screen_pos.y+center_y,0);
                int clamped_y_end1 = (int)(screen_verts[1].screen_pos.y - screen_verts[0].screen_pos.y)-(int)Math.max(0, screen_verts[1].screen_pos.y - center_y);

                Screen_Vertex lower_x = screen_verts[0].clone();
                Screen_Vertex higher_x = screen_verts[0].clone(); 
                for (int y = clamped_y_start1; y <= clamped_y_end1 ; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(higher_x, screen_verts[0], screen_verts[1], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(lower_x, screen_verts[0], screen_verts[2], y);
                    draw_horizontal_line_orthografic(lower_x, higher_x);
                }

                
                /*  draw the top part of the trinagle by scaning from the left to the right for each y and shading in each pixel
        
                _______
                |      |______
                |            |______
                |__________________|
            
            */   

                int clamped_y_start2 = (int)- Math.min(screen_verts[1].screen_pos.y+center_y,0);
                int clamped_y_end2 = (int)(screen_verts[2].screen_pos.y - screen_verts[1].screen_pos.y)-(int)Math.max(0, screen_verts[2].screen_pos.y - center_y);

                for (int y = clamped_y_start2; y <= clamped_y_end2; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(higher_x, screen_verts[1], screen_verts[2], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_orthografic(lower_x, screen_verts[0], screen_verts[2], y+screen_verts[1].screen_pos.y-screen_verts[0].screen_pos.y);
                    draw_horizontal_line_orthografic(lower_x, higher_x);
                }
                
            }
        }












        if (perspective == true) {
            if (is_leftsided_Triangle(screen_verts)) {
                /*  we know that the triangle bends to the right
                and that therfore that the x values of the right line will be bigger than the ones on the left

                                    ______
                            ______|     |
                        ____|           |
                        |_______________|
                            |___       |
                                |___  |             
                                    */ 



                /*  draw the lower part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                            ____________
                            |___       |
                                |___  |             
                                    */      


                
                int clamped_y_start1 = (int)- Math.min(screen_verts[0].screen_pos.y+center_y,0);
                int clamped_y_end1 = (int)(screen_verts[1].screen_pos.y - screen_verts[0].screen_pos.y)-(int)Math.max(0, screen_verts[1].screen_pos.y - center_y);

                Screen_Vertex lower_x = screen_verts[0].clone();
                Screen_Vertex higher_x = screen_verts[0].clone(); 
                for (int y = clamped_y_start1; y <= clamped_y_end1 ; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(lower_x, screen_verts[0], screen_verts[1], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(higher_x, screen_verts[0], screen_verts[2], y);
                    //System.out.println("y: " + y + " " + lower_x.screen_pos.y + " " + higher_x.screen_pos.y  );
                    draw_horizontal_line_perspective(lower_x, higher_x);
                }

                /*  draw the top part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                                ______
                        ______|     |
                    _____|           |
                    |______________*/       
        
                int clamped_y_start2 = (int)- Math.min(screen_verts[1].screen_pos.y+center_y,0);
                int clamped_y_end2 = (int)(screen_verts[2].screen_pos.y - screen_verts[1].screen_pos.y)-(int)Math.max(0, screen_verts[2].screen_pos.y - center_y);

                for (int y = clamped_y_start2; y <= clamped_y_end2; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(lower_x, screen_verts[1], screen_verts[2], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(higher_x, screen_verts[0], screen_verts[2], y+screen_verts[1].screen_pos.y-screen_verts[0].screen_pos.y);
                    draw_horizontal_line_perspective(lower_x, higher_x);
                }
                
            }

            else {

                //knowing the THE triangle is rightsided (the bend is on the right)

                /*  
        
                _______
                |      |______
                |            |______
                |__________________|
                |        ____|
                    |  ____|
                    */
        


                /*  draw the lower part of the trinagle by scaning from the left to the right for each y and shading in each pixel
                ______________
                |        ____|
                    |  ____|
                    */    
        
                int clamped_y_start1 = (int)- Math.min(screen_verts[0].screen_pos.y+center_y,0);
                int clamped_y_end1 = (int)(screen_verts[1].screen_pos.y - screen_verts[0].screen_pos.y)-(int)Math.max(0, screen_verts[1].screen_pos.y - center_y);

                Screen_Vertex lower_x = screen_verts[0].clone();
                Screen_Vertex higher_x = screen_verts[0].clone(); 
                for (int y = clamped_y_start1; y <= clamped_y_end1 ; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(higher_x, screen_verts[0], screen_verts[1], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(lower_x, screen_verts[0], screen_verts[2], y);
                    //System.out.println("y: " + y + " " + lower_x.screen_pos.y + " " + higher_x.screen_pos.y  );
                    draw_horizontal_line_perspective(lower_x, higher_x);
                }

                
                /*  draw the top part of the trinagle by scaning from the left to the right for each y and shading in each pixel
        
                _______
                |      |______
                |            |______
                |__________________|
            
            */   



                int clamped_y_start2 = (int)- Math.min(screen_verts[1].screen_pos.y+center_y,0);
                int clamped_y_end2 = (int)(screen_verts[2].screen_pos.y - screen_verts[1].screen_pos.y)-(int)Math.max(0, screen_verts[2].screen_pos.y - center_y);

                for (int y = clamped_y_start2; y <= clamped_y_end2; y++) {
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(higher_x, screen_verts[1], screen_verts[2], y);
                    lerp_screen_vert_between_screen_vert_given_y_step_perspective(lower_x, screen_verts[0], screen_verts[2], y+screen_verts[1].screen_pos.y-screen_verts[0].screen_pos.y);
                    //System.out.println("y: " + y + " " + lower_x.screen_pos.y + " " + higher_x.screen_pos.y + " x: " + lower_x.screen_pos.x + " " + higher_x.screen_pos.x );
                    draw_horizontal_line_perspective(lower_x, higher_x);
                }
                
            }
        }
    }

    public boolean is_leftsided_Triangle(Screen_Vertex[] Screen_verts) {
        double ax = Screen_verts[2].screen_pos.x - Screen_verts[0].screen_pos.x;
        double ay = Screen_verts[2].screen_pos.y - Screen_verts[0].screen_pos.y;
        double bx = Screen_verts[1].screen_pos.x - Screen_verts[0].screen_pos.x;
        double by = Screen_verts[1].screen_pos.y - Screen_verts[0].screen_pos.y;
        return (ax * by - bx * ay) > 0.0;
    }

    public Vector3D vector_from_local_to_screen(Vector3D local_vec) {
        if (perspective) {
            return new Vector3D(fov_x * local_vec.x / Math.abs(local_vec.z), fov_y * local_vec.y / Math.abs(local_vec.z), local_vec.z);
        } else {
            return new Vector3D(local_vec.x * fov_x, local_vec.y * fov_y, local_vec.z);
        }
    }

    public Vector3D vector_from_screen_to_local(Vector3D screen_vec) {
        if (perspective) {
            return new Vector3D(screen_vec.x * Math.abs(screen_vec.z) / fov_x,
                    screen_vec.y * Math.abs(screen_vec.z) / fov_y, screen_vec.z);
        }

        else {
            return new Vector3D(screen_vec.x / fov_x, screen_vec.y / fov_y, screen_vec.z);
        }
    }

    public boolean coordinates_in_bounds(int x, int y, int width, int height) {
        return ((x > 0 && x < width) || (y > 0 && y < height));
    }

    public void set_RGB(Color color, int x, int y, BufferedImage image) {
        if (coordinates_in_bounds(x, y, image.getWidth(), image.getHeight())) {
            image.setRGB(x, y, color.getRGB());
        }
    }

    public void request_camera_resolution(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double get_lerp_attribute_given_x_on_screen(Vector3D local_a, Vector3D local_b, double a_attribute, double b_attribute, double x) {
        double factor = ((x / fov_x * local_a.z) - local_a.x / ((local_b.x - local_a.x) - (x / fov_x * (local_b.z - local_a.z))));

        return (a_attribute + factor * (b_attribute - a_attribute));
    }

    public double get_lerp_attribute_given_y_on_screen(Vector3D local_a, Vector3D local_b, double a_attribute, double b_attribute, double y) {
        double factor = ((y / fov_y * local_a.z) - local_a.y / ((local_b.y - local_a.y) - (y / fov_y * (local_b.z - local_a.z))));

        return (a_attribute + factor * (b_attribute - a_attribute));
    }
    private void lerp_screen_vert_between_screen_vert_given_y_step_perspective(Screen_Vertex screen_vert ,Screen_Vertex lower_y, Screen_Vertex higher_y, double y_step){
        double y_dif = higher_y.screen_pos.y - lower_y.screen_pos.y;
        double x_dif = higher_y.screen_pos.x - lower_y.screen_pos.x;
        double fac = y_step/y_dif;
        double lower_y_z_inverse = 1/ lower_y.screen_pos.z;
        double higher_y_z_inverse = 1/ higher_y.screen_pos.z;
         

        
        if (y_dif != 0) {
            double x_per_y = x_dif / y_dif;
            screen_vert.screen_pos.x = (y_step * x_per_y) + lower_y.screen_pos.x;
            screen_vert.screen_pos.y = y_step + lower_y.screen_pos.y;
            screen_vert.screen_pos.z = 1/lerp(lower_y_z_inverse,higher_y_z_inverse, fac);
        }
    }

    private void lerp_screen_vert_between_screen_vert_given_x_step_perspective(Screen_Vertex screen_vert ,Screen_Vertex lower_y, Screen_Vertex higher_y, double x_step){
        double y_dif = higher_y.screen_pos.y - lower_y.screen_pos.y;
        double x_dif = higher_y.screen_pos.x - lower_y.screen_pos.x;
        double fac = x_step/x_dif;
        double lower_x_z_inverse = 1/ lower_y.screen_pos.z;
        double higher_x_z_inverse = 1/ higher_y.screen_pos.z;

        if (x_dif != 0) {
            double y_per_x = y_dif / x_dif;
            screen_vert.screen_pos.x =  x_step + lower_y.screen_pos.x;
            screen_vert.screen_pos.y = (x_step * y_per_x) + lower_y.screen_pos.y;
            screen_vert.screen_pos.z = 1/lerp(lower_x_z_inverse,higher_x_z_inverse, fac);
        }
    }


    private void draw_horizontal_line_perspective(Screen_Vertex lower_x, Screen_Vertex higher_x) {

        Screen_Vertex screen_vert = lower_x.clone();

        int clamped_x_start1 = (int)- Math.min(lower_x.screen_pos.x+center_x,0);
        int clamped_x_end1 = (int)(higher_x.screen_pos.x-lower_x.screen_pos.x)-(int)Math.max(0, higher_x.screen_pos.x - center_x);

        for(double x = clamped_x_start1; x <= clamped_x_end1; x++) {
            lerp_screen_vert_between_screen_vert_given_x_step_perspective(screen_vert, lower_x, higher_x, x);
            screen_vert.screen_pos.x += center_x;
            screen_vert.screen_pos.y += center_y;
            current_pixel_shader.Shade_Pixel(screen_vert);
        }
    }

    private void lerp_screen_vert_between_screen_vert_given_y_step_orthografic(Screen_Vertex screen_vert ,Screen_Vertex lower_y, Screen_Vertex higher_y, double y_step){
        double y_dif = higher_y.screen_pos.y - lower_y.screen_pos.y;
        double x_dif = higher_y.screen_pos.x - lower_y.screen_pos.x;
        double z_dif = higher_y.screen_pos.z - lower_y.screen_pos.z;

        
        if (y_dif != 0) {
            double x_per_y = x_dif / y_dif;
            double z_per_y = z_dif / y_dif;
            screen_vert.screen_pos.x = (y_step * x_per_y) + lower_y.screen_pos.x;
            screen_vert.screen_pos.y = y_step + lower_y.screen_pos.y;
            screen_vert.screen_pos.z = (z_per_y * y_step) + lower_y.screen_pos.z;
        }
    }

    private void lerp_screen_vert_between_screen_vert_given_x_step_orthografic(Screen_Vertex screen_vert ,Screen_Vertex lower_y, Screen_Vertex higher_y, double x_step){
        double y_dif = higher_y.screen_pos.y - lower_y.screen_pos.y;
        double x_dif = higher_y.screen_pos.x - lower_y.screen_pos.x;
        double z_dif = higher_y.screen_pos.z - lower_y.screen_pos.z;

        if (x_dif != 0) {
            double y_per_x = y_dif / x_dif;
            double z_per_x = z_dif / x_dif;
            screen_vert.screen_pos.x =  x_step + lower_y.screen_pos.x;
            screen_vert.screen_pos.y = (x_step * y_per_x) + lower_y.screen_pos.y;
            screen_vert.screen_pos.z = (x_step * z_per_x) + lower_y.screen_pos.z;
        }
    }


    private void draw_horizontal_line_orthografic(Screen_Vertex lower_x, Screen_Vertex higher_x) {
        Screen_Vertex screen_vert = lower_x.clone();

        int clamped_x_start1 = (int)- Math.min(lower_x.screen_pos.x+center_x,0);
        int clamped_x_end1 = (int)(higher_x.screen_pos.x-lower_x.screen_pos.x)-(int)Math.max(0, higher_x.screen_pos.x - center_x);

        for(double x = clamped_x_start1; x <= clamped_x_end1; x++) {
            lerp_screen_vert_between_screen_vert_given_x_step_orthografic(screen_vert, lower_x, higher_x, x);
            screen_vert.screen_pos.x += center_x;
            screen_vert.screen_pos.y += center_y;
            current_pixel_shader.Shade_Pixel(screen_vert);
        }
    }
    

    public double lerp(double a, double b, double fac) {
        return a + fac * (b - a);
    }

    public boolean coordinates_in_bounds(int x,int y){
        return( (x >-center_x && x< center_x) && (y >-center_y && y< center_y));
    }


    private Screen_Vertex[] sort_Screen_vertexes_with_lowest_screen_y_first(Screen_Vertex a, Screen_Vertex b,
            Screen_Vertex c) {
        Screen_Vertex[] Vertexes = { null, null, null };

        if (a.screen_pos.y <= b.screen_pos.y) {
            if (a.screen_pos.y < c.screen_pos.y) {
                if (b.screen_pos.y < c.screen_pos.y) {
                    Vertexes[0] = a;
                    Vertexes[1] = b;
                    Vertexes[2] = c;
                } else {
                    Vertexes[0] = a;
                    Vertexes[1] = c;
                    Vertexes[2] = b;

                }
            } else {
                Vertexes[0] = c;
                Vertexes[1] = a;
                Vertexes[2] = b;
            }
        }

        else {

            // we know that b < a
            if (b.screen_pos.y < c.screen_pos.y) {
                if (a.screen_pos.y < c.screen_pos.y) {
                    Vertexes[0] = b;
                    Vertexes[1] = a;
                    Vertexes[2] = c;
                } else {
                    Vertexes[0] = b;
                    Vertexes[1] = c;
                    Vertexes[2] = a;
                }
            }

            else {
                // c < b
                Vertexes[0] = c;
                Vertexes[1] = b;
                Vertexes[2] = a;

            }
        }
        return Vertexes;
    }

}
