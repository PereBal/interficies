# Applicació Interfícies
# Servlets

## API highcharts
**Això ha de retornar JSONs**
[How-To](http://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet#2010993)

**Los putos JSONs los defines tu GILIPOLLAS**
* `GET /:uid` **Proposta:** `GET /`
  * 200 -> Obj JSON dades de les gràfiques
  * _**Comentari**_: Cada gràfica hauría de ser una petició al microservei corresponent.
  La idea seria algo del pal:
        /:graphId[/params]
        ò
        /:graphId/:rangeBegin/:rangeEnd[/params]


## Admin
* `GET /:uid/admin | POST /admin/:uid`
  * _**Comentari**_: S'ha de fer un _**CRUD**_.

_**PROPOSTA**_
* `GET /admin`
  * 200 -> `admin.jsp`

_**CRUD**_ sobre usuaris
* `GET /admin/users` -> Llistar usuaris
* `GET /admin/users/:id` -> Collir un usuaris
* `POST /admin/users` -> Crear un usuari
* `PUT /admin/users/:id` -> Modificar un usuari
* `DELETE /admin/users/:id` -> Borrar un usuari

## Login
* `GET /login`
  * 200 -> `login.jsp`


* `POST /login`
  * 302 -> `home.jsp`, URI: `/`
  * 401 -> `login.jsp`, URI: `/login`

## Chat
* **Proposta:** `GET /chats[/:cid]`
  * 200 -> `chat.jsp`, Ordered by time
    * *Comentari per experts* Els nulls tenen preferència


* `POST /:uid/chats/create-with/:uid2` **Proposta:**`POST /chats?party=uid`
  * 302 -> `chat.jsp`, URI: `/chats/:cid`


* `GET /:uid/chats/:cid?unread=&begin=` **Proposta:**`GET /chats/:cid?unread=&begin=`
  * 200 -> `Ajax`, Ordered by time
  ```json
  {
    "cid": "",
    "mssg": [
      {
        "txt": "",
        "time": "yyyy-mm-dd HH:MM:SS UTC+1"
      }
    ]
  }
  ```


* `GET /:uid/chats/unread/:cid-focus` **Proposta:** `/chats/unread?focus=cid`
  * 200 -> `Ajax`
  ```json
    [
      "cid": {
        "count": "",
        "lmssg": ""
      }
    ]
  ```



* `POST /send/:uid/to/:cid` **Proposta:**`/send?to=uid`
  * 200 -> `Ajax`


* `GET /search?val=string`
  * 200 -> `Ajax`, matching per nom i correu , limit 5
  ```json
    [
      {
        "uid": "",
        "mail": "",
        "name": "",
        "lname": "",
      }
    ]
  ```


# Base de Dades
## Model
Tendrem 3 bases de dades:
  * `highcharts` → Dades del gràfics
  * `www` → Rols i usuaris
  * `chats` → Dades de la missatgeria **NO** instatània

### Www - MariaDB
| vname      | type            |
| ---------- | --------------- |
| id         | integer (PK)    |
| email      | string (unique) |
| name       | string          |
| last_name  | string          |
| role       | bool            |
| pswd       | string          |
| rmbr_token | string          |

### Chats - MongoDB
```
Chat::Agg
  - id::Attr

  Party::List
    User::Agg
      - id::Attr
      - lread_msg::Attr

  Messages::List
    Message::Agg
      - text::Attr
      - uid::Attr
      - timestamp::Attr
```

## Configuració
#### highcharts
```java
public class DBProperties {
  public static final String host = "localhost";
  public static final String port = "3306";
  public static final String db   = "highcharts";
  public static final String user = "root";
  public static final String pass = "";
}
```

#### www
```java
public class DBProperties {
  public static final String host = "localhost";
  public static final String port = "3306";
  public static final String db   = "www";
  public static final String user = "root";
  public static final String pass = "";
}
```

#### chats
```java
public class DBProperties {
  public static final String host = "localhost";
  public static final String port = "27017";
  public static final String db   = "chat";
  public static final String user = "root";
  public static final String pass = "";
}
```
