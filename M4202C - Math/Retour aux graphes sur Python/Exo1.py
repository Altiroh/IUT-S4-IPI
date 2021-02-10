import math
from collections import deque

mat1=[[1,0,0,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]
G={1:[1,2],2:[2,3,4],3 :[],4 :[2]}
mat2=[[0,1,0,1,0,0],[0,0,0,0,0,0],[0,1,0,0,1,0],[0,0,0,0,0,1],[0,0,1,1,0,0],[0,0,0,0,0,0]]

def succ(mat,som):
    listSucc=[]
    for i in range (len(mat[som])):
            if mat[som][i]==1: #vérifiacation de l'existence d'un successeur
                listSucc.append(i)
    return listSucc

def nb_succ(mat,som):
    nb=0
    for i in range (len(mat[som])):
            if mat[som][i]==1: #vérification de l'existence d'un successeur
                nb=nb+1
    return nb

def pred(mat,som):
    listPred=[]
    for i in range (len(mat[som])):
            if mat[i][som]==1: #vérification de l'existence d'un pred
                listPred.append(i)
    return listPred

def nb_pred(mat,som):
    nb=0
    for i in range (len(mat[som])):
            if mat[i][som]==1:
                nb=nb+1
    return nb

#print(succ(mat1,2))
#print(nb_succ(mat1,2))
#print(pred(mat1,2))
#print(nb_pred(mat1,2))
    
def succD(dico,som):
    listSucc=[]
    listSucc.append(dico[som])
    return listSucc

#def predD(dico,som):
    #listPred=[]
   # listpred=[]
    
   # for i, som in dico.items():
  #      listPred.append(dico[i])
   # for i in range (len(listPred[som])):
   #         if listPred[som][i]==1  : #vérification de l'existence d'un pred
   #             listpred.append(i)
  #  return listpred

def nb_succD(dico,som):
    listSucc=[]
    nb=0
    listSucc.append(dico[som])
    nb=len(listSucc[0])
    return nb

def mat_adj(dico):
    mat=[]
    mat_adj=[]
    for i in dico:
        mat.append(dico[i])
        mat_adj.append(0)
    return mat_adj
    

#print(succD(G,2))
#print(nb_succD(G,2))
#print(predD(G,3))
#print(mat_adj(G))

def warshall(mat):
    for k in range(len(mat)):
        for i in range(len(mat)):
            for j in range(len(mat)):
                if (mat[i][j]==1 or (mat[i][k]==1 and mat[k][j]==1)):
                    mat[i][j]=1
                else:
                    mat[i][j]=0
    return mat

#print(warshall(mat2))

def parcoursL(mat):
    Pred=[-1]*len(mat)
    Vu=[-1]*len(mat)
    file=[]
    for s in range(len(mat)):
        Pred[s]=None
        Vu[s]=False
    Vu[s]=True
    file.append(s)
    while file!=[]:
        file.remove(s)
    for i in range (len(mat[s])):
        file.append(i)
        Vu[i]=True
        Pred[i]=s
    return Pred

def parcoursP(mat):
    Pred=[-1]*len(mat)
    Vu=[-1]*len(mat)
    pile=[]
    for s in range(len(mat)):
        Pred[s]=None
        Vu[s]=False
    Vu[s]=True
    pile.append(s)
    while pile!=[]:
        pile.remove(len(pile))
    for i in range (len(mat[s])):
        pile.append(i)
        Vu[i]=True
        Pred[i]=s
    return Pred

def decomN(mat):
    nbPred=[-1]*len(mat)
    niv=[-1]*len(mat)
    file=[]
    for i in range (len(mat)):
        niv[i]=None
        nbPred[i]=nb_pred(mat,i)
        if nbPred[i]==0:
            niv[i]=0
            file.append(i)
    while file!=[]:
        file.remove[i]
        for j in range (len(mat[i])):
            nbPred[j]=nbPred[j]-1
            if nbPred[j]==0:
                niv[j]=niv[i]+1
                file.append(j)
    return file

def dijkstra(graph, vertex):
    queue = deque([vertex])
    distance = {vertex: 0}
    while queue:
        t = queue.popleft()
        for voisin in graph[t]:
                queue.append(voisin)
                nouvelle_distance = distance[t] + graph[t][voisin]
                if(voisin not in distance or nouvelle_distance < distance[voisin]):
                    distance[voisin] = nouvelle_distance
                    
    return distance
            

print(decomN(mat1))