var mappr = L.map('map').setView([-40, -65], 4,3);

	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 20,
		id: 'mapbox.light'
	}).addTo(mappr);

	// control that shows state info on hover
	var info = L.control();

	info.onAdd = function (mappr) {
		this._div = L.DomUtil.create('div', 'info');
		this.update();
		return this._div;
	};
	//leyenda que muestra las regiones
	info.update = function (props) {
		this._div.innerHTML = '<h4>Tweets por Región</h4>' +  (props ?
			'<b>' + props.Region + '</b><br />' + tweets[props.id] + ' tweets'
			: '¡recorre las regiones!');
	};

	info.addTo(mappr);
	// cantidad de tweets por region según el id de cada region
	var tweets= [23,45,32,12,65,762,90,533,1103,344,32,45,50,51,23];

	var colours= ['#ffffd9','#edf8b1','#c7e9b4','#7fcdbb','#41b6c4','#1d91c0','#225ea8','#0c2c84'];
	// define el color segun la cantidad de tweets
	function getColor(d) {

		return tweets[d] > 1000 ? '#0c2c84' :
				tweets[d] > 500  ? '#225ea8' :
				tweets[d] > 200  ? '#1d91c0' :
				tweets[d] > 100  ? '#41b6c4' :
				tweets[d] > 50   ? '#7fcdbb' :
				tweets[d] > 20   ? '#c7e9b4' :
				tweets[d] > 10   ? '#edf8b1' :
							'#ffffd9';
	}

	function style(feature) {
		return {
			weight: 2,
			opacity: 1,
			color: 'white',
			dashArray: '3',
			fillOpacity: 0.7,
			fillColor: getColor(feature.properties.id)
		};
	} 
	
	function highlightFeature(e) {
		var layer = e.target;

		layer.setStyle({
			weight: 5,
			color: '#666',
			dashArray: '',
			fillOpacity: 0.2
		});

		if (!L.Browser.ie && !L.Browser.opera && !L.Browser.edge) {
			layer.bringToFront();
		}

		info.update(layer.feature.properties);
	}

	var geojson;

	function resetHighlight(e) {
		geojson.resetStyle(e.target);
		info.update();
	}

	function zoomToFeature(e) {
		mappr.fitBounds(e.target.getBounds());
	}

	function onEachFeature(feature, layer) {
		layer.on({
			mouseover: highlightFeature,
			mouseout: resetHighlight,
			click: zoomToFeature
		});
	}

	geojson = L.geoJson(chileRegions, {
		style: style,
		onEachFeature: onEachFeature
	}).addTo(mappr);

	var legend = L.control({position: 'bottomright'});
	// leyenda con los colores del mappra con sus respectivos valores
	legend.onAdd = function (mappr) {

		var div = L.DomUtil.create('div', 'info legend'),
			grades = [0, 10, 20, 50, 100, 200, 500, 1000],
			labels = [],
			from, to;

		for (var i = 0; i < grades.length; i++) {
			from = grades[i];
			to = grades[i + 1];

			labels.push(
				'<i style="background:' + colours[i] + '"></i> ' +
				from + (to ? '&ndash;' + to : '+'));
		}

		div.innerHTML = labels.join('<br>');
		return div;
	};

	legend.addTo(mappr);