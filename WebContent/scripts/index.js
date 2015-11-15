var aPopup1 = '<a style="position:inherit;" href="#infoPopup1" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup2 = '<a style="position:inherit;" href="#infoPopup2" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup3 = '<a style="position:inherit;" href="#infoPopup3" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup4 = '<a style="position:inherit;" href="#infoPopup4" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var aPopup5 = '<a style="position:inherit;" href="#infoPopup5" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">';
var jsUrlName = "http://www.example.com/" //Replace with Url name
var lblPopup1 = "No"; //Replace with yes/no for Content-Security Policy
var lblPopup2 = "No"; //Replace with es/no for HTTP Strict Transport Policy
var lblPopup3 = "No"; //Replace with yes/no for HttpOnly and Secure Cookies
var lblPopup4 = "No"; //Replace with yes/no for Anticlickjacking headers (X-Frame-Options)
var lblPopup5 = "No"; //Replace with yes/no for Nonces in web forms

$( document ).ready(function() {
    
	document.getElementById('domainName').value = "https://paypal.com";
	document.getElementById('numberPages').value = "2";
	document.getElementById('btnResults').style.visibility = "hidden";
	document.getElementById('btnClear').style.visibility = "hidden";
	document.getElementById('loader').style.visibility = "hidden";
	
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
	//alert(data);
	//alert("In function addUrlsTable " + JSON.stringify(data));
	
	for (var index = 0; index<data.resultObjects.length;index++) {
		
	    jsUrlName = data.resultObjects[index].urlName;
	    
	    if(data.resultObjects[index].cspEnabled) {
	    	lblPopup1="Yes";
	    	document.getElementById('infoPopup1').innerHTML = "Content-Security Policy";
	    }
	    if(data.resultObjects[index].strictEnabled) {
	    	lblPopup2="Yes";
	    	document.getElementById('infoPopup2').innerHTML = "HTTP Strict Transport Policy";
	    }
	    if(data.resultObjects[index].httpOnlyEnabled) {
	    	lblPopup3="Yes";
	    	document.getElementById('infoPopup3').innerHTML = data.resultObjects[index].httpOnlyDetails;
	    }
	    if(data.resultObjects[index].antiClickEnabled) {
	    	lblPopup4="Yes";
	    	document.getElementById('infoPopup4').innerHTML = "Anticlickjacking headers (X-Frame-Options)";
	    }
	    if(data.resultObjects[index].noncesEnabled) {
	    	lblPopup5="Yes";
	    	document.getElementById('infoPopup5').innerHTML = "Nonces in web forms";
	    }
	    
	    $('#tableBody').append('<tr><td>'+jsUrlName+'</td><td>'+aPopup1+lblPopup1+'</a></td><td>'+aPopup2+lblPopup2+'</a></td><td>'+aPopup3+lblPopup3+
	    		'</a></td><td>'+aPopup4+lblPopup4+'</a></td><td>'+aPopup5+lblPopup5+'</a></td></tr>');
	   		
	}
}

function getUrls() {
	$.ajax({
		  type: "GET",
		  url: "UserInput?option=getUrls",
		  dataType: "json",
		  success: function(data) {
			  addUrlsTable(data);
			  document.getElementById('btnResults').style.visibility = "hidden";
			  document.getElementById('btnClear').style.visibility = "visible";
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
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

