# Projet de Graphes II et réseaux
### Binarisation d'images
Thomas Lapierre et Lucas Lelièvre

## Utilisation
1. Se placer dans la racine du projet :
`cd ImageSegmentation/`

2. Exécuter la commande suivante : 
```java -jar imageSegmentation.jar <sourceDuFichierTxt> <parametre> (--info) ```

```<parametre>``` : Obligatoire ✅

```--info```: Pas obligatoire ❌

| ```<parametre>``` | Description                                                                                                  |
|-------------|--------------------------------------------------------------------------------------------------------------|
| -a          | Affiche les pixels présents dans les plans un et deux et renvoie la représentation graphique de la segmentation de l'image. |
| -p          | Affiche uniquement la représentation graphique de la segmentation de l'image.                                          |

```--info``` : Affichage des informations du parsing du fichier texte d'entrée


#### Exemple 1

```java -jar imageSegmentation.jar data/deuxlunes.txt -p```

##### Résultat
<img src="https://github.com/thomaslprr/ImageSegmentation/blob/master/readme-image/demo-result.png" width="350">

#### Exemple 2

```java -jar imageSegmentation.jar data/demo_sujet.txt -a --info```

##### Résultat

<img src="https://github.com/thomaslprr/ImageSegmentation/blob/master/readme-image/demo-result-2.png" width="350">

**A noter :** Pour éviter tout problème, un dossier "data" est déjà présent. Le plus simple est donc de mettre les fichiers que l'on souhaite traiter dedans et ainsi ```<sourceDuFichierTxt>``` aura pour valeur : ```data/<nomDeVotreFichier>``` 
