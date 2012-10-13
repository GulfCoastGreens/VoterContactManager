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
            contacts: {
                PUTaddNewContactType: function(json,callback) {
                    ///contact/type
                    json = jQuery.extend(true,{
                        name: ""
                    },json);
                    $.ajax({
                        url: '/VoterContactManager/contact/type/',
                        type: "PUT",
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
        var matches = new Object();   
        var contacts = new Object();
        var tabs = $('div#tabs').find('div#matches').each(function() {
            matches.table = $(this).find('table#matches');
        }).end().find('div#contacts').each(function() {
            $(this).find('table#contactAdmin').width('100%')
            .end().find('select#contactType').each(function() {
                contacts.contactTypeAll = $('<option />').val("").html("--All Contacts--");
                contacts.contactType = $(this);
            })
            .end().find('button#addContactTypeButton').button().click(function() {
                $('<div />')
                .data({
                    name: $('<input />',{
                        "id": "contactType"
                    })
                })
                .addClass('ui-state-default ui-widget-content')
                .append(
                    $('<p />')
                    .css({
                        "text-align": "center",
                        "margin-top": "0px",
                        "margin-bottom": "0px",
                        "padding": "0px"
                    })
                ).dialog({
                    autoOpen: true,
                    bgiframe: true,
                    resizable: false,
                    title: 'Add New Contact Type',
                    height:220,
                    width:400,
                    modal: true,
                    zIndex: 3999,
                    overlay: {
                        backgroundColor: '#000',
                        opacity: 0.5
                    },
                    open: function() {
                        $(this).append(
                            $('<label />',{
                                "for": "contactType"
                            }).html("Contact Type Name: ")
                        ).append(
                            $(this).data().name
                        )
                    },
                    buttons: {
                        "Create New Contact Type": function() {
                            var dialog = $(this);
                            if($.trim(dialog.data().name.val()).length < 1) {
                                alert("Error! You must supply a name for the new Contact Type");
                            } else {
                                $.voterContactManager.contacts.PUTaddNewContactType({
                                    name: $.trim(dialog.data().name.val())
                                },function(newContactTypeResponse, textStatus, jqXHR) {
                                    if(newContactTypeResponse.contactType === "error") {
                                        alert("The contact type you specified caused an error. Try a different name");
                                    } else {                                        
                                        contacts.contactType
                                        .find('option:first').remove().end()
                                        .append(contacts.contactTypeAll.clone().val(newContactTypeResponse.contactType.id).html(newContactTypeResponse.contactType.name))
                                        .each(function() {
                                            var options = $(this).children().detach();
                                            options.sort(function(a,b) {
                                                var nameA = $(a).html().toLowerCase();
                                                var nameB = $(b).html().toLowerCase();
                                                if (nameA < nameB) //sort string ascending
                                                    return -1; 
                                                if (nameA > nameB)
                                                    return 1;
                                                return 0; //default return value (no sorting)                                                                                                                                
                                            });
                                            $(this).append(options);
                                        })
                                        .prepend(contacts.contactTypeAll.clone())
                                        .val(newContactTypeResponse.contactType.id)
                                        .change();
                                        dialog.dialog('close');
                                        dialog.dialog('destroy');
                                        dialog.remove();                                                                
                                    }
                                });
                            }
                        },
                        "Cancel": function() {
                            $(this).dialog('close');
                            $(this).dialog('destroy');
                            $(this).remove();
                        }
                    }
                });                                                
            })
            .end().find('button#editContactTypeButton').button()
            .end().find('button#removeContactTypeButton').button()
            .end().find('table#contacts').each(function() {
                contacts.dataTable = (contacts.table = $(this)).dataTable({
                    "sDom": '<"H"Tfr>t<"F"ip>',
                    "oTableTools": {
                        "sSwfPath": "js/TableTools-2.1.3/media/swf/copy_csv_xls_pdf.swf",
                        "aButtons": [
                            "copy", "csv", "xls", "pdf",
                            {
                                "sExtends":    "collection",
                                "sButtonText": "Save",
                                "aButtons":    [ "csv", "xls", "pdf" ]
                            }
                        ]                                
                    },                            
                    "sScrollX": "100%",
                    "bStateSave": true,
                    "bProcessing": true,
                    "bJQueryUI": true,
                    "bSort": false,
                    "bAutoWidth": false,
                    "sPaginationType": "full_numbers",
                    "asStripeClasses": [ 'ui-priority-primary', 'ui-priority-secondary' ]                        
                });
            });
        }).end().find('div#search').each(function() {
            var searchTab = $(this);
            $(this).find('table:first').find('table').find('tr').find('td:last').css({'text-align':'right'});
            search.firstName = $(this).find('input#firstName');
            search.middleName = $(this).find('input#middleName');
            search.lastName = $(this).find('input#lastName');
            search.gender = $(this).find('select#gender').each(function() { $(this).width(search.firstName.width()*0.7) });
            search.race = $(this).find('select#race').each(function() { $(this).width(search.firstName.width()*0.7); });
            search.bornBefore = $(this).find('input#bornBefore');
            search.bornAfter = $(this).find('input#bornAfter');
            search.county = $(this).find('select#county').each(function() { $(this).width(search.firstName.width()*0.7); }).unbind('change').change(function() {
                if($(this).val() != "" && search.party.val() != "") {
                    search.months.prop('disabled',false);
                    search.useNewlyRegistered.prop('disabled',false);
                } else {
                    search.months.prop('disabled',true);
                    search.useNewlyRegistered.prop('disabled',true);
                    search.useNewlyRegistered.prop('checked',false);
                }
            });
            search.address = $(this).find('input#address');
            search.city = $(this).find('input#city');
            search.zip = $(this).find('input#zip');
            search.party = $(this).find('select#party').each(function() { $(this).width(search.firstName.width()*0.7); }).unbind('change').change(function() {
                if($(this).val() != "" && search.county.val() != "") {
                    search.months.prop('disabled',false);
                    search.useNewlyRegistered.prop('disabled',false);
                } else {
                    search.months.prop('disabled',true);
                    search.useNewlyRegistered.prop('disabled',true);
                    search.useNewlyRegistered.prop('checked',false);
                }
            });
            search.status = $(this).find('select#status').each(function() { $(this).width(search.firstName.width()*0.7); });
            search.congressionalDistrict = $(this).find('input#congressionalDistrict');
            search.senateDistrict = $(this).find('input#senateDistrict');
            search.countyCommissionDistrict = $(this).find('input#countyCommissionDistrict');
            search.schoolBoardDistrict = $(this).find('input#schoolBoardDistrict');
            search.precinct = $(this).find('input#precinct');
            search.precinctGroup = $(this).find('input#precinctGroup');
            search.precinctSplit = $(this).find('input#precinctSplit');
            search.precinctSuffix = $(this).find('input#precinctSuffix');
            search.countyPrecinct = $(this).find('input#countyPrecinct');
            search.cityPrecinct = $(this).find('input#cityPrecinct');
            search.judicialDistrict = $(this).find('input#judicialDistrict');
            search.schoolDistrict = $(this).find('input#schoolDistrict');
            search.landDistrict = $(this).find('input#landDistrict');
            
            search.importDate = $(this).find('select#importDate');
            search.months = $(this).find('select#months').each(function() { $(this).prop('disabled',true); });
            search.useNewlyRegistered = $(this).find('input#useNewlyRegistered').each(function() { 
                $(this).prop('disabled',true); 
                $(this).prop('checked',false); 
            });
            search.electionType = $(this).find('select#electionType').each(function() { $(this).width(search.zip.width()*0.7); });
            search.votedBefore = $(this).find('input#votedBefore');
            search.votedAfter = $(this).find('input#votedAfter');
            search.minVoteCount = $(this).find('input#minVoteCount');
            search.historyVoteType = $(this).find('select#historyVoteType').each(function() { $(this).width(search.zip.width()*0.7); });
            search.absentee = $(this).find('input#absentee');
            search.partyVoted = $(this).find('select#partyVoted').each(function() { $(this).width(search.firstName.width()*0.7); });
            search.findVoters = $(this).find('button#findVoters').button().click(function() {
                $.voterContactManager.search.POSTsearch({
                    search: $.extend(true,{
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
                        party: {
                            code: search.party.val()
                        },
                        congressionalDistrict: search.congressionalDistrict.val(),
                        senateDistrict: search.senateDistrict.val(),
                        countyCommissionDistrict: search.countyCommissionDistrict.val(),
                        importDate: search.importDate.val(),
                        months: search.months.val(),
                        useNewlyRegistered: search.useNewlyRegistered.is(':checked'),
                        state: {
                            code: search.stateCode.val()
                        },
                        history: {
                            electionType: search.electionType.val(),
                            electionDate: {
                                votedBefore: search.votedBefore.val(),
                                votedAfter: search.votedAfter.val()
                            },
                            minVoteCount: search.minVoteCount.val()
                        }
                    },{
                        stateSpecificFields: function(stateCode) {
                            switch(stateCode) {
                                case 'FL':
                                    return {
                                        precinct: {
                                            precinct: search.precinct.val(),
                                            precinctGroup: search.precinctGroup.val(),
                                            precinctSplit: search.precinctSplit.val(),
                                            precinctSuffix: search.precinctSuffix.val()
                                        },
                                        schoolBoardDistrict: search.schoolBoardDistrict.val(),
                                        history: {
                                            historyVoteType: search.historyVoteType.val(),
                                            partyVoted: search.partyVoted.val()
                                        }
                                    }
                                    break;
                                case 'GA':
                                    return {
                                        history: {
                                            absentee: search.absentee.val()
                                        },
                                        countyPrecinct: search.countyPrecinct.val(),
                                        cityPrecinct: search.cityPrecinct.val(),
                                        judicialDistrict: search.judicialDistrict.val(),
                                        schoolDistrict: search.schoolDistrict.val(),
                                        landDistrict: search.landDistrict.val()
                                    }
                            }
                        }
                    }.stateSpecificFields(search.stateCode.val()))
                },function(getSearchResponse, textStatus, jqXHR) {
                    matches.dataTable.fnClearTable();
                    matches.dataTable.fnAddData(getSearchResponse);
                });
            });
            search.stateCode = $(this).find('select#stateCode').change(function() {
                $.voterContactManager.voterContactManager.GETgetInit({
                    stateCode: $(this).val()
                },function(getInitResponse, textStatus, jqXHR) {
                    contacts.contactType.empty().append(contacts.contactTypeAll.clone());
                    $.each(getInitResponse.contactTypes,function(index,contactType) {
                        contacts.contactType.append(contacts.contactTypeAll.clone().val(contactType.id).html(contactType.name))
                    });                    
                    // Rebuild Matches Datatable
                    if("dataTable" in matches) {
                        matches.dataTable.fnDestroy();
                    }
                    matches.table.find('tbody').empty().end().find('thead').find('tr').empty().each(function() {
                        var tr = $(this);
                        $.each(getInitResponse.matchesHeaders,function(index,header) {
                            $('<th />').append(header).appendTo(tr);
                        });
                        tabs.tabs( "option", "selected", 1 );
                    }).end().end().each(function() {
                        matches.dataTable = $(this).dataTable({
                            "sDom": '<"H"Tfr>t<"F"ip>',
                            "oTableTools": {
                                "sSwfPath": "js/TableTools-2.1.3/media/swf/copy_csv_xls_pdf.swf",
                                "aButtons": [
                                    "copy", "csv", "xls", "pdf",
                                    {
                                        "sExtends":    "collection",
                                        "sButtonText": "Save",
                                        "aButtons":    [ "csv", "xls", "pdf" ]
                                    }
                                ]                                
                            },                            
                            "sScrollX": "100%",
                            "bStateSave": true,
                            "bProcessing": true,
                            "bJQueryUI": true,
                            "bSort": false,
                            "bAutoWidth": false,
                            "sPaginationType": "full_numbers",
                            "asStripeClasses": [ 'ui-priority-primary', 'ui-priority-secondary' ]                        
                        });
                        tabs.tabs( "option", "selected", 0 );
                    });
                    
                    // Finish Rebuild Datatable
                    
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
                    search.electionType.empty().append(
                        $('<option />').val("").html('--Election Type--')
                    );
                    search.historyVoteType.empty().append(
                        $('<option />').val("").html('--Vote Type--')
                    ).each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    });
                    search.absentee.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.partyVoted.empty().append(
                        $('<option />').val("").html('--Party Voted--')
                    ).each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    });
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
                        $('<option />').val(party.code).html(party.name).appendTo(search.party).each(function() {
                            if(search.stateCode.val() == 'GA') {
                                search.partyVoted.append($(this).clone());
                            }
                        });
                    });
                    $.each(getInitResponse.statuses,function(index,status) {
                        $('<option />').val(status.code).html(status.name).appendTo(search.status);
                    });
                    $.each(getInitResponse.importDates,function(index,importDate) {
                        $('<option />').val(importDate).html(importDate).appendTo(search.importDate);
                    });
                    $.each(getInitResponse.electionTypes,function(index,electionType) {
                        $('<option />').val(electionType.code).html(electionType.name).appendTo(search.electionType);
                    });
                    $.each(getInitResponse.historyVoteTypes,function(index,historyVoteType) {
                        $('<option />').val(historyVoteType.code).html(historyVoteType.name).appendTo(search.historyVoteType);
                    });
                    search.schoolBoardDistrict.each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.precinct.each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.precinctGroup.each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.precinctSplit.each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.precinctSuffix.each(function() {
                        switch(search.stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.countyPrecinct.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.cityPrecinct.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.judicialDistrict.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.schoolDistrict.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    search.landDistrict.each(function() {
                        switch(search.stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                });
            }).change();
        }).end().tabs();        
    })(jQuery);
}
