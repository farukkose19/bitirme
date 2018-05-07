var app=angular.module('App',['ui.router','ngCookies']);

app.config(function ($stateProvider,$urlRouterProvider) {
   $urlRouterProvider.otherwise("/");
   $stateProvider
       .state("home",{
           url:'/home',
           templateUrl:"Pages/Home.html",
           controller:"Controller"
        })
       .state("friends",{
           url:'/friends',
           templateUrl:"Pages/Friends.html",
           controller:'friendController'
       })
       .state("worddetail",{
           url:'/worddetail/{id}',
           templateUrl:"Pages/wordDetail.html",
           controller: 'wordDetail'
       })
       .state("/",{
           url:'/',
           templateUrl:"Pages/Login.html",
           controller:"login"
       })
       .state("logout",{
           url:'/logout',
           templateUrl:"Pages/logout.html",
           controller:"logout"
       })
       .state("package",{
           url:'/package',
           templateUrl:"Pages/Package.html",
           controller:"package"
       });
});

app.controller("login",function ($scope,$http,$window,$cookies) {
    var userrr=$cookies.get("userid");
    if(typeof userrr === 'undefined') {
        $scope.newlogin = function () {
            var url2 = "http://127.0.0.1:8080/kullanici/login/" + $scope.username + "/" + $scope.password;
            $http({
                method: "GET",
                url: url2
            }).then(function mySuccess(response) {
                $scope.user = response.data;
                var userid = response.data.id;
                $cookies.put("userid", userid);
                $window.location.href = '#!/home';
            }, function myError(response) {
                $scope.myWelcome = response.statusText;
            });
        }
    }else{
        $window.location.href = '#!/home';
    }
});

app.controller("Controller", function ($scope,$http,$window,$cookies) {
    $scope.ss=true;
    $scope.user=$cookies.get("userid");
        var url = "http://127.0.0.1:8080/word/wgetuser/"+$scope.user;
        $http({
            method: "GET",
            url: url
        }).then(function mySuccess(response) {
            $scope.words = response.data;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });

        $scope.addword=function () {

            var url2 = "http://127.0.0.1:8080/word/add/" + $scope.newword + "/" + $scope.user;
            $http({
                method: "GET",
                url: url2
            }).then(function mySuccess(response) {
                $scope.newwordd = response.data;
                $scope.newwordmean();
            }, function myError(response) {
                $scope.myWelcome = response.statusText;
            });
        };

        $scope.newwordmean=function () {
            var url3 = "http://127.0.0.1:8080/mean/add/" + $scope.newmean + "/" + $scope.newwordd.id;
            $http({
                method: "GET",
                url: url3
            }).then(function mySuccess(response) {
                $scope.newwordmeann = response.data;
                $window.location.reload();

            }, function myError(response) {
                $scope.myWelcome = response.statusText;
                $window.location.reload();
            });
        };
        
        $scope.stab=function () {
            if($scope.tab==1){
                $scope.tab=0;
            }
            else{
                $scope.tab=1;
            }
        }

});

app.controller("friendController",function ($scope,$http,$cookies,$window) {
    var userid=$cookies.get("userid");
    var url="http://127.0.0.1:8080/kullanici/getfriend/"+userid;
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.friends = response.data;
    }, function myError(response) {
        var error = response.statusText;
    });

    $scope.click=function (id) {
        $cookies.put("friendid",id);
        $window.location.href = '#!/package';
    }
});

app.controller("wordDetail", function ($scope,$http,$stateParams,$window) {

    var url="http://127.0.0.1:8080/word/getid/"+$stateParams.id;
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getworddetail = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    $scope.wordupdate=function () {

        var url="http://127.0.0.1:8080/word/update/"+$stateParams.id+"/"+$scope.updateword;
        $http({
            method : "GET",
            url : url
        }).then(function mySuccess(response) {
            $scope.getworddetail = response.data;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    };

    $scope.deletemean=function (id) {
        var url="http://127.0.0.1:8080/mean/delete/"+id;
        $http({
            method : "GET",
            url : url
        }).then(function mySuccess(response) {
            $scope.getworddetail = response.data;
            $window.location.reload();
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    };

    $scope.deleteword=function (id) {
        var url="http://127.0.0.1:8080/word/delete/"+id;
        $http({
            method : "GET",
            url : url
        }).then(function mySuccess(response) {
            $scope.getworddetail = response.data;
            $window.location.href = '#!/home';
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
            $window.location.href = '#!/home';
        });
    };
    $scope.addmean=function (id) {
        var url="http://127.0.0.1:8080/mean/add/"+$scope.addmeann+"/"+id;
        $http({
            method : "GET",
            url : url
        }).then(function mySuccess(response) {
            $scope.getworddetail = response.data;
            $window.location.reload();
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    };

});

app.controller("logout", function ($scope,$http,$window,$cookies) {
    $cookies.remove("userid");
    $window.location.href = '#!/';
});

app.controller("package",function ($scope,$http,$window,$cookies) {
    var url="http://127.0.0.1:8080/package/getpackage/"+$cookies.get("userid")+"/"+$cookies.get("friendid");
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getpackage = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

});

//$cookies.put("key", "value");
//var value = $cookies.get("key");