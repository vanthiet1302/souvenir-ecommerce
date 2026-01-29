    /**
 * Search Autocomplete with Suggestions
 */

document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('search');
    if (!searchInput) return;
    
    const contextPath = searchInput.closest('form').action.split('/search')[0];
    let debounceTimer;
    let suggestionsBox;
    
    // Create suggestions box
    function createSuggestionsBox() {
        if (suggestionsBox) return suggestionsBox;
        
        suggestionsBox = document.createElement('div');
        suggestionsBox.className = 'search-suggestions';
        suggestionsBox.style.display = 'none';
        
        // Insert after search input's parent
        const searchContainer = searchInput.closest('.center');
        if (searchContainer) {
            searchContainer.style.position = 'relative';
            searchContainer.appendChild(suggestionsBox);
        }
        
        return suggestionsBox;
    }
    
    // Format price
    function formatPrice(price) {
        return new Intl.NumberFormat('vi-VN').format(price) + ' ₫';
    }
    
    // Show suggestions
    function showSuggestions(products) {
        const box = createSuggestionsBox();
        
        if (products.length === 0) {
            box.style.display = 'none';
            return;
        }
        
        let html = '<div class="suggestions-list">';
        
        products.forEach(product => {
            const imageUrl = product.image.startsWith('http') 
                ? product.image 
                : contextPath + product.image;
                
            html += `
                <a href="${contextPath}/product?id=${product.id}" class="suggestion-item">
                    <img src="${imageUrl}" 
                         alt="${product.name}"
                         onerror="this.src='https://placehold.co/50x50?text=No+Image'">
                    <div class="suggestion-info">
                        <div class="suggestion-name">${product.name}</div>
                        <div class="suggestion-price">${formatPrice(product.price)}</div>
                    </div>
                </a>
            `;
        });
        
        html += '</div>';
        box.innerHTML = html;
        box.style.display = 'block';
    }
    
    // Hide suggestions
    function hideSuggestions() {
        if (suggestionsBox) {
            suggestionsBox.style.display = 'none';
        }
    }
    
    // Fetch suggestions
    function fetchSuggestions(query) {
        if (query.trim().length < 2) {
            hideSuggestions();
            return;
        }
        
        fetch(`${contextPath}/search-suggestions?q=${encodeURIComponent(query)}`)
            .then(response => response.json())
            .then(products => {
                showSuggestions(products);
            })
            .catch(error => {
                console.error('Error fetching suggestions:', error);
                hideSuggestions();
            });
    }
    
    // Input event with debounce
    searchInput.addEventListener('input', function(e) {
        clearTimeout(debounceTimer);
        
        const query = e.target.value;
        
        debounceTimer = setTimeout(() => {
            fetchSuggestions(query);
        }, 300); // Wait 300ms after user stops typing
    });
    
    // Focus event
    searchInput.addEventListener('focus', function(e) {
        const query = e.target.value;
        if (query.trim().length >= 2) {
            fetchSuggestions(query);
        }
    });
    
    // Click outside to close
    document.addEventListener('click', function(e) {
        if (!searchInput.contains(e.target) && 
            (!suggestionsBox || !suggestionsBox.contains(e.target))) {
            hideSuggestions();
        }
    });
    
    // Keyboard navigation
    let selectedIndex = -1;
    
    searchInput.addEventListener('keydown', function(e) {
        if (!suggestionsBox || suggestionsBox.style.display === 'none') return;
        
        const items = suggestionsBox.querySelectorAll('.suggestion-item');
        if (items.length === 0) return;
        
        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();
                selectedIndex = Math.min(selectedIndex + 1, items.length - 1);
                updateSelection(items);
                break;
                
            case 'ArrowUp':
                e.preventDefault();
                selectedIndex = Math.max(selectedIndex - 1, -1);
                updateSelection(items);
                break;
                
            case 'Enter':
                if (selectedIndex >= 0 && selectedIndex < items.length) {
                    e.preventDefault();
                    items[selectedIndex].click();
                }
                break;
                
            case 'Escape':
                hideSuggestions();
                selectedIndex = -1;
                break;
        }
    });
    
    function updateSelection(items) {
        items.forEach((item, index) => {
            if (index === selectedIndex) {
                item.classList.add('selected');
                item.scrollIntoView({ block: 'nearest' });
            } else {
                item.classList.remove('selected');
            }
        });
    }
});
