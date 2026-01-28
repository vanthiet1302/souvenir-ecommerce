// Shopping Cart JavaScript
document.addEventListener('DOMContentLoaded', function() {
    const cartItems = document.querySelectorAll('.cart-item-card');
    
    cartItems.forEach(item => {
        const minusBtn = item.querySelector('.minus-btn');
        const plusBtn = item.querySelector('.plus-btn');
        const qtyInput = item.querySelector('.qty-input');
        const removeBtn = item.querySelector('.remove-item-btn');
        
        // Lấy productId từ URL của nút xóa
        const productId = removeBtn ? removeBtn.href.match(/productId=(\d+)/)?.[1] : null;
        
        if (!productId) return;
        
        // Xử lý nút giảm số lượng
        if (minusBtn) {
            minusBtn.addEventListener('click', function(e) {
                e.preventDefault();
                let currentQty = parseInt(qtyInput.value);
                if (currentQty > 1) {
                    updateQuantity(productId, currentQty - 1, qtyInput, item);
                }
            });
        }
        
        // Xử lý nút tăng số lượng
        if (plusBtn) {
            plusBtn.addEventListener('click', function(e) {
                e.preventDefault();
                let currentQty = parseInt(qtyInput.value);
                updateQuantity(productId, currentQty + 1, qtyInput, item);
            });
        }
        
        // Xử lý thay đổi trực tiếp input
        if (qtyInput) {
            qtyInput.addEventListener('change', function() {
                let newQty = parseInt(this.value);
                if (newQty < 1) newQty = 1;
                updateQuantity(productId, newQty, qtyInput, item);
            });
        }
    });
    
    // Hàm cập nhật số lượng
    function updateQuantity(productId, quantity, inputElement, itemCard) {
        const contextPath = window.location.pathname.split('/')[1];
        
        fetch(`/${contextPath}/update-cart`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `productId=${productId}&quantity=${quantity}`
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                // Cập nhật input
                inputElement.value = quantity;
                
                // Reload trang để cập nhật tổng tiền
                location.reload();
            } else {
                alert('Có lỗi xảy ra: ' + (data.message || 'Không thể cập nhật giỏ hàng'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Có lỗi xảy ra khi cập nhật giỏ hàng');
        });
    }
});
