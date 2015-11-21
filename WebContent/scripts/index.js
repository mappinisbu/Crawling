var redbg='#DC657D';
var greenbg='#65DC6B';

var aPopup10 = '<a style="position:inherit;background-color:';
var aPopup11 = ';" href="#infoPopup-1-';
var aPopup12 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';

var aPopup20 = '<a style="position:inherit;background-color:';
var aPopup21 = ';" href="#infoPopup-2-';
var aPopup22 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';

var aPopup30 = '<a style="position:inherit;background-color:';
var aPopup31 = ';" href="#infoPopup-3-';
var aPopup32 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';

var aPopup40 = '<a style="position:inherit;background-color:';
var aPopup41 = ';" href="#infoPopup-4-';
var aPopup42 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';

var aPopup50 = '<a style="position:inherit;background-color:';
var aPopup51 = ';" href="#infoPopup-5-';
var aPopup52 = '" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';


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
var bg1=redbg;
var bg2=redbg;
var bg3=redbg;
var bg4=redbg;
var bg5=redbg;
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
	document.getElementById('btnClear').style.visibility = "hidden";
	document.getElementById('loader').style.visibility = "hidden";
	document.getElementById('tableResults').style.visibility = "hidden";
	
	$('#btnSubmit').click(function(){
		document.getElementById('btnSubmit').disabled = true;
	    updateInput();
	    document.getElementById('loader').style.visibility = "visible";
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
		document.getElementById('infoPopup-1-'+index).innerHTML = data.resultObjects[index].cspDetails;
		document.getElementById('infoPopup-2-'+index).innerHTML = data.resultObjects[index].strictDetails;
		document.getElementById('infoPopup-3-'+index).innerHTML = data.resultObjects[index].httpOnlyDetails;
		document.getElementById('infoPopup-4-'+index).innerHTML = data.resultObjects[index].antiClickDetails;
		document.getElementById('infoPopup-5-'+index).innerHTML = '<pre>' + data.resultObjects[index].noncesDetails.replace(/&/g, '&amp;').replace(/</g, '&lt;') + '</pre>';
		
	    if(data.resultObjects[index].cspEnabled) {
	    	lblPopup1="Yes";
	    	bg1=greenbg;
	    	countCsp++;
	    } else {
	    	lblPopup1 = "No";
	    	bg1=redbg;
	    }
	    if(data.resultObjects[index].strictEnabled) {
	    	lblPopup2="Yes";
	    	bg2=greenbg;
	    	countStrict++;
	    } else {
	    	lblPopup2 = "No";
	    	bg2=redbg;
	    }
	    if(data.resultObjects[index].httpOnlyEnabled) {
	    	lblPopup3="Yes";
	    	bg3=greenbg;
	    	countHttpOnly++;
	    } else {
	    	lblPopup3 = "No";
	    	bg3=redbg;
	    }
	    if(data.resultObjects[index].antiClickEnabled) {
	    	lblPopup4="Yes";
	    	bg4=greenbg;
	    	countAntiClick++;
	    } else {
	    	lblPopup4 = "No";
	    	bg4=redbg;
	    }
	    if(data.resultObjects[index].noncesEnabled) {
	    	lblPopup5="Yes";
	    	bg5=greenbg;
	    	countNonces++;
	    } else {
	    	lblPopup5 = "No";
	    	bg5=redbg;
	    	//document.getElementById('infoPopup-5-'+index).innerHTML = '<pre>' + data.resultObjects[index].noncesDetails.replace(/&/g, '&amp;').replace(/</g, '&lt;') + '</pre>';
	    }
	    
	    $('#tableBody').append('<tr><td>'+jsUrlName+'</td><td>'+aPopup10+bg1+aPopup11+index+aPopup12+lblPopup1+'</a></td><td>'+aPopup20+bg2+aPopup21+index+aPopup22+lblPopup2+'</a></td><td>'+aPopup30+bg3+aPopup31+index+aPopup32+lblPopup3+
	    		'</a></td><td>'+aPopup40+bg4+aPopup41+index+aPopup42+lblPopup4+'</a></td><td>'+aPopup50+bg5+aPopup51+index+aPopup52+lblPopup5+'</a></td></tr>');
	    
	    if (data.resultObjects[index].cspEnabled && data.resultObjects[index].strictEnabled && data.resultObjects[index].httpOnlyEnabled && data.resultObjects[index].antiClickEnabled && data.resultObjects[index].noncesEnabled) {
	    	countSecure++;
	    }	
	}
}

function showResultData() {

	//Add percentages to the results data table
	document.getElementById('securePercent').innerHTML = "Fully Secure Urls: " + ( Math.round((countSecure/numberOfUrls) * 100) ).toString() + " %";
	document.getElementById('resultDataCsp').innerHTML = nameCsp + ": " + ( Math.round((countCsp/numberOfUrls) * 100) ).toString() + " %";
	document.getElementById('resultDataStrict').innerHTML = nameStrict + ": " + ( Math.round((countStrict/numberOfUrls) * 100) ).toString() + " %";
	document.getElementById('resultDataHttpOnly').innerHTML = nameHttpOnly + ": " + ( Math.round((countHttpOnly/numberOfUrls) * 100) ).toString() + " %";
	document.getElementById('resultDataAntiClick').innerHTML = nameAntiClick + ": " + ( Math.round((countAntiClick/numberOfUrls) * 100) ).toString() + " %";
	document.getElementById('resultDataNonces').innerHTML = nameNonces + ": " + ( Math.round((countNonces/numberOfUrls) * 100) ).toString() + " %";

	//Color the data based on percentage threshold (50%)
	resultDataColoring(countSecure/numberOfUrls, "securePercent");
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
			  getUrls();
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
			
			//Clear Fully Secure Urls text
			document.getElementById('securePercent').innerHTML = "";
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

