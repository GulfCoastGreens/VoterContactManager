modules = {
    application {
        resource url: 'js/application.js'
    }
    jqmobile {
        resource url: 'https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.js', disposition:'head'        
        resource url: 'http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.js', disposition:'head'
        resource url: 'http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.css', disposition:'head'
    }
}