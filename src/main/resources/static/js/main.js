window.addEventListener('DOMContentLoaded', () => {
    'use strict';
    slider()
});

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
