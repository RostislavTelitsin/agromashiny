window.addEventListener('DOMContentLoaded', () => {
    'use strict';
    if (document.querySelectorAll('.header__bottom_slider_item').length != 0) {
        slider()
    }
    burgerMenu()
});

const burgerMenu = () => {
    const show = document.querySelector('.header__content_menu')
    const burger = document.querySelector('.burger')

    burger.addEventListener('click', () => {
        if (show.style.display == "none" || show.style.display == "") {
            show.style.display = 'block'
        }
        else {
            show.style.display = 'none'
        }
    })
    window.addEventListener('click', () => {
        if (show.style.display == "block") {
            hideBurger(event)
        }
    })
    function hideBurger(e) {
        if (!e.target.classList.contains('burger_span') && !e.target.classList.contains('header__content_menu_list_link')) {
            show.style.display = 'none'
        }
    }

    window.addEventListener('resize', () => {
        if (window.screen.availWidth > 992 && show.style.display == 'none') {
            show.style.display = 'block'
        }
        else if (window.screen.availWidth < 992 && show.style.display == 'block') {
            show.style.display = 'none'
        }
    })
}


const slider = () => {
    const slides = document.querySelectorAll('.header__bottom_slider_item')
    const count = document.querySelectorAll('.count')
    let slideIndex = 1,
        paused = false

    function showSlides(n) {
        if (n > slides.length) {
            slideIndex = 1;
        }
        if (n < 1) {
            slideIndex = slides.length;
        }
    }

    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    function startInterval() {
        paused = setInterval(function () {
            slides[slideIndex - 1].classList.remove('display')
            count[slideIndex - 1].classList.remove('count_active')
            plusSlides(1)
            slides[slideIndex - 1].classList.add('display')
            count[slideIndex - 1].classList.add('count_active')
        }, 5000)
    }

    slides.forEach(item => {
        item.addEventListener('click', function () {
            slides[slideIndex - 1].classList.remove('display')
            count[slideIndex - 1].classList.remove('count_active')
            plusSlides(1)
            slides[slideIndex - 1].classList.add('display')
            count[slideIndex - 1].classList.add('count_active')
        })
    })



    slides[slideIndex - 1].classList.add('display')
    count[slideIndex - 1].classList.add('count_active')

    showSlides(slideIndex);

    startInterval()

    document.querySelector('.header__bottom_slider').addEventListener('mouseenter', function () {
        clearInterval(paused)
    })

    document.querySelector('.header__bottom_slider').addEventListener('mouseleave', function () {
        startInterval()
    })
}
