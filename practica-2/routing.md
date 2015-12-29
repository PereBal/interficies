# Microserveis
+ ```default```
  + 404 -> ```Si``` url no trobada
  + 403 -> ```Si``` usuari sense permisos

## Login
+ ```POST /login```
  + 200 -> Obj Session

## Dashboard
+ ```GET /:id```
  + 200 -> Obj JSON dades de les gràfiques
  + _**Comentari**_: Cada gràfica hauría de ser una petició al microservei corresponent.

## Mailing
+ ```GET /:id/chats[/:begin]```
  + 200 -> Obj Llista Conversacions
    + ```Si``` :begin > max_count ```llavors``` llista buida


+ ```GET /:id/chats/:user[/:begin]```  
  + 200 -> Obj Llista Missatges de la conversació
    + ```Si``` :begin > max_count ```llavors``` llista buida


+ ```POST /:id/send/:user```  
  + 200

## Admin
+ ```GET /:id/admin```
  + _**Comentari**_: S'ha de fer un _**CRUD**_.
