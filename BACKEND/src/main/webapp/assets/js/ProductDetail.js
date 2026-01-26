$(document).ready(function () {

    /* =====================================================
       GLOBAL VARS
    ===================================================== */
    const productId = $('#productId').val();
    const contextPath = $('#contextPath').val();

    let currentPage = 1;
    let currentRating = '';
    let currentSort = 'newest';
    let isLoading = false;

    /* =====================================================
       IMAGE ZOOM MODAL
    ===================================================== */
    $('.btn-zoom').on('click', function () {
        const imgSrc = $('.product-gallery img').attr('src');
        if (!imgSrc) return;

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

        modal.on('click', '.modal-overlay, .modal-close', function () {
            modal.removeClass('show');
            setTimeout(() => modal.remove(), 300);
        });
    });

    /* =====================================================
       QUANTITY CONTROL
    ===================================================== */
    $('.qty-btn.plus').on('click', function () {
        const input = $(this).closest('.quantity-control').find('.qty-input');
        let val = parseInt(input.val()) || 1;
        const max = parseInt(input.attr('max')) || 999;

        if (val < max) {
            input.val(val + 1);
        }
    });

    $('.qty-btn.minus').on('click', function () {
        const input = $(this).closest('.quantity-control').find('.qty-input');
        let val = parseInt(input.val()) || 1;

        if (val > 1) {
            input.val(val - 1);
        }
    });

    /* =====================================================
       ADD TO CART (AJAX)
    ===================================================== */
    $('.add-cart').on('click', function (e) {
        e.preventDefault();

        const form = $(this).closest('.buy-form');

        $.ajax({
            url: form.attr('action'),
            method: 'POST',
            data: form.serialize(),
            success: function () {
                showToast('Đã thêm vào giỏ hàng 🛒');
            },
            error: function () {
                showToast('Có lỗi xảy ra ❌');
            }
        });
    });


    function loadReviews(reset = false) {
        if (isLoading) return;
        isLoading = true;

        if (reset) {
            currentPage = 1;
            $('#reviewContainer').empty();
        }

        $.ajax({
            url: contextPath + '/reviews',
            method: 'GET',
            data: {
                productId: productId,
                rating: currentRating,
                sort: currentSort,
                page: currentPage,
                size: 5
            },
            success: function (html) {
                if ($.trim(html) === '') {
                    $('#loadMoreReview').hide();
                } else {
                    $('#reviewContainer').append(html);
                    $('#loadMoreReview').show();
                }
            },
            complete: function () {
                isLoading = false;
            },
            error: function () {
                showToast('Không tải được đánh giá ❌');
                isLoading = false;
            }
        });
    }


    $('.filter-btn').on('click', function () {
        $('.filter-btn').removeClass('active');
        $(this).addClass('active');

        currentRating = $(this).data('rating') || '';
        loadReviews(true);
    });

    $('.sort-select').on('change', function () {
        currentSort = $(this).val();
        loadReviews(true);
    });

    $('#loadMoreReview').on('click', function () {
        currentPage++;
        loadReviews(false);
    });

    if ($('#reviewContainer').length) {
        loadReviews(true);
    }

});