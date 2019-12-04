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
    // This method extracts the time cost of the best route from src to dest for the user by using dijkstra 
    public int explore(int src, int dest, int energy_capacity, int time_rest){
        Set<Integer> settled = new HashSet<Integer>();
        int route[] = new int[this.length*this.length];
        PriorityQueue<Terrain> pq;
        int costET[] = new int[this.length*this.length];
        for(int i = 0; i<this.length*this.length; i++){
            costET[i] = Integer.MAX_VALUE;
        }
        costET[src] = 0;
        pq = new PriorityQueue<Terrain>(this.length*this.length, new Terrain());
        pq.add(new Terrain(src, 0, 0));
        route[src] = src;
        
        while(settled.size() != this.length*this.length){
            Terrain u = pq.remove();
            int u_id = u.id;
            settled.add(u_id);
            if(u_id == dest){
                break;
            }
            neighbors(u_id, settled, energy_capacity, time_rest, costET, pq, route);
        }
        System.out.println("The shortest expected time cost from source to dest is:" + costET[dest]);
        int actual_time_cost = theActualTime(route, src, dest, energy_capacity, time_rest);
        System.out.println("The actual time cost from source to dest is:" + actual_time_cost);
        return actual_time_cost;
    }
    // This method extracts the time cost of the route for the user with energy_capacity and time_rest
    public int theActualTime(int[] route, int src, int dest, int energy_capacity, int time_rest){ 
        int energy = energy_capacity;
        int time = 0;
        Terrain current_node = map.TheCell(dest);
        while(true){
            if(current_node.id!=src){ 
                if(energy>=current_node.energy){
                    energy = (int)(energy-current_node.energy(route[current_node.id]));
                    time = (int)(time+current_node.time(route[current_node.id]));
                }
                else{
                    energy = (int)(energy_capacity-current_node.energy(route[current_node.id]));
                    time = (int)(time+current_node.time(route[current_node.id])+ time_rest);
                } 
                current_node = map.TheCell(route[current_node.id]);
            }
            else{
                break;
            }
        }
        return time;
    }
    private void neighbors(int u, Set<Integer> settled, int energy_capacity, int time_rest, int[] costET, PriorityQueue<Terrain> pq, int[] route){
        int edgeET = -1;
        int newET = -1;

        for(int i =0; i< adj.get(u).size(); i++){
            Terrain v = adj.get(u).get(i);

            if(!settled.contains(v.id)){
                edgeET = (int)(v.time(u) + (int)(v.energy(u)*time_rest/energy_capacity));
                newET= (int)(costET[u] + edgeET);

                if(newET < costET[v.id]){
                    costET[v.id] = newET;
                }
                route[v.id] = u;
                pq.add(new Terrain(v.id, (int)costET[v.id], (int)costET[v.id]));

            }

        }
    }
    // This method extracts the distance matrix, 
    // which contains the "best route" between all nodes(planned visited points) for the user with energy_capacity and time_rest
    public int[][] explore(List<Integer> nodes, int energy_capacity, int time_rest){
        int distance_matrix[][] = new int[nodes.size()][nodes.size()];
        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.size();j++){
                distance_matrix[i][j] = this.explore(nodes.get(i), nodes.get(j), energy_capacity, time_rest);
            }
        }
        return distance_matrix;
    }

}