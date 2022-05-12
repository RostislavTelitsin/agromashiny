window.addEventListener('DOMContentLoaded', () => {
    'use strict';
    if (document.querySelectorAll('.header__bottom_slider_item').length != 0) {
        slider('.header__bottom_slider_item', '.count', '.header__bottom_slider')
        validateForm()
        form()
    }
    if (document.querySelectorAll('.news__slider_item').length != 0) {
        slider('.news__slider_item', '.news__count', null, '.left', '.right')
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

const slider = (slidesItem, quantity, slidesParent, left, right) => {
    const slides = document.querySelectorAll(slidesItem)
    const count = document.querySelectorAll(quantity)
    console.log(slides)
    console.log(count)
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

    if (!left && !right) {

        slides.forEach(item => {
            item.addEventListener('click', function () {
                slides[slideIndex - 1].classList.remove('display')
                count[slideIndex - 1].classList.remove('count_active')
                plusSlides(1)
                slides[slideIndex - 1].classList.add('display')
                count[slideIndex - 1].classList.add('count_active')
            })
        })
    }
    else {
        document.querySelector(right).addEventListener('click', () => {
            slides[slideIndex - 1].classList.remove('display')
            count[slideIndex - 1].classList.remove('count_active')
            plusSlides(1)
            slides[slideIndex - 1].classList.add('display')
            count[slideIndex - 1].classList.add('count_active')
        })



        document.querySelector(left).addEventListener('click', () => {
            slides[slideIndex - 1].classList.remove('display')
            count[slideIndex - 1].classList.remove('count_active')
            plusSlides(-1)
            slides[slideIndex - 1].classList.add('display')
            count[slideIndex - 1].classList.add('count_active')
        })
    }


    slides[slideIndex - 1].classList.add('display')
    count[slideIndex - 1].classList.add('count_active')

    showSlides(slideIndex);


    if (slidesParent) {
        startInterval()

        document.querySelector(slidesParent).addEventListener('mouseenter', function () {
            clearInterval(paused)
        })

        document.querySelector(slidesParent).addEventListener('mouseleave', function () {
            startInterval()
        })
    }
}

const form = () => {
    let btn = document.querySelector('.about__form')
    btn.addEventListener('submit', function (event) {
        event.preventDefault()
        console.log(event)
        let formData = {
            name: event.target[0].value,
            mail: event.target[1].value,
            text: event.target[2].value,
        };
        console.log(formData)

        let req = fetch('form', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify(formData)
        })
            .then(response => response)
            .then(result => {
                let formTitle = document.querySelector('.about__form_title')
                let message = document.createElement('div')

                if (result.status === 200) {

                    message.innerHTML = 'Ваше Сообщение отправлено'
                    message.style.color = 'green'
                    formTitle.insertAdjacentElement('afterend', message)
                }
                else {
                    message.innerHTML = 'Не удалось отправить Ваше сообщение'
                    message.style.color = 'red'
                    formTitle.insertAdjacentElement('afterend', message)
                }
                setTimeout(() => {
                    message.remove()
                    btn.reset()
                    const email = document.querySelector('#email')
                    email.style.backgroundColor = 'white'
                }, 5000)
            })
    })
}