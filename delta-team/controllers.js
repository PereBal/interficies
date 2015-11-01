var appControllers = angular.module("cookingAppControllers", ['ngAnimate', 'ui.bootstrap']);

/*
 * Receptes -> all, filter, filter_by user
 * User -> all, filter
 */

var get = function (data, eval) {
  console.log("get(" + data + ")");
  for (var i = 0, len = data.length; i < len; i++) {
    console.log(i + "::" + data[i].id + "::" + eval(data[i]));
    if (eval(data[i])) {
      return data[i];
    }
  }
  return false;
}

var filter = function (data, eval) {
  console.log("filter(" + data + ")");
  res = [];
  for (var i = 0, len = data.length; i < len; i++) {
    console.log(i + "::" + data[i].id + "::" + eval(data[i]));
    if (eval(data[i])) {
      res.push(data[i]);
    }
  }
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

var loadContext = function ($rootScope, $scope, $http, $routeParams, callback, args) {
  var userId = ($routeParams.userId) ? $routeParams.userId:false;
  var recipeId = ($routeParams.recipeId) ? $routeParams.recipeId:false;

  var evalUser = function (elem) {
    return elem.id == evalUser.id;
  }
  evalUser.id = userId;

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
          $scope.user = get($rootScope.users, evalUser);
          $scope.recipe = get($rootScope.recipes, evalUserRecipe);
          callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
        });
      });

    } else if (!$rootScope.users) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;
        $scope.user = get($rootScope.users, evalUser);
        $scope.recipe = get($rootScope.recipes, evalUserRecipe);
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else if (!$rootScope.recipes) {
      $http.get('data/recipes.json').success(function (data) {
        $rootScope.recipes = data;
        $scope.user = get($rootScope.users, evalUser);
        $scope.recipe = get($rootScope.recipes, evalUserRecipe);
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.user = get($rootScope.users, evalUser);
      $scope.recipe = get($rootScope.recipes, evalUserRecipe);
      callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
    }

  } else if (userId) { // assert userId != false
    if (!$rootScope.users) {
      $http.get('data/users.json').success(function (data) {
        $rootScope.users = data;
        $scope.user = get($rootScope.users, evalUser);
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.user = get($rootScope.users, evalUser);
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
        callbackIfNeeded(callback, $rootScope, $scope, $http, $routeParams, args);
      });

    } else {
      $scope.recipe = get($rootScope.recipes, evalRecipe);
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
}

function RecipeListCtrl($rootScope, $scope, $http, $location, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.view = function (recipe) {
    $location.path('/recipes/' + recipe.id);
  }

  $scope.viewUser = function (userId) {
    $location.path('/users/' + userId + '/profile');
  }

  $scope.alertSocial = function (socialNetwork) {
    alert("Share on " + socialNetwork);
  }

  $scope.predicate = 'rating';
  $scope.reverse = true;
}

function RecipeCtrl($rootScope, $scope, $routeParams, $http) {
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.addIngredient = function () {
    var next = $scope.recipe.ingredients.length + 1;
    $scope.recipe.ingredients.push({id: next, name: "", quantity: 0.0, unit: ""});
  }

  $scope.removeIngredient = function (ingredient) {
    $scope.recipe.ingredients.splice($scope.recipe.ingredients.indexOf(ingredient), 1);
  }

  $scope.addStep = function () {
    var next = $scope.recipe.steps.length + 1;
    $scope.recipe.steps.push({id: next, name: "", quantity: 0.0, unit: ""});
  }

  $scope.removeStep = function (step) {
    $scope.recipe.steps.splice($scope.recipe.steps.indexOf(step), 1);
  }

  $scope.alertSocial = function (socialNetwork) {
    alert("Share on " + socialNetwork);
  }
}

function LoginCtrl($rootScope, $scope, $http, $location, $routeParams) {
  $scope.validate = function (user) {
    args = [$location, user];
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams, args) {
      console.log('login');
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
    console.log('register');
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
    $location.path('/users/' + recipe.userId + '/recipes/' + recipe.id);
  }

  $scope.fork = function (recipe) {
    // TODO intro les dades per defecte
    $location.path('/users/' + $routeParams.userId + '/recipes/create');
  }

  $scope.alertSocial = function(socialNetwork) {
    alert("Share on " + socialNetwork);
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

  $scope.alertSocial = function (socialNetwork) {
    alert("Share on " + socialNetwork);
  }
}

function UsrRecipeCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams);
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
  loadContext($rootScope, $scope, $http, $routeParams);

  $scope.save = function () {
    loadContext($rootScope, $scope, $http, $routeParams, function ($rootScope, $scope, $http, $routeParams) {
      var eval = function (elem, elem2) {
        return elem.id == elem2.id;
      }
      save($rootScope.recipes, $scope.recipe, eval);
    });
    $location.path('/users/' + $routeParams.userId + '/recipes');
  }
}

function UsrProfileCtrl($rootScope, $scope, $http, $routeParams) {
  loadContext($rootScope, $scope, $http, $routeParams);
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
