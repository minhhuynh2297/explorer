import java.util.*;

public class RouteFinder {
    //list of adjacent edges - current state of map is that it is FULLY connected / DENSE
    private List<List<Terrain>> adj = new ArrayList<List<Terrain>>();
    private Map map;
    private int length;
	public RouteFinder(Map map){
		this.map = map;
        this.length = this.map.Length();
        this.Init_AdjacentMatrix();
	}
    public void Init_AdjacentMatrix(){
        /* initializes lists in ArrayList */
        for(int i = 0; i < this.length; i++){
            for(int j=0; j< this.length; j++){
                List<Terrain> item = new ArrayList<Terrain>();
                adj.add(item);
            }
        }
        /* adds in adjacent terrains */
        for(int i = 0; i< this.length; i++){
            for (int j=0; j< this.length; j++){
                int id = this.map.TheCell(i,j).id;
                if(i!=0 && this.map.TheCell(i-1, j)!=null){
                    adj.get(id).add(this.map.TheCell(i-1, j));
                }
                if(j!=this.length-1 && this.map.TheCell(i, j+1)!=null){
                    adj.get(id).add(this.map.TheCell(i, j+1));
                }
                if(j!=0 && this.map.TheCell(i, j-1)!=null){
                    adj.get(id).add(this.map.TheCell(i, j-1));
                }
                if(i!=this.length-1 && this.map.TheCell(i+1, j)!=null){
                    adj.get(id).add(this.map.TheCell(i+1, j));
                }
            }

        }
    }
    public float explore(int src, int dest, int energy_capacity, int time_rest){
        Set<Integer> settled = new HashSet<Integer>();
        PriorityQueue<Terrain> pq;
        float costET[] = new float[this.length*this.length];
        for(int i = 0; i<this.length*this.length; i++){
            costET[i] = Integer.MAX_VALUE;
        }
        costET[src] = 0;

        pq = new PriorityQueue<Terrain>(this.length*this.length, new Terrain());
        pq.add(new Terrain(src, 0, 0));
        while(settled.size() != this.length*this.length){
            int u = pq.remove().id;
            settled.add(u);
            neighbors(u, settled, energy_capacity, time_rest, costET, pq);
        }
        System.out.println("The shortest expected time cost from source to dest is:" + costET[dest]);
        return costET[dest];
    }

    private void neighbors(int u, Set<Integer> settled, int energy_capacity, int time_rest, float[] costET, PriorityQueue<Terrain> pq){
        float edgeET = -1;
        float newET = -1;

        for(int i =0; i< adj.get(u).size(); i++){
            Terrain v = adj.get(u).get(i);

            if(!settled.contains(v.id)){
                edgeET = v.time + (float)v.energy*time_rest/energy_capacity;
                newET= costET[u] + edgeET;

                if(newET < costET[v.id]){
                    costET[v.id] = newET;
                }

                pq.add(new Terrain(v.id, costET[v.id], costET[v.id]));
            }

        }
    }
}