import java.util.Comparator;

public class Terrain implements Comparator<Terrain> {

    public int id, time, energy;

    public Terrain(){

    }

    public Terrain(int id, int time, int energy){
        this.id = id;
        this.time = time;
        this.energy = energy;
    }

    @Override
    public int compare(Terrain o1, Terrain o2) {
        if(o1.time < o2.time){
            return -1;
        }
        if(o1.time > o2.time){
            return 1;
        }
        return 0;
    }
}
