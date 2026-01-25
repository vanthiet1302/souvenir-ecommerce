document.addEventListener("DOMContentLoaded", function () {

    const filterForm = document.querySelector(".filter-sidebar form");
    if (!filterForm) return;

    const sortSelect   = filterForm.querySelector("select[name='sort']");
    const ratingSelect = filterForm.querySelector("select[name='rating']");

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

    if (sortSelect) {
        sortSelect.addEventListener("change", function () {
            ensurePageReset();
            filterForm.submit();
        });
    }

    if (ratingSelect) {
        ratingSelect.addEventListener("change", function () {
            ensurePageReset();
            filterForm.submit();
        });
    }

    filterForm.addEventListener("submit", function () {
        ensurePageReset();
    });
});
