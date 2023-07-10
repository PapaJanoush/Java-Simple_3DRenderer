import java.util.ArrayList;
import java.util.List;

public class Input {
    // a class meant to store the inputs that where given in a certain time range
    // used by the World class to store the inputs that are getting given at that verry moment the inputs that where given in the last frame and the inputs that where given befor the last frame(in order to for example detect changs in th mouse position)

    private List<String> pressed_keys;

    private int mouse_x;
    private int mouse_y;
    public int delta_mouse_x;
    public int delta_mouse_y;
    private boolean middle_mouse_button_pressed;

    private boolean lmb_pressed;

    public Input(int x, int y){
        mouse_x = x;
        mouse_y = y;
        pressed_keys = new ArrayList();
        lmb_pressed = false;
    }

    public void add_pressed_key(String cha){
        if (!pressed_keys.contains(cha)){
            pressed_keys.add(cha);
        }
    }

    public void remove_pressed_key(String cha){
        if (!pressed_keys.contains(cha)){
            pressed_keys.remove(cha);
        }
    }

    public boolean key_pressed( String cha){
        if( pressed_keys.contains(cha)){
            return true;
        }
        return false;
    }

    public void set_mouse_position(int x, int y){
        mouse_x = x;
        mouse_y = y;
    }

    
    public int get_mouse_x(){
        return mouse_x;
    }
    
    public int get_mouse_y(){
        return mouse_y;
    }

    public void set_lmb_pressed( boolean bool){
        lmb_pressed = bool;
    }
    public boolean get_lmb_pressed(){
        return lmb_pressed;
     }

    public void set_middle_mouse_button_pressed(Boolean bool){
        middle_mouse_button_pressed = bool;
    }
    public Boolean get_middle_mouse_button_pressed(){
        return middle_mouse_button_pressed;
    }




    
}
