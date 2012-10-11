<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
		<title>Welcome to Grails</title>
                <!-- link rel="stylesheet" href="${resource(dir: 'css', file: 'default.css')}" type="text/css" -->
	</head>
	<body>
		<!-- a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a -->
                <div id="tabs">
                  <ul>
                    <li><a href="#search">Search</a></li>
                    <li><a href="#matches">Matches</a></li>
                    <li><a href="#walkingList">Walking Lists</a></li>
                    <li><a href="#contacts">Contacts</a></li>
                    <li><a href="#import">Import</a></li>
                  </ul>
                  <div id="search" role="main">
                    <table border="0">
                      <thead>
                        <tr>
                          <th class="ui-state-default ui-widget-header" colspan="3">Search Elements</th>
                        </tr>
                      </thead>
                      <tbody>
                        <tr>
                          <td colspan="3">
                            <label for="stateCode" class="select">Select State:</label>
                            <vcm:uploadStateSelect />
                          </td>
                        </tr>
                        <tr>
                          <td style="vertical-align: top;">
                            <table border="0" style="font-size: 70%" width="100%">
                              <thead>
                                <tr>
                                  <th class="ui-state-default ui-widget-header" colspan="3">Name Parameters</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>
                                    <label for="firstName">First Name</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="firstName" name="firstName" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="middleName">Middle Name</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="middleName" name="middleName" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="lastName">Last Name</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="lastName" name="lastName" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="gender">Gender</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="gender" name="gender" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="race">Race</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="race" name="race" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="bornBefore">Born Before</label>
                                  </td>
                                  <td colspan="2">
                                    <vcm:jqDatePicker name="bornBefore" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="bornAfter">Born After</label>
                                  </td>
                                  <td colspan="2">
                                    <vcm:jqDatePicker name="bornAfter" />
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </td>
                          <td style="vertical-align: top;">
                            <table border="0" style="font-size: 70%" width="100%">
                              <thead>
                                <tr>
                                  <th class="ui-state-default ui-widget-header" colspan="3">Location Parameters</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>
                                    <label for="county">County Name</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="county" name="county" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="address">Address</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="address" name="address" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="city">City</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="city" name="city" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="zip">Zip Code</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="zip" name="zip" type="text" />
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                            <table border="0" style="font-size: 70%" width="100%">
                              <thead>
                                <tr>
                                  <th class="ui-state-default ui-widget-header" colspan="3">History Parameters</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>
                                    <label for="electionType">Election Type</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="electionType" name="electionType" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="votedBefore">Voted Before</label>
                                  </td>
                                  <td colspan="2">
                                    <vcm:jqDatePicker name="votedBefore" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="votedAfter">Voted After</label>
                                  </td>
                                  <td colspan="2">
                                    <vcm:jqDatePicker name="votedAfter" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="historyVoteType">Vote Type</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="historyVoteType" name="historyVoteType" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="absentee">Absentee</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="absentee" name="absentee" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="partyVoted">Party Voted</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="partyVoted" name="partyVoted" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="minVoteCount">Voted Count Threshold</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="minVoteCount" name="minVoteCount" type="number" />
                                  </td>
                                </tr>
                              </tbody>
                            </table>
                          </td>
                          <td style="vertical-align: top;">
                            <table border="0" style="font-size: 70%" width="100%">
                              <thead>
                                <tr>
                                  <th class="ui-state-default ui-widget-header" colspan="3">Registration Parameters</th>
                                </tr>
                              </thead>
                              <tbody>
                                <tr>
                                  <td>
                                    <label for="party">Registered Party</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="party" name="party" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="status">Registered Status</label>
                                  </td>
                                  <td colspan="2">
                                    <select id="status" name="status" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="congressionalDistrict">Congressional District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="congressionalDistrict" name="congressionalDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="houseDistrict">House District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="houseDistrict" name="houseDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="senateDistrict">Senate District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="senateDistrict" name="senateDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="countyCommissionDistrict">County Commission District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="countyCommissionDistrict" name="countyCommissionDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="schoolBoardDistrict">School Board District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="schoolBoardDistrict" name="schoolBoardDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="precinct">Precinct</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="precinct" name="precinct" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="precinctGroup">Precinct Group</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="precinctGroup" name="precinctGroup" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="precinctSplit">Precinct Split</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="precinctSplit" name="precinctSplit" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="precinctSuffix">Precinct Suffix</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="precinctSuffix" name="precinctSuffix" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="countyPrecinct">County Precinct</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="countyPrecinct" name="countyPrecinct" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="cityPrecinct">City Precinct</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="cityPrecinct" name="cityPrecinct" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="judicialDistrict">Judicial District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="judicialDistrict" name="judicialDistrict" type="text" />
                                  </td>
                                </tr>
                                <tr>
                                  <td>
                                    <label for="schoolDistrict">School District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="schoolDistrict" name="schoolDistrict" type="text" />
                                  </td>
                                </tr>      
                                <tr>
                                  <td>
                                    <label for="landDistrict">Land District</label>
                                  </td>
                                  <td colspan="2">
                                    <input id="landDistrict" name="landDistrict" type="text" />
                                  </td>
                                </tr>      
                              </tbody>
                            </table>
                          </td>
                        </tr>
                      </tbody>
                        <tfoot>
                          <tr>
                            <td colspan="3" style="font-size: 65%;">
                              <button id="findVoters">Find Matching Voters</button>
                              <label for="importDate" style="padding-left:0.3cm;">Select Import Date</label>
                              <select id="importDate" name="importDate"></select>
                              <label for="months" style="padding-left:0.3cm;">Limit to new party registrations in the last</label>
                              <g:select name="months" from="${[1,2,3,4,5,6,7,8,9,10,11,12]}" optionValue="${{(it < 2)?(it+' month'):(it+' months')}}"/>
                              <input type="checkbox" name="useNewlyRegistered" title="You MUST have both a county and party affiliation selected to use this" disabled="" id="useNewlyRegistered"/>
                              <label for="useNewlyRegistered">Enable</label>
                            </td>
                          </tr>
                        </tfoot>
                    </table>
                  </div>
                  <div id="matches" role="main">
                    <table id="matches">
                      <thead>
                        <tr></tr>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                  </div>
                  <div id="walkingList" role="main">
                  </div>
                  <div id="contacts" role="main">
                  </div>
                  <div id="import" role="main">
                    <h2>Upload Voter Data</h2>
                    <div data-role="fieldcontain">
                      <g:uploadForm controller="uploadZippedData" action="uploadZip" name="voterUpload" data-ajax="false">
                      <label for="stateCode" class="select">Select State:</label>
                      <vcm:uploadStateSelect />
                      <input type="hidden" value="voter" name="zipContentType" id="zipContentType" />
                      <input id="contents" name="contents" type="file" value="Choose File">
                      <input type="submit" value="Upload Voter Data (Zipped)">
                      </g:uploadForm>    
                    </div>
                    <h2>Upload History Data</h2>
                    <div data-role="fieldcontain">
                      <g:uploadForm controller="uploadZippedData" action="uploadZip" name="historyUpload" data-ajax="false">
                      <input type="hidden" value="history" name="zipContentType" id="zipContentType" />
                      <label for="stateCode" class="select">Select State:</label>
                      <vcm:uploadStateSelect />
                      <input id="contents" name="contents" type="file" value="Choose File">      
                      <input type="submit" value="Upload History Data (Zipped)">
                      </g:uploadForm>    
                    </div>                    
                  </div>
                </div>
		<!--
                <div id="status" role="complementary">
			<h1>Application Status</h1>
                        <vcm:applicationStatus />
			<h1>Installed Plugins</h1>
			<vcm:installedPlugins />
		</div>
		<div id="page-body" role="main">
			<h1>Welcome to Grails</h1>
			<p>Congratulations, you have successfully started your first Grails application! At the moment
			   this is the default page, feel free to modify it to either redirect to a controller or display whatever
			   content you may choose. Below is a list of controllers that are currently deployed in this application,
			   click on each to execute its default action:</p>

			<div id="controller-list" role="navigation">
				<h2>Available Controllers:</h2>
                                <vcm:availableControllers />
			</div>
		</div>
                -->
	</body>
</html>
