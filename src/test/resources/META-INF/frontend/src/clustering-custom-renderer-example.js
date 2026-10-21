/*-
 * #%L
 * Google Maps Addon
 * %%
 * Copyright (C) 2020 - 2024 Flowing Code
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
const markerscolors = new Map([
  ["green", "#00e64d"],
  ["yellow", "#fdf569"],
  ["purple", "#8e67fd"],
  ["blue", "#6991fd"]
]);

window.customRenderer = {

	/*
	 * Function that contains the custom renderer implementation.
	 * The function name needs to be "render".
	 */
	render: function(cluster) {

		// get markers count within cluster
		const count = cluster.count;

		// get color to applied to cluster 
		const color = this._getClusterColor(cluster);

		// create svg url with fill color
		const svg = window.btoa(`
	    <svg fill="${color}" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 240 240">
    		<circle cx="120" cy="120" opacity=".9" r="70" />    
  		</svg>`);

		// get cluster position
		const position = cluster.position;

		// create marker for cluster 
		return new google.maps.Marker({
			position,
			// set icon using defined svg and set size
			icon: {
				url: `data:image/svg+xml;base64,${svg}`,
				scaledSize: new google.maps.Size(75, 75),
			},
			// set text, color, and font-size for cluster label
			label: {
				text: String(count),
				color: "rgba(255,255,255,0.9)",
				fontSize: "14px",
			},
			// adjust zIndex to be above other markers
			zIndex: 1000 + count,
		});
	},

	/*
	 * Returns the color to be applied to thecluster. 
	 * The color is derived from the color of the markers that are part of the cluster.
	 * The color with more occurrences within the cluster will be the one to be applied.
	 */
	_getClusterColor(cluster) {
		let markersIconsColors = [];
		cluster.markers.forEach(m => {
			const markerIcon = m.__data.icon;
			const startIdx = markerIcon.lastIndexOf('/');
			const endIdx = markerIcon.indexOf('.png');
			const markerColor = markerIcon.substring(startIdx + 1, endIdx);
			markersIconsColors.push(markerColor);
		});

		const maxColor =  markersIconsColors.reduce((previous, current, _, arr) => {
			if (arr.filter((item) => item === previous).length >
				arr.filter((item) => item === current).length) {
				return previous;
			} else {
				return current;
			}
		});
		
		return markerscolors.get(maxColor);
	}
}
