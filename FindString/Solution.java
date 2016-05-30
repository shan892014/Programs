import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
/* Head ends here */
    
    static int getLengthIndex(int [] lengths,int index){
        int start =0;
        int end = lengths.length;
        
        while(start<=end){
            if(start==end)
                return start;
            if(start== end-1){
                if(index<lengths[start])
                    return start;
                else
                    return end;
            }
            
            int mid = start+(end-start)/2;
            
            if((mid ==0 && lengths[mid]>index) || (lengths[mid]>index && lengths[mid-1]<=index))
               return mid;
               
            if(lengths[mid]>index)
               end = mid-1;
            else
               start = mid+1;
        }

        return 0;
    }
    public static int[] getSuffixArray(String str,int[] length){
        int k=0;
        int totLength = str.length();
        Suffix[] suffixes = new Suffix[totLength];
        for(int i=0;i<totLength;i++){
            Suffix suffix =new Suffix();
            suffix.index =i;
            suffix.rank[0] = str.charAt(i)-'a';
            if((i+1)>=length[k]){
                suffix.rank[1]=-1;
                k++;
            }
            else{
                suffix.rank[1] = str.charAt(i+1)-'a';
            }
            
            suffixes[i]=suffix;
        }
        
        
        Arrays.sort(suffixes);
        
        int [] indexMap =new int[totLength];
        
        for(k=2;k<(2*totLength);k=k*2){
            int rank=0;
            int prev_rank = suffixes[0].rank[0];
            suffixes[0].rank[0]=rank;
            indexMap[suffixes[0].index]=0;
            
            for(int i=1;i<totLength;i++){
                if(suffixes[i].rank[0]==prev_rank && suffixes[i].rank[1]==suffixes[i-1].rank[1])
                    suffixes[i].rank[0]=rank;
                else{
                    prev_rank =suffixes[i].rank[0];
                    suffixes[i].rank[0]=++rank;
                }
                
                indexMap[suffixes[i].index]=i;
                    
            }
            
            for(int i=0;i<totLength;i++){
                int j = suffixes[i].index+k/2;
                int lengthIndex = getLengthIndex(length,suffixes[i].index);
                if(j>=length[lengthIndex])
                    suffixes[i].rank[1]=-1;
                else{
                    suffixes[i].rank[1]=suffixes[indexMap[j]].rank[0];
                }
            }
            
            Arrays.sort(suffixes);
        }
        
        int [] suffixArr = new int[suffixes.length];
        
        for(int i=0;i<suffixes.length;i++){
            suffixArr[i]=suffixes[i].index;
        }
        
        return suffixArr;
    }
    //Kasai Algorithm Implementation
    public static int[] getLCPArray(String str,int [] suffixArr,int[] lengths){
        
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

            int lengthI = lengths[getLengthIndex(lengths,i)];
            int lengthJ = lengths[getLengthIndex(lengths,j)];
            while(i+k<lengthI && (j+k)<lengthJ && str.charAt(i+k)==str.charAt(j+k))
                   k++;
            lcpArr[indexMap[i]]=k;
        }
        
        return lcpArr;
    }
    static int find(long[] numberOfSubstring,int index){
        int start =0;
        int end = numberOfSubstring.length-1;
        while(start<=end){
            if(start ==end)
                return start;
            if(start == end-1){
                if(numberOfSubstring[start]>=index)
                    return start;
                return end;
            }

            int mid = start + (end-start)/2;

            if((mid ==0 && numberOfSubstring[mid]>=index) ||(numberOfSubstring[mid]>=index && numberOfSubstring[mid-1]<index))
                return mid;

            if(numberOfSubstring[mid]>index){
                end = mid-1;
            }
            else
                start = mid+1;
        }
        return 0;
    }
    static void findStrings(String[] a, int[] querry) {
        int [] lengths = new int[a.length];
        
        lengths[0]=a[0].length();
        for(int i=1;i<a.length;i++){
            lengths[i]=lengths[i-1]+a[i].length();
        }
        
        StringBuffer buffer =new StringBuffer("");
        for(int i=0;i<a.length;i++){
            buffer.append(a[i]);
        }
        
        String str = buffer.toString();
        // System.out.println(str);
        int [] suffixArray = getSuffixArray(str,lengths);
        int [] lcpArray = getLCPArray(str,suffixArray,lengths);
        // for(int i=0;i<suffixArray.length;i++){
        //     System.out.print(suffixArray[i]+" ");
        // }
        // System.out.println();

        // for(int i=0;i<lcpArray.length;i++){
        //     System.out.print(lcpArray[i]+" ");
        // }
        // System.out.println();
        long [] numberOfSubstring = new long[str.length()];
        int length =lengths[getLengthIndex(lengths,suffixArray[0])];
        numberOfSubstring[0]=length-suffixArray[0];

        for(int i=1;i<lcpArray.length;i++){
            length =lengths[getLengthIndex(lengths,suffixArray[i])];
            numberOfSubstring[i]=numberOfSubstring[i-1]+length-suffixArray[i]-lcpArray[i-1];
        }

        // for(int i=0;i<numberOfSubstring.length;i++)
        //     System.out.print(numberOfSubstring[i]);
        // System.out.println();

        for(int i=0;i<querry.length;i++){
            int q = querry[i];
            if(q>numberOfSubstring[lcpArray.length-1]){
                System.out.println("INVALID");
                continue;
            }

            int index = find(numberOfSubstring,q);
            //System.out.println(q+" " +index);
            if(index==0){
                int ind = suffixArray[0]+q;
                System.out.println(str.substring(suffixArray[0],ind));
            }
            else{
                long t=q+lcpArray[index-1]-numberOfSubstring[index-1];
                long ind = suffixArray[index]+t;
                System.out.println(str.substring(suffixArray[index],(int)ind));                
            }
        }
    }
/* Tail starts here */
public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
                
        int _cases = Integer.parseInt(in.nextLine());
        String[] _a = new String[_cases];
        
       for(int _a_i = 0; _a_i < _cases; _a_i++) {
            _a[_a_i] = in.nextLine();
        }
        int _query = Integer.parseInt(in.nextLine());
        int[] query = new int[_query];
        for(int _a_i = 0; _a_i < _query; _a_i++) {
            query[_a_i] = Integer.parseInt(in.nextLine());
        }
        
        findStrings(_a,query);
    }
}

class Suffix implements Comparable<Suffix>{
    public int index;
    public int[] rank = new int[2];

    @Override
    public int compareTo(Suffix suffix){
        if(rank[0]==suffix.rank[0]){
            return rank[1]-suffix.rank[1];
        }

        return rank[0]-suffix.rank[0];
    }
}
