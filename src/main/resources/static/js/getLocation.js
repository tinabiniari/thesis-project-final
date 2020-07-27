function showlocation() {
    // One-shot position request.
    document.getElementById("display_loading").style.display="block";
    navigator.geolocation.getCurrentPosition(callback);


}
function loadLocation() {
    navigator.geolocation.getCurrentPosition(call);
}

function callback(position) {
    var t0 = performance.now();

    document.getElementById('lat').value = position.coords.latitude;
    document.getElementById('lon').value = position.coords.longitude;
    var lat = position.coords.latitude;
    var lon = position.coords.longitude;
    console.log(lat,lon);
    var t1 = performance.now();
    document.getElementById("coords-btn").style.display="block";
    if(document.getElementById("coords-btn").style.display == "block"){
        document.getElementById("display_loading").style.display="none";
    }

}

function call(position) {
    document.getElementById('lat').value = position.coords.latitude;
    document.getElementById('lon').value = position.coords.longitude;
    var lat = position.coords.latitude;
    var lon = position.coords.longitude;
    console.log(lat,lon);
    return "/current/location?lat="+lat+"&lon="+lon;
}

