import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;


class Suffix implements Comparable<Suffix>{
    public int [] rank = new int[2];
    public int index;
    
    @Override
    public int compareTo(Suffix b){
        if(rank[0]==b.rank[0]){
            return rank[1]-b.rank[1];
        }
        return rank[0]-b.rank[0];
    }
    
}
public class Solution {

    public static int[] getSuffixArray(String str,int firstLength){
        int length = str.length();
        Suffix[] suffixes = new Suffix[length];
        
        for(int i=0;i<length;i++){
            Suffix suffix =new Suffix();
            suffix.index =i;
            suffix.rank[0] = str.charAt(i)-'a';
            if(i<firstLength){
				suffix.rank[1] = (i+1)>=firstLength?-1:str.charAt(i+1)-'a';
            }
            else{
            	suffix.rank[1] = (i+1)>=length?-1:str.charAt(i+1)-'a';
            }
            suffixes[i]=suffix;
        }
        
        Arrays.sort(suffixes);
        
        int [] indexMap =new int[length];
        
        for(int k=2;k<(2*length);k=k*2){
            int rank=0;
            int prev_rank = suffixes[0].rank[0];
            suffixes[0].rank[0]=rank;
            indexMap[suffixes[0].index]=0;
            
            for(int i=1;i<length;i++){
                if(suffixes[i].rank[0]==prev_rank && suffixes[i].rank[1]==suffixes[i-1].rank[1])
                    suffixes[i].rank[0]=rank;
                else{
                    prev_rank =suffixes[i].rank[0];
                    suffixes[i].rank[0]=++rank;
                }
                
                indexMap[suffixes[i].index]=i;
                    
            }
            
            for(int i=0;i<length;i++){
                int j = suffixes[i].index+k/2;
                if(suffixes[i].index<firstLength){
                	if(j>=firstLength)
                    	suffixes[i].rank[1]=-1;
                	else{
                    	suffixes[i].rank[1]=suffixes[indexMap[j]].rank[0];
                	}
                }
                else{
                	if(j>=length)
                    	suffixes[i].rank[1]=-1;
                	else{
                    	suffixes[i].rank[1]=suffixes[indexMap[j]].rank[0];
                	}
            	}
            }
            
            Arrays.sort(suffixes);
        }
        
        int [] suffixArr = new int[length];
        
        for(int i=0;i<length;i++){
            suffixArr[suffixes[i].index]=i;
        }
        
        return suffixArr;
    }
    public static void main(String[] args) throws IOException{
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(reader.readLine());

        while(T>0){
        	T--;
        	String str1= reader.readLine();
        	String str2 = reader.readLine();
        	StringBuilder builder = new StringBuilder();
        	builder.append(str1);
        	builder.append(str2);
        	int [] suffixArray = getSuffixArray(builder.toString(),str1.length());

        	int j = str1.length();
        	int i=0;

        	StringBuilder result = new StringBuilder();

        	while(i<str1.length() && j< builder.length()){
        		if(suffixArray[i]<suffixArray[j]){
        			result.append(builder.charAt(i));
        			i++;
        		}
        		else{
        			result.append(builder.charAt(j));
        			j++;
        		}
        	}

        	while(i<str1.length()){
        		result.append(builder.charAt(i));
        		i++;        		
        	}

        	while(j<builder.length()){
        		result.append(builder.charAt(j));
        		j++;
        	}
        	System.out.println(result.toString());
        }
    }
}