window.geolocation = {

	get: function(map) {
    // if browser supports geolocation, return current location
    if(navigator.geolocation) {

      navigator.geolocation.getCurrentPosition(
      position => {
        map.$server.handleGeolocation(position.coords.latitude, position.coords.longitude);
      },
      () => {
        map.$server.handleGeolocationError(true);
      });      
    
    } else { // browser doesn't support geolocation
      map.$server.handleGeolocationError(false);
    }
  }

}
