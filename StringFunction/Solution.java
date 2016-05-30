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
    
    public static int[] getSuffixArray(String str){
        int length = str.length();
        Suffix[] suffixes = new Suffix[length];
        
        for(int i=0;i<length;i++){
            Suffix suffix =new Suffix();
            suffix.index =i;
            suffix.rank[0] = str.charAt(i)-'a';
            suffix.rank[1] = (i+1)>=(length)?-1:str.charAt(i+1)-'a';
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
                
                if(j>=length)
                    suffixes[i].rank[1]=-1;
                else{
                    suffixes[i].rank[1]=suffixes[indexMap[j]].rank[0];
                }
            }
            
            Arrays.sort(suffixes);
        }
        
        int [] suffixArr = new int[length];
        
        for(int i=0;i<length;i++){
            suffixArr[i]=suffixes[i].index;
        }
        
        return suffixArr;
    }
    //Kasai Algorithm Implementation
    public static int[] getLCPArray(String str,int [] suffixArr){
        
        int length = str.length();
        int [] indexMap = new int[length];
        int [] lcpArr = new int[length];
        
        for(int i=0;i<length;i++){
            indexMap[suffixArr[i]]=i;
        }
        
        int k=0;
        for(int i=0;i<length;i++){
               if(k>0)
                k--;
            if(indexMap[i]==length-1){
                continue;
            }
           
            int j = suffixArr[indexMap[i]+1];
           
            while(i+k<length && (j+k)<length && str.charAt(i+k)==str.charAt(j+k))
                   k++;
            lcpArr[indexMap[i]]=k;
        }
        
        return lcpArr;
    }
    
    
    public static long getFunction(String str){
        int [] suffixArr = getSuffixArray(str);
        int [] lcpArray = getLCPArray(str,suffixArr);
         
        suffixArr = null;
        
        long max =0;
        
        Stack<Integer> stackData = new Stack();
        
        for(int i=0;i<lcpArray.length;i++){
            
            while(!stackData.isEmpty() && lcpArray[stackData.peek()]>lcpArray[i]){
                int index =stackData.pop();
                int left =stackData.isEmpty()?-1:stackData.peek();
                long area = (i-left)*lcpArray[index];
                max = area>max?area:max;
            }   
            stackData.push(i);
        }
        
        max = max>str.length()?max:str.length();
        return max;
    }
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        String str;
        Scanner scanner = new Scanner(System.in);
        str = scanner.nextLine();
        
        System.out.println(getFunction(str));
    }
}