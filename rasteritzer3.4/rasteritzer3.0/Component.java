public class Component {
    public Object parent;
    public String name;
    public Component(Object parent){
        this.parent = parent;
        name = "";
    }
    public Component(Object parent, String name){
        this.parent = parent;
        this.name = name;
    }
    public void execute(){}

}
