import java.util.*;
import java.util.ArrayList;
public class KarpSteelCyclePatching{
    private int distance_matrix[][];
    public KarpSteelCyclePatching(int[][] distance_matrix){
       this.distance_matrix = distance_matrix;
    }
    public List<Integer> travel(){
        Hungarian ha = new Hungarian(this.distance_matrix);
        int[] assignment_array = ha.findOptimalAssignment();
        // int[] assignment_array = {3,0,4,1,5,2};
        List<List<Integer>> cycle_list = this.findCycles(assignment_array);
        return this.cyclePatching(cycle_list);
    } 
    private List<Integer> cyclePatching(List<List<Integer>> cycle_list){
		List<Integer> big_a = new ArrayList<Integer>();
		List<Integer> big_b = new ArrayList<Integer>();;
    	while(cycle_list.size()>1){
    		big_a = new ArrayList<Integer>(cycle_list.get(0));
    		cycle_list.remove(0);
    		big_b = new ArrayList<Integer>(cycle_list.get(0));
    		cycle_list.remove(0);
    		int min_id_a = Integer.MAX_VALUE;
    		int min_id_a_next = Integer.MAX_VALUE;
    		int min_id_b = Integer.MAX_VALUE;
    		int min_id_b_next = Integer.MAX_VALUE;
    		int min_permutation = Integer.MAX_VALUE;
    		for(int id_a=0; id_a<big_a.size(); id_a++){
    			int id_a_next;
    			if(id_a+1<big_a.size()){
    				id_a_next = id_a+1;
    			}
    			else{
    				id_a_next = 0;
    			}
    			int v_a = big_a.get(id_a);
    			int v_a_next = big_a.get(id_a_next);
    			// System.out.println(v_a+" "+v_a_next);
    			// System.out.println("---------------------------------------");
    			for(int id_b=0; id_b<big_b.size(); id_b++){
    				int id_b_next;
	    			if(id_b+1<big_b.size()){
	    				id_b_next = id_b+1;
	    			}
	    			else{
	    				id_b_next = 0;
	    			}	
	    			int v_b = big_b.get(id_b);
	    			int v_b_next = big_b.get(id_b_next);
	    			int p_cost = this.permutationCost( v_a, v_a_next, v_b, v_b_next);
	    			// System.out.println(v_b+" "+v_b_next+" "+p_cost);
	    			if(min_permutation>p_cost){
	    				min_permutation = p_cost;
	    				min_id_a = id_a;
	    				min_id_a_next = id_a_next;
	    				min_id_b = id_b;
	    				min_id_b_next = id_b_next;
	    			}
	    			// System.out.println("----------");
				}
				// System.out.println("---------------------------------------");
    		}
    		// System.out.println("------------MIN_Vs---------");
    		// System.out.println(min_id_a+" "+min_id_a_next);
    		// System.out.println(min_id_b+" "+min_id_b_next);
    		int id_in_old_b = min_id_b_next;
    		int id_in_new_a = min_id_a_next;
    		while(true){
    			big_a.add(id_in_new_a, big_b.get(id_in_old_b));
    			if(id_in_old_b == min_id_b){
    				break;
    			}
    			id_in_new_a++;
    			id_in_old_b++;
    			if(id_in_old_b>=big_b.size()){
					id_in_old_b = 0;
    			}
    		}    		
    		List<Integer> big_sum = new ArrayList<Integer>(big_a);
    		cycle_list.add(0, big_sum);
    	}                   
        return this.cyclePresentation(cycle_list.get(0));	
    }
    private List<Integer> cyclePresentation(List<Integer> cycle){
    	List<Integer> cycle_p = new ArrayList<Integer>();
    	int id_zero = cycle.indexOf(0);
    	for(int id=id_zero; id<cycle.size(); id++){
    		cycle_p.add(cycle.get(id));
    	}
    	for(int id=0; id<id_zero; id++){
    		cycle_p.add(cycle.get(id));
    	}  
    	cycle_p.add(0);  	
		// System.out.println(cycle_p.toString());
		return cycle_p; 
    }
    private int permutationCost(int v_a, int v_a_next, int v_b, int v_b_next){
    	return distance_matrix[v_a][v_b_next]+distance_matrix[v_b][v_a_next]-distance_matrix[v_a][v_a_next]-distance_matrix[v_b][v_b_next];
    }
    private List<List<Integer>> findCycles(int[] assignment_array){
        List<List<Integer>> cycle_list = new ArrayList<List<Integer>>();
        List<Integer> cycle = new ArrayList<Integer>();      
        boolean[] visit = new boolean[assignment_array.length]; 
        Arrays.fill(visit, false);
        List<Integer> non_assigned_ids = new ArrayList<Integer>();
        for(int i=0; i<assignment_array.length; i++){
            non_assigned_ids.add(i);
        }
        int current_node_id = 0;
        while(true){
            if(visit[current_node_id]){
                List<Integer> clone_cycle = new ArrayList<>();
                clone_cycle.addAll(cycle);
                cycle_list.add(clone_cycle);
                if(non_assigned_ids.size()>0){
                    cycle = new ArrayList<>();
                    current_node_id = non_assigned_ids.get(0);
                }
                else{
                    break;
                }
                
            }
            else{ 
                visit[current_node_id] = true;
                cycle.add(current_node_id);
                non_assigned_ids.remove(new Integer(current_node_id));
                current_node_id = assignment_array[current_node_id];
            }
        }

    	Comparator<List<Integer>> stringLengthComparator = new Comparator<List<Integer>>()
	    {
	        @Override
	        public int compare(List<Integer> o1, List<Integer> o2)
	        {
	            return Integer.compare(o2.size(), o1.size());
	        }
	    };
	    Collections.sort(cycle_list, stringLengthComparator);
        for(int id=0; id<cycle_list.size(); id++){
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<cycle_list.get(id).size(); i++){
				int num = cycle_list.get(id).get(i);
				sb.append(num);            	
            }
            // for (int i = cycle_list.get(id).size() - 1; i >= 0; i--) {

            // }
            String result = sb.toString();
            // System.out.println(result);            
        }    
        return cycle_list;	
    }
}