function loadCurrentWeather(searchBy,param1,param2) {

    var city = document.getElementById('city').value;
    var country = document.getElementById('country').value;
    var lat = document.getElementById('lat').value;
    var lon = document.getElementById('lon').value;
    var predictionType = document.getElementById('predictionType').value;
    console.log(city,country,lat,lon,predictionType,searchBy,param1,param2);


    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.status == 200) {
            document.getElementById("errorField").style.display="none";
            document.getElementById("weatherCard").innerHTML = this.responseText;
        }
        else if(this.status == 0){
            document.getElementById("errorField").style.display="none";
        }
        else{
            document.getElementById("errorField").style.display="block";
            document.getElementById("errorField").style.color="#680e0e";
            document.getElementById("errorField").style.fontSize="16px";
            document.getElementById("errorField").style.background="#c1172f36";
            document.getElementById("errorField").classList.add("my-4","p-2","rounded");
            document.getElementById("errorField").innerHTML = "Sorry, the city or country you are searching for,is not found";
        }
        console.log(this.status);
    };


    if(searchBy == 'city'){
        if((city) || (country)){
            xhttp.open("GET", "/" + predictionType + "/" + searchBy + "?" + param1 + "=" + city + "&" + param2 + "=" + country, true);
            console.log("/" + predictionType + "/" + searchBy + "?" + param1 + "=" + city + "&" + param2 + "=" + country);
        }else{
            document.getElementById("errorField").style.display="block";
            document.getElementById("errorField").innerHTML = "Please fill in both fields";
        }
    }
    if(searchBy == 'location'){
        document.getElementById("errorField").style.display="none";
        xhttp.open("GET", "/"+predictionType+"/"+searchBy+"?"+param1+"="+lat+"&"+param2+"="+lon, true);
    }
    xhttp.send();




}

function addToSavedCities(lat,lon) {

    var saveCityRequest = new XMLHttpRequest();
    saveCityRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("cityStatus").innerHTML = this.responseText;
            document.getElementById("cityStatus").classList.add("succesMessage","mt-4","text-dark");
        }
    };

    saveCityRequest.open("POST", "/current/save/" + lat + "/" + lon + "", true);
    saveCityRequest.send();
}

function deleteCity(cityId) {


    console.log(cityId);

    var saveCityRequest = new XMLHttpRequest();
    saveCityRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("successDelete").innerHTML = this.responseText;
        }
    };

    saveCityRequest.open("GET", "/deleteCity/"+cityId, true);
    saveCityRequest.send();
}

function forecastWeatherFunction(){
    var lat = document.getElementById("lat").value;

    var lon = document.getElementById("lon").value;

    var forecastWeatherRequest = new XMLHttpRequest();
    forecastWeatherRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("modalBox").innerHTML = this.responseText;
        }
    };
    forecastWeatherRequest.open("GET", "/forecast/location/?lat=" + lat + "&lon=" + lon + "", true);
    forecastWeatherRequest.send();
}

function historyWeatherFunction() {


    var lat = document.getElementById("lat").value;

    var lon = document.getElementById("lon").value;

    var historyWeatherRequest = new XMLHttpRequest();
    historyWeatherRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("modalBox").innerHTML = this.responseText;
        }
    };
    historyWeatherRequest.open("GET", "/historyCard/location?lat=" + lat + "&lon=" + lon + "", true);
    historyWeatherRequest.send();
}

function airQualityFunction() {
    var lat = document.getElementById("lat").value;

    var lon = document.getElementById("lon").value;

    var airQualityRequest = new XMLHttpRequest();
    airQualityRequest.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("modalBox").innerHTML = this.responseText;
        }
    };
    airQualityRequest.open("GET", "/air/location?lat=" + lat + "&lon=" + lon + "", true);
    airQualityRequest.send();
}

function edit() {
    var elements = document.getElementsByClassName("delete-city-button");
    var background = document.getElementById("edit-button").style.backgroundColor;
    console.log(background);
    if (background == "") {
        document.getElementById("edit-button").style.backgroundColor = "rgb(52,38,110)";
        document.getElementById("edit-button").classList.add("text-light");
    } else {
        document.getElementById("edit-button").style.backgroundColor = "";
        document.getElementById("edit-button").classList.remove("text-light");
    }
    console.log(elements);

    for (let element of elements) {
        if (element.classList.contains("d-none")) {
            element.classList.remove("d-none");
            element.classList.add("d-block");
            document.getElementById("edit-button").value = "Done!";
        } else {
            element.classList.remove("d-block");
            element.classList.add("d-none");
            document.getElementById("edit-button").value = "Edit";
        }
    }
}

