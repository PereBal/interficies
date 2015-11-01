var app = angular.module("cookingApp", [
    'ngRoute',
    'cookingAppControllers'
]);

app.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/recipes', {
        templateUrl: 'recipe_list.html',
        controller: 'RecipeListCtrl'
      }).
      when('/recipes/:recipeId', {
        templateUrl: 'recipe.html',
        controller: 'RecipeCtrl'
      }).
      when('/login', {
        templateUrl: 'login.html',
        controller: 'LoginCtrl'
      }).
      when('/users/:userId', {
        templateUrl: 'user_home.html',
        controller: 'UsrHomeCtrl'
      }).
      when('/users/:userId/profile', {
        templateUrl: 'user_profile.html',
        controller: 'UsrProfileCtrl'
      }).
      when('/users/:userId/profile/edit', {
        templateUrl: 'user_profile.html',
        controller: 'UsrEditProfileCtrl'
      }).
      when('/users/:userId/recipes', {
        templateUrl: 'recipe_list.html',
        controller: 'UsrRecipeListCtrl'
      }).
      when('/users/:userId/recipes/:recipeId', {
        templateUrl: 'recipe.html',
        controller: 'UsrRecipeCtrl'
      }).
      when('/users/:userId/recipes/create', {
        templateUrl: 'recipe.html',
        controller: 'UsrCreateRecipeCtrl'
      }).
      when('/users/:userId/recipes/:recipeId/edit', {
        templateUrl: 'recipe.html',
        controller: 'UsrEditRecipeCtrl'
      }).
      otherwise({
        redirectTo: '/recipes'
      });
  }]);
