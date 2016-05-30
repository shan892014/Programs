import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    //Implementing Fenwick Tree
    static long similarPairs=0;
    static long [] fenwickTree;
    static long queryFenwick(int value){
        long count =0;

        while(value>0){
            count+=fenwickTree[value];
            value-=(value&(-value));
        }
    
        return count;
    }
        
    static void updateFenwick(int value,int limit,int increment){
        while(value<=limit){
            // System.out.println("Update "+value);
            fenwickTree[value]+=1;
            value+=(value&(-value));
        }
    }
    static void dfs(int root,int n,ArrayList<ArrayList<Integer>> adjList,int T,boolean[] visited){
        Stack<int> dfsStack = new Stack();
        dfsStack.push(root);
        while(dfsStack.size()!=n){
            root=dfsStack.pop();
            ArrayList<Integer> children = adjList.get(root);
            visited[root]=true;
            // System.out.println(root);
            long valTmorePrev = queryFenwick(Math.min(n,root+T+1));
            long valTlessPrev = queryFenwick(root-T);
            for(int i=0;i<children.size();i++){
                // System.out.println("OMG");
                if(visited[children.get(i)]==false)
                    dfsStack.push(children.get(i));
            }
        }

        while(!dfsStack.isEmpty())
        {
            root = dfsStack.pop();
            long valTmore = queryFenwick(Math.min(n,root+T+1));
            long valTless = queryFenwick(root-T);  
            // if(root==2){
            //     printFenwick(n);
            //     System.out.println(valTless+" "+valTmore+" "+valTlessPrev+" "+valTmorePrev+" "+Math.min(root+T+1,n));
            // }
            similarPairs+=(valTmore-valTless)-(valTmorePrev-valTlessPrev);
            // System.out.println(root+" "+similarPairs);  
            updateFenwick(root+1,n,1);
            //printFenwick(n);
        }
    
    }    
    static void printFenwick(int n){
        for(int i=1;i<=n;i++){
            System.out.print(fenwickTree[i]+" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int T = scanner.nextInt();
        int s,e;
    
        ArrayList<ArrayList<Integer>> adjList = new ArrayList();
        fenwickTree = new long[n+1];
        int [] parent = new int[n];

        boolean []visited =new boolean[n];
    
        for(int i=1;i<=n;i++){
            parent[i-1]=-1;
            adjList.add(new ArrayList());
        }
        
        for(int i=0;i<(n-1);i++){
            s=scanner.nextInt();
            e=scanner.nextInt();
            adjList.get(s-1).add(e-1);
            parent[e-1]=s-1;
        }
    
        int root=-1;
    
        for(int i=0;i<n;i++){
            if(parent[i]==-1){
                root =i;
                break;
            }
        }
    
        dfs(root,n,adjList,T,visited);
        //printFenwick(n);
        System.out.println(similarPairs);   
       }
}

