/**
 * Created by TriPQM on 06/25/2015.
 */
// This example displays an address form, using the autocomplete feature
// of the Google Places API to help users fill in the information.
var autocomplete;


function initialize() {
    // Create the autocomplete object, restricting the search
    // to geographical location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {HTMLInputElement} */(document.getElementById('txtAddress')),
        {types: ['geocode'], componentRestrictions: {country: 'vn'}});

}

function initialize1() {
    // Create the autocomplete object, restricting the search
    // to geographical location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {HTMLInputElement} */(document.getElementById('driverAddress')),
        {types: ['geocode'], componentRestrictions: {country: 'vn'}});

}

function initialize2() {
    // Create the autocomplete object, restricting the search
    // to geographical location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {HTMLInputElement} */(document.getElementById('accidentPlace')),
        {types: ['geocode'], componentRestrictions: {country: 'vn'}});

}

function initialize3() {
    // Create the autocomplete object, restricting the search
    // to geographical location types.
    autocomplete = new google.maps.places.Autocomplete(
        /** @type {HTMLInputElement} */(document.getElementById('observerAddress')),
        {types: ['geocode'], componentRestrictions: {country: 'vn'}});

}

// [START region_geolocation]
// Bias the autocomplete object to the user's geographical location,
// as supplied by the browser's 'navigator.geolocation' object.
function geolocate() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var geolocation = new google.maps.LatLng(
                position.coords.latitude, position.coords.longitude);
            var circle = new google.maps.Circle({
                center: geolocation,
                radius: position.coords.accuracy
            });
            autocomplete.setBounds(circle.getBounds());
        });
    }
}
// [END region_geolocation]
