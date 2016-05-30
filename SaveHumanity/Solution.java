import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

	static int[] getZArray(String str,int patLength){
		int totalLength = str.length();
		int stringLength = totalLength - patLength;
		int [] zArray = new int[stringLength];
		int [] pArray = new int [patLength];

		pArray[0]=0;
		int l=0;
		int r=0;
		for(int i=1;i<patLength;i++){
			if(i<=r){
				pArray[i]=Math.min(r-i+1,pArray[i-l]);
			}

			while(i+pArray[i]<patLength && str.charAt(i+pArray[i])==str.charAt(pArray[i]))
				pArray[i]++;

			if(i+pArray[i]-1>r){
				r=i+pArray[i]-1;
				l=i;

				// System.out.println(l+" -- "+r);
			}
		}
		int k=0;

		for(int i=patLength;i<totalLength;i++,k++){
			if(i<=r){
				int val = i-l;
				// System.out.println(k+" "+val);
				zArray[k]=Math.min(r-i+1,pArray[val]);
			}

			while(i+zArray[k]<totalLength && zArray[k]<patLength && str.charAt(i+zArray[k]) == str.charAt(zArray[k])){
				zArray[k]++;
			}
			if(i+zArray[k]-1>r){
				r=i+zArray[k]-1;
				l=i;
			}
		}

		return zArray;

	}
    public static void main(String[] args)throws IOException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());

        while(T>0){
        	T--;
        	String temp= reader.readLine();
        	String [] arr = temp.split(" ");
        	String str = arr[0];
        	String pat =arr[1];
        	StringBuilder builder = new StringBuilder();
        	
        	builder.append(pat);
        	builder.append(str);

        	int [] prefix = getZArray(builder.toString(),pat.length());
        	
        	builder = new StringBuilder();
        	builder.append(pat);
        	builder.reverse();
        	StringBuilder builder1= new StringBuilder(str);
        	builder1.reverse();
        	builder.append(builder1);
        	// System.out.println("OMG");
        	int [] suffix = getZArray(builder.toString(),pat.length());

        	int k=0;
        	int j= str.length()-1;

        	while(k<j){
        		int t1 =suffix[k];
        		suffix[k]=suffix[j];
        		suffix[j]=t1;
        		k++;
        		j--;
        	}

        	// for(int i=0;i<str.length();i++)
        	// 	System.out.print(prefix[i]+" ");
        	// System.out.println();

        	// for(int i=0;i<str.length();i++)
        	// 	System.out.print(suffix[i]+" ");
        	// System.out.println();
        	ArrayList<Integer> result = new ArrayList<>();

        	for(int i=0;i<=(str.length()-pat.length());i++){
        		if((prefix[i]+suffix[i+pat.length()-1])>=pat.length()-1){
        			result.add(i);
        		}
        	}

        	if(result.size() ==0){
        		System.out.println("No Match!");
        	}
        	else{
            	Iterator it = result.iterator();
        		while(it.hasNext()){
        			System.out.print(it.next()+" ");
        		}
        		System.out.println();
        	}
       	}
    }
}