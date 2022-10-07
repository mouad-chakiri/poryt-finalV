document.addEventListener('DOMContentLoaded', function() {
	new Swiper('.swiper-container', {
		loop: true,
		slidesPerView: 1,
		spaceBetween: 32,
		autoplay: {
			delay: 8000,
		},
		breakpoints: {
			640: {
				centeredSlides: true,
				slidesPerView: 1.25,
			},
			1024: {
				centeredSlides: false,
				slidesPerView: 1.5,
			},
		},
		navigation: {
			nextEl: '.next-button',
			prevEl: '.prev-button',
		},
	})
})





$(document).ready(function() {
	$('.header-btn').click(function() {
		$('.header .menu').toggleClass("active");
	})
});

$(document).ready(function() {
	$('.header .menu a').click(function() {
		$('.header .menu').toggleClass("active");
	})
});
