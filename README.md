# Tests logiciels - Felix & Camix
*Robin CARREZ & Axel COUDRAY - FIL A1*

## Avancement
Nous nous sommes penchés sur tous les tests à effectuer et avons développé les deux fonctionnalités. Cependant certains tests ne fonctionnent pas correctement... Cela ne semble pourtant pas être dû au développement qui lui, fonctionnent correctement en IHM. 

Vous pourrez retrouver dans le `felix_cu.pdf` nos scénarios et les changements opérés dans le cu fournit au début du projet. 

## Problèmes rencontrés : 

**FelixConnexionImpossible :**
En phase de test avec Jemmy, le libellé ne se met pas à jour tout seul lors de l'appui sur le bouton connexion. Le champ d'information ne passe pas de `Connexion au chat @%s:%s` à `Connexion au chat @%s:%s impossible`.

**FelixConnexionPossible :**
En phase de test avec Jemmy, la nouvelle fenêtre de chat ne s'ouvre pas à la fin et je ne comprend pas pourquoi. 

**FelixTestQuitter :**
Problème de connexion avec Camix.

**ServiceChatTestQuitter :**
Je voulais mocker l'envoie de message pour qu'il n'appelle pas la connexion mais je ne sais pas comment faire pour que la méthode ne s'exécute pas (clientMock.envoieMessage(messageSortie)). Je voulais que cette méthode n'exécute rien.
