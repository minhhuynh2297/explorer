import java.util.Comparator;

public class Terrain implements Comparator<Terrain> {

    public int id;
    public int time, energy;
    private int cost_feature = 10;

    public Terrain(){

    }

    public Terrain(int id, int energy, int time){
        this.id = id;
        this.time = time;
        this.energy = energy;
    }
    public int time(int preid){
        if(this.id>preid){
            return this.time+cost_feature;
        }
        else if(this.id<preid){
            return this.time;
        }
        else{
            return 0;
        }
    }
    public int energy(int preid){
        if(this.id>preid){
            return this.energy+cost_feature;
        }
        else if(this.id<preid){
            return this.energy;
        }
        else{
            return 0;
        }
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
