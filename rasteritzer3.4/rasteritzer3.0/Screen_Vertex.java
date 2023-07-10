import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Screen_Vertex {
    //a datastructure meant to store the information of a point on a triangle on the screen
    //this could be either a vertex of the triangle or a interpolated point in between them 

    public Vector3D local_pos;
    public Vector3D screen_pos;
    public Vector3D normal;
    public List<Vector3D> UVs;
    public List<Color> vertexcolors;

    public static void main(String[] args){
        Screen_Vertex a = new Screen_Vertex(new Vector3D(1, 2, 3),new Vector3D(1, 2, 3),new Vector3D(1, 2, 3), null, null);
        Screen_Vertex b = a.clone();
        a.local_pos = new Vector3D(6, 6, 6);
        a.local_pos.print("");
        b.local_pos.print("");

        a.screen_pos = new Vector3D(6, 6, 6);
        a.screen_pos.print("");
        b.screen_pos.print("");
        
        a.normal = new Vector3D(6, 6, 6);
        a.normal.print("");
        b.normal.print("");
        
    }

    public Screen_Vertex(Vector3D pos, Vector3D normal, Vector3D screen_pos){
        this.screen_pos = screen_pos;
        this.local_pos = pos;
        this.normal = normal;
        this.UVs = new ArrayList<>();
        this.vertexcolors = new ArrayList<>();
    }

    public Screen_Vertex(){
        this.screen_pos = new Vector3D(0, 0, 0);
        this.local_pos = new Vector3D(0, 0, 0);
        this.normal = new Vector3D(0, 0, 0);
        this.UVs = new ArrayList<>();
        this.vertexcolors = new ArrayList<>();
    }

    public Screen_Vertex(Vector3D pos, Vector3D normal,  Vector3D screen_pos, List<Vector3D> UVs, List<Color> vertexcolors){
        this.local_pos = pos;
        this.screen_pos = screen_pos;
        this.normal = normal;
        this.UVs = UVs;
        this.vertexcolors = vertexcolors;
    }
    
    public Screen_Vertex(Vector3D pos, Vector3D screen_pos, Vector3D normal, Vertex3D vertex){
            this.local_pos = pos;
            this.screen_pos = screen_pos;
            this.normal = normal;
            this.UVs = vertex.UVs;
            this.vertexcolors = vertex.vertexcolors;
    }


    public void set_UV_List_size(int size){
            int difference = UVs.size()-size;
            if(difference< 0){
                for(int i = difference; i < 0; i++){
                    UVs.add(new Vector3D(0,0,0));
                }
            }
            if(difference > 0){
                for(int i = 0; i < difference; i++){
                    UVs.remove(i);
                }
            }
        }
    
    public void set_color_List_size(int size){
        int difference = vertexcolors.size()-size;
        if(difference< 0){
            for(int i = difference; i < 0; i++){
                vertexcolors.add(new Color(0,0,0));
            }
        }
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                vertexcolors.remove(i);
        }
        }
    }


    public Screen_Vertex clone(){
        Vector3D clone_pos = this.local_pos.clone();
        Vector3D clone_screen_pos = this.screen_pos.clone();
        Vector3D clone_normal = this.normal.clone();
        List<Vector3D> clone_UVs = new ArrayList<>();
        List<Color> clone_vertexcolors = new ArrayList<>();

        if(UVs != null){
            for(Vector3D p : UVs){
                clone_UVs.add(p.clone());
            }
        }

        if(vertexcolors != null){
            for(Color p : vertexcolors){
                clone_vertexcolors.add(new Color(p.getRGB()));
            }
        }

        return new Screen_Vertex(clone_pos, clone_normal, clone_screen_pos, clone_UVs, clone_vertexcolors);
    } 
}


