$( document ).ready(function() {
    
	document.getElementById('domainName').value = "https://paypal.com";
	document.getElementById('numberPages').value = "3";
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
	    $('#tableMain').append('<tr><td>'+urlName+'</td><td></td><td></td><td></td><td></td><td></td></tr>');
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

