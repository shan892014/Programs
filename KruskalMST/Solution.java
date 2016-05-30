import java.io.*;
import java.util.*;

class EdgeSet implements Comparable<EdgeSet>{
	public int source;
	public int end;
	public int distance;

	public EdgeSet(int source,int end,int distance){
		this.source = source;
		this.end = end;
		this.distance =distance;
	}

	public int compareTo(EdgeSet elem){
		return this.distance - elem.distance;
	}
}
class Graph{
    ArrayList<EdgeSet> edgeSets;
    int numberOfNodes;

    public Graph(int numberOfNodes){
    	edgeSets = new ArrayList();
    	this.numberOfNodes = numberOfNodes;
    }

    public void addEdge(int start,int end,int distance){
    	edgeSets.add(new EdgeSet(start,end,distance));
    }

    private int getRoot(int [] parent,int node){
    	if(parent[node]<0)
    		return node;
    	return getRoot(parent,parent[node]);
    }

    private void union(int [] parent,int root1,int root2){
    	if(parent[root1]<parent[root2]){
    		int temp =parent[root2];
    		parent[root2]=root1;
    		parent[root1]+=temp;
    	}
    	else{
    		int temp =parent[root1];
    		parent[root1]=root2;
    		parent[root2]+=temp;
    	}
    }
    public int calculateMinWeight(int startNode){
    	Collections.sort(edgeSets);
    	int [] parent = new int[numberOfNodes+1];
    	for(int i=1;i<=numberOfNodes;i++)
    		parent[i]=-1;

    	int count =0;

    	for(int i=0;i<edgeSets.size();i++){
    		EdgeSet edge = edgeSets.get(i);

    		int source=getRoot(parent,edge.source);
    		int end =getRoot(parent,edge.end);

    		if(source!=end){
    			union(parent,source,end);
    			count+=edge.distance;
    		}
    	}

    	return count;
    }
}
public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
    	int N = scanner.nextInt();
    	int M = scanner.nextInt();

    	Graph graph = new Graph(N);

    	for(int i=0;i<M;i++){
    		int s = scanner.nextInt();
    		int e = scanner.nextInt();
    		int d = scanner.nextInt();
    		graph.addEdge(s,e,d);
    	}

    	int startNode = scanner.nextInt();

    	System.out.println(graph.calculateMinWeight(startNode));     
    }
}