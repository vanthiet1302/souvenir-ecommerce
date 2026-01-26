
/* ================= BANNER SLIDESHOW ================= */
document.addEventListener("DOMContentLoaded", () => {
    const container = document.getElementById("headerSlideshow");
    if (!container) return;

    const slides = container.querySelectorAll(".slide");
    const prevBtn = container.querySelector(".prev");
    const nextBtn = container.querySelector(".next");
    const dots = container.querySelectorAll(".dot");

    if (slides.length <= 1) {
        slides.forEach(s => s.style.display = "block");
        prevBtn && (prevBtn.style.display = "none");
        nextBtn && (nextBtn.style.display = "none");
        return;
    }

    let currentIndex = 0;
    let autoTimer = null;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.style.display = i === index ? "block" : "none";
        });

        dots.forEach((dot, i) => {
            dot.classList.toggle("active", i === index);
        });
    }

    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        showSlide(currentIndex);
    }

    function prevSlide() {
        currentIndex = (currentIndex - 1 + slides.length) % slides.length;
        showSlide(currentIndex);
    }

    function startAuto() {
        stopAuto();
        autoTimer = setInterval(nextSlide, 4000);
    }

    function stopAuto() {
        autoTimer && clearInterval(autoTimer);
    }

    nextBtn?.addEventListener("click", () => {
        nextSlide();
        startAuto();
    });

    prevBtn?.addEventListener("click", () => {
        prevSlide();
        startAuto();
    });

    dots.forEach((dot, index) => {
        dot.addEventListener("click", () => {
            currentIndex = index;
            showSlide(currentIndex);
            startAuto();
        });
    });

    container.addEventListener("mouseenter", stopAuto);
    container.addEventListener("mouseleave", startAuto);

    showSlide(currentIndex);
    startAuto();
});

/* ================= EXTENSION CATEGORY SLIDER ================= */
document.addEventListener("DOMContentLoaded", () => {
    const slider = document.getElementById("extSlider");
    const prevBtn = document.getElementById("extPrev");
    const nextBtn = document.getElementById("extNext");

    if (!slider || !prevBtn || !nextBtn) return;

    const cards = slider.querySelectorAll(".product-card");
    if (cards.length <= 1) return;

    let index = 0;
    let autoSlide;

    function getStep() {
        const cardWidth = cards[0].offsetWidth;
        const gap = parseInt(getComputedStyle(slider).gap) || 16;
        return cardWidth + gap;
    }

    function update() {
        slider.style.transform = `translateX(-${index * getStep()}px)`;
    }

    function next() {
        index = (index + 1) % cards.length;
        update();
    }

    function prev() {
        index = (index - 1 + cards.length) % cards.length;
        update();
    }

    function startAuto() {
        stopAuto();
        autoSlide = setInterval(next, 3500);
    }

    function stopAuto() {
        autoSlide && clearInterval(autoSlide);
    }

    nextBtn.addEventListener("click", () => {
        next();
        startAuto();
    });

    prevBtn.addEventListener("click", () => {
        prev();
        startAuto();
    });

    slider.parentElement.addEventListener("mouseenter", stopAuto);
    slider.parentElement.addEventListener("mouseleave", startAuto);

    window.addEventListener("resize", update);

    update();
    startAuto();
});
