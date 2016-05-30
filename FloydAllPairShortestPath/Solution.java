import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner scanner = new Scanner(System.in);
    	int N = scanner.nextInt();
    	int M = scanner.nextInt();

        long [][] distance = new long[N][N];
        for(int i=0;i<N;i++){
            for(int j=0;j<N;j++){
                if(i!=j)
                    distance[i][j]=Integer.MAX_VALUE;
                else
                    distance[i][j]=0;
            }
        }

    	for(int i=0;i<M;i++){
    		int s = scanner.nextInt();
    		int e = scanner.nextInt();
    		int d = scanner.nextInt();
            distance[s-1][e-1]=d;
    	}

        for(int k=0;k<N;k++){
            for(int i=0;i<N;i++){
                for(int j=0;j<N;j++){
                    distance[i][j]=Math.min(distance[i][j],distance[i][k]+distance[k][j]);
                }
            }
        }

        int Q = scanner.nextInt();

        for(int i=1;i<=Q;i++){
            int s = scanner.nextInt();
            int e = scanner.nextInt();

            if(distance[s-1][e-1]!=Integer.MAX_VALUE)
                System.out.println(distance[s-1][e-1]);
            else
                System.out.println(-1);
        } 
    }
}