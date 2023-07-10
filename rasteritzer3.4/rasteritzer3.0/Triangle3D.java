public class Triangle3D {
    //datastructure meant to resemble a triangle in a 3mesh

    //it holds the three indexes of the vertexes that thefin the triangle

    public int fst;
    public int scd;
    public int trd;
    public Triangle3D(int a, int b, int c){
        fst = a;
        scd = b;
        trd = c;
    }

    public Triangle3D clone(){
        return new Triangle3D(fst,scd,trd);
}
}
