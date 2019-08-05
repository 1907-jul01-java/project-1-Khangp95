window.onload = function(){
	//getReimbursementTable();
	Load();
}

function getReimbursementTable(){
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-khangp/Employee", true);
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			if (this.status == 200) {
				var line = JSON.parse(xhr.responseText);
				var table = document.getElementById("Reimbursements");
				var div = "<thead><tr><th>ID</th><th>Username</th><th>Cost</th><th>Status</th></tr></thead><tbody>";
				for(var i = 0; i < line.length;i++){
					var count = line[i];
					div += "<tr><td>"+count.request_id+"</td><td>"+count.username+"</td><td>"+count.cost+"</td><td>"+count.reimbursementStatus+"</td></tr>";
				}
				table.innerHTML = div+"</tbody>";
				dataTable();
			}
		}
	};
	xhr.setRequestHeader("Content-type",
			"application/x-www-form-urlencoded");
	xhr.send("methodname=loadAllReim");
}

function dataTable(){
	$('#requestTable').DataTable();
}


function Load(){
	var xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-khangp/Employee", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var JSONObject = JSON.parse(xhr.responseText);
			var employeeInfo = document.getElementById("employeeInfo");
			employeeInfo.innerHTML = "";
			var resultfirstName = JSONObject.firstName;
			var resultlastName = JSONObject.lastName;
			employeeInfo.innerHTML = `<div>
	            <h3>Name: ${JSONObject.firstName} ${JSONObject.lastName}</h3>
	            </div>
	    `
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loademp"));
}


function newReimb(){
    var newCost = document.getElementById("newCost").value;
    var newType = document.getElementById("newType").value;
    var param = "newCost="+newCost+"&newType="+newType;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8080/project-1-khangp/Employee", true);
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
            console.log(xhr.response);
            getReimbursementTable();
            var JSONObject = JSON.parse(xhr.responseText);
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   xhr.send(encodeURI(param));
}


