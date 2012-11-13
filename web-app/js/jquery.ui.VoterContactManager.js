/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
(function($) {
    $.widget("ui.VoterContactManager", {
        options: { 
        },
        _create: function() {
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
                        // window.location.reload();
                        console.log(textStatus);
                        console.log(errorThrown);
                        console.log(jqXHR);
                    }
                }
            });
            var self = this,
            o = self.options,
            el = self.element;
            self.buildMatches();
            self.buildContacts();
            self.buildSearch();
            $(el).tabs();
        },
        buildMatches: function() {
            var self = this,
            o = self.options,
            el = self.element,
            matchesTab = $(self.matchesTab = $(el).find('div#matches')),
            matchesTable = $(self.matchesTable = matchesTab.find('table#matches'));
        },
        buildContacts: function() {
            var self = this,
            o = self.options,
            el = self.element,
            contactsTab = $(self.contactsTab = $(el).find('div#contacts')),
            contactAdminTable = $(self.contactType = contactsTab.find('table#contactAdmin').width('100%')),
            contactType = $(self.contactType = contactsTab.find('select#contactType')),
            addContactTypeButton = $(self.addContactTypeButton = contactsTab.find('button#addContactTypeButton')).button().click(function() {
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
                                $.VoterContactManager.contacts.PUTaddNewContactType({
                                    name: $.trim(dialog.data().name.val())
                                },function(newContactTypeResponse, textStatus, jqXHR) {
                                    if(newContactTypeResponse.contactType === "error") {
                                        alert("The contact type you specified caused an error. Try a different name");
                                    } else {                                        
                                        contacts.contactType
                                        .find('option:first').remove().end()
                                        .append(self.contactElements.contactTypeAll.clone().val(newContactTypeResponse.contactType.id).html(newContactTypeResponse.contactType.name))
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
                                        .prepend(self.contactElements.contactTypeAll.clone())
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
            }),
            editContactTypeButton = $(self.editContactTypeButton = contactsTab.find('button#editContactTypeButton')).button().click(function() {
                if(contactType.val().toString() === "") {
                    alert("You must select a contact type to perform this operation");
                } else {
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
                        title: 'Edit Contact Type',
                        height:220,
                        width:400,
                        modal: true,
                        zIndex: 3999,
                        overlay: {
                            backgroundColor: '#000',
                            opacity: 0.5
                        },
                        open: function() {
                            $(this).data().name.val(contactType.find(':selected').text());
                            $(this).append(
                                $('<label />',{
                                    "for": "contactType"
                                }).html("Contact Type Name: ")
                            ).append(
                                $(this).data().name
                            )
                        },
                        buttons: {
                            "Rename Contact Type": function() {
                                var dialog = $(this);
                                if($.trim(dialog.data().name.val()).length < 1) {
                                    alert("Error! You must supply a name for the new Contact Type");
                                } else {
                                    $.VoterContactManager.contacts.POSTeditContactType({
                                        name: $.trim(dialog.data().name.val()),
                                        id: contactType.val()
                                    },function(editContactTypeResponse, textStatus, jqXHR) {
                                        if(editContactTypeResponse.contactType === "error") {
                                            alert("The contact type you specified caused an error. Try a different name");
                                        } else {                                        
                                            contactType
                                            .find('option:first').remove().end()
                                            .children().each(function(index,option) {
                                                if($(option).val().toString() === editContactTypeResponse.contactType.id.toString()) {
                                                    $(option).html(editContactTypeResponse.contactType.name);
                                                    return false;
                                                }
                                                return true;
                                            }).end()
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
                                            .prepend(self.contactElements.contactTypeAll.clone())
                                            .val(editContactTypeResponse.contactType.id)
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
                }                
            }),
            removeContactTypeButton = $(self.removeContactTypeButton = contactsTab.find('button#removeContactTypeButton')).button().click(function() {
                if(contactType.val().toString() === "") {
                    alert("You must select a contact type to perform this operation");
                } else {
                    $('<div />')
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
                        title: 'Delete Contact Type',
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
                                $('<span />').html("Are you SURE you want to delete this contact type?")
                            );
                        },
                        buttons: {
                            "Remove Contact Type": function() {
                                var dialog = $(this);
                                $.VoterContactManager.contacts.DELETEremoveContactType({
                                    id: contactType.val()
                                },function(removeContactTypeResponse, textStatus, jqXHR) {
                                    if(removeContactTypeResponse.status === "error") {
                                        alert("The contact type you specified caused an error. Try a different name");
                                    } else {                                        
                                        contactType.find('option:selected').remove();
                                        dialog.dialog('close');
                                        dialog.dialog('destroy');
                                        dialog.remove();                                                                
                                    }
                                });                                
                            },
                            "Cancel": function() {
                                $(this).dialog('close');
                                $(this).dialog('destroy');
                                $(this).remove();
                            }
                        }
                    });                                                
                }                
            }),
            contactTypeButtonset = $(self.contactTypeButtonset = contactsTab.find('span#contactTypeButtonset')).buttonset(),
            contactsTable = $(self.contactsTable = contactsTab.find('table#contactsTable')),
            contactsTableHead = contactsTable.find('thead'),
            addContactButton = $(self.addContactButton = contactsTableHead.find('button#addContactButton')).button({
                text: false,
                icons: {
                    primary: "ui-icon-person"
                }
            }).click(function() {
                $('<div />')
                .data({
                    last: $('<input />',{
                        "id": "last"
                    }),
                    first: $('<input />',{
                        "id": "first"
                    }),
                    middle: $('<input />',{
                        "id": "middle"
                    }),
                    nickname: $('<input />',{
                        "id": "nickname"
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
                    title: 'Create New Contact',
                    height:280,
                    width:400,
                    modal: true,
                    zIndex: 3999,
                    overlay: {
                        backgroundColor: '#000',
                        opacity: 0.5
                    },
                    open: function() {
                        $(this).append(
                            $('<table />').width('100%')
                            .append(
                                $('<tr />')
                                .append(
                                    $('<td />')
                                    .append(
                                        $('<label />',{
                                            "for": "last"
                                        }).html("Last Name: ")
                                    )
                                )
                                .append(
                                    $('<td />')
                                    .append(
                                        $(this).data().last.css({ "float" : "right"})
                                    )
                                )
                            )
                            .append(
                                $('<tr />')
                                .append(
                                    $('<td />')
                                    .append(
                                        $('<label />',{
                                            "for": "first"
                                        }).html("First Name: ")
                                    )
                                )
                                .append(
                                    $('<td />')
                                    .append(
                                        $(this).data().first.css({ "float" : "right"})
                                    )
                                )
                            )
                            .append(
                                $('<tr />')
                                .append(
                                    $('<td />')
                                    .append(
                                        $('<label />',{
                                            "for": "middle"
                                        }).html("Middle Name: ")
                                    )
                                )
                                .append(
                                    $('<td />')
                                    .append(
                                        $(this).data().middle.css({ "float" : "right"})
                                    )
                                )
                            )
                            .append(
                                $('<tr />')
                                .append(
                                    $('<td />')
                                    .append(
                                        $('<label />',{
                                            "for": "nickname"
                                        }).html("Nickname: ")
                                    )
                                )
                                .append(
                                    $('<td />')
                                    .append(
                                        $(this).data().nickname.css({ "float" : "right"})
                                    )
                                )
                            )
                        );
                    },
                    buttons: {
                        "Create Contact": function() {
                            var dialog = $(this);
                            $.VoterContactManager.contacts.PUTaddNewContact({
                                name: {
                                    last: $.trim(dialog.data().last.val()),
                                    first: $.trim(dialog.data().first.val()),
                                    middle: $.trim(dialog.data().middle.val())
                                },
                                nickname: $.trim(dialog.data().nickname.val()),
                                contactType: {
                                    name: contactType.val()
                                }
                            },function(addContactResponse, textStatus, jqXHR) {
                                if(addContactResponse.contact === "error") {
                                    alert("The contact type you specified caused an error. Try a different name");
                                } else {
                                    contacts.dataTable.fnAddData([ addContactResponse.contact ]);
                                    dialog.dialog('close');
                                    dialog.dialog('destroy');
                                    dialog.remove();                                                                
                                }
                            });                                
                        },
                        "Cancel": function() {
                            $(this).dialog('close');
                            $(this).dialog('destroy');
                            $(this).remove();
                        }
                    }                                
                });            
            }),
            removeContactButton = $(self.removeContactButton = contactsTableHead.find('button#removeContactButton')).button({
                text: false,
                icons: {
                    primary: "ui-icon-trash"
                },
                disabled: true
            }).click(function() {
                $('<div />')
                .data({
                    nRow: $(contactsDataTable.fnGetNodes( )).find('.ui-widget-shadow')[0],
                    aData: contactsDataTable.fnGetData($(contactsDataTable.fnGetNodes( )).find('.ui-widget-shadow')[0])
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
                    title: 'Delete Contact',
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
                            $('<span />').html("Are you SURE you want to delete this contact?")
                        );
                    },
                    buttons: {
                        "Remove Contact": function() {
                            var dialog = $(this);
                            $.VoterContactManager.contacts.DELETEremoveContact({
                                id: dialog.data().aData[0].contactId
                            },function(removeContactResponse, textStatus, jqXHR) {
                                alert(dialog.data().aData[0].contactId);
                                alert(JSON.stringify(dialog.data().aData));
                                if(removeContactResponse.status === "error") {
                                    alert("The contact you specified caused an error. Try refresh first");
                                } else {             
                                    contactsDataTable.fnDeleteRow(contactsDataTable.fnGetPosition(dialog.data().nRow));
                                    dialog.dialog('close');
                                    dialog.dialog('destroy');
                                    dialog.remove();                                                                
                                }
                            });                                
                        },
                        "Cancel": function() {
                            $(this).dialog('close');
                            $(this).dialog('destroy');
                            $(this).remove();
                        }
                    }
                });
            }),
            contactButtonset = contactsTableHead.find('span#contactButtonset').buttonset(),
            contactsDataTable = $(self.contactsDataTable = contactsTable.dataTable({
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
                "aoColumns": [
                    { "bSortable": false,"bVisible": true,"mDataProp": null,"sDefaultContent":"","fnRender":
                        function(oObj) {
                            return "<button type='button' id='details' />";
                        }
                    },
                    { "bVisible": true,"mDataProp": "name.last","sDefaultContent":"" },
                    { "bVisible": true,"mDataProp": "name.first","sDefaultContent":"" },
                    { "bVisible": true,"mDataProp": "name.middle","sDefaultContent":"" },
                    { "bVisible": true,"mDataProp": "nickname","sDefaultContent":"" }
                ],                    
                "sScrollX": "100%",
                "bStateSave": true,
                "bProcessing": true,
                "bJQueryUI": true,
                "bSort": false,
                "bAutoWidth": false,
                "sPaginationType": "full_numbers",
                "asStripeClasses": [ 'ui-priority-primary', 'ui-priority-secondary' ],
                "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
                    var nRowData = $(nRow).data();
                    return $(nRow)
                    .unbind('click')
                    .click(function(event) {
                        if(!($(event.target).is('button#details',nRow))) {
                            if($(nRow).hasClass('ui-widget-shadow')) {
                                $(nRow).removeClass('ui-widget-shadow');
                                removeContactButton.button("option","disabled", true);
                            } else {
                                $(contactsDataTable.fnGetNodes( )).each(function() {
                                    $(this).removeClass('ui-widget-shadow');
                                });
                                $(nRow).addClass('ui-widget-shadow');
                                removeContactButton.button("option","disabled", false);
                            }
                        }
                    })
                    .each(function() {
                        $(nRowData.detailsButton = $(this).find('button#details'))
                        .button({
                            text: false,
                            icons: {
                                primary: "ui-icon-circle-triangle-e"
                            }
                        })
                        .unbind('toggle')
                        .toggle(
                            function() {
                                // Close open rows properly via event
                                $(contactsDataTable.fnGetNodes()).filter(function(index) {
                                    return contactsDataTable.fnIsOpen(this);
                                }).each(function(index,Tr) {
                                    contactsDataTable.fnClose(Tr);
                                    $(Tr).find('button#details').button("option","icons").primary = "ui-icon-circle-triangle-e";
                                });
                                //$($.grep(self.ruleSetEditorDataTable.fnGetNodes(),function(Tr,index) {
                                //    return self.ruleSetEditorDataTable.fnIsOpen(Tr);
                                //})).find('button#details').trigger('click');                                                                    
                                // Open the details
                                $(this).button( "option", "icons", {
                                    primary: "ui-icon-circle-triangle-s"
                                });
                                $(nRowData.detailsRow = $(contactsDataTable.fnOpen(
                                    nRow,
                                    "<fieldset id='contactDetails' />",
                                    'ui-widget-content'))
                                ).find('fieldset#contactDetails')
                                .each(function(index,contactDetailsFieldset) {
                                    $(contactDetailsFieldset).addClass('ui-widget-content')
                                    .append(
                                        $('<legend />')
                                        .html('Contact Details')
                                        .addClass('ui-widget-header ui-corner-all')
                                    );

                                });
                            },
                            function() {
                                // Close the details
                                $(this).button( "option", "icons", {
                                    primary: "ui-icon-circle-triangle-e"
                                });
                                contactsDataTable.fnClose(nRow);
                            }
                        );
                    });
                }                
            }));                                                            
        },    
        contactElements: {
            contactTypeAll: $('<option />').val("").html("--All Contacts--")
        },
        buildSearch: function () {
            var self = this,
            o = self.options,
            el = self.element,
            searchTab = $(self.searchTab = $(el).find('div#search')),
            searchElements = (self.searchElements = {
                firstName: searchTab.find('input#firstName'),
                middleName: searchTab.find('input#middleName'),
                lastName: searchTab.find('input#lastName'),
                gender: searchTab.find('select#gender'),
                race: searchTab.find('select#race'),
                bornBefore: searchTab.find('input#bornBefore'),
                bornAfter: searchTab.find('input#bornAfter'),
                county: searchTab.find('select#county'),
                address: searchTab.find('input#address'),
                city: searchTab.find('input#city'),
                zip: searchTab.find('input#zip'),
                party: searchTab.find('select#party'),
                status: searchTab.find('select#status'),
                congressionalDistrict: searchTab.find('input#congressionalDistrict'),
                senateDistrict: searchTab.find('input#senateDistrict'),
                countyCommissionDistrict: searchTab.find('input#countyCommissionDistrict'),
                schoolBoardDistrict: searchTab.find('input#schoolBoardDistrict'),
                precinct: searchTab.find('input#precinct'),
                precinctGroup: searchTab.find('input#precinctGroup'),
                precinctSplit: searchTab.find('input#precinctSplit'),
                precinctSuffix: searchTab.find('input#precinctSuffix'),
                countyPrecinct: searchTab.find('input#countyPrecinct'),
                cityPrecinct: searchTab.find('input#cityPrecinct'),
                judicialDistrict: searchTab.find('input#judicialDistrict'),
                schoolDistrict: searchTab.find('input#schoolDistrict'),
                landDistrict: searchTab.find('input#landDistrict'),
                importDate: searchTab.find('select#importDate'),
                months: searchTab.find('select#months'),
                useNewlyRegistered: searchTab.find('input#useNewlyRegistered'),
                electionType: searchTab.find('select#electionType'),
                votedBefore: searchTab.find('input#votedBefore'),
                votedAfter: searchTab.find('input#votedAfter'),
                minVoteCount: searchTab.find('input#minVoteCount'),
                historyVoteType: searchTab.find('select#historyVoteType'),
                absentee: searchTab.find('input#absentee'),
                partyVoted: searchTab.find('select#partyVoted')                
            }),
            findVotersButton = $(self.findVotersButton = searchTab.find('button#findVoters')).button().click(function() {
                $.VoterContactManager.search.POSTsearch({
                    search: $.extend(true,{
                        name: { 
                            firstName: searchElements.firstName.val(),
                            middleName: searchElements.middleName.val(),
                            lastName: searchElements.lastName.val()
                        },
                        gender: {
                            code: searchElements.gender.val()
                        },
                        race: {
                            code: searchElements.race.val()
                        },
                        bornBefore: searchElements.bornBefore.val(),
                        bornAfter: searchElements.bornAfter.val(),
                        county: {
                            code: searchElements.county.val()
                        },
                        address: {
                            line1: searchElements.address.val(),
                            city: searchElements.city.val(),
                            zip: searchElements.zip.val()
                        },
                        voterStatus: {
                            code: searchElements.status.val()
                        },
                        party: {
                            code: searchElements.party.val()
                        },
                        congressionalDistrict: searchElements.congressionalDistrict.val(),
                        senateDistrict: searchElements.senateDistrict.val(),
                        countyCommissionDistrict: searchElements.countyCommissionDistrict.val(),
                        importDate: searchElements.importDate.val(),
                        months: searchElements.months.val(),
                        useNewlyRegistered: searchElements.useNewlyRegistered.is(':checked'),
                        state: {
                            code: searchElements.stateCode.val()
                        },
                        history: {
                            electionType: searchElements.electionType.val(),
                            electionDate: {
                                votedBefore: searchElements.votedBefore.val(),
                                votedAfter: searchElements.votedAfter.val()
                            },
                            minVoteCount: searchElements.minVoteCount.val()
                        }
                    },{
                        stateSpecificFields: function(stateCode) {
                            switch(stateCode) {
                                case 'FL':
                                    return {
                                        precinct: {
                                            precinct: searchElements.precinct.val(),
                                            precinctGroup: searchElements.precinctGroup.val(),
                                            precinctSplit: searchElements.precinctSplit.val(),
                                            precinctSuffix: searchElements.precinctSuffix.val()
                                        },
                                        schoolBoardDistrict: searchElements.schoolBoardDistrict.val(),
                                        history: {
                                            historyVoteType: searchElements.historyVoteType.val(),
                                            partyVoted: searchElements.partyVoted.val()
                                        }
                                    }
                                    break;
                                case 'GA':
                                    return {
                                        history: {
                                            absentee: searchElements.absentee.val()
                                        },
                                        countyPrecinct: searchElements.countyPrecinct.val(),
                                        cityPrecinct: searchElements.cityPrecinct.val(),
                                        judicialDistrict: searchElements.judicialDistrict.val(),
                                        schoolDistrict: searchElements.schoolDistrict.val(),
                                        landDistrict: searchElements.landDistrict.val()
                                    }
                            }
                        }
                    }.stateSpecificFields(stateCode.val()))
                },function(getSearchResponse, textStatus, jqXHR) {
                    self.matchesDataTable.fnClearTable();
                    self.matchesDataTable.fnAddData(getSearchResponse);
                });        
            }),
            stateCode = $(self.stateCode = searchTab.find('select#stateCode')).change(function() {
                alert("Chain");
                $.VoterContactManager.voterContactManager.GETgetInit({
                    stateCode: $(this).val()
                },function(getInitResponse, textStatus, jqXHR) {
                    self.contactType.empty().append(self.contactElements.contactTypeAll.clone());
                    $.each(getInitResponse.contactTypes,function(index,contactType) {
                        self.contactType.append(self.contactElements.contactTypeAll.clone().val(contactType.id).html(contactType.name))
                    });                    
                    self.contactsDataTable.fnAddData(getInitResponse.contacts);
                    // Rebuild Matches Datatable
                    if("matchesDataTable" in self) {
                        self.matchesDataTable.fnDestroy();
                    }
                    self.matchesTable.find('tbody').empty().end().find('thead').find('tr').empty().each(function() {
                        var tr = $(this);
                        $.each(getInitResponse.matchesHeaders,function(index,header) {
                            $('<th />').append(header).appendTo(tr);
                        });
                        $(el).tabs( "option", "selected", 1 );
                    }).end().end().each(function() {
                        self.matchesDataTable = $(this).dataTable({
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
                        $(el).tabs( "option", "selected", 0 );
                    });
                    
                    // Finish Rebuild Datatable
                    
                    searchElements.gender.empty().append(
                        $('<option />').val("").html('--Select Gender--')
                    );
                    searchElements.race.empty().append(
                        $('<option />').val("").html('--Select Race--')
                    );
                    searchElements.county.empty().append(
                        $('<option />').val("").html('--Select County--')
                    );
                    searchElements.party.empty().append(
                        $('<option />').val("").html('--Select Party Registration--')
                    );
                    searchElements.status.empty().append(
                        $('<option />').val("").html('--Select Registered Status--')
                    );
                    searchElements.importDate.empty().append(
                        $('<option />').val("").html('--All Import Dates--')
                    );
                    searchElements.electionType.empty().append(
                        $('<option />').val("").html('--Election Type--')
                    );
                    searchElements.historyVoteType.empty().append(
                        $('<option />').val("").html('--Vote Type--')
                    ).each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    });
                    searchElements.absentee.each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.partyVoted.empty().append(
                        $('<option />').val("").html('--Party Voted--')
                    ).each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    });
                    $.each(getInitResponse.genders,function(index,gender) {
                        $('<option />').val(gender.code).html(gender.name).appendTo(searchElements.gender);
                    });
                    $.each(getInitResponse.races,function(index,race) {
                        $('<option />').val(race.code).html(race.name).appendTo(searchElements.race);
                    });
                    $.each(getInitResponse.counties,function(index,county) {
                        $('<option />').val(county.code).html(county.name).appendTo(searchElements.county);
                    });
                    $.each(getInitResponse.parties,function(index,party) {
                        $('<option />').val(party.code).html(party.name).appendTo(searchElements.party).each(function() {
                            if(stateCode.val() === 'GA') {
                                searchElements.partyVoted.append($(this).clone());
                            }
                        });
                    });
                    $.each(getInitResponse.statuses,function(index,status) {
                        $('<option />').val(status.code).html(status.name).appendTo(searchElements.status);
                    });
                    $.each(getInitResponse.importDates,function(index,importDate) {
                        $('<option />').val(importDate).html(importDate).appendTo(searchElements.importDate);
                    });
                    $.each(getInitResponse.electionTypes,function(index,electionType) {
                        $('<option />').val(electionType.code).html(electionType.name).appendTo(searchElements.electionType);
                    });
                    $.each(getInitResponse.historyVoteTypes,function(index,historyVoteType) {
                        $('<option />').val(historyVoteType.code).html(historyVoteType.name).appendTo(searchElements.historyVoteType);
                    });
                    searchElements.schoolBoardDistrict.each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.precinct.each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.precinctGroup.each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.precinctSplit.each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.precinctSuffix.each(function() {
                        switch(stateCode.val()) {
                            case 'FL':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.countyPrecinct.each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.cityPrecinct.each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.judicialDistrict.each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.schoolDistrict.each(function() {
                        switch(stateCode.val()) {
                            case 'GA':
                                $(this).parent().parent().show();
                                break;
                            default:
                                $(this).parent().parent().hide();
                                break;
                        }
                    }).val("");
                    searchElements.landDistrict.each(function() {
                        switch(stateCode.val()) {
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
            searchElements.gender.each(function() { $(this).width(searchElements.firstName.width()*0.7); });
            searchElements.race.each(function() { $(this).width(searchElements.firstName.width()*0.7); });
            searchElements.county.each(function() { $(this).width(searchElements.firstName.width()*0.7); }).unbind('change').change(function() {
                if($(this).val() !== "" && searchElements.party.val() !== "") {
                    searchElements.months.prop('disabled',false);
                    searchElements.useNewlyRegistered.prop('disabled',false);
                } else {
                    searchElements.months.prop('disabled',true);
                    searchElements.useNewlyRegistered.prop('disabled',true);
                    searchElements.useNewlyRegistered.prop('checked',false);
                }
            });
            searchElements.party.each(function() { $(this).width(searchElements.firstName.width()*0.7); }).unbind('change').change(function() {
                if($(this).val() !== "" && searchElements.county.val() !== "") {
                    searchElements.months.prop('disabled',false);
                    searchElements.useNewlyRegistered.prop('disabled',false);
                } else {
                    searchElements.months.prop('disabled',true);
                    searchElements.useNewlyRegistered.prop('disabled',true);
                    searchElements.useNewlyRegistered.prop('checked',false);
                }
            });
            searchElements.status.each(function() { $(this).width(searchElements.firstName.width()*0.7); });
            searchElements.months.each(function() { $(this).prop('disabled',true); });
            searchElements.useNewlyRegistered.each(function() { 
                $(this).prop('disabled',true); 
                $(this).prop('checked',false); 
            });
            searchElements.electionType.each(function() { $(this).width(searchElements.zip.width()*0.7); });
            searchElements.historyVoteType.each(function() { $(this).width(searchElements.zip.width()*0.7); });
            searchElements.partyVoted.each(function() { $(this).width(searchElements.firstName.width()*0.7); });
            searchTab.find('table:first').find('table').find('tr').find('td:last').css({'text-align':'right'});
            
        },
        _setOption: function(option, value) {
            $.Widget.prototype._setOption.apply( this, arguments );
            var self = this,
            el = self.element,
            o = self.options;
        }
    });
    $.VoterContactManager = {
        googleContacts: {
            GETgetGContacts: function(json,callback) {
                json = jQuery.extend({
                    "key": "AIzaSyDG8Af8zr2kRr8odedTFhYiq7o7x6UFdDw"
                },json);
                $.ajax({
                    url: "https://www.google.com/m8/feeds/contacts/default/full?callback=?",
//                        url: "/VoterContactManager/voterContactManager/",
                    type: "GET",
                    dataType : "jsonp",
                    beforeSend: function (XMLHttpRequest, settings) {
                        XMLHttpRequest.setRequestHeader("GData-Version", "3.0");
                        XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                        XMLHttpRequest.setRequestHeader("Accept", "application/json");
                    },
                    data: json,
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
            },
            POSTeditContactType: function(json,callback) {
                json = jQuery.extend(true,{
                    name: "",
                    id: ""
                },json);
                $.ajax({
                    url: '/VoterContactManager/contact/type/',
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
            },
            DELETEremoveContactType: function(json,callback) {
                json = jQuery.extend({
                    id: "-1"
                },json);
                $.ajax({
                    url: "/VoterContactManager/contact/type/?id="+$.trim(json.id),
                    type: "DELETE",
                    dataType : "json",
                    beforeSend: function (XMLHttpRequest, settings) {
                        XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                        XMLHttpRequest.setRequestHeader("Accept", "application/json");
                    },
                    data: {
                        id: $.trim(json.id)
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
            },
            PUTaddNewContact: function(json,callback) {
                ///contact/type
                json = jQuery.extend(true,{
                    name: ""
                },json);
                $.ajax({
                    url: '/VoterContactManager/contact/',
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
            },
            DELETEremoveContact: function(json,callback) {
                json = jQuery.extend({
                    id: "-1"
                },json);
                $.ajax({
                    url: "/VoterContactManager/contact/?id="+$.trim(json.id),
                    type: "DELETE",
                    dataType : "json",
                    beforeSend: function (XMLHttpRequest, settings) {
                        XMLHttpRequest.setRequestHeader("Content-Type", "application/json");
                        XMLHttpRequest.setRequestHeader("Accept", "application/json");
                    },
                    data: {
                        id: $.trim(json.id)
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
                            // console.log(textStatus);
                            window.location.reload();
                        }
                    }
                });
            }
        }
        
    };
})(jQuery);


