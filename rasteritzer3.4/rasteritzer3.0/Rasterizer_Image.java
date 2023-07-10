import java.awt.*;
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


public class Rasterizer_Image {

    // a class that resembles the image an the z_buffer of the camera and provides additional utilities such as viewing the depth/z_buffer

    // the reason for the need of a z_bufer is so that when pixels of a tiangle get drawn the 
    //Rasterizerimage can tell weather it is closer or father away than another pixel on that 
    // same position that belonged to another trianle so as to prioritize pixels of triangles
    // that are closer to the viewer that therefore should be drawn in front

    
    public BufferedImage image_buffer;
    public double[][] z_buffer;
    
    public Rasterizer_Image( int WIDTH, int HEIGHT){
        System.out.println("   creating rasterizer image: " + WIDTH + " , " + HEIGHT );
        image_buffer = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

        z_buffer = new double[WIDTH][HEIGHT];

        float width_step = (float)((1/(double)WIDTH));
        float height_step = (float)((1/(double)HEIGHT));

        System.out.println("Width: " + WIDTH + " " +width_step*WIDTH);
        float r = 0;
        float b = 0;
        float g = 1;
        int num = 100000; // how deep should the initial pixels be in the z of the screen
        for(int i = 0; i < WIDTH; i++) {
            r += width_step;

            b = 0;
            for(int j = 0; j < HEIGHT; j++) {
                b += height_step;
                if(r > 1){
                    r = 1;
                }
                if(b >1){
                    b=1;
                }
                image_buffer.setRGB(i,j, new Color(r,g-(Math.max(r,b)),b).getRGB());
                
                z_buffer[i][j] = num;
            }
        }
    }

    public static void main(String [] args){
        Rasterizer_Image test = new Rasterizer_Image(700,700);
        Vector3D[] vectors = test.sort_vectors_with_lowest_x_first(new Vector3D(10, 100,1),new Vector3D(100, 10,1),new Vector3D(500, 600,100));
        Vector3D[] vectors2 = test.sort_vectors_with_lowest_x_first(new Vector3D(500, 600,100), new Vector3D(10, 100,1),new Vector3D(100, 10,1));
        for(Vector3D a : vectors){
            a.print("");
        }
        for(Vector3D a : vectors2){
            a.print("");
        }

        System.out.println(test.coordinates_in_bounds(50,50));
    }
    


    public void Draw_Line(Vector3D start, Vector3D end, Color color){
        // the draw line algorithm will draw a line iterating through either the x or y coordinate depending on which is longer

        double x_dif = ((int)end.x - ((int)start.x));
        //System.out.println("x_ dif:                      "+ x_dif);
        double y_dif = ((int)end.y - ((int)start.y));
        double z_dif = (end.z - start.z);
        //System.out.println("z_ dif:                      "+ z_dif);


        // make sure nothing is zero so we dont dizide by zero
        if(!(x_dif == 0 && y_dif == 0)){
            // iterate through x if x_is bigger than y in terms of magnitude
            if(Math.abs(x_dif) > Math.abs(y_dif)){
                double y = start.y;
                double z = start.z;
                double y_per_x = (double) y_dif/x_dif;
                double z_per_x = (double) z_dif/x_dif;
                // iterate forward if the end.x is higher than the start
                if(Math.signum(x_dif)== 1){
                    for( int x = (int) start.x ; x <= end.x; x++){
                        set_RGB(color, x, (int)y, z);
                        y +=y_per_x;
                        z += z_per_x;

                    }
                }

                //else iterate backwards
                else{
                    for( int x = (int)start.x ; x >= end.x; x--){
                        set_RGB(color, x, (int)y, z);
                        y -=y_per_x;
                        z -= z_per_x;

                    }
                }

            }

            //else iterate along the y axis
            else{
                double x = start.x;
                double z = start.z;
                double x_per_y =  x_dif/y_dif;
                //System.out.println("x_per_y:         "+ x_per_y);
                double z_per_y =  z_dif/y_dif;
                //System.out.println("z_per_y:         "+ z_per_y);
                // iterate forward if the end.y is higher than the start
                if(Math.signum(y_dif)== 1){
                    for( int y = (int)start.y ; y <= end.y; y++){
                        set_RGB(color, (int)x,y, z);
                        x += x_per_y;
                        z += z_per_y;

                    }
                }

                //else iterate backwards
                else{
                    for( int y = (int)start.y ; y >= end.y; y--){
                        set_RGB(color,(int) x,y, z);
                        x -=x_per_y;
                        z -= z_per_y;
          //              System.out.println(x +" "+ y +" " + z);

                    }
                }

            } 
        }
        else{
            set_RGB(color,(int) start.x,(int) start.y, start.z- (0.5*(end.z-start.z)));
        }
    }

    public void DRAW_vertical_Line(int x, int y1, int y2, double z1, double z2, Color color){
        if(y1 == y2){
            set_RGB(color, x, y2, z1 + (z2-z1)/2);
        }
        else{
            if(y1 < y2){
                double z_per_y = (z2-z1) / (y2-y1);
                double z = z1;
                for(int y = y1; y<= y2; y++){
                    set_RGB(color, x, y, z);
                    z += z_per_y;
                }
            }

            else{
                double z_per_y = (z1-z2) / (y1-y2);
                double z = z2;
                for(int y = y2; y<= y1; y++){
                    set_RGB(color, x, y, z);
                    z += z_per_y;
                }
            }
        }
    }

    public void Draw_Triangle(Vector3D a, Vector3D b, Vector3D c, Color color){
        Vector3D[] vectors = sort_vectors_with_lowest_x_first(a, b, c);
        
        // draw the lower part of the triangle between the lowest and the second lowest x value

        for(int x = 0-(int)Math.min(0, vectors[0].x); x< vectors[1].x - vectors[0].x; x++){
            Vector3D d = lerp_vector_along_x(vectors[0], vectors[1], x);
            if(d.y < 0){
                d.y = 0;
            }
            if(d.y > image_buffer.getHeight()){
                d.y = image_buffer.getHeight();
            }
            Vector3D e = lerp_vector_along_x(vectors[0], vectors[2], x);
            if(e.y < 0){
                e.y = 0;
            }
            if(e.y > image_buffer.getHeight()){
                e.y = image_buffer.getHeight();
            }
            DRAW_vertical_Line((int) d.x,(int) d.y,(int) e.y, d.z , e.z, color); 
        }
        for(int x = 0; x<= (vectors[2].x - vectors[1].x)-Math.max(0,vectors[2].x-image_buffer.getWidth() ); x++){
            Double aa = vectors[1].x - vectors[0].x;
            Vector3D d = lerp_vector_along_x(vectors[1], vectors[2], x);
            Vector3D e = lerp_vector_along_x(vectors[0], vectors[2], aa+x);
            DRAW_vertical_Line((int) d.x,(int) d.y,(int) e.y, d.z , e.z, color); 
        }
        
    }

    public Vector3D lerp_vector_along_x(Vector3D a, Vector3D b, double step){
        double x_dif = b.x-a.x;
        double y_dif = b.y-a.y;
        double z_dif = b.z-a.z;

        if(x_dif != 0){
            // return the vector in between the two vectors
            double y_per_x = y_dif/x_dif;
            double z_per_x = z_dif/x_dif;


            if(x_dif > 0){

                return new Vector3D(a.x + step, a.y + y_per_x*step, a.z + z_per_x*step);
            }

            if(x_dif < 0){

                return new Vector3D(a.x - step, a.y - y_per_x*step, a.z - z_per_x*step);
            }
        }
        return new Vector3D(a.x, a.y, a.z+ (0.5 *( b.z-a.z)));
    }

    public boolean coordinates_in_bounds(int x,int y){
        //System.out.println("give the coordinates x and y: " + x + " " + y);
        //System.out.println("is in bound is: " + ( (x >0 && x< width) && (y >0 && y< height)));
        return( (x >0 && x< image_buffer.getWidth()) && (y >0 && y< image_buffer.getHeight()));
    }
    public void set_RGB(Color color, int x,int  y, double z ){
        if(coordinates_in_bounds(x,y) && z< z_buffer[x][y] && z>0){
            image_buffer.setRGB(x,y, color.getRGB());
            z_buffer[x][y] = z;
        }
    }  
    
    public Vector3D[] sort_vectors_with_lowest_x_first(Vector3D a, Vector3D b, Vector3D c){
        Vector3D[] vectors = {new Vector3D(0, 0, 0),new Vector3D(0, 0, 0),new Vector3D(0, 0, 0)};
        
        if(a.x <= b.x){
            if(a.x < c.x){
                if(b.x < c.x){
                    vectors[0] = a;
                    vectors[1] = b;
                    vectors[2] = c;
                }
                else{
                    vectors[0] = a;
                    vectors[1] = c;
                    vectors[2] = b;
                
                }
            }
            else{
                vectors[0] = c;
                vectors[1] = a;
                vectors[2] = b;
            }
        }

        else{

            // we know that b < a
            if(b.x < c.x){
                if(a.x <c.x){
                    vectors[0] = b;
                    vectors[1] = a;
                    vectors[2] = c;
                }
                else{
                    vectors[0] = b;
                    vectors[1] = c;
                    vectors[2] = a;
                }
            }

            else{
                // c < b
                vectors[0] = c;
                vectors[1] = b;
                vectors[2] = a;
                
            }
        }
        return vectors;
    }

    BufferedImage z_buffer_to_image(){
        int width = image_buffer.getWidth();
        int height = image_buffer.getHeight();
        BufferedImage image = new BufferedImage(width , height, BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x< width; x++){
            for(int y = 0; y < height; y++){
                //System.out.println(256/(1+z_buffer[600][667]));
                int z_val = (int)(255/(1+z_buffer[x][y]));
                //System.out.println(z_val);
                image.setRGB(x, y, new Color(z_val, z_val, z_val).getRGB());
            }
        }
        return image;
     } 
    
}

