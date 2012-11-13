window.onload = function() {
    $(document).ready(function () {
        $('div#tabs').VoterContactManager();
        // $('div#tabs').tabs();
        if (google && google.load) {
            // gapi.client.setApiKey('AIzaSyDG8Af8zr2kRr8odedTFhYiq7o7x6UFdDw');
            // gapi.client.load('plus', 'v1', function() { console.log('loaded.'); });
            // google.load( 'gdata', '2');
            google.setOnLoadCallback(function() {
                // $('div#tabs').tabs();
                // $('div#tabs').VoterContactManager();
            });
//            gapi.client.load('contacts', 'v3', function() { console.log('loaded.'); });
//            google.load("maps", "2", function() {
//                console.log('maps loaded');
//            });
        } else {
            alert("Didn't load");
        }        
    });
};
