if (typeof jQuery !== 'undefined') {
    (function($) {
        $('#spinner').ajaxStart(function() {
                $(this).fadeIn();
        }).ajaxStop(function() {
                $(this).fadeOut();
        });
        $.ajaxSetup({
            dataType : "json",
            type: "POST",
            beforeSend: function (XMLHttpRequest, settings) {
                XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                XMLHttpRequest.setRequestHeader("Accept", "application/json");
            },
            complete: function () {
            },
            async: true,
            error: function (jqXHR,  textStatus, errorThrown) {
                if (jqXHR.status === 0) {
                    // Session has probably expired and needs to reload and let CAS take care of the rest
                    alert('Your session has expired, the page will need to reload and you may be asked to log back in');
                    // reload entire page - this leads to login page
                    window.location.reload();
                }
            }
        });
        
        $.voterContactManager = {
            search: {
                POSTsearch: function(json,callback) {
                    json = jQuery.extend(true,{
                        search: new Object()
                    },json);
                    $.ajax({
                        url: '/VoterContactManager/search/',
                        type: "POST",
                        dataType : "json",
                        beforeSend: function (XMLHttpRequest, settings) {
                            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                            XMLHttpRequest.setRequestHeader("Accept", "application/json");
                        },
                        data: JSON.stringify(json),
                        success: callback,
                        error: function (jqXHR,  textStatus, errorThrown) {
                            if (jqXHR.status === 0) {
                                // Session has probably expired and needs to reload and let CAS take care of the rest
                                alert('Your session has expired, the page will need to reload and you may be asked to log back in');
                                // reload entire page - this leads to login page
                                window.location.reload();
                            }
                        }
                    });                    
                }
            },
            voterContactManager: {
                GETgetInit: function(json,callback) {
                    json = jQuery.extend({
                        stateCode: "FL"
                    },json);
                    $.ajax({
                        url: "/VoterContactManager/voterContactManager/",
                        type: "GET",
                        dataType : "json",
                        beforeSend: function (XMLHttpRequest, settings) {
                            XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                            XMLHttpRequest.setRequestHeader("Accept", "application/json");
                        },
                        data: {
                            stateCode: $.trim(json.stateCode)
                        },
                        success: callback,
                        error: function (jqXHR,  textStatus, errorThrown) {
                            if (jqXHR.status === 0) {
                                // Session has probably expired and needs to reload and let CAS take care of the rest
                                alert('Your session has expired, the page will need to reload and you may be asked to log back in');
                                // reload entire page - this leads to login page
                                window.location.reload();
                            }
                        }
                    });
                }
            }
        };
        var search = new Object();
        $('div#tabs').find('div#search').each(function() {
            var searchTab = $(this);
            search.firstName = $(this).find('input#firstName');
            search.middleName = $(this).find('input#firstName');
            search.lastName = $(this).find('input#firstName');
            search.gender = $(this).find('select#gender');
            search.race = $(this).find('select#race');
            search.bornBefore = $(this).find('input#bornBefore');
            search.bornAfter = $(this).find('input#bornAfter');
            search.county = $(this).find('select#county');
            search.address = $(this).find('input#address');
            search.city = $(this).find('input#city');
            search.zip = $(this).find('input#zip');
            search.party = $(this).find('select#party');
            search.status = $(this).find('select#status');
            search.congressionalDistrict = $(this).find('input#congressionalDistrict');
            search.senateDistrict = $(this).find('input#senateDistrict');
            search.countyCommissionDistrict = $(this).find('input#countyCommissionDistrict');
            search.schoolBoardDistrict = $(this).find('input#schoolBoardDistrict');
            search.precinct = $(this).find('input#precinct');
            search.precinctGroup = $(this).find('input#precinctGroup');
            search.precinctSplit = $(this).find('input#precinctSplit');
            search.precinctSuffix = $(this).find('input#precinctSuffix');
            search.importDate = $(this).find('select#importDate');
            search.months = $(this).find('select#months');
            search.useNewlyRegistered = $(this).find('input#useNewlyRegistered');
            search.findVoters = $(this).find('button#findVoters').button().click(function() {
                $.voterContactManager.search.POSTsearch({
                    name: { 
                        firstName: search.firstName.val(),
                        middleName: search.middleName.val(),
                        lastName: search.lastName.val()
                    },
                    gender: {
                        code: search.gender.val()
                    },
                    race: {
                        code: search.race.val()
                    },
                    bornBefore: search.bornBefore.val(),
                    bornAfter: search.bornAfter.val(),
                    county: {
                        code: search.county.val()
                    },
                    address: {
                        line1: search.address.val(),
                        city: search.city.val(),
                        zip: search.zip.val()
                    },
                    voterStatus: {
                        code: search.status.val()
                    },
                    congressionalDistrict: search.congressionalDistrict.val(),
                    senateDistrict: search.senateDistrict.val(),
                    countyCommissionDistrict: search.countyCommissionDistrict.val(),
                    schoolBoardDistrict: search.schoolBoardDistrict.val(),
                    precinct: {
                        precinct: search.precinct.val(),
                        precinctGroup: search.precinctGroup.val(),
                        precinctSplit: search.precinctSplit.val(),
                        precinctSuffix: search.precinctSuffix.val()
                    },
                    importDate: search.importDate.val(),
                    months: search.months.val(),
                    useNewlyRegistered: search.useNewlyRegistered.is(':checked'),
                    state: {
                        code: search.stateCode.val()
                    }
                },function(getSearchResponse, textStatus, jqXHR) {
                    
                });
            });
            search.stateCode = $(this).find('select#stateCode').change(function() {
                $.voterContactManager.voterContactManager.GETgetInit({
                    stateCode: $(this).val()
                },function(getInitResponse, textStatus, jqXHR) {
                    search.gender.empty().append(
                        $('<option />').val("").html('--Select Gender--')
                    );
                    search.race.empty().append(
                        $('<option />').val("").html('--Select Race--')
                    );
                    search.county.empty().append(
                        $('<option />').val("").html('--Select County--')
                    );
                    search.party.empty().append(
                        $('<option />').val("").html('--Select Party Registration--')
                    );
                    search.status.empty().append(
                        $('<option />').val("").html('--Select Registered Status--')
                    );
                    search.importDate.empty().append(
                        $('<option />').val("").html('--All Import Dates--')
                    );
                    $.each(getInitResponse.genders,function(index,gender) {
                        $('<option />').val(gender.code).html(gender.name).appendTo(search.gender);
                    });
                    $.each(getInitResponse.races,function(index,race) {
                        $('<option />').val(race.code).html(race.name).appendTo(search.race);
                    });
                    $.each(getInitResponse.counties,function(index,county) {
                        $('<option />').val(county.code).html(county.name).appendTo(search.county);
                    });
                    $.each(getInitResponse.parties,function(index,party) {
                        $('<option />').val(party.code).html(party.name).appendTo(search.party);
                    });
                    $.each(getInitResponse.statuses,function(index,status) {
                        $('<option />').val(status.code).html(status.name).appendTo(search.status);
                    });
                    $.each(getInitResponse.importDates,function(index,importDate) {
                        $('<option />').val(importDate).html(importDate).appendTo(search.importDate);
                    });
                });
            }).change();
        }).end().tabs();
    })(jQuery);
}
