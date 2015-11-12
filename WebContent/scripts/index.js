var aPopup1 = '<a style="position:inherit;" href="#infoPopup1" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">Yes</a>';
var aPopup2 = '<a style="position:inherit;" href="#infoPopup2" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">Yes</a>';
var aPopup3 = '<a style="position:inherit;" href="#infoPopup3" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">Yes</a>';
var aPopup4 = '<a style="position:inherit;" href="#infoPopup4" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">Yes</a>';
var aPopup5 = '<a style="position:inherit;" href="#infoPopup5" data-rel="popup" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window">Yes</a>';

$( document ).ready(function() {
    
	document.getElementById('domainName').value = "https://paypal.com";
	document.getElementById('numberPages').value = "2";
	document.getElementById('btnResults').style.visibility = "hidden";
	document.getElementById('loader').style.visibility = "hidden";
	
	$('#btnSubmit').click(function(){
	    updateInput();
	    document.getElementById('loader').style.visibility = "visible";
	});
	
	$('#btnResults').click(function(){
		getUrls();
	});
	
});

function addUrlsTable(data) {
	for (var index in data.urls) {
	    var urlName = data.urls[index];
	    
	  //Here is where vulnerability text should assigned to each popup
	    document.getElementById('infoPopup1').innerHTML = "Content-Security Policy";
	    document.getElementById('infoPopup2').innerHTML = "HTTP Strict Transport Policy";
	    document.getElementById('infoPopup3').innerHTML = "HttpOnly and Secure Cookies";
	    document.getElementById('infoPopup4').innerHTML = "Anticlickjacking headers (X-Frame-Options)";
	    document.getElementById('infoPopup5').innerHTML = "Nonces in web forms";
	    
	    $('#tableMain').append('<tr><td>'+urlName+'</td><td>'+aPopup1+'</td><td>'+aPopup2+'</td><td>'+ aPopup3 +
	    		'</td><td>'+aPopup4+'</td><td>'+aPopup5+'</td></tr>');
	}
}

function getUrls() {
	$.ajax({
		  type: "GET",
		  url: "UserInput?get=getUrls",
		  dataType: "json",
		  success: function(data) {
			  addUrlsTable(data);
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
			  document.getElementById('btnResults').style.visibility = "visible";
			  document.getElementById('loader').style.visibility = "hidden";
		  },
		  error: function() {
			  alert("error");
		  }
		});
}

