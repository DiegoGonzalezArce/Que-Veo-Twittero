angular.module("d3")
    .factory("d3", ['$document', '$q', '$rootScope',
    function($document, $q, $rootScope) {
        var d = $q.defer();
        function onScriptLoad() {
          // Load client in the browser
          $rootScope.$apply(function() { d.resolve(window.d3); });
        }
        // Create a script tag with d3 as the source
        // and call our onScriptLoad callback when it
        // has been loaded
        var scriptTag = $document[0].createElement('script');
        scriptTag.type = 'text/javascript';
        scriptTag.src = 'http://d3js.org/d3.v3.min.js';
        scriptTag.onreadystatechange = function () {
          if (this.readyState == 'complete') onScriptLoad();
        }
        scriptTag.onload = onScriptLoad;

        var s = $document[0].getElementsByTagName('body')[0];
        s.appendChild(scriptTag);

        return {
          d3: function() { return d.promise; }
        };
    }]);

angular.module("d3")
    .factory("d3p", ['$document', '$q', '$rootScope',
    function($document, $q, $rootScope) {
        var d = $q.defer();
        function onScriptLoad() {
          // Load client in the browser
          $rootScope.$apply(function() { d.resolve(window.d3); });
        }
        // Create a script tag with d3 as the source
        // and call our onScriptLoad callback when it
        // has been loaded
        var scriptTag = $document[0].createElement('script');
        scriptTag.type = 'text/javascript';
        scriptTag.src = 'http://d3js.org/d3.v4.min.js';
        scriptTag.onreadystatechange = function () {
          if (this.readyState == 'complete') onScriptLoad();
        }
        scriptTag.onload = onScriptLoad;

        var s = $document[0].getElementsByTagName('body')[0];
        s.appendChild(scriptTag);

        return {
          d3: function() { return d.promise; }
        };
    }]);    

angular.module('mainModule')
	.directive('d3Ranking', ["d3", function(d3){
		 return {
            restrict: 'EA',
            scope:{},
            link: function(scope, element, attrs)
            {
                d3.d3().then(function(d3)
                {
        		var margin = {top: 20, right: 20, bottom: 150, left: 40},
				    width = 600 - margin.left - margin.right,
				    height = 300 - margin.top - margin.bottom;


				// set the ranges
				var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

				var y = d3.scale.linear().range([height, 0]);

				// define the axis
				var xAxis = d3.svg.axis()
				    .scale(x)
				    .orient("bottom")


				var yAxis = d3.svg.axis()
				    .scale(y)
				    .orient("left")
				    .ticks(10);


				// add the SVG element
				var svg = d3.select(element[0]).append("svg")
				    .attr("width", width + margin.left + margin.right)
				    .attr("height", height + margin.top + margin.bottom)
				 	.append("g")
				    .attr("transform", 
				          "translate(" + margin.left + "," + margin.top + ")");


				// load the data
				d3.json("http://localhost:8080/QVTwittero/programas", function(error, data) {

				    data.forEach(function(d) {
				        d.nombre = d.nombre;
				        d.menciones = +d.menciones;
				    });

				    
				  
				  // scale the range of the data	
				  x.domain(data.map(function(d) { return d.nombre; }));
				  y.domain([0, d3.max(data, function(d) { return d.menciones; })]);

				  // add axis
				  svg.append("g")
				      .attr("class", "x axis")
				      .attr("transform", "translate(0," + height + ")")
				      .call(xAxis)
				    .selectAll("text")
				      .style("text-anchor", "end")
				      .attr("dx", "-.8em")
				      .attr("dy", "-.55em")
				      .attr("transform", "rotate(-90)" );

				  svg.append("g")
				      .attr("class", "y axis")
				      .call(yAxis)
				    .append("text")
				      .attr("transform", "rotate(-90)")
				      .attr("y", 5)
				      .attr("dy", ".71em")
				      .style("text-anchor", "end")
				      .text("menciones");


				  // Add bar chart
				  svg.selectAll("bar")
				      .data(data)
				    .enter().append("rect")
				      .attr("class", "bar")
				      .attr("x", function(d) { return x(d.nombre); })
				      .attr("width", x.rangeBand())
				      .attr("y", function(d) { return y(d.menciones); })
				      .attr("height", function(d) { return height - y(d.menciones); });

				});
                }
            )}
        }
	}]);

angular.module('mainModule')
	.directive('d3Categoria', ["d3", function(d3){
		 return {
            restrict: 'EA',
            scope:{},
            link: function(scope, element, attrs)
            {
                d3.d3().then(function(d3)
                {
        		var margin = {top: 20, right: 20, bottom: 150, left: 40},
				    width = 600 - margin.left - margin.right,
				    height = 300 - margin.top - margin.bottom;


				// set the ranges
				var x = d3.scale.ordinal().rangeRoundBands([0, width], .05);

				var y = d3.scale.linear().range([height, 0]);

				// define the axis
				var xAxis = d3.svg.axis()
				    .scale(x)
				    .orient("bottom")


				var yAxis = d3.svg.axis()
				    .scale(y)
				    .orient("left")
				    .ticks(10);


				// add the SVG element
				var svg = d3.select(element[0]).append("svg")
				    .attr("width", width + margin.left + margin.right)
				    .attr("height", height + margin.top + margin.bottom)
				 	.append("g")
				    .attr("transform", 
				          "translate(" + margin.left + "," + margin.top + ")");


				// load the data
				d3.json("http://localhost:8080/QVTwittero/categorias", function(error, data) {

				    data.forEach(function(d) {
				        d.nombre = d.nombre;
				        d.menciones = +d.menciones;
				    });

				    
				  
				  // scale the range of the data	
				  x.domain(data.map(function(d) { return d.nombre; }));
				  y.domain([0, d3.max(data, function(d) { return d.menciones; })]);

				  // add axis
				  svg.append("g")
				      .attr("class", "x axis")
				      .attr("transform", "translate(0," + height + ")")
				      .call(xAxis)
				    .selectAll("text")
				      .style("text-anchor", "end")
				      .attr("dx", "-.8em")
				      .attr("dy", "-.55em")
				      .attr("transform", "rotate(-90)" );

				  svg.append("g")
				      .attr("class", "y axis")
				      .call(yAxis)
				    .append("text")
				      .attr("transform", "rotate(-90)")
				      .attr("y", 5)
				      .attr("dy", ".71em")
				      .style("text-anchor", "end")
				      .text("menciones");


				  // Add bar chart
				  svg.selectAll("bar")
				      .data(data)
				    .enter().append("rect")
				      .attr("class", "bar")
				      .attr("x", function(d) { return x(d.nombre); })
				      .attr("width", x.rangeBand())
				      .attr("y", function(d) { return y(d.menciones); })
				      .attr("height", function(d) { return height - y(d.menciones); });

				});
                }
            )}
        }
	}]);


angular.module('mainModule')
	.directive('d3Programa', ["d3p", function(d3){
		 return {
            restrict: 'EA',
            scope:{
            	progra: '@'
            },
            link: function(scope, element, attrs)
            {



            d3.d3().then(function(d3) {
	        'use strict';
	          
	      

			scope.$watch('progra', function (newVal) {
				   

		
			var urlGr = "http://localhost:8080/QVTwittero/programas/graficoTorta/" + scope.progra;
			console.log(urlGr);
		d3.json(urlGr, function(error, dataset) {     
          dataset.resultados.forEach(function(d){

            d.label = d.label;
            d.count = + d.count;
          });

	        var width = 360;
	        var height = 360;
	        var radius = Math.min(width, height) / 2;
	        var donutWidth = 75;
	        var legendRectSize = 18;
	        var legendSpacing = 5;
	        var color = d3.scaleOrdinal().range(["#1f77b4","#2ca02c","#d62728"]) //Aquí se definen los colores para el gráfico

	        var svg = d3.select('#chart')
	          .append('svg')
	          .attr('width', width)
	          .attr('height', height)
	          .append('g')
	          .attr('transform', 'translate(' + (width / 2) +
	            ',' + (height / 2) + ')');

	        var arc = d3.arc()
	          .innerRadius(radius - donutWidth)
	          .outerRadius(radius);

	        var pie = d3.pie()
	          .value(function(d) { return d.count; })
	          .sort(null);

	        var tooltip = d3.select('#chart')                                
	          .append('div')                                                 
	          .attr('class', 'tooltip');                                     

	        tooltip.append('div')                                            
	          .attr('class', 'label');                                       

	        tooltip.append('div')                                            
	          .attr('class', 'count');                                       

	        tooltip.append('div')                                            
	          .attr('class', 'percent');  

	          var path = svg.selectAll('path')
	            .data(pie(dataset.resultados))
	            .enter()
	            .append('path')
	            .attr('d', arc)
	            .attr('fill', function(d, i) {
	              return color(d.data.label);
	            });
                                       

	          //Creo que al matar el json, muere el evento. INTENTAR ARREGLAR
	          path.on('mouseover', function(d) {                              
	            var total = d3.sum(dataset.resultados.map(function(d) {                 
	              return d.count;                                            
	            }));    
	            //Hasta aca funca            
	            var percent = Math.round(1000 * d.data.count / total) / 10;
	              
	            tooltip.select('.label').html(d.data.label);                 
	            tooltip.select('.count').html('tweets: '+d.data.count);                 
	            tooltip.select('.percent').html(percent + '%');              
	            tooltip.style('display', 'block');  

                         
	          });      
	          console.log("asd");                                                    
	          path.on('mouseout', function() {                               
	            tooltip.style('display', 'none');                            
	          });                                                            

	         
	          path.on('mousemove', function(d) {                             // esta funcion hace que el cuadro de texto se mueva en conjunto con el mouse sobre el grafico
	            tooltip.style('top', (d3.event.layerY + 10) + 'px')         
	              .style('left', (d3.event.layerX + 10) + 'px');             
	          });                                                            
	          

	          var legend = svg.selectAll('.legend')                         // deja la leyenda en el medio
	            .data(color.domain())
	            .enter()
	            .append('g')
	            .attr('class', 'legend')
	            .attr('transform', function(d, i) {
	              var height = legendRectSize + legendSpacing;
	              var offset =  height * color.domain().length / 2;
	              var horz = -2 * legendRectSize;
	              var vert = i * height - offset;
	              return 'translate(' + horz + ',' + vert + ')';
	            });

	          legend.append('rect')
	            .attr('width', legendRectSize)
	            .attr('height', legendRectSize)
	            .style('fill', color)
	            .style('stroke', color);

	          legend.append('text')
	            .attr('x', legendRectSize + legendSpacing)
	            .attr('y', legendRectSize - legendSpacing)
	            .text(function(d) { return d; });
	           

	        
	        	})			//JSON
			});				//watch
	      })(window.d3);	//Then
		}					//link
	}						//Return
}]);						//Fin	



angular.module('mainModule')
	.directive('d3Grafo', ["d3p", function(d3){
		 return {
            restrict: 'EA',
            scope:{},
            link: function(scope, element, attrs)
            {
            d3.d3().then(function(d3) {

            	var width = 940;
            	var height = 600;

				var svg = d3.select(element[0])
							.append("svg")
							.attr("width", width)
							.attr("height", height);

				var color = d3.scaleOrdinal(["#ff8c00","#483d8b"]);//colores para nodos programa(1), twiteros(2) 
				var colorline = d3.scaleOrdinal(["#006400","#800000","#191970"]);//colores para comentarios positivos(1) negativos(2) neutrales(3)(d.value)
				var simulation = d3.forceSimulation()
				    .force("link", d3.forceLink().id(function(d) { return d.id; }))
				    .force("charge", d3.forceManyBody())
				    .force("center", d3.forceCenter(width / 2, height / 2));

				d3.json("http://localhost:8080/QVTwittero/programas/neo4jg", function(error, graph) {
				  if (error) throw error;

				  console.log(graph);

				  var g = svg.append("g")
				  			.attr("class", "everything");

				  var link = g.append("g")
				      .attr("class", "links")
				    .selectAll("line")
				    .data(graph.links)
				    .enter().append("line")
				      .attr("stroke", verAnalisis);
				      

				  var node = g.append("g")
				      .attr("class", "nodes")
				    .selectAll("circle")
				    .data(graph.nodos)
				    .enter().append("circle").attr("stroke-width", function(d){ return (Math.pow(d.group,2));}) // Cambia el tamaño segun el grupo (los más grandes son los programas)
				      .attr("r", 5)
				      .attr("fill", function(d) { return color(d.group);})
				      .call(d3.drag()
				          .on("start", dragstarted)
				          .on("drag", dragged)
				          .on("end", dragended));

				  node.append("title")
				      .text(function(d) { return d.id; });

				  simulation
				      .nodes(graph.nodos)
				      .on("tick", ticked);

				  simulation.force("link")
				      .links(graph.links);

				  function ticked() {
				    link
				        .attr("x1", function(d) { return d.source.x; })
				        .attr("y1", function(d) { return d.source.y; })
				        .attr("x2", function(d) { return d.target.x; })
				        .attr("y2", function(d) { return d.target.y; });

				    node
				        .attr("cx", function(d) { return d.x; })
				        .attr("cy", function(d) { return d.y; });
				  }

			  		var zoom_handler = d3.zoom()
										    .on("zoom", zoom_actions);
										 
					zoom_handler(svg);

					function zoom_actions(){
						    g.attr("transform", d3.event.transform)
						}				

				});

				function dragstarted(d) {
				  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
				  d.fx = d.x;
				  d.fy = d.y;
				}

				function dragged(d) {
				  d.fx = d3.event.x;
				  d.fy = d3.event.y;
				}

				function dragended(d) {
				  if (!d3.event.active) simulation.alphaTarget(0);
				  d.fx = null;
				  d.fy = null;
				}

				function verAnalisis(d){
				  if(d.value > 0){
				    return colorline(1);
				  }
				  else if(d.value < 0){
				    return colorline(2);
				  }
				  else
				    return colorline(3);
				}
			})}}}]);	