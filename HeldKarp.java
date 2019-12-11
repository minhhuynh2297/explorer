import java.util.*;
import java.util.Map;
import java.util.Arrays;
public class HeldKarp{
    private int distance_matrix[][];
    public HeldKarp(int[][] distance_matrix){
       this.distance_matrix = distance_matrix;
    }
    public List<Integer> travel(){
        Set<Integer> g_set;
        Pair<Integer, Set<Integer>> pair;
        Map<String, Integer> cost = new HashMap<String, Integer>();
        Map<String, Integer> successor = new HashMap<String, Integer>();
        for(int i=1; i<this.distance_matrix.length; i++){
            g_set = new HashSet<Integer>();
            pair = new Pair<>(i,g_set);
            cost.put(pair.toString(), this.distance_matrix[i][0]);
            successor.put(pair.toString(), 0);
        }
        Set<Integer> s = new HashSet<Integer>();
        for(int i=1; i<this.distance_matrix.length; i++){
            s.add(i);
        }       
        // System.out.println(s.toString());
        for(int k=1; k<this.distance_matrix.length-1; k++){
            this.updateCombination(s, this.distance_matrix.length, k);
            for(int i=0; i<combinations.size(); i++){  
                for(int j=1; j<this.distance_matrix.length; j++){
                    int min = Integer.MAX_VALUE;
                    int min_suc = Integer.MAX_VALUE;
                    if(checkNotInCombinations(j, combinations.get(i))){
                        String pair_str;
                        for(int id_in_set=0; id_in_set<combinations.get(i).length; id_in_set++){
                            int arr[] = combinations.get(i).clone();
                            int suc = arr[id_in_set];
                            arr = this.removeTheElement(arr, id_in_set);
                            int front_term = distance_matrix[j][suc];
                            pair_str = Integer.toString(suc)+"-"+Arrays.toString(arr);
                            // System.out.println("**"+pair_str);
                            int final_term = front_term+cost.get(pair_str);
                            if(min>final_term){
                                min = final_term;
                                min_suc = suc;
                            }
                            // System.out.println("********");
                            // System.out.println(j);
                            // System.out.println(front_term);
                            // System.out.println(final_term);
                            // System.out.println(pair_str);
                            // System.out.println("********");
                        }
                        pair_str = Integer.toString(j)+"-"+Arrays.toString(combinations.get(i));
                        pair = new Pair<>(pair_str);
                        cost.put(pair.toString(), min);
                        successor.put(pair.toString(), min_suc);
                        // System.out.println("****MIN****");
                        // System.out.println(pair_str);
                        // System.out.println(min);
                        // System.out.println(min_suc);
                        // System.out.println("********");
                    }
                }
            }
        }
        int s_array[] = this.ToIntArray(s);
        int j=0;
        int min = Integer.MAX_VALUE;
        int min_suc = Integer.MAX_VALUE;
        List<Integer> tour = new ArrayList<Integer>();
        if(checkNotInCombinations(j, s_array)){
            tour.add(j);
            String pair_str;
            for(int id_in_set=0; id_in_set<s_array.length; id_in_set++){
                int arr[] = s_array.clone();
                int suc = arr[id_in_set];
                arr = this.removeTheElement(arr, id_in_set);
                int front_term = distance_matrix[j][suc];
                pair_str = Integer.toString(suc)+"-"+Arrays.toString(arr);
                int final_term = front_term+cost.get(pair_str);
                if(min>final_term){
                    min = final_term;
                    min_suc = suc;
                }
                // System.out.println("********");
                // System.out.println(j);
                // System.out.println(front_term);
                // System.out.println(final_term);
                // System.out.println(pair_str);
                // System.out.println("********");
            }
            pair_str = Integer.toString(j)+"-"+Arrays.toString(s_array);
            pair = new Pair<>(pair_str);
            cost.put(pair.toString(), min);
            successor.put(pair.toString(), min_suc);
            // System.out.println("****Final MIN****");
            // System.out.println(pair_str);
            // System.out.println(min);
            // System.out.println(min_suc);
            // System.out.println("********");
            int visited_point = successor.get(pair.toString());
            tour.add(visited_point); 
            int intArr[] = s_array.clone();
            while(visited_point!=0){
                intArr = this.removeTheElementWithTheValue(intArr, visited_point);
                pair_str = Integer.toString(visited_point)+"-"+Arrays.toString(intArr);
                visited_point = successor.get(pair_str);
                tour.add(visited_point); 
            }
            // System.out.println(tour.toString());
        }

        return tour;
    }
    private static int[] removeTheElementWithTheValue(int[] arr, int value){
        int new_arr[] = new int[arr.length-1];
        int new_i=0;
        for(int old_i=0; old_i<arr.length; old_i++){
            if(arr[old_i]!=value){
                new_arr[new_i] = arr[old_i];
                new_i++;
            }
        }
        return new_arr;
    }
    // Function to remove the element 
    private static int[] removeTheElement(int[] arr, 
                                          int index) 
    { 
  
        // If the array is empty 
        // or the index is not in array range 
        // return the original array 
        if (arr == null
            || index < 0
            || index >= arr.length) { 
  
            return arr; 
        } 
  
        // Create another array of size one less 
        int[] anotherArray = new int[arr.length - 1]; 
  
        // Copy the elements except the index 
        // from original array to the other array 
        for (int i = 0, k = 0; i < arr.length; i++) { 
  
            // if the index is 
            // the removal element index 
            if (i == index) { 
                continue; 
            } 
  
            // if the index is not 
            // the removal element index 
            anotherArray[k++] = arr[i]; 
        } 
  
        // return the resultant array 
        return anotherArray; 
    } 
    private boolean checkNotInCombinations(int x, int[] set){
        for(int id_c=0; id_c<set.length; id_c++){
            if(set[id_c]==x){
                return false;
            }
        }
        return true;
    }
    // Ref GeekToGeek: https://www.geeksforgeeks.org/print-all-possible-combinations-of-r-elements-in-a-given-array-of-size-n/
    public int[] ToIntArray(Set<Integer> s){
        int arr[]=new int[s.size()];
        // Copying contents of s to arr[] 
        Iterator<Integer> it = s.iterator(); 
        for (int i=0; i<s.size(); i++) 
            arr[i] = it.next();
        return  arr;
    }
    private List<int[]> combinations = new ArrayList();
    // The main function that prints all combinations of size r 
    // in arr[] of size n. This function mainly uses combinationUtil() 
    public void updateCombination(Set<Integer> s, int n, int r) 
    { 
        combinations = new ArrayList();
        int arr[]= new int[n];
        // Copying contents of s to arr[] 
        Iterator<Integer> it = s.iterator(); 
        for (int i=0; i<s.size(); i++) 
            arr[i] = it.next(); 
        // A temporary array to store all combination one by one 
        int[] data= new int[r];
  
        // Print all combination using temprary array 'data[]' 
        combinationUtil(arr, data, 0, n-1, 0, r); 
    } 
    public void combinationUtil(int arr[], int data[], int start, 
                                int end, int index, int r) 
    { 
        // Current combination is ready to be printed, print it 
        if (index == r) 
        { 
            int c_arr[] = new int[r];
            for (int j=0; j<r; j++){        
                if(data[j]!=0){
                    // System.out.print(data[j]+" ");
                    c_arr[j] = data[j];                    
                }
                else{
                    c_arr = new int[0];
                    break;
                }         
            }
            if(c_arr.length>0){
                // System.out.println("-----"); 
                combinations.add(c_arr);
            }

            return; 
        } 
        for (int i=start; i<=end && end-i+1 >= r-index; i++) 
        { 
            data[index] = arr[i]; 
            combinationUtil(arr, data, i+1, end, index+1, r); 
        } 
    } 

    public class Pair<A, B> {
        public Integer first;
        public Set<Integer> second;
        public Pair(Integer first, Set<Integer> second) {
            super();
            this.first = first;
            this.second = second;
        }
        public Pair(String str){
            String[] arrOfStr = str.split("-");
            this.first = Integer.parseInt(arrOfStr[0]);
            this.second = new HashSet<Integer>();
            arrOfStr[1] = arrOfStr[1].replace("[", "");
            arrOfStr[1] = arrOfStr[1].replace("]", "");
            if(arrOfStr[1].length()>0){
                arrOfStr = arrOfStr[1].split(",");
                for(int i=0;i<arrOfStr.length;i++){
                    this.second.add(Integer.valueOf(arrOfStr[i].replace(" ", "")));
                }                
            }
        }
        public String toString(){
            return Integer.toString(this.first)+"-"+this.second.toString();
        }

    }
}

