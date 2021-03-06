angular.module('starter')

    .directive('footer', function(){
        return {
            restrict: 'E',
            templateUrl: 'templates/components/footer.html'
        }
    })
    
    .directive('navbar', function(){
        return {
            restrict: 'E',
            templateUrl: 'templates/components/navbar.html',
            controller: 'NavbarCtrl'
        }
    })

    .directive('sidebar', function(){
        return {
            restrict: 'E',
            templateUrl: 'templates/components/sidebar.html',
            controller: 'SidebarCtrl'
        }
        
    });
