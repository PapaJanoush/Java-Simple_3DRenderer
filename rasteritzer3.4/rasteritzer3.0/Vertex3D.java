import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Vertex3D {
    // a datastructure that stores a vertex of a mesh with all its atributs such as the normal and the position
    //but also with the capability of storing mire advanced atributes ofthen used in more sufisticated 3D graphics
    // such as UV coordinates and vertexcolors(although these will probably not be utalized in ths version of the programm)


    public Vector3D pos;
    public Vector3D normal;
    public List<Vector3D> UVs;
    public List<Color> vertexcolors;
    public Vertex3D(Vector3D pos, Vector3D normal){
        this.pos = pos;
        this.normal = normal;
        this.UVs = new ArrayList<>();
        this.vertexcolors = new ArrayList<>();
    }
    public Vertex3D(Vector3D pos, Vector3D normal,  List<Vector3D> UVs, List<Color> vertexcolors){
        this.pos = pos;
        this.normal = normal;
        this.UVs = UVs;
        this.vertexcolors = vertexcolors;
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

    public void a(){
        vertexcolors.get(0);
    }

    public Vertex3D clone(){
        Vector3D clone_pos = this.pos.clone();
        Vector3D clone_normal = this.normal.clone();
        List<Vector3D> clone_UVs = new ArrayList<>();
        List<Color> clone_vertexcolors = new ArrayList<>();

        for(Vector3D p : UVs){
            clone_UVs.add(p.clone());
        }

        for(Color p : vertexcolors){
            clone_vertexcolors.add(new Color(p.getRGB()));
        }


        return new Vertex3D(clone_pos, clone_normal, clone_UVs, clone_vertexcolors);
    } 
}


