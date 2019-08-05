 function Login(){
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    var param = "username="+username+"password"+password;
    let xhr = new XMLHttpRequest();
    xhr.open('POST', "http://localhost:8080/project-1-khangp/login", true);
    xhr.onreadystatechange = function(){
        if(xhr.readyState == 4 && xhr.status == 200){
        	var JSONObject = xhr.responseText;
			if (JSONObject == "false") {
				alert("wrong username/password");
			}
			if (JSONObject == "employee") {
				window.location ="http://localhost:8080/project-1-khangp/Employee.html";
			}
			if (JSONObject == "manager") {
				window.location ="http://localhost:8080/project-1-khangp/Manager.html";
			}
        }
    };
    xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
   xhr.send(encodeURI(param));
}