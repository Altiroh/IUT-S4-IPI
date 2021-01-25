mat1=[[0,0,0,1],[0,0,1,0],[0,0,1,1],[1,1,1,1]]

def succ(mat,som):
    listSucc=[]
    for i in range (len(mat[som])):
            if mat[som][i]==1: #vérifiacation de l'existence d'un successeur
                listSucc.append(i)
    return listSucc