var map = L.map('map').setView([-40, -65], 4,3);

	L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
		maxZoom: 20,
		id: 'mapbox.light'
	}).addTo(map);

	// control that shows state info on hover
	var info = L.control();

	info.onAdd = function (map) {
		this._div = L.DomUtil.create('div', 'info');
		this.update();
		return this._div;
	};
	//leyenda que muestra las regiones
	info.update = function (props) {
		this._div.innerHTML = '<h4>Popularidad por Región</h4>' +  (props ?
			'<b>' + props.Region + '</b><br/>' + 'Programa: ' + popular[props.id]  
			: '¡recorre las regiones!');
	};

	info.addTo(map);
	// cantidad de tweets por region según el id de cada region
	var popular= ["Amanda","Tolerancia Cero","Chile Secreto","Morande Con Compañia","Primer Plano","Entre Dos Amores","Kozem","CHV Noticias","Tolerancia Cero","Morande Con Compañia","Tolerancia Cero","asdasd","Morande Con Compañia","Tolerancia Cero","Tolerancia Cero"];

	var colours= ['#a6cee3','#1f78b4','#b2df8a','#33a02c','#fb9a99','#ff0040','#e31a1c','#fdbf6f','#ff7f00','#cab2d6','#bf00ff','#6a3d9a','#ffff99','#ffff00','#b15928']
	// define el color segun la cantidad de tweets
	function getColor(d) {

		return colours[d];
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
		map.fitBounds(e.target.getBounds());
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
	}).addTo(map);

	var legend = L.control({position: 'bottomright'});

	legend.addTo(map);
