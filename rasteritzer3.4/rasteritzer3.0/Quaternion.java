public class Quaternion {
    // a class used by the transform to store rotation 
    
    // the reason for opting to use it in the programm stems from the fact that every 3Dsoftware uses these internaly to deal with rotation because they are easyer to compute


  // source: chatgpt
  private final double w, x, y, z;

  public Quaternion(double w, double x, double y, double z) {
      this.w = w;
      this.x = x;
      this.y = y;
      this.z = z;
  }

  public Quaternion multiply(Quaternion q) {
      double w = this.w * q.w - this.x * q.x - this.y * q.y - this.z * q.z;
      double x = this.w * q.x + this.x * q.w + this.y * q.z - this.z * q.y;
      double y = this.w * q.y - this.x * q.z + this.y * q.w + this.z * q.x;
      double z = this.w * q.z + this.x * q.y - this.y * q.x + this.z * q.w;
      return new Quaternion(w, x, y, z);
  }

  public Matrix3x3 get_Matrix3x3() {
      double[][] values = new double[3][3];
      double x2 = 2 * x;
      double y2 = 2 * y;
      double z2 = 2 * z;
      double wx2 = 2 * w * x;
      double wy2 = 2 * w * y;
      double wz2 = 2 * w * z;
      double xy2 = 2 * x * y;
      double xz2 = 2 * x * z;
      double yz2 = 2 * y * z;
      values[0][0] = 1 - y2 * y - z2 * z;
      values[0][1] = xy2 - wz2;
      values[0][2] = xz2 + wy2;
      values[1][0] = xy2 + wz2;
      values[1][1] = 1 - x2 * x - z2 * z;
      values[1][2] = yz2 - wx2;
      values[2][0] = xz2 - wy2;
      values[2][1] = yz2 + wx2;
      values[2][2] = 1 - x2 * x - y2 * y;
      return new Matrix3x3(values);
  }

  public static Quaternion eulerToQuaternion(double pitch, double yaw, double roll) {
    double cy = Math.cos(Math.toRadians(yaw) * 0.5);
    double sy = Math.sin(Math.toRadians(yaw) * 0.5);
    double cp = Math.cos(Math.toRadians(pitch) * 0.5);
    double sp = Math.sin(Math.toRadians(pitch) * 0.5);
    double cr = Math.cos(Math.toRadians(roll) * 0.5);
    double sr = Math.sin(Math.toRadians(roll) * 0.5);

    double qw = cy * cp * cr + sy * sp * sr;
    double qx = cy * cp * sr - sy * sp * cr;
    double qy = sy * cp * sr + cy * sp * cr;
    double qz = sy * cp * cr - cy * sp * sr;

    // Return quaternion
    return new Quaternion(qw, qx, qy, qz);
    }


    public void print(){
        System.out.println(" w: " + w + "x: " + x + "y: "+y+"z: "+ z );
    }
    public static void main(String[] args) {
        Quaternion test_Quaternion = eulerToQuaternion(0, 0, 0);
        test_Quaternion.print();
        Matrix3x3 test_Matrix = test_Quaternion.get_Matrix3x3();
        System.out.println(test_Matrix.toString());
    }


}
