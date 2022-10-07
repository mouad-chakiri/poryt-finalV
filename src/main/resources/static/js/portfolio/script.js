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


//////////////////////////////////

//////////////////////////////////
let section = document.querySelectorAll("section");
let menu = document.querySelectorAll(".header .menu a");

window.onscroll = () => {
  section.forEach((i) => {
    let top = window.scrollY;
    let offset = i.offsetTop - 150;
    let height = i.offsetHeight;
    let id = i.getAttribute("id");

    if (top >= offset && top < offset + height) {
      menu.forEach((link) => {
        link.classList.remove("active");
        document
          .querySelector(".header .menu a[href*=" + id + "]")
          .classList.add("active");
      });
    }
  });
};

function reveal() {
  var reveals = document.querySelectorAll(".reveal , .revealXright , .revealXleft");
  for (var i = 0; i < reveals.length; i++) {
    var windowHeight = window.innerHeight;
    var elementTop = reveals[i].getBoundingClientRect().top;
    var elementVisible = 150;
    if (elementTop < windowHeight - elementVisible) {
      reveals[i].classList.add("active");
    } else {
      reveals[i].classList.remove("active");
    }
  }
}

window.addEventListener("scroll", reveal);

// To check the scroll position on page load
reveal();




