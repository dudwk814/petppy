(function($) {

	"use strict";


	$(window).stellar({
    responsive: true,
    parallaxBackgrounds: true,
    parallaxElements: true,
    horizontalScrolling: false,
    hideDistantElements: false,
    scrollProperty: 'scroll'
  });


	var fullHeight = function() {

		$('.js-fullheight').css('height', $(window).height());
		$(window).resize(function(){
			$('.js-fullheight').css('height', $(window).height());
		});

	};
	fullHeight();

	// loader
	var loader = function() {
		setTimeout(function() { 
			if($('#ftco-loader').length > 0) {
				$('#ftco-loader').removeClass('show');
			}
		}, 1);
	};
	loader();

  var carousel = function() {
		$('.carousel-testimony').owlCarousel({
			center: true,
			loop: true,
			items:1,
			margin: 30,
			stagePadding: 0,
			nav: false,
			navText: ['<span class="ion-ios-arrow-back">', '<span class="ion-ios-arrow-forward">'],
			responsive:{
				0:{
					items: 1
				},
				600:{
					items: 2
				},
				1000:{
					items: 3
				}
			}
		});

	};
	carousel();


	/*$('nav .dropdown').hover(function(){
		var $this = $(this);
		// 	 timer;
		// clearTimeout(timer);
		$this.addClass('show');
		$this.find('> a').attr('aria-expanded', true);
		// $this.find('.dropdown-menu').addClass('animated-fast fadeInUp show');
		$this.find('.dropdown-menu').addClass('show');
	}, function(){
		var $this = $(this);
			// timer;
		// timer = setTimeout(function(){
			$this.removeClass('show');
			$this.find('> a').attr('aria-expanded', false);
			// $this.find('.dropdown-menu').removeClass('animated-fast fadeInUp show');
			$this.find('.dropdown-menu').removeClass('show');
		// }, 100);
	});*/


	$('#dropdown04').on('show.bs.dropdown', function () {
	  console.log('show');
	});

	// magnific popup
	$('.image-popup').magnificPopup({
    type: 'image',
    closeOnContentClick: true,
    closeBtnInside: false,
    fixedContentPos: true,
    mainClass: 'mfp-no-margins mfp-with-zoom', // class to remove default margin from left and right side
     gallery: {
      enabled: true,
      navigateByImgClick: true,
      preload: [0,1] // Will preload 0 - before current, and 1 after the current image
    },
    image: {
      verticalFit: true
    },
    zoom: {
      enabled: true,
      duration: 300 // don't foget to change the duration also in CSS
    }
  });

  $('.popup-youtube, .popup-vimeo, .popup-gmaps').magnificPopup({
    disableOn: 700,
    type: 'iframe',
    mainClass: 'mfp-fade',
    removalDelay: 160,
    preloader: false,

    fixedContentPos: false
  });


  var counter = function() {
		
		$('#section-counter').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('ftco-animated') ) {

				var comma_separator_number_step = $.animateNumber.numberStepFactories.separator(',')
				$('.number').each(function(){
					var $this = $(this),
						num = $this.data('number');
						console.log(num);
					$this.animateNumber(
					  {
					    number: num,
					    numberStep: comma_separator_number_step
					  }, 7000
					);
				});
				
			}

		} , { offset: '95%' } );

	}
	counter();

	var contentWayPoint = function() {
		var i = 0;
		$('.ftco-animate').waypoint( function( direction ) {

			if( direction === 'down' && !$(this.element).hasClass('ftco-animated') ) {
				
				i++;

				$(this.element).addClass('item-animate');
				setTimeout(function(){

					$('body .ftco-animate.item-animate').each(function(k){
						var el = $(this);
						setTimeout( function () {
							var effect = el.data('animate-effect');
							if ( effect === 'fadeIn') {
								el.addClass('fadeIn ftco-animated');
							} else if ( effect === 'fadeInLeft') {
								el.addClass('fadeInLeft ftco-animated');
							} else if ( effect === 'fadeInRight') {
								el.addClass('fadeInRight ftco-animated');
							} else {
								el.addClass('fadeInUp ftco-animated');
							}
							el.removeClass('item-animate');
						},  k * 50, 'easeInOutExpo' );
					});
					
				}, 100);
				
			}

		} , { offset: '95%' } );
	};
	contentWayPoint();

	$('.appointment_date').datepicker({
		'format': 'yyyy-mm-dd',
		'autoclose': true,
		'startDate' : '0d',
		'title' : '예약일'
	}).on("changeDate", function(e) {
		let date = $(this).val();

		let servicesType = $('#services-type').attr('data-type');

		let toDay = new Date();


		$.ajax({
			url: '/reserve/find/' + date,
			type: 'get',
			data: {
				"servicesType" : servicesType
			},
			success: function (result) {

				if (result.length > 0) {	// 선택한 날짜의 예약이 있다면 예약된 시간 .reserveStartTime option disabled
					$.each(result, function (index, timeNumber) {


						$('.reserveStartTime option').each(function(){

							$(this).removeAttr("disabled");
							$(this).css('color', '');

							if ($(this).data('time') == timeNumber) {
								$(this).attr("disabled","disabled");
								$(this).css('color', 'red');
							}

						});

					});
				} else {
					$('.reserveStartTime option').each(function() {		// 예약된 시간이 없다면 .reserveStartTime option disabled 해제
						$(this).removeAttr("disabled");
						$(this).css('color', '');
					});
				}

				$('.reserveStartTime option').each(function() {	// 선택한 날짜가 오늘이고 현재 시간이 option의 시간 값보다 크다면 .reserveStartTime option disabled


					let dateToHour = $(this).val().substring(0, 2);

					if (date === dateFormat(toDay)) {

						if (toDay.getHours() >= dateToHour) {

							$(this).attr("disabled","disabled");
							$(this).css('color', 'red');
						}
					}

				});



			}
		})
	});

	function dateFormat(date) {
		let month = date.getMonth() + 1;
		let day = date.getDate();


		month = month >= 10 ? month : '0' + month;
		day = day >= 10 ? day : '0' + day;

		return date.getFullYear() + '-' + month + '-' + day;
	}


	/*$('.appointment_time').timepicker({
		'minTime': '09:00am', // 조회하고자 할 시작 시간 ( 09시 부터 선택 가능하다. )
		'maxTime': '20:00pm', // 조회하고자 할 종료 시간 ( 20시 까지 선택 가능하다. )
		'timeFormat': 'H:i',
		'step': 120 // 30분 단위로 지정. ( 10을 넣으면 10분 단위 )

	});*/

})(jQuery);

