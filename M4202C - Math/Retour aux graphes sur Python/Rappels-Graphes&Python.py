# -*- coding: utf-8 -*-
#-- Exercice 1
mat1=[[1,0,0,0],[0,0,1,0],[0,0,0,1],[1,0,1,1]]
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

#--Exercice 2/A

#Pour récupérer le dernier élément de lst et le supprimer de lst. => lst.del[len(lst)-1]
#Pour récupérer le premier élément de lst et le supprimer de lst. => lst.del[0]
#Pour ajouter l’élément 2 en fin de la liste lst ? => lst.append(2)
#Pour supprimer l'élément 3 de la liste lst => lst.remove('3')
#Manipuler une file ou une pile avec python =>

#pile = [1,2,3]
#Empiler x => pile.append(x)
#Dépiler => pile.pop()

#file = [1,2,3]
#Enfiler x => file.append(x)
#Défiler => file.pop(0)

#--Exercice 2/B

def warshall(mat):
    for k in range(len(mat)):
        for i in range(len(mat)):
            for j in range(len(mat)):
                if (mat[i][j]==1 or (mat[i][k]==1 and mat[k][j]==1)):
                    mat[i][j]=1
                else:
                    mat[i][j]=0
    return mat

print(warshall(mat2))
