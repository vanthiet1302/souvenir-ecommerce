document.addEventListener("DOMContentLoaded", function () {

    const filterForm = document.querySelector(".filter-sidebar form");
    if (!filterForm) return;

    const sortSelect = filterForm.querySelector("select[name='sort']");
    const minPriceInput = filterForm.querySelector("input[name='minPrice']");
    const maxPriceInput = filterForm.querySelector("input[name='maxPrice']");

    /* ================= UTIL ================= */

    function ensurePageReset() {
        let pageInput = filterForm.querySelector("input[name='page']");
        if (!pageInput) {
            pageInput = document.createElement("input");
            pageInput.type = "hidden";
            pageInput.name = "page";
            filterForm.appendChild(pageInput);
        }
        pageInput.value = "1";
    }

    /* ================= SORT CHANGE ================= */

    if (sortSelect) {
        sortSelect.addEventListener("change", function () {
            ensurePageReset();
            filterForm.submit();
        });
    }

    /* ================= FILTER SUBMIT ================= */

    filterForm.addEventListener("submit", function () {
        ensurePageReset();
    });

});
