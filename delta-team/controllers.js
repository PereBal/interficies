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

  $rootScope.firstRegions = [ 'Spain', 'Italy', 'France', 'Germany', 'England' ];
  $rootScope.secondRegions = [ 'North america', 'Argentina', 'Japan', 'China', 'Burundy' ];
  $rootScope.allRegions = [ 'Spain', 'Italy', 'France', 'Germany', 'England', 'North america', 'Argentina', 'Japan', 'China', 'Burundy' ];

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
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.alertShowNext = function(message) {
    alert('Not yet implemented, this will show the '+ message);
  }

  $scope.view = function (recipe) {
    $location.path('/recipes/' + recipe.id);
  }

  $scope.viewUser = function (userId) {
    $location.path('/users/' + userId + '/profile');
  }

  $scope.predicate = 'rating';
  $scope.reverse = true;

   $scope.userNews = [

    {
      source: 'user1',
      action: 'like a la receta de pedro',
      img: 'images/carrey.jpeg'
    },
   {
    source: 'user5',
    action: 'añadio a favoritos a la receta de pedro',
    img: 'images/carrey.jpeg'
    },
     {
      source: 'user10',
      action: 'lcompartio en twitter a la receta de pedro',
      img: 'images/carrey.jpeg'
    },
     {
      source: 'user8',
      action: 'like a la receta de pedro',
      img: 'images/carrey.jpeg'
    },
     {
      source: 'user1',
      action: 'like a la receta de pedro',
      img: 'images/carrey.jpeg'
    },
     {
      source: 'user1',
      action: 'like a la receta de pedro',
      img: 'images/carrey.jpeg'
    },
  ];
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
    args = [$location, user];
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
    args = [$location, user];
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams, args) {
      var eval = function (elem) {
        return elem.id == eval.id;
      }
      eval.id = args[1].id;

      save($rootScope.users, args[1], eval);
      $rootScope.user = args[1];
      args[0].path('/users/' + args[1].id);
    }, args);
  }
}

function UsrHomeCtrl($rootScope, $scope, $http, $location, $routeParams) {
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
  });

  $scope.view = function (recipe) {
    $location.path('/recipes/' + recipe.id);
  }

  $scope.viewUser = function (userId) {
    $location.path('/users/' + userId + '/profile');
  }

  $scope.fork = function (recipe) {
    alert('Iniciar fork de la receta ' + recipe.title);
  }
}

function UsrRecipeListCtrl($rootScope, $scope, $http, $location, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams, function($rootScope, $scope, $http, $routeParams) {
    var eval = function (elem) {
      return elem.userId == eval.id;
    }
    eval.id = $routeParams.userId;

    $scope.recipes = filter($rootScope.recipes, eval);
  });

  $scope.view = function (recipe) {
    $location.path('/users/' + $routeParams.userId + '/recipes/' + recipe.id);
  }

  $scope.recipePrivacy = function (recipe) {
    return (recipe.private) ? 'Private':'Public';
  }

  $scope.recipePrivacyClass = function (recipe) {
    return (recipe.private) ? 'btn btn-danger btn-xs':'btn btn-success btn-xs';
  }
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
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.save = function () {
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
      save($rootScope.recipes, $scope.recipe);
    });
    $location.path('/users/' + $routeParams.userId + '/recipes');
  }
}

function UsrEditRecipeCtrl($rootScope, $scope, $http, $location, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
    $scope.save = function () {
      loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
        var eval = function (elem, elem2) {
          return elem.id == elem2.id;
        }
        save($rootScope.recipes, $scope.recipe, eval);
      });
      $location.path('/users/' + $routeParams.userId + '/recipes');
    }
  });
}

function UsrProfileCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
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
  });
}

function UsrEditProfileCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.save = function () {
    args = [$location];
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
