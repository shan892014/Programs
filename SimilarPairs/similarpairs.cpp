#include <cmath>
#include <cstdio>
#include <vector>
#include <iostream>
#include <algorithm>
using namespace std;
#define MAX 100000
long similarPairs=0;
long fenwickTree[MAX+1];

long queryFenwick(long value){
    long count =0;

    while(value>0){
        count+=fenwickTree[value];
        value-=(value&(-value));
    }
    
    return count;
}

void prlongFenwick(long n){
    for(long i=1;i<=n;i++)
        cout<<fenwickTree[i]<<" ";
    cout<<endl;
}

long minimum(long a,long b){
	return a<b?a:b;
}


void updateFenwick(long value,long limit,long increment){
    //cout<<value<<" "<<limit<<endl;
    while(value<=limit){
        //cout<<value<<" "<<limit<<endl;
        fenwickTree[value]+=1;
        value+=(value&(-value));
    }
}
void dfs(long root,long n,vector<vector<long> >&adjList,long T,vector<bool>& visited){
    vector<long> children = adjList[root];
    visited[root]=true;
    cout<<"Root::"<<root<<" "<<children.size()<<endl;
    //long valTmorePrev = queryFenwick(minimum(n,root+T+1));
    //long valTlessPrev = queryFenwick(root-T);
    for(long i=0;i<children.size();i++){
        //cout<<children[i]<<endl;
        if(visited[children[i]]==false)
            dfs(children[i],n,adjList,T,visited);
    }
    
    //long valTmore = queryFenwick(minimum(n,root+T+1));
    //long valTless = queryFenwick(root-T);  
    //similarPairs+=(valTmore-valTless)-(valTmorePrev-valTlessPrev);
    //updateFenwick(root+1,n,1);
    
} 

int main() {
    /* Enter your code here. Read input from STDIN. Prlong output to STDOUT */ 
    long n,T,s,e;  
    cin>>n;
    cin>>T;
    
    vector<vector<long> > adjList;
    long parent[n];

    vector<bool> visited(n);

    for(long i=0;i<n;i++){
        parent[i]=-1;
        visited[i]=false;
        vector<long> adjListRow;
        adjList.push_back(adjListRow);
    }
    
    for(long i=0;i<(n-1);i++){
        cin>>s;
        cin>>e;
        adjList[s-1].push_back(e-1);
        parent[e-1]=s-1;
    }

    long root=-1;

    for(long i=0;i<n;i++){
        if(parent[i]==-1){
            root =i;
            break;
        }
    }
    dfs(root,n,adjList,T,visited);
    //prlongFenwick(n);
    cout<<similarPairs<<endl;    
	return 0;
}



