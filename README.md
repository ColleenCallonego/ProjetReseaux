# PROJET DE RESEAUX


&nbsp;
> Dans ce README vous allez découvrir les points suivants : 
> - Le contexte de ce projet (*Introduction*) 
> - Les différentes fonctionnalités de notre serveur (*Ce que notre serveur fait*)
> - Les problèmes et fonctionnalités non implémentés sur notre serveur (*Les problèmes/manques de notre serveur*)
> - Comment faire fonctionner notre serveur (*Mode d'emploi*) 
> - Notre avis sur ce projet (*Conclusion*)


&nbsp;
## INTRODUCTION 
Dans le cadre du cours de Réseaux, nous avons du réaliser, par groupe, un serveur.  
Notre groupe de travail est composé de **Maxime Arnould, Colleen Callonego et Gabriel Couroux**.  


&nbsp;
Le serveur que nous devions réaliser devait :  
* Pouvoir se lancer à partir d'un .bat ou d'un .sh ou encore de à partir de quelques commandes
* Etre implémenté en Java 
* Ne pas utliser de librairie "toutes faites"
* Savoir exploiter une requête GET en HTTP/1.x
* Afficher correctement les sites exemples fournis 
* Gérer les erreurs les plus courantes 
* Gérer les connexions en paralèles
* Bien écrit 
* Afficher du log sur stdout
* Gérer le multisite 
* Pourvoir protéger une ressource
* Externaliser la configuration du serveur dans un fichier
* Implémenter au moins une des fonctionnalités "bonus" suivantes : 
  * gérer les server-side includes (#include et #exec)
  * générer du contenu dynamique à l'aide d'un programme externe (php, python, node)
  * gérer le listing des répertoires (doit pouvoir être désactivé par configuration)
  * compresser les ressources js et css en gzip 
  
  
&nbsp;
## CE QUE NOTRE SERVEUR FAIT
Voilà la liste de ce que notre serveur est capable de faire : 
* Il se lance à partir d'un .bat 
* Il sait exploiter une requête GET 
* Il affiche correctement les sites exemples fournis
* Il gére les erreurs les plus courantes (400, 401, 403, 404)
* Il affiche du log sur stdout 
* Il gère le multisite 
* Il sait protéger une ressource
* La configuration du serveur se fait dans un fichier .properties
* Il implémente la fonctionnalité bonus suivante 
  * générer du contenu dynamique à l'aide d'un programme externe (pour du php)

&nbsp;
## LES PROBLEMES/MANQUES DE NOTRE SERVEUR
Durant le développement de ce serveur, nous avons rencontrer beaucoup de problèmes, certains que nous avons réussi à résoudre, d'autre non.  
Nous allons détailler cela ici.


&nbsp;
<ins>__1er problème__</ins> : avec notre projet maven, nous avons pu générer la distribution binaire pour Windows et pour Unix, la distribution Windows (run.bat) 
fonctionne mais nous avons tester la distribution binaire unix sur un Mac et elle ne fonctionne pas vraiment ```Can't assign requested address```  


&nbsp;
<ins>__2ème problème__</ins> : pour protéger une ressource, nous n'avons pas trouvé comment bien faire, nous avons donc réfléchis et opter pour une sécurisation non
conventionelle, lorsqu'un client essaie d'accéder à une ressource protégé, notre serveur lui envoie une erreur 401 et lui indique que pour avoir accès à ce contenu il doit rajouter ```/@user:password@```
à la fin de l'URL


&nbsp;


&nbsp;
<ins>__1er manque__</ins> : dans notre serveur il nous manque les fonctionnalités bonus suivantes (nous ne les avons pas implémentées) : 
* gérer les server-side includes (#include et #exec)
* gérer le listing des répertoires (doit pouvoir être désactivé par configuration)
* compresser les ressources js et css en gzip 


&nbsp;
<ins>__2ème manque__</ins> : nous avons un Socket par requête, même si les requêtes viennent du "même client", nous n'avons pas trouvé comment faire pour gérer bien cela. 


&nbsp;
<ins>__3ème manque__</ins> : notre serveur sécurise un répertoire seulement dans le cadre de l'utilisation de domaine


&nbsp;
## MODE D'EMPLOI

### Sites disponibles pour tester notre serveur    
> Tapez les adresses décrites dans la barre d'adresse de votre navigateur 
* Un répertoire dopetrope avec dedans un index.html   
  * Pour y accéder avec un domaine : www.dopetrope.com  
  * Pour y accéder avec une adresse : 127.0.0.2/dopetrope/ ou 127.0.0.2/dopetrope/index.html (changer le 127.0.0.2 en fonction de la configuration du serveur)  
* Un répertoire verti avec dedans un index.html   
  * Pour y accéder avec un domaine : www.verti.com  
  * Pour y accéder avec une adresse : 127.0.0.2/verti/ ou 127.0.0.2/verti/index.html (changer le 127.0.0.2 en fonction de la configuration du serveur)  
* Un répertoire essai avec dedans un jeu.html   
  * Pour y accéder : 127.0.0.2/essai/jeu.html (changer le 127.0.0.2 en fonction de la configuration du serveur)  
* Un repertoire jeu avec dedans un index.html  
  * Pour y accéder : 127.0.0.2/jeu/ ou 127.0.0.2/jeu/index.html (changer le 127.0.0.2 en fonction de la configuration du serveur)  
* Un fichier index.html  
  * Pour y accéder : 127.0.0.2 ou 127.0.0.2/ ou 127.0.0.2/index.html (changer le 127.0.0.2 en fonction de la configuration du serveur)  
* Un fichier index.php
  * Pour y accéder :127.0.0.2/index.php (changer le 127.0.0.2 en fonction de la configuration du serveur)


&nbsp;
### Pour configurer le serveur
Notre serveur est configurable avec le fichier ```.properties``` qui se trouve à la racine du projet (il est possible que vous ne le voyez pas car il peut être masqué, dans ce cas
modifier les paramètres d'affichage de votre exploirateur de fichier pour qu'il vous montre les éléments cachés). 
Voilà à quoi ressemble ce fichier .properties : 
```
address=127.0.0.2
port=80
repertoireSitesWeb=sites
```
Vous pouvez donc changer l'adresse qu'écoute notre serveur, le port aussi.  
Et vous pouvez modifier le nom où notre serveur va chercher les sites à afficher.


&nbsp;
### Pour simuler les domaines 
Les "domaines" que nous avons utiliser pour que ce projet marche en multisite, et que nous avons écrit dans notre fichier host sont : 
```
#pour le réseau
127.0.0.2 www.verti.com
127.0.0.2 www.dopetrope.com
```
Pour que les domaines marchent lorsque vous allez lancer notre serveur, vous devez rajouter les lignes ci-dessus dans le fichier host de votre ordinateur.
(L'adresse peut être changé en fonction de l'adresse configurer dans le fichier .properties) 


&nbsp;
### Pour créer un répertoire protégé et configurer les utilisateurs et mot de passe
Pour protéger un répertoire il suffit d'ajout à la racine de ce répertoire un fichier ```.htpasswd```.  
Une fois ce fichier créé vous ajouter les différents utilisateurs qui peuvent accèder à ce répertoire comme ceci : 
```
username1:password1_en_md5
username2:password2_en_md5
username3:password3_en_md5
...
```

&nbsp;
### Pour lancer le serveur à partir des sources 
Il faut en premier ouvrir notre project avec un IDE. Et run le ```Main.java```.  


&nbsp;
### Pour lancer le serveur à partir d'une distribution binaire
Pour lancer la distribution binaire pour Windows il faut aller dans le dossier ```dindist-win``` puis dans le dossier ```bin```, et lancer le fichier run.bat.  
Pour lancer la distribution binaire pour Unix il faut aller dans le dossier ```dindist-unix``` puis dans le dossier ```bin```, et lancer le fichier run.  


&nbsp;
### Après avoir lancer le programme soit avec les sources soit avec le fichier binaire
Une fois le programme lancé, nous devons aller sur un navigateur (Chrome, Firefox ...).  
Une fois sur le navigateur plusieurs choix s'offre à nous :  


&nbsp;
<ins>__Pour le multisite__</ins>  
Deux noms de domaines sont gérer par ce serveur : 
* www.verti.com
* www.dopetrope.com
> Notre serveur sécurise un répertoire seulement lors de l'utilisation de domaine 

Dans la configuration initial du projet, le site www.verti.com est sécurisé et le site www.dopetrope.com n'est pas sécurisé.  
Pour accéder au site verti, il suffit de taper  www.verti.com dans la barre d'adresse du navigateur. Une authentification vous sera demandé, si les données de connexions 
que vous rentrés sont correctes vous aurez accès au site sinon non. 
Pour accéder au site dopetrop, il suffit de taper  www.dopetrope.com dans la barre d'adresse du navigateur. 
Une fois arriver dans le site, vous pourrez naviguer dedans comme dans tous les sites internents.


&nbsp;
<ins>__Pour une utilisation avec une adresse (exemple : 127.0.0.2)__</ins>  
Pour une simple utilisation de notre serveur il suffit de taper dans la barre d'adresse du navigateur l'adresse suivi du chemin du fichier à visualiser
par exemple ```127.0.0.2/index.html```.  
Il faut faire attention, car lorsque vous taper un chemin il faut toujours fini le chemin par un ```/``` ou un nom de fichier comme ```index.html```.

<ins>__Pour générer du contenu dynamique (php)__</ins>  
Pour générer du contenu dynamique, cela marche comme pour l'utilisation simple du serveur (ci-dessus), il suffit de taper l'adresse suffit du chemin du fichier .php. 
Par exemple ```127.0.0.2/index.php``` (le fichier index.php est disponible dans le répertoire ```sites```). 
De cette manière lorsqu'un site fait appel à des fichiers .php notre serveur pourra les générer.   


&nbsp;
### Pour arrêter le serveur 
Il suffit de stopper le programme dans l'IDE.    
Il suffit de faire CONTROLE-C pour arrête le serveur avec la distribution binaire.   


&nbsp;
## CONCLUSION
Ce projet a été assez difficile à réaliser mais ensemble nous y sommes arrivé. Même si toutes les fonctionnalités ne sont pas vraiment réalisées de manières correctes, 
elles fonctionnent plutôt bien.  
Nous sommes content de nous pour ce projet. :smile:
