import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class Default_Shader extends Shader{

    // the currently only avalible shader in the programm that draws the triangles of the mesh of an object to the screen

    private BufferedImage image_buffer;

    private double[][] z_buffer;

    private Rasterizer_Image image;

    private Camera_Component camera;

    private Screen_Vertex fst,scd,trd;

    private Default_Pixel_Shader pixel_Shader = new Default_Pixel_Shader();

    public Default_Shader(){
        super();
        image_buffer = null;
        z_buffer = null;
        image = null;
        camera = null;

    }

    @Override
    public void shade_object(Object object, Camera_Component cam){
        
        System.out.println("shading object");

        Transformer3D transformer = object.transform.get_transformer3D();

        // "tell" the pixelshader what object it is going to be shading from whitch camera on whitch image before passing it to the camera
        pixel_Shader.cam = cam;
        pixel_Shader.image = cam.image;
        pixel_Shader.obj = object;
        for(Component k :object.components){
            if(k instanceof Mesh_Component){
                Mesh_Component m = (Mesh_Component)k;
                Mesh r = m.real_mesh;
                for(Triangle3D t : r.triangles ){
                //    Triangle3D t = r.triangles.get(0);
                    fst = cam.Vertex3D_to_Screen_Vertex(r.vertexes.get(t.fst), transformer);
                    //fst.screen_pos = new Vector3D(-70,-135,1);
                    scd = cam.Vertex3D_to_Screen_Vertex(r.vertexes.get(t.scd), transformer);
                    //scd.screen_pos = new Vector3D(100,0,1);
                    trd = cam.Vertex3D_to_Screen_Vertex(r.vertexes.get(t.trd), transformer);
                    //trd.screen_pos = new Vector3D(-50,140,1);
                    cam.Draw_Triangle(fst, scd, trd, pixel_Shader);
                }
            }
        }
    }
    public static boolean shade_pixel(float a_to_b_ratio, float a_b_to_c_ratio){
        return true;
    }
}