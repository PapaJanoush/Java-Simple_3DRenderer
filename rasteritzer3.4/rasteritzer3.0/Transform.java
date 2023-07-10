public class Transform {

    //the transformer is a datastructure meant to hold the position rotation and scale of something in 3D space

    //is usually utalized by the bject class to store its position rotation and scale in the worldspace 
    public Vector3D pos;
    public Quaternion rot;
    public Vector3D scale;
    

    public Transform(Vector3D position, Quaternion rotation, Vector3D scale){
        pos = position;
        rot = rotation;
        this.scale = scale;
    }

    public Transformer3D get_transformer3D(){
        Matrix3x3 matrix = rot.get_Matrix3x3();
        matrix.scale_colum(0, scale.x);
        matrix.scale_colum(1, scale.y);
        matrix.scale_colum(2, scale.z);
        return new Transformer3D(matrix, pos, scale);
        
    }

    public static Transform default_Transform(){
        return new Transform(new Vector3D(0, 0, 0), new Quaternion(1, 0, 0, 0), new Vector3D(1, 1, 1));
    }

    public static void main (String[] args){
        Transform test_Transform = Transform.default_Transform();
        test_Transform.rot = Quaternion.eulerToQuaternion(45, 10, 30);
        Transformer3D a = test_Transform.get_transformer3D();

        a.print();

        Vector3D vec = new Vector3D(1, -2, 5);
        vec.print("original vector");
        vec = a.get_transformed_vector(vec);
        vec.print("transformedector");
        Transformer3D a_inverse = a.inverse();

        a_inverse.print();
        
        //vec = a_inverse.transformation_matrix.transformed_vector(vec);
        vec = a_inverse.get_inverse_transformed_vector(vec);
        vec.print("inverse transformed vector");


    }

}
