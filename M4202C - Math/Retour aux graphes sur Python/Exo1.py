mat1=[[0,0,0,1],[0,0,1,0],[0,0,1,1],[1,1,1,1]]
G={1:[1,2],2:[2,3,4],3 :[],4 :[2]}

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


print(succD(G,2))
print(nb_succD(G,2))
#print(predD(G,3))