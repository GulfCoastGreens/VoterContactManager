<!--
  To change this template, choose Tools | Templates
  and open the template in the editor.
-->

<%@ page contentType="text/html;charset=UTF-8" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="layout" content="main"/>
    <title>Upload Voter Data</title>
  </head>
  <body>
    <div id="uploadData" data-role="content">
      <h2>Upload Voter Data</h2>
      <div data-role="fieldcontain">
        <g:uploadForm controller="uploadData" action="uploadVoterData" name="voterUpload" data-ajax="false">
        <label for="stateCode" class="select">Select State:</label>
        <ud:uploadStateSelect />
        <input id="contents" name="contents" type="file" value="Choose File">
        <input type="submit" value="Upload Voter Data (Zipped)">
        </g:uploadForm>    
      </div>
      <h2>Upload History Data</h2>
      <div data-role="fieldcontain">
        <g:uploadForm controller="uploadData" action="uploadHistoryData" name="historyUpload" data-ajax="false">
        <label for="stateCode" class="select">Select State:</label>
        <ud:uploadStateSelect />
        <input id="contents" name="contents" type="file" value="Choose File">      
        <input type="submit" value="Upload History Data (Zipped)">
        </g:uploadForm>    
      </div>
    </div>
    
  </body>
</html>
