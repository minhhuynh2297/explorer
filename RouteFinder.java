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
        length = mapp.Length();
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
    private int cal_timeOfAEdge(Cell a, Cell b) {
        int time;
        int potential_time = map.TheCell(b.id).time(a.id);
        int potential_energy = map.TheCell(b.id).energy(a.id);
        if (potential_energy > a.energy) {
            int num_rest = (potential_energy-a.energy)/energy_capacity + 1;
            //b.energy = energy_capacity+a.energy-potential_energy;
            time = time_rest * num_rest + potential_time;
        }
        else {
            //b.energy =
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
    private int cal_heuristic(Cell id) {
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
            }
        }
        return res;
    }

    public int explore(int src, int dest, int energy_capacity, int time_rest, boolean whether_a_star, boolean whether_lowest) {
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
            cell[i].heuristic=cal_heuristic(cell[i]);
            cell[i].energy=0;
        }
        // cell[src].time_cost=0;
        // cell[src].energy_cost=0;
        cell[src].timeToThisVertex=0;
        cell[src].energy=energy_capacity;
        cell[src].prev = null;
        cell[dest].heuristic=0;
        PriorityQueue<Cell> priorityQueue;
        if(whether_a_star && whether_lowest){
            priorityQueue = new PriorityQueue<>(timeComparator1);
        }
        else if(!(whether_a_star) && whether_lowest){
            priorityQueue = new PriorityQueue<>(timeComparator2);
        }
        else if(whether_a_star && !(whether_lowest)){
            priorityQueue = new PriorityQueue<>(timeComparator3);
        }
        else{
            priorityQueue = new PriorityQueue<>(timeComparator4);   
        }
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
                route.add(0,j.id);
                while (j.prev!=null) {
                    j=j.prev;
                    route.add(0,j.id);
                }
                //  System.out.println(this.theActualTime(this.theRoute(cell), src, dest, energy_capacity, time_rest));
                return res;
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
    public int theActualTime(int[] route_info, int src, int dest, int energy_capacity, int time_rest){ 
        int energy = energy_capacity;
        int time = 0;
        Terrain current_node = map.TheCell(dest);
        // System.out.print("Route: " );
        // System.out.print(dest +" " );
        while(true){
            if(current_node.id!=src){ 
                if(energy>=current_node.energy){
                    // System.out.print(current_node.id +" " );
                    energy = (int)(energy - current_node.energy(route_info[current_node.id]));
                    time = (int)(time + current_node.time(route_info[current_node.id]));
                }
                else{
                    energy = (int)(energy_capacity - current_node.energy(route_info[current_node.id]));
                    time = (int)(time + current_node.time(route_info[current_node.id]) + time_rest);
                } 
                current_node = map.TheCell(route_info[current_node.id]);
            }
            else{
                break;
            }
        }
        // System.out.print(src +" " );
        // System.out.println("");
        return time;
    }
    // This method extracts the distance matrix, 
    // which contains the "best route" between all nodes(planned visited points) for the user with energy_capacity and time_rest
    public int[][] explore(List<Integer> nodes, int energy_capacity, int time_rest, boolean whether_a_star, boolean whether_lowest){
        int distance_matrix[][] = new int[nodes.size()][nodes.size()];
        for(int i=0;i<nodes.size();i++){
            for(int j=0;j<nodes.size();j++){
                distance_matrix[i][j] = this.explore(nodes.get(i), nodes.get(j), energy_capacity, time_rest, whether_a_star, whether_lowest);
            }
        }
        return distance_matrix;
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
    Comparator<Cell> timeComparator2 =  new Comparator<>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            int distance1=o1.timeToThisVertex;
            int distance2=o2.timeToThisVertex;
            return distance1 < distance2? -1:1;
        }
    };
    Comparator<Cell> timeComparator3 =  new Comparator<>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            int o1_preid;
            if(o1.prev==null){
                o1_preid = o1.id;
            }
            else{
                o1_preid = o1.prev.id;
            }
            int o1_time = map.TheCell(o1.id).time(o1_preid);
            int o1_energy = map.TheCell(o1.id).energy(o1_preid);
            int o2_preid;
            if(o2.prev==null){
                o2_preid = o2.id;
            }
            else{
                o2_preid = o2.prev.id;
            }
            int o2_time = map.TheCell(o2.id).time(o2_preid);
            int o2_energy = map.TheCell(o2.id).energy(o2_preid);
            int distance1=o1_time+o1_energy*time_rest/energy_capacity+o1.heuristic;
            int distance2=o2_time+o2_energy*time_rest/energy_capacity+o2.heuristic;
            return distance1 < distance2? -1:1;
        }
    };
    Comparator<Cell> timeComparator4 =  new Comparator<>() {
        @Override
        public int compare(Cell o1, Cell o2) {
            int o1_preid;
            if(o1.prev==null){
                o1_preid = o1.id;
            }
            else{
                o1_preid = o1.prev.id;
            }
            int o1_time = map.TheCell(o1.id).time(o1_preid);
            int o1_energy = map.TheCell(o1.id).energy(o1_preid);
            int o2_preid;
            if(o2.prev==null){
                o2_preid = o2.id;
            }
            else{
                o2_preid = o2.prev.id;
            }
            int o2_time = map.TheCell(o2.id).time(o2_preid);
            int o2_energy = map.TheCell(o2.id).energy(o2_preid);
            // System.out.println(o2_preid);
            int distance1=o1_time+o1_energy*time_rest/energy_capacity;
            int distance2=o2_time+o2_energy*time_rest/energy_capacity;
            return distance1 < distance2? -1:1;
        }
    };









}


