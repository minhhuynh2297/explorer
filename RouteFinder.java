import java.util.*;
class Cell {
    int id;
    int timeToThisVertex;
    int heuristic;
    int energy;
    Cell prev;
    public Cell(int i) {
        id = i;
    }
}

public class RouteFinder {
    private Map map;
    private int length;
    public RouteFinder(Map mapp){
        map = mapp;
        length = map.Length();
    }
    private int energy_capacity;
    private int source;
    private int destination;
    private int time_rest;
    private Cell[] cell;
    private int[][] direction = {{1,0},{-1,0},{0,1},{0,-1}};
    private int[] timeTothisVertex=new int[length*length];
    private int[] heuristic=new int[length*length];
    private List<Integer> route = new ArrayList<>(); 
    private List<List<Integer>> route_list = new ArrayList<List<Integer>>(); 
    private int cal_timeOfAEdge(Cell a, Cell b) {
        int time;
        int potential_time = map.TheCell(b.id).time(a.id);
        int potential_energy = map.TheCell(b.id).energy(a.id);
        if (potential_energy > a.energy) {
            int num_rest = (potential_energy-a.energy)/energy_capacity + 1;
            time = time_rest * num_rest + potential_time;
        }
        else {
            time = potential_time;
        }
        return time;

    }
    private int cal_energy(Cell a, Cell b) {
        int potential_energy = map.TheCell(b.id).energy(a.id);
        if (potential_energy <= a.energy) return a.energy-potential_energy;
        else {
            return energy_capacity *((potential_energy-a.energy)/energy_capacity + 1) +a.energy-potential_energy;
        }


    }
    private int cal_time_heuristic(Cell id) {
        int row = id.id/length;
        int col = id.id%length;
        int res = Integer.MAX_VALUE;
        for (int[] dir: direction) {
            int newr = row + dir[0];
            int newc = col + dir[1];
            int newid = newr*length+newc;
            if (newc >= 0 &&newc < length && newr >=0 && newr < length) {
                int potential_time = map.TheCell(newid).time(id.id);
                int potential_energy = map.TheCell(newid).energy(id.id);
                res=Math.min(res, potential_time);
            }
        }
        return res;
    }
    private int cal_time_rest_heuristic(Cell id) {
        int row = id.id/length;
        int col = id.id%length;
        int res = Integer.MAX_VALUE;
        for (int[] dir: direction) {
            int newr = row + dir[0];
            int newc = col + dir[1];
            int newid = newr*length+newc;
            if (newc >= 0 &&newc < length && newr >=0 && newr < length) {
                int potential_time = map.TheCell(newid).time(id.id);
                int potential_energy = map.TheCell(newid).energy(id.id);
                if (potential_energy > id.energy) {
                    res=Math.min(res, time_rest+ potential_time);
                }
                else{
                    res=Math.min(res, potential_time);   
                }
            }
        }
        return res;
    }
    private int cal_expected_time_heuristic(Cell id) {
        int row = id.id/length;
        int col = id.id%length;
        int res = Integer.MAX_VALUE;
        for (int[] dir: direction) {
            int newr = row + dir[0];
            int newc = col + dir[1];
            int newid = newr*length+newc;
            if (newc >= 0 &&newc < length && newr >=0 && newr < length) {
                int potential_time = map.TheCell(newid).time(id.id);
                int potential_energy = map.TheCell(newid).energy(id.id);
                res=Math.min(res, potential_time+potential_energy*time_rest/energy_capacity);
            }
        }
        return res;
    }
    public int explore(int src, int dest, int energy_capacity, int time_rest, int heuristic_type) {
        int res = Integer.MAX_VALUE-1000;
        this.source = src;
        this.destination = dest;
        this.energy_capacity = energy_capacity;
        this.time_rest = time_rest;
        Cell[] cell = new Cell[length*length];
        Set<Cell> closeSet = new HashSet<>();
        for (int i = 0; i < length*length;i++) {
            cell[i] = new Cell(i);
            cell[i].timeToThisVertex = Integer.MAX_VALUE-1000;
            if(heuristic_type == 1){
                cell[i].heuristic=cal_time_heuristic(cell[i]);
            }
            else if(heuristic_type == 2){
                cell[i].heuristic=cal_expected_time_heuristic(cell[i]);
            }
            else if(heuristic_type == 3){
                cell[i].heuristic=cal_time_rest_heuristic(cell[i]);
            }
            else{
                cell[i].heuristic=0;
            }
            cell[i].energy=0;
        }
        cell[src].timeToThisVertex=0;
        cell[src].energy=energy_capacity;
        cell[src].prev = null;
        cell[dest].heuristic=0;
        PriorityQueue<Cell> priorityQueue = new PriorityQueue<>(timeComparator1);
        
        for (Cell i : cell) {
            priorityQueue.add(i);
        }
        while (!priorityQueue.isEmpty()) {
            Cell temp = priorityQueue.poll();
            int row = temp.id/this.length;
            int col = temp.id%this.length;
            if (temp.id == dest) {
                res = temp.timeToThisVertex;
                Cell j = temp;
                route = new ArrayList<>(); 
                route.add(0,j.id);
                while (j.prev!=null) {
                    j=j.prev;
                    route.add(0,j.id);
                }
                return res;
                // return closeSet.size();
            }
            closeSet.add(temp);
            for (int[] dir: direction) {
                int newr = row + dir[0];
                int newc = col + dir[1];
                int newid = newr * length + newc;
                if (newc >= 0 && newc < length && newr >= 0 && newr < length && !closeSet.contains(cell[newid])){
                    if (cell[newid].timeToThisVertex > cell[temp.id].timeToThisVertex + cal_timeOfAEdge(temp,cell[newid]) ) {
                        priorityQueue.remove(cell[newid]);
                        cell[newid].timeToThisVertex = cell[temp.id].timeToThisVertex + cal_timeOfAEdge(temp,cell[newid]);
                        cell[newid].prev = cell[temp.id];
                        cell[newid].energy = cal_energy(temp,cell[newid]);
                        priorityQueue.add(cell[newid]);
                    }
                    else if (cell[newid].timeToThisVertex == cell[temp.id].timeToThisVertex + cal_timeOfAEdge(temp,cell[newid]) ) {
                        if (cell[newid].energy < cal_energy(temp,cell[newid]) ) {
                            priorityQueue.remove(cell[newid]);
                            cell[newid].energy = cal_energy(temp,cell[newid]);
                            cell[newid].prev = temp;
                            priorityQueue.add(cell[newid]);
                        }
                    }

                }
            }

        }
        // System.out.println(this.theActualTime(this.theRoute(cell), src, dest, energy_capacity, time_rest));
        return res;
        // return closeSet.size();

    }
    public int [] theRoute(Cell[] cell) {
        int [] route_info = new int[cell.length];
        for(int i=0; i<route_info.length; i++){
            if(cell[i].prev != null){
                route_info[cell[i].id] = (cell[i].prev).id;
            }
            else{
                route_info[cell[i].id] = Integer.MAX_VALUE;
            }
        }
        return route_info;
    }
    // This method extracts the distance matrix, 
    // which contains the "best route" between all nodes(planned visited points) for the user with energy_capacity and time_rest
    public int[][] explore(List<Integer> nodes, int energy_capacity, int time_rest, int heuristic_type){
        int distance_matrix[][] = new int[nodes.size()][nodes.size()];
        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.size();j++){
                distance_matrix[i][j] = this.explore(nodes.get(i), nodes.get(j), energy_capacity, time_rest, heuristic_type);                    
                route_list.add(route);
            }
        }     
        return distance_matrix;
    }
    public List<List<Integer>> getRoutes(){
        return route_list;
    }

    /**
     * Define the compare rule for A star
     */

    Comparator<Cell> timeComparator1 =  new Comparator<>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            long distance1=o1.timeToThisVertex+o1.heuristic;
            long distance2=o2.timeToThisVertex+o2.heuristic;
            return distance1 < distance2? -1:1;
        }
    };

}


