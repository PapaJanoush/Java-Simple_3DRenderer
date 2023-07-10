class Vector3D {
    // a datastructure that stores a vector and has many method for combining vectors +-*/ etc

    double x;
    double y;
    double z;
    public Vector3D( double x,double y,double z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  
    // function to add two 3D vectors
    public void add(Vector3D vector) {
      x = vector.x+ x;
      y = vector.y+ y;
      z = vector.z + z;
    }
  
    // function to subtract two 3D vectors
    public void subtract(Vector3D vector) {
      x = -vector.x+ x;
      y = -vector.y+ y;
      z = -vector.z + z;
    }

    public void multiply(Vector3D a){
      x *= a.x;
      y *= a.y;
      z *= a.z;
    }
  
    // function to multiply a 3D vector by a scalar
    public void scale(Double scalar) {
      this.x *= scalar;
      this.y *= scalar;
      this.z *= scalar;
    }
    
    public Vector3D scaled_Vector( Double b){
      return new Vector3D(x*b, y*b, z*b);
    }

    public Vector3D inverse(){
      return new Vector3D(1/x, 1/y, 1/z);
    }

    public Vector3D clone(){
      return new Vector3D(x, y, z);
    }

    public static Vector3D scale_Vector3Ds(Vector3D a, Double scalar){
      return new Vector3D(a.x*scalar, a.y*scalar, a.z*scalar);
    }
    public static Vector3D multiplyVector3Ds(Vector3D a, Vector3D b){
      return new Vector3D(a.x*b.x, a.y*b.y, a.z*b.z);
    }
    
    public static Vector3D addVectos3Ds(Vector3D a, Vector3D b){
      return new Vector3D(a.x+b.x, a.y+b.y, a.z+b.z);
    }

    public static Vector3D subtractVectos3Ds(Vector3D a, Vector3D b){
      return new Vector3D(a.x-b.x, a.y-b.y, a.z-b.z);
    }

    public static Vector3D lerp(Vector3D a, Vector3D b, double factor){
      // lerp means linear interpolation
      Vector3D c =Vector3D.subtractVectos3Ds(b, a);
      c.scale(factor);
      c.add(a);
      return c;
    }

    public static double dot_product(Vector3D a, Vector3D b){
      double dotProduct = 0;
      dotProduct += a.x*b.x;
      dotProduct += a.y*b.y;
      dotProduct += a.z*b.z;

      return dotProduct;
    }

    public static Vector3D cross_product(Vector3D a, Vector3D b){
      double x = a.y * b.z - a.z * b.y;
      double y = a.z * b.x - a.x * b.z;
      double z = a.x * b.y - a.y * b.x;
      return new Vector3D(x, y, z);
    }
    public void normalize() {
      double magnitude = Math.sqrt(x * x + y * y + z * z);
      x /= magnitude;
      y /= magnitude;
      z /= magnitude;
    }

    public void print(String message){
      System.out.println(message);
      System.out.println("x: " +x );
      System.out.println("y: " +y );
      System.out.println("z: " +z );
    }

    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      sb.append(x + " ");
      sb.append(y + " ");
      sb.append(z + " ");
      sb.append("]\n");

      return sb.toString();
    }
  }
