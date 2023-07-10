import java.util.ArrayList;
import java.util.List;

public class Mesh {

    //A class that hold a single threedimensional mesh with some fuctionalities and primitives

    public List<Vertex3D> vertexes;
    public List<Triangle3D> triangles;


    public Mesh(){
        vertexes = new ArrayList<>();
        triangles = new ArrayList<>();
    }

    public Mesh clone(){
        Mesh copy = new Mesh();
        for(Vertex3D p : vertexes){
            copy.vertexes.add(p.clone());
        }
        for(Triangle3D p : triangles){
            copy.triangles.add(p.clone());
        }
        return copy;
    }

    public static Mesh planeMesh(){
        Mesh plane = new Mesh();
        plane.vertexes.add(new Vertex3D(new Vector3D(1, 1, 0), new Vector3D(0, 0, 1)));
        plane.vertexes.add(new Vertex3D(new Vector3D(-1, 1, 0), new Vector3D(0, 0, 1)));
        plane.vertexes.add(new Vertex3D(new Vector3D(1, -1, 0), new Vector3D(0, 0, 1)));
        plane.vertexes.add(new Vertex3D(new Vector3D(-1, -1, 0), new Vector3D(0, 0, 1)));
        plane.triangles.add(new Triangle3D(0, 1, 2));
        plane.triangles.add(new Triangle3D(3, 1, 2));

        return plane;
    }

    public static Mesh cubeMesh() {
        Mesh cube = new Mesh();
        cube.vertexes.add(new Vertex3D(new Vector3D(1, 1, -1), new Vector3D(0, 0, -1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(-1, 1, -1), new Vector3D(0, 0, -1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(1, -1, -1), new Vector3D(0, 0, -1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(-1, -1, -1), new Vector3D(0, 0, -1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(1, 1, 1), new Vector3D(0, 0, 1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(-1, 1, 1), new Vector3D(0, 0, 1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(1, -1, 1), new Vector3D(0, 0, 1)));
        cube.vertexes.add(new Vertex3D(new Vector3D(-1, -1, 1), new Vector3D(0, 0, 1)));
        cube.triangles.add(new Triangle3D(0, 1, 2));
        cube.triangles.add(new Triangle3D(3, 1, 2));
        cube.triangles.add(new Triangle3D(4, 5, 6));
        cube.triangles.add(new Triangle3D(7, 6, 5));
        cube.triangles.add(new Triangle3D(0, 4, 2));
        cube.triangles.add(new Triangle3D(2, 4, 6));
        cube.triangles.add(new Triangle3D(1, 0, 5));
        cube.triangles.add(new Triangle3D(5, 0, 4));
        cube.triangles.add(new Triangle3D(2, 3, 6));
        cube.triangles.add(new Triangle3D(6, 3, 7));
        cube.triangles.add(new Triangle3D(1, 5, 3));
        cube.triangles.add(new Triangle3D(3, 5, 7));
        return cube;
    }

    public static Mesh pyramidMesh() {
        Mesh pyramid = new Mesh();
    
        pyramid.vertexes.add(new Vertex3D(new Vector3D(-1, 0, -1), new Vector3D(0, -1, 0)));
        pyramid.vertexes.add(new Vertex3D(new Vector3D(1, 0, -1), new Vector3D(0, -1, 0)));
        pyramid.vertexes.add(new Vertex3D(new Vector3D(1, 0, 1), new Vector3D(0, -1, 0)));
        pyramid.vertexes.add(new Vertex3D(new Vector3D(-1, 0, 1), new Vector3D(0, -1, 0)));
    
        pyramid.vertexes.add(new Vertex3D(new Vector3D(0, 1, 0), new Vector3D(0, 1, 0)));
    
        pyramid.triangles.add(new Triangle3D(0, 1, 2));
        pyramid.triangles.add(new Triangle3D(2, 3, 0));
    
        pyramid.triangles.add(new Triangle3D(0, 4, 1));
        pyramid.triangles.add(new Triangle3D(1, 4, 2));
        pyramid.triangles.add(new Triangle3D(2, 4, 3));
        pyramid.triangles.add(new Triangle3D(3, 4, 0));
    
        return pyramid;
    }
    
}
