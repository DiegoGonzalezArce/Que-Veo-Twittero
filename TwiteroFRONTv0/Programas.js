$(".dropdown-menu li a").click(function(){
  var selText = $(this).text();
  $(this).parents('.btn-group').find('.dropdown-toggle').html(selText+' <span class="caret"></span>');
  
  //combobox behavior
  if (selText=="United States") {
      $("#btnUS").removeClass("hide");
      $("#btnCA").addClass("hide");
  }
  else if (selText=="Canada") {
      $("#btnCA").removeClass("hide");
      $("#btnUS").addClass("hide");
  }
  
});


$("#btnSearch").click(function(){
	alert($('.btn-select').text()+", "+$('.btn-select2').text());
});