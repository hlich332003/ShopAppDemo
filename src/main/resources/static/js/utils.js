// Format price
function formatPrice(price) {
    return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".") + '₫';
}

// Show notification
function showNotification(message, type = 'info') {
    alert(message); // Có thể nâng cấp bằng toast sau
}

// Add to cart
function addToCart(productId) {
    fetch('/gio-hang/them', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'productId=' + productId + '&quantity=1'
    })
    .then(response => {
        if (response.ok) {
            showNotification('✅ Đã thêm vào giỏ hàng!');
            updateCartCount();
        } else {
            showNotification('❌ Thêm vào giỏ hàng thất bại!');
        }
    })
    .catch(error => {
        console.error('Lỗi khi thêm vào giỏ hàng:', error);
        showNotification('⚠️ Lỗi kết nối đến server!');
    });
}

// Update cart count
function updateCartCount() {
    fetch('/gio-hang/so-luong')
        .then(response => response.text())
        .then(count => {
            document.querySelectorAll('.cart-count').forEach(el => {
                el.textContent = count;
            });
        })
        .catch(error => {
            console.error('Không thể lấy số lượng giỏ hàng:', error);
        });
}

// Initialize when page loads
document.addEventListener('DOMContentLoaded', function() {
    updateCartCount();
});
