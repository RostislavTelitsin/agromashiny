window.addEventListener('DOMContentLoaded', () => {
    'use strict';
    if (document.querySelectorAll('.header__bottom_slider_item').length != 0) {
        slider()
        validateForm()
    }
    burgerMenu()
});

const validateForm = () => {
    function ValidateEmail(inputText) {
        var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
        if (inputText.value.match(mailformat)) {
            email.style.backgroundColor = '#adf564'
        }
        else {
            email.style.backgroundColor = '#eb3b3b'
        }
    }

    const email = document.querySelector('#email')
    email.addEventListener('input', () => {
        ValidateEmail(email)
    })
}

const burgerMenu = () => {
    const show = document.querySelector('.header__content_menu')
    const burger = document.querySelector('.burger')

    burger.addEventListener('click', () => {
        if (show.style.width === '0px' || show.style.width == '') {
            if (window.screen.availWidth < 576) {
                show.style.width = '100px'
            }
            else {
                show.style.width = '150px'
            }
        }
        else {
            show.style.width = '0'
        }
    })
    window.addEventListener('click', () => {
        if (show.style.width == '150px' || show.style.width == '100px') {
            hideBurger(event)
        }
    })
    function hideBurger(e) {
        if (!e.target.classList.contains('burger_span') && !e.target.classList.contains('header__content_menu_list_link')) {
            show.style.width = '0'
        }
    }

    window.addEventListener('scroll', () => {
        if (show.style.width == '150px' || show.style.width == '100px') {
            show.style.width = '0'
        }
    })

    window.addEventListener('resize', () => {
        if (window.screen.availWidth > 992 && show.style.width == '0px') {
            show.style.width = ''
        }
        if (show.style.width == '150px' || show.style.width == '100px') {
            show.style.width = '0'
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
