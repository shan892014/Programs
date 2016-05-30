import java.io.*;
import java.util.*;


class Heap{
	AdjListNode [] heapData;
	int [] position;
	int size;

	private void swap(int pos1,int pos2){
		AdjListNode temp = heapData[pos1];
		heapData[pos1]=heapData[pos2];
		heapData[pos2]=temp;

		position[heapData[pos1].node]=pos1;
		position[heapData[pos2].node]=pos2;
	}

	private void heapify(int index){
		int min =index;
		int left = index*2+1;
		int right = index*2+2;

		if(left<size && heapData[left].distance<heapData[min].distance){
			min=left;
		}

		if(right<size && heapData[right].distance<heapData[min].distance){
			min=right;
		}

		if(min!=index){
			swap(min,index);
			heapify(min);
		}
	}

	private void heapUp(int index){
		int parent = (index-1)/2;
		while(index>0 && heapData[index].distance<heapData[parent].distance){
			swap(index,parent);
			index =parent;
			parent = (index-1)/2;
		}
	}

	public Heap(int numberOfNodes){
		heapData = new AdjListNode[numberOfNodes];
		position = new int[numberOfNodes];
		size=0;		
	}

	public void addToHeap(int nodeNumber,int distance){
		AdjListNode node = new AdjListNode();
		node.node = nodeNumber;
		node.distance =distance;
		heapData[size++]=node;
		position[nodeNumber]=size-1;
	}

	public AdjListNode removeTopNode(){
		AdjListNode result = heapData[0];
		swap(0,size-1);
		size--;
		heapify(0);
		return result;
	}

	public boolean updateHeap(int nodeNumber,int newDistance){
		int heapPoition = position[nodeNumber];
		if(heapPoition<size && heapData[heapPoition].distance>newDistance){
			heapData[heapPoition].distance = newDistance;
			heapUp(heapPoition);
			return true;
		}

		return false;
	}

	public void printHeap(){
		for(int i=0;i<size;i++)
			System.out.print("{"+heapData[i].node+","+heapData[i].distance+"} ");
		System.out.println("");
	}
}

class AdjListNode{
	public int node;
	public int distance;
}
class Graph{
    ArrayList<ArrayList<AdjListNode>> adjList;
    int numberOfNodes;

    public Graph(int numberOfNodes){
    	adjList = new ArrayList();
    	this.numberOfNodes = numberOfNodes;
    	for(int i=0;i<numberOfNodes;i++){
    		adjList.add(new ArrayList<AdjListNode>());
    	}

    }

    public void addEdge(int start,int end,int distance){
    	// System.out.println("Hello"+start+" "+end+" "+numberOfNodes);
    	AdjListNode node = new AdjListNode();
    	node.node = end-1;
    	node.distance = distance;
    	adjList.get(start-1).add(node);

    	node = new AdjListNode();
    	node.node = start-1;
    	node.distance = distance;
    	adjList.get(end-1).add(node);
    }

    public int[] calculateMinWeight(int startNode){
    	int [] distance = new int [numberOfNodes];

    	Heap heap =new Heap(numberOfNodes);
    	heap.addToHeap(startNode-1,0);
    	for(int i=0;i<numberOfNodes;i++){
    		distance[i]=0;
    		if(i!=(startNode-1)){
    			heap.addToHeap(i,Integer.MAX_VALUE);
    		}
    	}


    	int count =0;
    	while(count!=numberOfNodes){

    		AdjListNode node = heap.removeTopNode();
    		if(node.distance!=Integer.MAX_VALUE){
	    		//System.out.println(node.node);
	    		//System.out.println("Heap"+node.node+" "+ node.distance);
	    		distance[node.node]=node.distance;
	    		ArrayList<AdjListNode> children = adjList.get(node.node);

	    		//heap.printHeap();

	    		for(int i=0;i<children.size();i++){
	    			//System.out.println(children.get(i).node);
	    			AdjListNode child = children.get(i);
	    			int newDistance = child.distance;

	    			heap.updateHeap(child.node,newDistance);
	    		}
    		}
    		else
    			break;

    		//heap.printHeap();
    		count++;

    	}

    	return distance;
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

    	int [] distance = graph.calculateMinWeight(startNode);
    	int cnt =0;
    	for(int i=0;i<N;i++){
    		cnt+=distance[i];
    	}

    	System.out.println(cnt);
    }
}