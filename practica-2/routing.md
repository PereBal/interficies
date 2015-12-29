# Microserveis
+ ```default```
  + 404 -> ```Si``` url no trobada
  + 403 -> ```Si``` usuari sense permisos

## Login
+ ```POST /login```
  + 200 -> Obj Session

## Dashboard
+ ```GET /:uid```
  + 200 -> Obj JSON dades de les gràfiques
  + _**Comentari**_: Cada gràfica hauría de ser una petició al microservei corresponent.
                     La idea seria algo del pal:
                        /:graphId[/params]
                        /:graphId/:rangeBegin/:rangeEnd[/params]

## Profile
+ ```GET /:uid/profile[/:uid2] | /profile/:uid```
  + 200 -> Obj Perfil d'usuari

## Mailing
+ ```GET /:uid/chats[/:begin]```
  + 200 -> Obj Llista Conversacions
    + ```Si``` :begin > max_count ```llavors``` llista buida


+ ```GET /:uid/chats/:uid2[/:begin]```
  + 200 -> Obj Llista Missatges de la conversació
    + ```Si``` :begin > max_count ```llavors``` llista buida



+ ```POST /:uid/send | POST /send/:uid/to/:uid2```
  + 200

## Admin
+ ```GET /:uid/admin | POST /admin/:uid```
  + _**Comentari**_: S'ha de fer un _**CRUD**_.
