# 1StreetArt Paris

## Resume
Projet étudiant faisant intervenir géolocalisation, base de données.
## Actions
L'utilisateur se voit présenté la carte de Paris dans la moitié supérieure de l'écran et une liste de photos dans la partie inférieure.
Un clic sur une image positionne un marqueur sur la carte.
Le floating action button déclenche l'application Google Camera. En retour, on enregistre dans la base de données l'URI locale de la prise de vue ainsi que la position courante grace a une opération de géolocalisation. 


### BDD SQLite
On y stocke les données concernant les images :
id, nom, description, URI, latitude et longitude (récupérées lors de la prise de vue).

### Que faire sans Geolocalisation ?
Si la position n'est pas disponible, l'image est positionnée sur Paris Notre-Dame.

