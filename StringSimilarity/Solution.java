import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    /* Head ends here */

    static int[] getZArray(String str){
    	int length = str.length();
    	int [] zArray = new int[length];
    	zArray[0]=0;
    	int l =0;
    	int r=0;
    	for(int i=1;i<length;i++){
    		if(i<=r){
    			zArray[i]=Math.min(r-i+1,zArray[i-l]);
    		}

    		while(i+zArray[i]<length && str.charAt(i+zArray[i])==str.charAt(zArray[i]))
    			zArray[i]++;

    		if(i+zArray[i]-1>r){
    			l=i;
    			r=i+zArray[i]-1;
    		}
    	}

    	return zArray;

    }
    static long stringSimilarity(String a) {

        int [] zArray = getZArray(a);

        long count =0;

        for(int i=0;i<zArray.length;i++)
        	count+=zArray[i];

        count+=a.length();
        return count;
    }

        /* Tail starts here */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long res;

        int _t_cases = Integer.parseInt(in.nextLine());
        for (int _t_i = 0; _t_i<_t_cases; _t_i++) {
            String _a = in.nextLine();
            res = stringSimilarity(_a);
            System.out.println(res);
        }
    }
}