function isNumeric(input) {
    var RE = /^-{0,1}\d*\.{0,1}\d+$/;
    if(!RE.test(input)){
        $("#numberError").html("Please provide a proper value");
        return false;
    }
    return true;
}

function notEmpty() {
    var name = document.forms["clientName"]["clientName"].value;
    if(name.length < 1){
        $("#nameError").html("Please provide the proper name.");
        return false;
    }
    return true;
}