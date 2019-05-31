$(document).ready(function () {

    $.get("user", function(data) {
        console.log(data)
        var JSONdata = JSON.parse(data)
        if (JSONdata.statusCode == 1000) {
            console.log(JSONdata.message + " " + typeof(JSONdata.message))

            $("#signupNavBar").hide()
            $("#loginNavBar").hide()
            $("#logoutNavBar").show()

            var JSONmessage = JSON.parse(JSONdata.message)
            if (JSONmessage.admin == true) {
                $("#userNearBrand").html(" - " + JSONmessage.user + " (ADMIN)")
                $("#usersNavBar").show()
            } else {
                $("#userNearBrand").html(" - " + JSONmessage.user)
            }
        }
    }).fail(function() {
        $("#signupNavBar").show()
        $("#loginNavBar").show()
        $("#usersNavBar").hide()
    })

    $("#logout").click(function() {
        console.log("logout clicked")
        $.get("LogOut", function(data) {
            var JSONdata = JSON.parse(data)
            if (JSONdata.statusCode == 1000) {
                window.location.replace("/Richkware-Manager-Server/index.html")
            } else {
                //chiama pagina di errore
            }
        })
    })

 });