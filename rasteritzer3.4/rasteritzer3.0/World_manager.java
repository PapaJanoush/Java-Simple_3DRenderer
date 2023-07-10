import java.util.ArrayList;
import java.util.List;

public class World_manager {
    // the class responsible for storing all the Worlds/distinct 3D spaces that we might have in our programn although in this version there is only one world 
    private List<World> worlds;
    public World_manager(){
        worlds = new ArrayList<>();
    }
    public World new_world(){
        World new_world = new World();
        worlds.add(new_world);
        return new_world;
    }

    public void update_worlds(){
        // for each frame all the worlds get updated
        for( World world: worlds){
            world.update();
        }
    }
}
