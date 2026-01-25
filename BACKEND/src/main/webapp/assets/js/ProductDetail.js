$(document).ready(function () {

    /* ===== IMAGE ZOOM ===== */
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

        modal.on('click', '.modal-overlay, .modal-close', function () {
            modal.removeClass('show');
            setTimeout(() => modal.remove(), 300);
        });
    });

    /* ===== QUANTITY ===== */
    $('.qty-btn.plus').on('click', function () {
        const input = $(this).siblings('input');
        const max = parseInt(input.attr('max'));
        let val = parseInt(input.val());

        if (val < max) input.val(val + 1);
    });

    $('.qty-btn.minus').on('click', function () {
        const input = $(this).siblings('input');
        let val = parseInt(input.val());

        if (val > 1) input.val(val - 1);
    });

    /* ===== ADD TO CART (AJAX) ===== */
    $('.buy-form').on('submit', function (e) {
        e.preventDefault();

        const form = $(this);

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

});
