# Applicació Interfícies
# Servlets

## API highcharts
**Això ha de retornar JSONs**
[How-To](http://stackoverflow.com/questions/2010990/how-do-you-return-a-json-object-from-a-java-servlet#2010993)

**Los putos JSONs los defines tu GILIPOLLAS**
* `GET /`
  * 200 → `index.jsp`


Obj JSON dades de les gràfiques:
  * _**Comentari**_: Cada gràfica hauría de ser una petició al microservei corresponent.
  La idea seria algo del pal:
        /:graphId[/params]
        ò
        /:graphId/:rangeBegin/:rangeEnd[/params]


## Admin
* `GET /admin`
  * 200 → `admin.jsp`

_**CRUD**_ sobre usuaris
* `GET /admin/users` → Llistar usuaris
* `GET /admin/users/:id` → Collir un usuaris
* `POST /admin/users` → Crear un usuari
* `PUT /admin/users/:id` → Modificar un usuari
* `DELETE /admin/users/:id` → Borrar un usuari

## Login
* `POST /login`
  * 302 → `index.jsp`, URI: `/`
  * 401 → `index.jsp`, URI: `/`

## Chat
* `GET /chats[/:cid]`
  * 200 → `chat.jsp`, Ordered by time
    * *Comentari per experts* Els nulls tenen preferència


* `POST /chats?party=uid`
  * 302 → `chat.jsp`, URI: `/chats/:cid`


* `GET /chats/:cid/messages?unread=&begin=`
  * 200 → `Ajax`, Ordered by time
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


* `GET /chats?unread=&skip=:cid`
  * 200 → `Ajax`
  ```json
    {
      "cid": {
        "count": "",
        "lmssg": ""
      }
    }
  ```


* `POST /chats/:cid/messages`
  * 200 → `Ajax`


* `GET /users?value=`
  * 200 → `Ajax`, matching per nom i correu , limit 5
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
| vname      | type                             |
| ---------- | -------------------------------- |
| id         | **integer** auto_increment PK    |
| email      | **varchar** (64) bunique         |
| name       | **varchar** (32) not null        |
| last_name  | **varchar** (128) default null   |
| sex        | **char** (1) in {H, M} not null  |
| pwd        | **varchar** (128) not null       |
| auth_token | **varchar** (64) default null    |
| birth_day  | **date** default null            |
| quote      | **varchar** (256) default null   |

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

  public static final String HOST = "localhost";
  public static final String PORT = "3306";
  public static final String DB   = "highcharts";
  public static final String USER = "interficies";
  public static final String PWD  = "living-on-the-edge";
}
```

#### www
```java
public class DBProperties {

    public static final String HOST = "localhost";
    public static final String PORT = "3306";
    public static final String DB   = "www";
    public static final String USER = "interficies";
    public static final String PWD  = "living-on-the-edge";
}
```

#### chats
```java
public class DBProperties {

  public static final String HOST = "localhost";
  public static final String PORT = "27017";
  public static final String DB   = "chat";
  public static final String COLL = "chats";
  public static final String OPTS = "";

  public static final String OPTS() {
    return (OPTS.equals("")) ? "" : "?" + OPTS;
  }
}
```
