$(document).ready(function () {

    const productId = $("#productId").val();
    const contextPath = $("#contextPath").val();

    let currentRating = "";
    let currentSort = "newest";
    let currentPage = 1;
    const pageSize = 5;

    /* ================= ADD TO CART ================= */
    $('.btn-add-cart').on('click', function (e) {
        e.preventDefault();

        const form = $(this).closest('form');

        $.ajax({
            url: form.attr('action'),
            method: 'POST',
            data: form.serialize(),
            success: () => showToast('Đã thêm sản phẩm vào giỏ hàng 🛒'),
            error: () => showToast('Có lỗi xảy ra ❌')
        });
    });

    /* ================= SCROLL TO REVIEW ================= */
    $('.scroll-review').on('click', function (e) {
        e.preventDefault();
        $('html, body').animate({
            scrollTop: $('.product-reviews').offset().top - 80
        }, 500);
    });

    /* ================= TOGGLE DESCRIPTION ================= */
    const desc = $('.description-content');
    const btnToggle = $('.btn-toggle-desc');

    if (desc.outerHeight() > 200) {
        desc.addClass('collapsed');
        btnToggle.show();
    } else {
        btnToggle.hide();
    }

    btnToggle.on('click', function () {
        desc.toggleClass('collapsed');
        $(this).text(desc.hasClass('collapsed') ? 'Xem thêm' : 'Thu gọn');
    });

    /* ================= LOAD REVIEWS ================= */
    function loadReviews(reset = false) {

        if (reset) {
            currentPage = 1;
            $("#reviewContainer").html("");
        }

        $.ajax({
            url: contextPath + "/reviews",
            method: "GET",
            data: {
                productId,
                rating: currentRating,
                sort: currentSort,
                page: currentPage,
                size: pageSize
            },
            success: function (html) {
                if (reset) {
                    $("#reviewContainer").html(html);
                } else {
                    $("#reviewContainer").append(html);
                }

                $("#loadMoreReview").toggle($.trim(html) !== "");
            },
            error: () => showToast("Không thể tải đánh giá ❌")
        });
    }

    loadReviews(true);

    /* ================= FILTER ================= */
    $('.filter-btn').on('click', function () {
        $('.filter-btn').removeClass('active');
        $(this).addClass('active');

        const rating = $(this).data('rating');
        currentRating = rating === "all" ? "" : rating;

        loadReviews(true);
    });

    $('.review-sort').on('change', function () {
        currentSort = $(this).val();
        loadReviews(true);
    });

    $('#loadMoreReview').on('click', function () {
        currentPage++;
        loadReviews(false);
    });

    /* ================= IMAGE ZOOM ================= */
    $('.btn-zoom').on('click', function () {

        const imgSrc = $('.product-image img').attr('src');

        const modal = $(`
            <div class="image-modal">
                <div class="modal-overlay"></div>
                <div class="modal-content">
                    <img src="${imgSrc}">
                    <button class="modal-close">×</button>
                </div>
            </div>
        `);

        $('body').append(modal);
        setTimeout(() => modal.addClass('show'), 10);

        modal.on('click', '.modal-overlay, .modal-close', () => {
            modal.removeClass('show');
            setTimeout(() => modal.remove(), 300);
        });
    });

    /* ================= TOAST ================= */
    function showToast(message) {
        const toast = $(`<div class="toast-message">${message}</div>`);
        $('body').append(toast);

        setTimeout(() => toast.addClass('show'), 50);
        setTimeout(() => toast.removeClass('show').remove(), 2600);
    }

});
