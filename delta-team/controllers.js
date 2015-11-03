var appControllers = angular.module("cookingAppControllers", ['ngAnimate', 'ui.bootstrap']);

/*
 * Receptes -> all, filter, filter_by user
 * User -> all, filter
 */

var get = function (data, eval) {
  console.log("get(" + data + ")");
  try {
    for (var i = 0, len = data.length; i < len; i++) {
      console.log(i + "::" + data[i].id + "::" + eval(data[i]));
      if (eval(data[i])) {
        return data[i];
      }
    }
  } catch (err) { console.log(err); };
  return false;
}

var filter = function (data, eval) {
  console.log("filter(" + data + ")");
  res = [];
  try {
    if (typeof data === 'undefined' || typeof eval == 'undefined')
      return res;
    for (var i = 0, len = data.length; i < len; i++) {
      console.log(i + "::" + data[i].id + "::" + eval(data[i]));
      if (eval(data[i])) {
        res.push(data[i]);
      }
    }
  } catch (err) { console.log(err); }
  return res;
}

var save = function (data, elem, eval) {
  console.log("save(" + data + ") - " + eval);
  var last = undefined;
  for (var i = 0, len = data.length; i < len; i++) {
    console.log(i + "::" + data[i].id + "::" + eval(data[i], elem));
    if (eval(data[i], elem)) {
      data[i] = elem;
    }
    last = data[i];
  }
  if (typeof elem.id === 'undefined') {
    elem.id = last.id + 1;
  }
  // iterar sobre el elem i inicialitzar amb valors per defecte
  if (typeof elem.img === 'undefined') {
    elem.img = 'images/no-user.jpg';
  }
  data.push(elem);
  return elem;
}

var callbackIfNeeded = function (callback, $rootScope, $scope, $http, $routeParams, args) {
  if (typeof callback !== 'undefined') {
    if (typeof args !== 'undefined') {
      callback($rootScope, $scope, $http, $routeParams, args);
    } else {
      callback($rootScope, $scope, $http, $routeParams);
    }
  }
}

/*
 * Carrega totes les coses interessants segons la ruta al scope i executa una
 * funció de callback si s'ha especificat
 *  sempre ->  recipes, users, alertSocial, favorite
 *  si userId i recipeId -> user, recipe, related (llista de relacionades a
 *  recipe), fav_users, fav_recipes
 *  si userId -> user, fav_users, fav_recipes
 *  si recipeId -> recipe, related
 */
var loadContext = function ($rootScope, $scope, $http, $routeParams, callback, args) {
  if (!$rootScope.allowEdit) {
    $rootScope.allowEdit = function () {
      return (typeof $rootScope.user !== 'undefined') && (typeof $routeParams.userId !== 'undefined') && $rootScope.user.id == $routeParams.userId;
    }
  }

  if (!$rootScope.favorite) {
    $rootScope.favorite = function (recipe) {
      alert("Recipe " + recipe.title + " added to favs!");
    }
  }
  if (!$rootScope.alertSocial) {
    $rootScope.alertSocial = function (socialNetwork) {
      alert("Share on " + socialNetwork);
    }
  }

  var userId = ($routeParams.userId) ? $routeParams.userId:false;
  var recipeId = ($routeParams.recipeId) ? $routeParams.recipeId:false;

  var evalUser = function (elem) {
    return elem.id == evalUser.id;
  }
  evalUser.id = userId;
  var evalRelated = function (elem) {
    return evalRelated.ids.indexOf(elem.id) != -1;
  }

  if (userId && recipeId) {
    var evalUserRecipe = function (elem) {
      return elem.id == evalUserRecipe.id && elem.userId == evalUserRecipe.userId;
    }
    evalUserRecipe.id = recipeId;
    evalUserRecipe.userId = userId;

    if(!$rootScope.users && !$rootScope.recipes) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;
        $http.get('data/recipes.json').success(function (data) {
          $rootScope.recipes = data;

          $scope.recipe = get($rootScope.recipes, evalUserRecipe);
          evalRelated.ids = $scope.recipe.related;
          $scope.related = filter(data, evalRelated);

          $scope.user = get($rootScope.users, evalUser);
          evalRelated.ids = $scope.user.fav_users;
          $scope.fav_users = filter($rootScope.users, evalRelated);
          evalRelated.ids = $scope.user.fav_recipes;
          $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

          callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
        });
      });

    } else if (!$rootScope.users) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;

        $scope.recipe = get($rootScope.recipes, evalUserRecipe);
        evalRelated.ids = $scope.recipe.related;
        $scope.related = filter(data, evalRelated);

        $scope.user = get($rootScope.users, evalUser);
        evalRelated.ids = $scope.user.fav_users;
        $scope.fav_users = filter($rootScope.users, evalRelated);
        evalRelated.ids = $scope.user.fav_recipes;
        $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else if (!$rootScope.recipes) {
      $http.get('data/recipes.json').success(function (data) {
        $rootScope.recipes = data;

        $scope.recipe = get($rootScope.recipes, evalUserRecipe);
        evalRelated.ids = $scope.recipe.related;
        $scope.related = filter(data, evalRelated);

        $scope.user = get($rootScope.users, evalUser);
        evalRelated.ids = $scope.user.fav_users;
        $scope.fav_users = filter($rootScope.users, evalRelated);
        evalRelated.ids = $scope.user.fav_recipes;
        $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.recipe = get($rootScope.recipes, evalUserRecipe);
      evalRelated.ids = $scope.recipe.related;
      $scope.related = filter($rootScope.recipes, evalRelated);

      $scope.user = get($rootScope.users, evalUser);
      evalRelated.ids = $scope.user.fav_users;
      $scope.fav_users = filter($rootScope.users, evalRelated);
      evalRelated.ids = $scope.user.fav_recipes;
      $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

      callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
    }

  } else if (userId) { // assert userId != false
    if (!$rootScope.users) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;

        $scope.user = get($rootScope.users, evalUser);
        evalRelated.ids = $scope.user.fav_users;
        $scope.fav_users = filter($rootScope.users, evalRelated);
        evalRelated.ids = $scope.user.fav_recipes;
        $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.user = get($rootScope.users, evalUser);
      evalRelated.ids = $scope.user.fav_users;
      $scope.fav_users = filter($rootScope.users, evalRelated);
      evalRelated.ids = $scope.user.fav_recipes;
      $scope.fav_recipes = filter($rootScope.recipes, evalRelated);

      callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
    }

  } else if (recipeId) {
    var evalRecipe = function (elem) {
      return elem.id == evalRecipe.id;
    }
    evalRecipe.id = recipeId;

    if (!$rootScope.recipes) {
      $http.get('data/recipes.json').success(function (data) {
        $rootScope.recipes = data;
        $scope.recipe = get(data, evalRecipe);
        evalRelated.ids = $scope.recipe.related;
        $scope.related = filter(data, evalRelated);

        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.recipe = get($rootScope.recipes, evalRecipe);
      evalRelated.ids = $scope.recipe.related;
      $scope.related = filter($rootScope.recipes, evalRelated);

      callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
    }

  } else {
    if (!$rootScope.users && !$rootScope.recipes) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;
        $http.get('data/recipes.json').success(function (data) {
          $rootScope.recipes = data;
          callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
        });
      });

    } else if (!$rootScope.users) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else if (!$rootScope.recipes) {
      $http.get('data/recipes.json').success(function (data) {
        $rootScope.recipes = data;
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });
    } else {
      callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
    }
  }

  $rootScope.alertFind = function(){
    alert('Not implemented yet, this button search through all the recipes what you have written in the inpup');
  }

  $rootScope.firstRegions = [ 
    {
      name:'Spain',
      selected:true
    },
    {
      name:'Italy',
      selected:false
    },
    {
      name:'France',
      selected:false
    },
    {
      name:'Germany',
      selected:false
    },
    {
      name:'England',
      selected:false
    }
  ];
  $rootScope.secondRegions = [
    {
      name:'North america',
      selected:false
    },
    {
      name:'Argentina',
      selected:false
    },
    {
      name:'Japan',
      selected:false
    },
    {
      name:'China',
      selected:false
    },
    {
      name:'Burundy',
      selected:false
    }
  ];
  $rootScope.allRegions = [ 
    {
      name:'Spain',
      selected:false
    },
    {
      name:'Italy',
      selected:false
    },
    {
      name:'France',
      selected:false
    },
    {
      name:'Germany',
      selected:false
    },
    {
      name:'England',
      selected:false
    },
    {
      name:'North america',
      selected:true
    },
    {
      name:'Argentina',
      selected:false
    },
    {
      name:'Japan',
      selected:false
    },
    {
      name:'China',
      selected:false
    },
    {
      name:'Burundy',
      selected:false
    }

  ];


  $rootScope.showRegions = 1;
  $rootScope.apliedFilter = false;

  $rootScope.showNextRegions = function() {
    if ($rootScope.showRegions === 1) {

      $rootScope.showRegions = 2;
    } else {

      $rootScope.showRegions = 1;
    }
  }
  $rootScope.showAllRegions = function() {

    if ($rootScope.showRegions ===3 ) {

      $rootScope.showRegions = 1;
    } else {

      $rootScope.showRegions = 3;
    }
  }
}

function RecipeListCtrl($rootScope, $scope, $http, $location, $routeParams) {
  var args = [$location];
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    $scope.alertShowNext = function(message) {
      alert('Not yet implemented, this will show the '+ message);
    }

    $scope.view = function (recipe) {
      args[0].path('/recipes/' + recipe.id);
    }

    $scope.viewUser = function (userId) {
      args[0].path('/users/' + userId + '/profile');
    }

  $rootScope.filteredRecipes = [
   {
        id: 1,
        title: "Spanish Burguer",
        author: "Steve",
        rating: 5,
        hardness: "medium",
        time: {"hours": 0, "minutes": 30},
        eaters: {"number": 4, "info": "per ration"},
        related: [3, 4],
        ingredients: [
        {
            name: "Cow meat",
            amount: 1,
            unit: "kg"
        },
        {
            name: "Onion",
            amount: 2,
            unit: "pieces"
        },
        {
            name: "salad",
            amount: 3,
            unit: "pieces"
        },
        {
            name: "tomato",
            amount: 4,
            unit: "pieces"
        },
        {
            name: "Bread",
            amount: 4,
            unit: "pieces"
        }        ],
        steps:[
        {
            description: "Adobe the meat with pepper an salt"
        },
        {
            description: "Make 4 balls and then smash them"
        },
        {
            description: "Grill the burguers durin 5 minutes each side"
        },
        {
            description: "Prepare the burguers with love"
        }],
        private: false,
        img: "images/burguer.jpg",
        description: "A hamburger (also called a beef burger, hamburger sandwich, burger, hamburg or cheeseburger when served with a slice of cheese) is a sandwich consisting of one or more cooked patties of ground meat, usually beef, placed inside a sliced bun. Hamburgers may be cooked in a variety of ways, including pan-frying, barbecuing, and flame-broiling. Hamburgers are often served with lettuce, bacon, tomato, onion, pickles, cheese and condiments such as mustard, mayonnaise, ketchup, relish, and chiles.[1] The term burger can also be applied to the meat patty on its own, especially in the UK where the term patty is rarely used. The term may be prefixed with the type of meat or meat substitute used, as in turkey burger, bison burger, or veggie burger. Hamburgers are sold at fast-food restaurants, diners, and specialty and high-end restaurants (where burgers may sell for several times the cost of a fast-food burger). There are many international and regional variations of the hamburger.",
        userId: 1
    },{
        id: 9,
        title: "Pa amb oli",
        author: "Robert de Niro",
        rating: 4,
        hardness: "medium",
        time: {"hours": 4, "minutes": 0},
        eaters: {"number": 100, "info": "per ration"},
        related: [],
        ingredients: [
        {
            name: "gegeric 1",
            amount: 1.0,
            unit: "piece"
        },
        {
            name: "gegeric 1",
            amount: 2.0,
            unit: "pieces"
        },
        {
            name: "gegeric 3",
            amount: 2.0,
            unit: "pieces"
        },
        {
            name: "gegeric 3",
            amount: 2.0,
            unit: "pieces"
        }
        ],
        steps:[
        {
            description: "step 1"
        },
        {
            description: "step 2"
        },
        {
            description: "step 3"
        },
        {
            description: "step 4"
        },
        {
            description: "step 5"
        }
        ],
        private: false,
        img: "images/paamboli.jpg",
        description: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum",
        userId: 6
    }
  ];

  $scope.predicate = 'rating';
  $scope.reverse = true;

  $scope.userNews = [
    {
      source: 'im Carrey',
      action: 'likes Einstein recipe a lot',
      img: 'images/carrey.jpg'
    },
    {
      source: 'Einstein',
      action: 'Liked Jim carreys like',
      img: 'images/einstein.jpg'
    },
    {
      source: 'Sr Mr Bean',
      action: 'Just added chids salad to his recipes',
      img: 'images/mr-bean.jpg'
    },
    {
      source: 'Steve jobs',
      action: 'disliked Pa amb oli from Robert de Niro',
      img: 'images/steve-jobs.jpg'
    },
    {
      source: 'DiCaprio',
      action: 'has shared in instagram his recipe',
      img: 'images/leo-dicaprio.jpg'
    },
    {
      source: 'Einstein',
      action: 'Liked Jim carreys like',
      img: 'images/einstein.jpg'
    }];
  }, args);

}

function RecipeCtrl($rootScope, $scope, $routeParams, $http) {
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    $scope.edit = false;
    $scope.allowEdit = function () {
      return false;
    }
    $scope.readOnly = function () {
      return true;
    }
  });
}

function LoginCtrl($rootScope, $scope, $http, $location, $routeParams) {
  $scope.validate = function (user) {
    var args = [$location, user];
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams, args) {
      var eval = function (elem) {
        return elem.email == eval.email && elem.password == eval.password;
      };
      eval.email = args[1].email;
      eval.password = args[1].password;

      user = get($rootScope.users, eval);
      if (user) {
        $rootScope.user = user;
        args[0].path('/users/'+user.id);
      }
    }, args);
  }

  $scope.save = function (user) {

    alert('Not yet implemented, if you want to feel the experience of beeing one of our users, try it with this login -> usr: foo@mail.com , pass:foo');
    /*var args = [$location, user];
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams, args) {
      var eval = function (elem) {
        return elem.id == eval.id;
      }
      eval.id = args[1].id;

      save($rootScope.users, args[1], eval);
      $rootScope.user = args[1];
      args[0].path('/users/' + args[1].id);
    }, args);*/
  }
}

function UsrHomeCtrl($rootScope, $scope, $http, $location, $routeParams) {
  var args = [$location];
  loadContext($rootScope, $scope, $http, $routeParams, function($rootScope, $scope, $http, $routeParams) {
    var eval = function (elem) {
      return elem.userId != eval.id;
    }
    eval.id = $routeParams.userId;

    if (!$rootScope.user_lastrecipes) {
      $http.get('data/user_lastrecipes.json').success(function (data) {
        $rootScope.user_lastrecipes = data;
        $scope.recipes = filter(data, eval);
      });
    } else {
      $scope.recipes = filter($rootScope.user_lastrecipes, eval);
    }

    $scope.view = function (recipe) {
      args[0].path('/recipes/' + recipe.id);
    }

    $scope.viewUser = function (userId) {
      args[0].path('/users/' + userId + '/profile');
    }

    $scope.fork = function (recipe) {
      alert('Iniciar fork de la receta ' + recipe.title);
    }


    
  $scope.userNews = [
    {
      source: 'im Carrey',
      action: 'likes Einstein recipe a lot',
      img: 'images/carrey.jpg'
    },
    {
      source: 'Einstein',
      action: 'Liked Jim carreys like',
      img: 'images/einstein.jpg'
    },
    {
      source: 'Sr Mr Bean',
      action: 'Just added chids salad to his recipes',
      img: 'images/mr-bean.jpg'
    },
    {
      source: 'Steve jobs',
      action: 'disliked Pa amb oli from Robert de Niro',
      img: 'images/steve-jobs.jpg'
    },
    {
      source: 'DiCaprio',
      action: 'has shared in instagram his recipe',
      img: 'images/leo-dicaprio.jpg'
    },
    {
      source: 'Einstein',
      action: 'Liked Jim carreys like',
      img: 'images/einstein.jpg'
    }];

  }, args);

}

function UsrRecipeListCtrl($rootScope, $scope, $http, $location, $routeParams) {
  var args = [$location];
  loadContext($rootScope, $scope, $http, $routeParams, function($rootScope, $scope, $http, $routeParams) {
    var eval = function (elem) {
      return elem.userId == eval.id;
    }
    eval.id = $routeParams.userId;

    var adding = false;

    $scope.recipeNew = [];

    $scope.recipes = filter($rootScope.recipes, eval);

    $scope.ingredients = [{
            name: '',
            amount: 0,
            unit: ''
        }];

      $scope.steps=[{
         description: ''
      }];

      $scope.img = 'images/add.png';


    $scope.view = function (recipe) {
      args[0].path('/users/' + $routeParams.userId + '/recipes/' + recipe.id);
    }

    $scope.recipePrivacy = function (recipe) {
      return (recipe.private) ? 'Private':'Public';
    }

    $scope.recipePrivacyClass = function (recipe) {
      return (recipe.private) ? 'btn btn-danger btn-xs':'btn btn-success btn-xs';
    }

    $scope.addIngredient = function () {
      $scope.ingredients.push({name: "", amount: 0.0, unit: ""});
    }

    $scope.removeIngredient = function (ingredient) {
      $scope.ingredients.splice($scope.ingredients.indexOf(ingredient), 1);
    }

    $scope.addStep = function () {
      $scope.steps.push({name: "", amount: 0.0, unit: ""});
    }

    $scope.removeStep = function (step) {
      $scope.steps.splice($scope.steps.indexOf(step), 1);
    }

    $scope.addUserRecipe = function() {
      $scope.adding = false;
      $scope.recipeNew.id = $scope.recipes.lenght + 1;
      $scope.recipeNew.img = $scope.img;
      $scope.recipeNew.author = 'Steve';

      $scope.recipes.push($scope.recipeNew);
  }

    $scope.deleteUserRecipe = function(id) {

      $scope.recipes.splice(id, 1);
  }

  }, args);
}

function UsrRecipeCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {

    $scope.addIngredient = function () {
      $scope.recipe.ingredients.push({name: "", amount: 0.0, unit: ""});
    }

    $scope.removeIngredient = function (ingredient) {
      $scope.recipe.ingredients.splice($scope.recipe.ingredients.indexOf(ingredient), 1);
    }

    $scope.addStep = function () {
      $scope.recipe.steps.push({name: "", amount: 0.0, unit: ""});
    }

    $scope.removeStep = function (step) {
      $scope.recipe.steps.splice($scope.recipe.steps.indexOf(step), 1);
    }

    $scope.edit = false;
    $scope.readOnly = function () {
        return !$scope.edit || !$rootScope.allowEdit();
    }

    $scope.recipePrivacy = function () {
      return ($scope.recipe.private) ? 'Private':'Public';
    }

    $scope.recipePrivacyClass = function () {
      return ($scope.recipe.private) ? 'btn btn-danger':'btn btn-success';
    }

    $scope.alertPrivacy = function () {
      alert('The privacy of the recipe ' + $scope.recipe.title + ' will change shortly as requested');

    }

  });
}

function UsrCreateRecipeCtrl($rootScope, $scope, $http, $location, $routeParams) {
  var args = [$location];
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    $scope.save = function () {
      loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
        save($rootScope.recipes, $scope.recipe);
      });
      args[0].path('/users/' + $routeParams.userId + '/recipes');
    }
  }, args);
}

function UsrEditRecipeCtrl($rootScope, $scope, $http, $location, $routeParams) {
  var args = [$location];
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    $scope.save = function () {
      loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
        var eval = function (elem, elem2) {
          return elem.id == elem2.id;
        }
        save($rootScope.recipes, $scope.recipe, eval);
      });
      args[0].path('/users/' + $routeParams.userId + '/recipes');
    }
  }, args);
}

function UsrProfileCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    var eval = function (elem) {
      return elem.userId == eval.id;
    }
    eval.id = $routeParams.userId;
    $scope.recipes = filter($rootScope.recipes, eval);

    $scope.followed = false;
    $scope.edit = false;
    $scope.readOnly = function () {
        return !$scope.edit || !$rootScope.allowEdit();
    }

    $scope.radioProfessional = function (user) {
      return (user.professional.professional) ? 'active':''
    }

    $scope.radioAmateur = function (user) {
      return (user.professional.professional) ? '':'active'
    }

    $scope.verificationLevel = function (user) {
      if (!user.professional.professional)
        return 'amateur';
      else if (!user.professional.verified)
        return 'professional';
      else
        return 'verified';
    }

    $scope.verifyAlert = function (user) {
      alert('Verification process started for ' + user.name);
    }

    $scope.alertButton = function(msg){
      alert(msg);
    }

    $scope.followButton = function(msg){
      if(!$rootScope.allowEdit()) {
        alert('You must me registerd and logged to follow users');
      }else{
        alert(msg);
        $scope.followed = !$scope.followed;
      }
      
    }
  });
}

function UsrEditProfileCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.save = function () {
    var args = [$location];
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams, args) {
      var eval = function (elem, elem2) {
        return elem.id == elem2.id;
      }
      save($rootScope.recipes, $scope.recipe, eval);
      args[0].path('/users/' + $routeParams.userId + '/profile');
    }, args);
  }
}

appControllers.controller('RecipeListCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', RecipeListCtrl]);
appControllers.controller('RecipeCtrl', ['$rootScope', '$scope', '$routeParams', '$http', RecipeCtrl]);
appControllers.controller('LoginCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', LoginCtrl]);
appControllers.controller('UsrHomeCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', UsrHomeCtrl]);
appControllers.controller('UsrRecipeListCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', UsrRecipeListCtrl]);
appControllers.controller('UsrRecipeCtrl', ['$rootScope', '$scope', '$http', '$routeParams', UsrRecipeCtrl]);
appControllers.controller('UsrCreateRecipeCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', UsrCreateRecipeCtrl]);
appControllers.controller('UsrEditRecipeCtrl', ['$rootScope', '$scope', '$http', '$location', '$routeParams', UsrEditRecipeCtrl]);
appControllers.controller('UsrProfileCtrl', ['$rootScope', '$scope', '$http', '$routeParams', UsrProfileCtrl]);
appControllers.controller('UsrEditProfileCtrl', ['$rootScope', '$scope', '$http', '$routeParams', UsrEditProfileCtrl]);

angular.module("cookingAppFilters", [])
  .filter('hidePrivateRecipes', ['$rootScope', '$routeParams', function ($rootScope, $routeParams) {
    return function (data) {
      console.log('hello');
      var user = $rootScope.user;
      var pathUserId = $routeParams.userId;
      var filtered = [];
      if (typeof data === 'undefined')
        return filtered;

      // filter by privacy
      if (typeof user === 'undefined' || typeof pathUserId === 'undefined' || user.id != pathUserId) {
        console.log('privacy');
        for (var i = 0, len=data.length; i < len; i++) {
          console.log(i + '::' + data[i].private);
          if (!data[i].private) {
            filtered.push(data[i]);
          }
        }
      } else {
        filtered = data;
      }
      return filtered;
    }
  }]);
