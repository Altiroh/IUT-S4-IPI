mat1=[[0,0,0,1],[0,0,1,0],[0,0,1,1],[1,1,1,1]]

def succ(mat,som):
    listSucc=[]
    for i in range (len(mat[som])):
            if mat[som][i]==1: #vérifiacation de l'existence d'un successeur
                listSucc.append(i)
    return listSucc

def nb_succ(mat,som):
    nb=0
    for i in range (len(mat[som])):
            if mat[som][i]==1: #vérifiacation de l'existence d'un successeur
                nb=nb+1
    return nb

def pred(mat,som):
    listPred=[]
    for i in range (len(mat[som])):
            if mat[i][som]==1: #vérifiacation de l'existence d'un successeur
                listPred.append(i)
    return listPred

def nb_pred(mat,som):
    nb=0
    for i in range (len(mat[som])):
            if mat[i][som]==1: #vérifiacation de l'existence d'un successeur
                nb=nb+1
    return nb

print(succ(mat1,2))
print(nb_succ(mat1,2))
print(pred(mat1,2))
print(nb_pred(mat1,2))