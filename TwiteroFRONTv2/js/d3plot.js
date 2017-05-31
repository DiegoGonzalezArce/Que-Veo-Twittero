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

angular.module('mainModule')
	.directive('d3Ranking', ["d3", function(d3){
		 return {
            restrict: 'EA',
            scope:{},
            link: function(scope, element, attrs)
            {
                d3.d3().then(function(d3)
                {
        		var margin = {top: 20, right: 20, bottom: 70, left: 40},
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
