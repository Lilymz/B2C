$(function(){
	'use-strict';

	// portfolio
	$(document).on("click",".portfolio-filter li",function() {
        $('.portfolio-filter li').removeClass('active');
        $(this).addClass('active');
    });

    $('.portfolio-item').filterizr();  

});