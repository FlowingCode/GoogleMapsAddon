/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2022 Flowing Code
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
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
