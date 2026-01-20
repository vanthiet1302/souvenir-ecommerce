$(document).ready(function () {

    /* =====================================================
     * GLOBAL VARIABLES
     * ===================================================== */
    const productId = $("#productId").val();
    const contextPath = $("#contextPath").val();

    let currentRating = "";
    let currentSort = "newest";
    let currentPage = 1;
    const pageSize = 5;


    /* =====================================================
     * ADD TO CART (AJAX)
     * ===================================================== */
    $('.btn-add-cart').on('click', function (e) {
        e.preventDefault();

        const form = $(this).closest('form');

        $.ajax({
            url: form.attr('action'),
            method: 'POST',
            data: form.serialize(),
            success: function () {
                showToast('Đã thêm sản phẩm vào giỏ hàng 🛒');
            },
            error: function () {
                showToast('Có lỗi xảy ra, vui lòng thử lại ❌');
            }
        });
    });


    /* =====================================================
     * SCROLL TO REVIEW
     * ===================================================== */
    $('.scroll-review').on('click', function (e) {
        e.preventDefault();

        $('html, body').animate({
            scrollTop: $('.product-reviews').offset().top - 80
        }, 500);
    });


    /* =====================================================
     * TOGGLE DESCRIPTION
     * ===================================================== */
    const desc = $('.description-content');
    const btnToggle = $('#toggleDescription');

    if (desc.height() > 200) {
        desc.addClass('short');
        btnToggle.show();
    } else {
        btnToggle.hide();
    }

    btnToggle.on('click', function () {
        desc.toggleClass('short');
        $(this).text(desc.hasClass('short') ? 'Xem thêm' : 'Thu gọn');
    });


    /* =====================================================
     * LOAD REVIEWS (AJAX)
     * ===================================================== */
    function loadReviews(reset = false) {

        if (reset) {
            currentPage = 1;
            $("#reviewList").html("");
        }

        $.ajax({
            url: contextPath + "/reviews",
            method: "GET",
            data: {
                productId: productId,
                rating: currentRating,
                sort: currentSort,
                page: currentPage,
                size: pageSize
            },
            success: function (html) {

                if (reset) {
                    $("#reviewList").html(html);
                } else {
                    $("#reviewList").append(html);
                }

                // nếu không còn review thì ẩn nút load more
                if ($.trim(html) === "") {
                    $("#loadMoreReview").hide();
                } else {
                    $("#loadMoreReview").show();
                }
            },
            error: function () {
                showToast("Không thể tải đánh giá ❌");
            }
        });
    }

    // load lần đầu
    loadReviews(true);


    /* =====================================================
     * FILTER BY RATING
     * ===================================================== */
    $('.filter-btn').on('click', function () {
        $('.filter-btn').removeClass('active');
        $(this).addClass('active');

        const rating = $(this).data('rating');
        currentRating = (rating === "all") ? "" : rating;

        loadReviews(true);
    });


    /* =====================================================
     * SORT REVIEW
     * ===================================================== */
    $('.review-sort').on('change', function () {
        currentSort = $(this).val();
        loadReviews(true);
    });


    /* =====================================================
     * LOAD MORE REVIEW
     * ===================================================== */
    $('#loadMoreReview').on('click', function () {
        currentPage++;
        loadReviews(false);
    });


    /* =====================================================
     * IMAGE ZOOM (MODAL)
     * ===================================================== */
    $('.btn-zoom').on('click', function () {

        const imgSrc = $('.product-image img').attr('src');

        const modal = $(`
            <div class="image-modal">
                <div class="modal-overlay"></div>
                <div class="modal-content">
                    <img src="${imgSrc}" alt="Zoom image">
                    <button class="modal-close">×</button>
                </div>
            </div>
        `);

        $('body').append(modal);
        setTimeout(() => modal.addClass('show'), 10);

        modal.on('click', '.modal-overlay, .modal-close', function () {
            modal.removeClass('show');
            setTimeout(() => modal.remove(), 300);
        });
    });


    /* =====================================================
     * TOAST MESSAGE
     * ===================================================== */
    function showToast(message) {

        const toast = $(`
            <div class="toast-message">${message}</div>
        `);

        $('body').append(toast);

        setTimeout(() => toast.addClass('show'), 50);
        setTimeout(() => {
            toast.removeClass('show');
            setTimeout(() => toast.remove(), 300);
        }, 2500);
    }

});
