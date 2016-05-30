import java.util.*;
import java.io.*;

class Solution{
	static String[] twoPowers=new String[801];
	static String multiplyBy2(String str){
		int i=str.length()-1;
		int carry=0;
		StringBuilder builder =  new StringBuilder("");
		while(i>=0){
			int digit= str.charAt(i)-48;
			digit=digit*2+carry;
			builder.append((char)(48+digit%10));
			carry = digit/10;
			i--;
		}

		while(carry>0){
			int d = carry%10;
			carry/=10;
			char ch = (char)(48+d);
			builder.append(ch);
		}
		builder.reverse();
		return builder.toString();
	}
	static void generateTwoPowers(){
		twoPowers[0]="1";
		twoPowers[1]="2";
		twoPowers[2]="4";
		twoPowers[3]="8";

		int i=4;

		while(i<=800){
			String prev = twoPowers[i-1];
			twoPowers[i]=multiplyBy2(prev);
			i++;	
		} 
	}
	public static void main(String[] args) throws IOException{
		generateTwoPowers();
		Trie trie = new Trie();
		//System.out.println("I want to die");
		for(int i=0;i<=800;i++){
			trie.insertString(twoPowers[i],i);
		}
		//System.out.println("Please Help Me to do it");
		trie.populateFailureNodes();

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		int T = Integer.parseInt(reader.readLine());
		//System.out.println(T);

		while(T>0){
			T--;
			String A = reader.readLine();
			//System.out.println(A);
			System.out.println(trie.getCount(A));
		}
	}
}

class Trie{
	TrieNode head;

	public Trie(){
		head = new TrieNode();
	}

	public void insertString(String str,int stringIndex){
		
		int length = str.length();
		int i=0;
		TrieNode current = head;
		while(i<length){
			int index = str.charAt(i)-48;
			TrieNode next =current.getNode(index);
			if(next == null){
				next = current.addGoto(index);
			}
			current =next;
			i++;
		}
		current.addOutput(stringIndex);
	}

	public void populateFailureNodes(){

		TrieNode [] nodes = head.getNodes();
		Queue<TrieNode> queue = new LinkedList<TrieNode>();
		for(int i=0;i<10;i++){
			if(nodes[i]==null)
				nodes[i]=head;
			else{
				queue.add(nodes[i]);
				nodes[i].setFailureNode(head);
			}
		}

		while(!queue.isEmpty()){
			TrieNode r = queue.remove();

			for(int i=0;i<10;i++){
				TrieNode u = r.getNode(i);
				if(u!=null){
					queue.add(u);	
					TrieNode v = r.getFailureNode();
					while(v.getNode(i)==null)
						v = v.getFailureNode();
					u.setFailureNode(v.getNode(i));
					u.addOutput(u.getFailureNode().getOutput());
				}
			}
		}		
	}

	public long getCount(String S){
		int i=0;
		int length = S.length();
		TrieNode current = head;
		long count =0;
		while(i<length){
			int index =S.charAt(i)-48;
			while(current.getNode(index)==null){
				current = current.getFailureNode();
			}

			current = current.getNode(index);

			count = count+current.getSize();
			i++;
		}

		return count;
	}
}

class TrieNode{
	
	TrieNode[] nodes=new TrieNode[10];
	TrieNode failureNode;
	HashSet<Integer> outputs;

	public TrieNode(){
		outputs =new HashSet<Integer>();
		failureNode = null;
	}

	public TrieNode addGoto(int index){
		TrieNode node = new TrieNode();
		nodes[index]=node;
		return node;
	}
	public void setFailureNode(TrieNode node){
		failureNode =node;
	}

	public TrieNode getNode(int index){
		return nodes[index];
	}

	public void addOutput(int index){
		outputs.add(index);
	}

	public TrieNode getFailureNode(){
		return failureNode;
	}

	public void addOutput(Set<Integer> set){
		outputs.addAll(set);
	}

	public TrieNode[] getNodes(){
		return nodes;
	}

	public Set<Integer> getOutput(){
		return outputs;
	}

	public long getSize(){
		return outputs.size();
	}
}