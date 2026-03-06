$(document).ready(function () {
    /* =================== GLOBAL VARIABLES ================================ */
    const productId = $('#productId').val();
    const contextPath = $('#contextPath').val();

    let currentPage = 1;
    let currentRating = '';
    let currentSort = 'newest';
    let isLoading = false;

    /* ==================== IMAGE ZOOM MODAL ========================== */
    $(document).on('click', '.btn-zoom', function () {
        const imgSrc = $('.product-gallery img').attr('src');
        if (!imgSrc) return;

        $('#zoomImage').attr('src', imgSrc);
        $('#imageModal').addClass('show');
    });

    $(document).on(
        'click',
        '#imageModal .modal-overlay, #imageModal .modal-close',
        function () {
            $('#imageModal').removeClass('show');
            $('#zoomImage').attr('src', '');
        }
    );
    /* ===== QUANTITY PLUS / MINUS – FINAL ===== */
    $(document).on('click', '.qty-btn', function (e) {
        e.preventDefault();
        e.stopPropagation();

        const $control = $(this).closest('.quantity-control');
        const $input = $control.find('.qty-input');

        let value = parseInt($input.val(), 10) || 1;
        const min = parseInt($input.attr('min'), 10) || 1;
        const max = parseInt($input.attr('max'), 10) || 999;

        if ($(this).hasClass('plus') && value < max) value++;
        if ($(this).hasClass('minus') && value > min) value--;

        $input.val(value);
    });

    /* ====================== ESC KEY – CLOSE MODALS ================================ */
    $(document).on('keydown', function (e) {
        if (e.key === 'Escape') {

            if ($('#imageModal').hasClass('show')) {
                $('#imageModal').removeClass('show');
                $('#zoomImage').attr('src', '');
            }

            if ($('#reviewModal').hasClass('show')) {
                $('#reviewModal').removeClass('show');
            }
        }
    });

    /* ================== REVIEW MODAL (OPEN / CLOSE) ==================================== */
    $(document).on('click', '.review-action-btn', function () {
        $('#reviewModal').addClass('show');
    });

    $(document).on(
        'click',
        '.review-overlay, .review-close',
        function () {
            $('#reviewModal').removeClass('show');
        }
    );

    /* =========================== STAR RATING (DYNAMIC) ================================= */
    $(document).on('click', '.rating-stars i', function () {
        const value = $(this).data('value');

        $('.rating-stars').attr('data-rating', value);

        $('.rating-stars i').each(function () {
            const starValue = $(this).data('value');
            if (starValue <= value) {
                $(this).addClass('active');
            } else {
                $(this).removeClass('active');
            }
        });
    });

    /* ====================== SUBMIT REVIEW (AJAX) ============================== */
    $(document).on('click', '.submit-review', function () {

        const rating = parseInt($('.rating-stars').attr('data-rating'), 10);
        const comment = $('#reviewText').val().trim();

        if (!rating || rating < 1 || rating > 5) {
            if (typeof showToast === 'function') {
                showToast('Vui lòng chọn số sao đánh giá ⭐');
            }
            return;
        }

        if (comment.length < 5) {
            if (typeof showToast === 'function') {
                showToast('Nhận xét quá ngắn ❗');
            }
            return;
        }

        $.ajax({
            url: contextPath + '/reviews',
            method: 'POST',
            data: {
                productId: productId,
                rating: rating,
                comment: comment
            },
            success: function () {

                if (typeof showToast === 'function') {
                    showToast('Đã gửi đánh giá ✔');
                }

                // Đóng modal
                $('#reviewModal').removeClass('show');

                // Reset form
                $('#reviewText').val('');
                $('.rating-stars').attr('data-rating', 0);
                $('.rating-stars i').removeClass('active');

                // Reload reviews
                currentPage = 1;
                $('#reviewContainer').empty();
                loadReviews(true);
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    showToast && showToast('Vui lòng đăng nhập để đánh giá');
                } else if (xhr.status === 403) {
                    showToast && showToast('Bạn chỉ có thể đánh giá khi đã mua sản phẩm');
                } else {
                    showToast && showToast('Không gửi được đánh giá ❌');
                }
            }
        });
    });

    /* ======================= LOAD REVIEWS (AJAX) ================================= */
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
            }
        });
    }

    /* ================= REVIEW FILTER & SORT ============================ */
    $(document).on('click', '.filter-btn', function () {
        $('.filter-btn').removeClass('active');
        $(this).addClass('active');

        currentRating = $(this).data('rating') || '';
        loadReviews(true);
    });

    $(document).on('change', '.sort-select', function () {
        currentSort = $(this).val();
        loadReviews(true);
    });

    $(document).on('click', '#loadMoreReview', function () {
        currentPage++;
        loadReviews(false);
    });

    if ($('#reviewContainer').length) {
        loadReviews(true);
    }

});
