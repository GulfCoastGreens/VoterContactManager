modules = {
    application {
        resource url:'js/application.js'
    }
    core {
        resource url: 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js', disposition:'head'        
        resource url: 'https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.js', disposition:'head'   
        resource url: 'https://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.1/jquery.dataTables.min.js', disposition:'head'
        // resource url: 'https://ajax.aspnetcdn.com/ajax/jquery.mobile/1.1.0/jquery.mobile-1.1.0.min.js', disposition:'head'
        // resource url: 'https://raw.github.com/LiosK/UUID.js/master/dist/uuid.core.js', disposition:'head'
        // resource url: 'js/jQuery-Timepicker-Addon/jquery-ui-timepicker-addon.js', disposition: 'head'
        // resource url: 'js/jquery.messageService.js', disposition: 'head'
        //resource url: 'https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.13/themes/le-frog/jquery-ui.css', disposition:'head', attrs:[rel:'stylesheet',type:'text/css']
        //resource url: 'https://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.1/css/jquery.dataTables_themeroller.css', disposition:'head', attrs:[rel:'stylesheet',type:'text/css']
        resource url: 'https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.13/themes/le-frog/jquery-ui.css'
        resource url: 'https://ajax.aspnetcdn.com/ajax/jquery.dataTables/1.9.1/css/jquery.dataTables_themeroller.css'
        
    }    
    
}