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
            url:'/package/{id}',
            templateUrl:"Pages/Package.html",
            controller:"package"
        })
        .state("allpackage",{
            url:'/allpackage',
            templateUrl:"Pages/tumPaketlerim.html",
            controller:"allpackage"
        })
        .state("packagedetail",{
            url:'/packagedetail/{id}',
            templateUrl:"Pages/packageDetail.html",
            controller:"packagedetail"
        })
        .state("packagedetail2",{
            url:'/packagedetail2/{id}',
            templateUrl:"Pages/Package_Detail_2.html",
            controller:"packagedetail2"
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
    };

    $scope.idSelectedVote = [];
    $scope.setSelected = function (idSelectedVote) {
        var bulundumu=false;
        for(var i=0;i<$scope.idSelectedVote.length;i++){
            var x=$scope.idSelectedVote[i];
            if(idSelectedVote == x){
                var index = $scope.idSelectedVote.indexOf(idSelectedVote);
                $scope.idSelectedVote.splice(index, 1);
                bulundumu=true;
            }
        }
        if(bulundumu==false)
        $scope.idSelectedVote.push(idSelectedVote);
        $scope.xxx=1;
    };

    $scope.varmi = function (id) {
        for(var i=0;i<$scope.idSelectedVote.length;i++){
            var x=$scope.idSelectedVote[i];
            if(id == x){
                return true;
            }
        }
        return false;
    };
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

    $scope.tab = 1;

    $scope.setTab = function(newTab){
        $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
        return $scope.tab === tabNum;
    };

    $scope.bulunduMu=false;

    function varMi(item) {
        if($scope.arkadasId==item.id){
            $scope.bulunduMu=true;
        }
    }

    $scope.bul=function () {
        var invalidEntries = 0;
        var url2="http://127.0.0.1:8080/kullanici/searchfriend/"+$scope.arkadas_ismi;
        $http({
            method : "GET",
            url : url2
        }).then(function mySuccess(response) {
            $scope.getFriends = response.data;

            function checkId(item) {
                return item.id !=userid;
            }


            $scope.friendss= $scope.getFriends.filter(checkId);

            $scope.visibilite=function (id) {
                $scope.bulunduMu=false;
                $scope.arkadasId=id;
                $scope.friends.friends.filter(varMi);
                if($scope.bulunduMu)
                    return {'visibility': 'visible'};
                else
                    return {'visibility': 'hidden'};
            }
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    };


    function arkadasEkle(id) {
        var userid=$cookies.get("userid");
        var url3="http://127.0.0.1:8080/kullanici/addfriend/"+userid+"/"+id;
        $http({
            method : "GET",
            url : url3
        }).then(function mySuccess(response) {
            $scope.eklenenArkadas = response.data;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });

    }
    $scope.ekliMi=function (id) {
        $scope.arkadasId=id;
        $scope.friends.friends.filter(varMi);
        if($scope.bulunduMu)
        {
            $window.location.href = '#!/package/'+id;
        }
        else {
            if($window.confirm("Arkadaşı eklemek ister misiniz?")) {
                arkadasEkle(id);
                $window.location.href = '#!/home/';
            } else {
                $scope.Message = "clicked NO";
            }
        }
    };

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

app.controller("package",function ($scope,$http,$window,$cookies,$stateParams) {
    var url="http://127.0.0.1:8080/package/getpackage/"+$cookies.get("userid")+"/"+$stateParams.id;
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getpackage = response.data;
        $scope.friend=$scope.getpackage[0].friends[0];
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    var url2="http://127.0.0.1:8080/package/getpackage/"+$stateParams.id+"/"+$cookies.get("userid");
    $http({
        method : "GET",
        url : url2
    }).then(function mySuccess(response) {
        $scope.getfriendPackage = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    $scope.tab = 1;

    $scope.setTab = function(newTab){
        $scope.tab = newTab;
    };

    $scope.isSet = function(tabNum){
        return $scope.tab === tabNum;
    };

    function arkadasiSil() {
        var url2="http://127.0.0.1:8080/kullanici/deletefriend/"+$cookies.get("userid")+"/"+$stateParams.id;
        $http({
            method : "GET",
            url : url2
        }).then(function mySuccess(response) {
            $scope.deleteFriend = response.data;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }

    $scope.delete = function () {
        if($window.confirm("Arkadaşınızı Silmek İstiyor Musunuz?")) {
            arkadasiSil();
            $window.location.href = '#!/friends';
        } else {
            $scope.Message = "clicked NO";
        }
    };


});

app.controller("allpackage",function ($scope,$http,$window,$cookies) {
    var url="http://127.0.0.1:8080/package/userpackage/"+$cookies.get("userid");
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getallpackage = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

});

app.controller("packagedetail",function ($scope,$http,$window,$cookies,$stateParams) {
    var url="http://127.0.0.1:8080/package/getpackageid/"+$stateParams.id;
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getallpackage = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });

    function paketiSil() {
        var url2="http://127.0.0.1:8080/package/delete/"+$stateParams.id;
        $http({
            method : "GET",
            url : url2
        }).then(function mySuccess(response) {
            $scope.deletedPackage = response.statusText;
        }, function myError(response) {
            $scope.myWelcome = response.statusText;
        });
    }

    $scope.delete = function () {
        if($window.confirm("Paketi Silmek İstiyor Musunuz?")) {
            var id=$scope.getallpackage.friends[0].id;
            paketiSil();

            $window.location.href = '#!/package/'+id;
        } else {
            $scope.Message = "clicked NO";
        }
    };
});

app.controller("packagedetail2",function ($scope,$http,$window,$cookies,$stateParams) {
    var url="http://127.0.0.1:8080/package/getpackageid/"+$stateParams.id;
    $http({
        method : "GET",
        url : url
    }).then(function mySuccess(response) {
        $scope.getallpackage2 = response.data;
    }, function myError(response) {
        $scope.myWelcome = response.statusText;
    });
});