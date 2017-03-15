const image = "image";
const url = "url";
const endpoint = "endpoint";

$('#profileList li').on('click', function(){
    var profile = $(this).attr("value");
    document.getElementById("profile").value = profile;

    var profileBtn = document.getElementById("profileBtn");
    profileBtn.innerHTML = $(this).text();
});

$('#locationList li').on('click', function(){
    var location = $(this).attr("value");
    document.getElementById("location").value = location;

    var locationBtn = document.getElementById("locationBtn");
    locationBtn.innerHTML = $(this).text();
});

$('#actionList li').on('click', function(){
    var action = $(this).attr("value");
    document.getElementById("action").value = action;

    var actionBtn = document.getElementById("actionBtn");
    actionBtn.innerHTML = $(this).text();

    if(action == image){
        document.getElementById("imageDiv").style.display="block";
        document.getElementById("urlDiv").style.display="none";
        document.getElementById("endpointDiv").style.display="none";
        document.getElementById("submitButton").style.display="none";
    }else if (action == url){
        document.getElementById("imageDiv").style.display="none";
        document.getElementById("urlDiv").style.display="block";
        document.getElementById("endpointDiv").style.display="none";
        document.getElementById("submitButton").style.display="block";
    }else if (action == endpoint){
        document.getElementById("imageDiv").style.display="none";
        document.getElementById("urlDiv").style.display="none";
        document.getElementById("endpointDiv").style.display="block";
        document.getElementById("submitButton").style.display="block";
    }
});

$('#methodList li').on('click', function(){
    var requestType = $(this).attr("value");
    document.getElementById("requestType").value = requestType;

    var methodBtn = document.getElementById("methodBtn");
    methodBtn.innerHTML = $(this).text();
});

$('#presetValueList').on('click', 'li', function () {
    console.log("Preset value selected");
    var value = ($(this).text());
    console.log(value);
    document.getElementById("values").value = value;
   // $('#values').val($(this).text());
});

function upload() {
    var selectedProfile = document.getElementById("profile").value;
    var selectedLocation = document.getElementById("location").value;
    var selectedType = document.getElementById("action").value;

    console.log(selectedLocation);
    console.log(selectedProfile);
    console.log(selectedType);

    document.getElementById("profileHidden").value = selectedProfile;
    document.getElementById("locationHidden").value = selectedLocation;
    document.getElementById("typeHidden").value = selectedType;
}

function addInput() {
    var innerHTML = "<div class='row'>" +
                        "<div class='col-lg-2 col-md-2'>" +
                            "<input type='text' class='form-control' name='keys' placeholder='key' autocomplete='off'/>" +
                        "</div>" +
                        "<div class='col-lg-3 col-md-3'>" +
                            "<div class='input-group'>" +
                                "<input type='text' id='values' name='values' placeholder='value' class='form-control'>" +
                                "<div class='input-group-btn'>" +
                                    "<button type='button' class='btn btn-default dropdown-toggle btn-lg' data-toggle='dropdown'>" +
                                        "<span class='caret'></span>" +
                                    "</button>" +
                                    "<ul id='presetValueList' class='dropdown-menu'>" +
                                        "<li><a href='#'>$profile</a></li>" +
                                        "<li><a href='#'>$location</a></li>" +
                                        "<li><a href='#'>$user</a></li>" +
                                        "<li><a href='#'>$timestamp</a></li>" +
                                    "</ul>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +
                    "</div>" +
                    "<div class='clearfix'></div>";
    $('#dynamicInput').append(innerHTML);
};;