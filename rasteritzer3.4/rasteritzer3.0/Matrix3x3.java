public class Matrix3x3 {
    // the class responsible for rotating and stratching verctors in threedimensional space

    // source chatGPT
    private double[][] matrix;

    public Matrix3x3(double[][] values) {
        if (values.length != 3 || values[0].length != 3) {
            throw new IllegalArgumentException("Matrix must be 3x3");
        }
        matrix = values;
    }

    public static void main(String[] args) {
        double[][] values = {{2, 1, 0}, {2, 0,3}, {0, 0, 6}};
        Matrix3x3 matrix = new Matrix3x3(values);
        System.out.println("Original matrix:\n" + matrix);
        matrix.scale_colum(2, 0);        
        System.out.println("new matrix:\n" + matrix);
        
    }
    
    public Matrix3x3 inverse() {
        //System.out.println("inverting matrix");
        double det = determinant();
        //System.out.println("determinant:" + det);

        if (det == 0) {
            throw new ArithmeticException("Matrix is not invertible");
        }
        double[][] adj = adjoint();
        double[][] inverse = new double[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse[i][j] = adj[i][j] / det;
            }
        }
    
        return new Matrix3x3(inverse);
    }

    private double determinant() {
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[0][2];
        double d = matrix[1][0];
        double e = matrix[1][1];
        double f = matrix[1][2];
        double g = matrix[2][0];
        double h = matrix[2][1];
        double i = matrix[2][2];
        return a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
    }

    private double[][] adjoint() {
        double[][] adj = new double[3][3];
        double a = matrix[0][0];
        double b = matrix[0][1];
        double c = matrix[0][2];
        double d = matrix[1][0];
        double e = matrix[1][1];
        double f = matrix[1][2];
        double g = matrix[2][0];
        double h = matrix[2][1];
        double i = matrix[2][2];
        adj[0][0] = e * i - f * h;
        adj[0][1] = -(b * i - c * h);
        adj[0][2] = b * f - c * e;
        adj[1][0] = -(d * i - f * g);
        adj[1][1] = a * i - c * g;
        adj[1][2] = -(a * f - c * d);
        adj[2][0] = d * h - e * g;
        adj[2][1] = -(a * h - b * g);
        adj[2][2] = a * e - b * d;
        return adj;
    }

    public Vector3D transformed_vector(Vector3D v) {
        double x = v.x;
        double y = v.y;
        double z = v.z;
        double newX = x * matrix[0][0] + y * matrix[0][1] + z * matrix[0][2];
        double newY = x * matrix[1][0] + y * matrix[1][1] + z * matrix[1][2];
        double newZ = x * matrix[2][0] + y * matrix[2][1] + z * matrix[2][2];
        return new Vector3D(newX, newY, newZ);
    }

    public static Matrix3x3 identity_matrix(){
        return new Matrix3x3(new double[][]{{1, 0,0}, {0, 1, 0}, {0, 0, 1}});
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append("[ ");
            for (int j = 0; j < 3; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("]\n");
        }
        return sb.toString();
    }

    public void scale_colum(int collum, double scalar){
        if(collum <=2 && collum > -1){
            matrix[0][collum] = matrix[0][collum]*scalar;
            matrix[1][collum] = matrix[1][collum]*scalar;
            matrix[2][collum] = matrix[2][collum]*scalar;

            
        }
    }

    public void scale_matrtix_collums(Vector3D scale_vec){
        scale_colum(0, scale_vec.x);
        scale_colum(0, scale_vec.y);
        scale_colum(0, scale_vec.z);
    }

    public Matrix3x3 clone(){
        return new Matrix3x3(matrix);
    }
}

