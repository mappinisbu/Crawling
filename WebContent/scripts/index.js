var aPopup1 = '<a style="position:inherit;" href="#infoPopup-1-';
var aPopup11 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup2 = '<a style="position:inherit;" href="#infoPopup-2-';
var aPopup22 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup3 = '<a style="position:inherit;" href="#infoPopup-3-';
var aPopup33 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup4 = '<a style="position:inherit;" href="#infoPopup-4-';
var aPopup44 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup5 = '<a style="position:inherit;" href="#infoPopup-5-';
var aPopup55 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var nameCsp = "Content-Security Policy";
var nameStrict = "HTTP Strict Transport Policy";
var nameHttpOnly = "HttpOnly and Secure Cookies";
var nameAntiClick = "Anticlickjacking Headers";
var nameNonces = "Nonces";
var jsUrlName = "http://www.example.com/" //Replace with Url name
var lblPopup1 = "No"; //Replace with yes/no for Content-Security Policy
var lblPopup2 = "No"; //Replace with yes/no for HTTP Strict Transport Policy
var lblPopup3 = "No"; //Replace with yes/no for HttpOnly and Secure Cookies
var lblPopup4 = "No"; //Replace with yes/no for Anticlickjacking headers (X-Frame-Options)
var lblPopup5 = "No"; //Replace with yes/no for Nonces in web forms
var countCsp = 0;
var countStrict = 0;
var countHttpOnly = 0;
var countAntiClick = 0;
var countNonces = 0;
var numberOfUrls = 0;
var countSecure = 0;

$( document ).ready(function() {
    
	document.getElementById('domainName').value = "https://paypal.com";
	document.getElementById('numberPages').value = "2";
	document.getElementById('btnResults').style.visibility = "hidden";
	document.getElementById('btnClear').style.visibility = "hidden";
	document.getElementById('loader').style.visibility = "hidden";
	document.getElementById('tableResults').style.visibility = "hidden";
	
	$('#btnSubmit').click(function(){
		document.getElementById('btnSubmit').disabled = true;
	    updateInput();
	    document.getElementById('loader').style.visibility = "visible";
	});
	
	$('#btnResults').click(function(){
		getUrls();
	});
	
	$('#btnClear').click(function(){
		clearUrls();
	});
	
});

function addUrlsTable(data) { 
	
	//Set size of data
	numberOfUrls = data.resultObjects.length;
	
	for (var index = 0; index < data.resultObjects.length; index++) {
		
		$('#popupList').append('<div class="divPopup" data-role="popup" id="infoPopup-1-'+index+'"><p>Not Enabled</p></div>');
		$('#infoPopup-1-'+index).popup();
		$('#popupList').append('<div class="divPopup" data-role="popup" id="infoPopup-2-'+index+'"><p>Not Enabled</p></div>');
		$('#infoPopup-2-'+index).popup();
		$('#popupList').append('<div class="divPopup" data-role="popup" id="infoPopup-3-'+index+'"><p>Not Enabled</p></div>');
		$('#infoPopup-3-'+index).popup();
		$('#popupList').append('<div class="divPopup" data-role="popup" id="infoPopup-4-'+index+'"><p>Not Enabled</p></div>');
		$('#infoPopup-4-'+index).popup();
		$('#popupList').append('<div class="divPopup" data-role="popup" id="infoPopup-5-'+index+'"><p>Not Enabled</p></div>');
		$('#infoPopup-5-'+index).popup();
		
		jsUrlName = data.resultObjects[index].urlName;
	    
	    if(data.resultObjects[index].cspEnabled) {
	    	lblPopup1="Yes";
	    	document.getElementById('infoPopup-1-'+index).innerHTML = data.resultObjects[index].cspDetails;
	    	countCsp++;
	    } else {
	    	lblPopup1 = "No";
	    }
	    if(data.resultObjects[index].strictEnabled) {
	    	lblPopup2="Yes";
	    	document.getElementById('infoPopup-2-'+index).innerHTML = data.resultObjects[index].strictDetails;
	    	countStrict++;
	    } else {
	    	lblPopup2 = "No";
	    }
	    if(data.resultObjects[index].httpOnlyEnabled) {
	    	lblPopup3="Yes";
	    	document.getElementById('infoPopup-3-'+index).innerHTML = data.resultObjects[index].httpOnlyDetails;
	    	countHttpOnly++;
	    } else {
	    	lblPopup3 = "No";
	    }
	    if(data.resultObjects[index].antiClickEnabled) {
	    	lblPopup4="Yes";
	    	document.getElementById('infoPopup-4-'+index).innerHTML = data.resultObjects[index].antiClickDetails;
	    	countAntiClick++;
	    } else {
	    	lblPopup4 = "No";
	    }
	    if(data.resultObjects[index].noncesEnabled) {
	    	lblPopup5="Yes";
	    	document.getElementById('infoPopup-5-'+index).innerHTML = '<pre>' + data.resultObjects[index].noncesDetails.replace(/&/g, '&amp;').replace(/</g, '&lt;') + '</pre>';
	    	countNonces++;
	    } else {
	    	lblPopup5 = "No";
	    	//document.getElementById('infoPopup-5-'+index).innerHTML = '<pre>' + data.resultObjects[index].noncesDetails.replace(/&/g, '&amp;').replace(/</g, '&lt;') + '</pre>';
	    }
	    
	    $('#tableBody').append('<tr><td>'+jsUrlName+'</td><td>'+aPopup1+index+aPopup11+lblPopup1+'</a></td><td>'+aPopup2+index+aPopup22+lblPopup2+'</a></td><td>'+aPopup3+index+aPopup33+lblPopup3+
	    		'</a></td><td>'+aPopup4+index+aPopup44+lblPopup4+'</a></td><td>'+aPopup5+index+aPopup55+lblPopup5+'</a></td></tr>');
	    
	    if (data.resultObjects[index].cspEnabled && data.resultObjects[index].strictEnabled && data.resultObjects[index].httpOnlyEnabled && data.resultObjects[index].antiClickEnabled && data.resultObjects[index].noncesEnabled) {
	    	countSecure++;
	    }	
	}
}

function showResultData() {

	//Add percentages to the results data table
	document.getElementById('resultSecureUrls').innerHTML = "Fully Secure Urls: " + ( (countSecure/numberOfUrls) * 100 ).toString() + " %";
	document.getElementById('resultDataCsp').innerHTML = nameCsp + ": " + ( (countCsp/numberOfUrls) * 100 ).toString() + " %";
	document.getElementById('resultDataStrict').innerHTML = nameStrict + ": " + ( (countStrict/numberOfUrls) * 100 ).toString() + " %";
	document.getElementById('resultDataHttpOnly').innerHTML = nameHttpOnly + ": " + ( (countHttpOnly/numberOfUrls) * 100 ).toString() + " %";
	document.getElementById('resultDataAntiClick').innerHTML = nameAntiClick + ": " + ( (countAntiClick/numberOfUrls) * 100 ).toString() + " %";
	document.getElementById('resultDataNonces').innerHTML = nameNonces + ": " + ( (countNonces/numberOfUrls) * 100 ).toString() + " %";

	//Color the data based on percentage threshold (50%)
	resultDataColoring(countSecure/numberOfUrls, "resultSecureUrls");
	resultDataColoring(countCsp/numberOfUrls, "resultDataCsp");
	resultDataColoring(countStrict/numberOfUrls, "resultDataStrict");
	resultDataColoring(countHttpOnly/numberOfUrls, "resultDataHttpOnly");
	resultDataColoring(countAntiClick/numberOfUrls, "resultDataAntiClick");
	resultDataColoring(countNonces/numberOfUrls, "resultDataNonces");
}

function resultDataColoring(percent, element) {
	//If % >= .5 make green, If % < .5, make red
	if (percent >= .5) {
		document.getElementById(element).style.color = "green";
	} else {
		document.getElementById(element).style.color = "red";
	}
}

function getUrls() {
	$.ajax({
		  type: "GET",
		  url: "UserInput?option=getUrls",
		  dataType: "json",
		  success: function(data) {
			  addUrlsTable(data);
			  showResultData();
			  document.getElementById('btnResults').style.visibility = "hidden";
			  document.getElementById('btnClear').style.visibility = "visible";
			  document.getElementById('tableResults').style.visibility = "visible";
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

function updateInput() {
	
	var name = document.getElementById('domainName').value;
	var number = document.getElementById('numberPages').value;
	
	$.ajax({
		  type: "POST",
		  url: "UserInput",
		  data: { domainName: name, numberPages: number },
		  success: function() {
			  document.getElementById('loader').style.visibility = "hidden";
			  document.getElementById('btnSubmit').style.visibility = "hidden";
			  document.getElementById('btnSubmit').disabled = false;
			  document.getElementById('btnResults').style.visibility = "visible";
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

function clearUrls() {
	$.ajax({
		  type: "GET",
		  url: "UserInput?option=clearUrls",
		  success: function() {
			  $("#tableBody").empty();
			  document.getElementById('btnSubmit').style.visibility = "visible";
			  document.getElementById('btnClear').style.visibility = "hidden";
			  document.getElementById('tableResults').style.visibility = "hidden";
			  
			//Reset counters
			countCsp = 0;
			countStrict = 0;
			countHttpOnly = 0;
			countAntiClick = 0;
			countNonces = 0;
			countSecure = 0;
			numberOfUrls = 0;
			
			//Clear popup contents
			document.getElementById('popupList').innerHTML = "";
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

