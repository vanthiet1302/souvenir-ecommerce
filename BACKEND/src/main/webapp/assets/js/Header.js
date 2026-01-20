document.addEventListener('DOMContentLoaded', () => {

    const menuBtn = document.getElementById('menuBtn');
    const dropdownMenu = document.getElementById('dropdownMenu');
    const overlay = document.getElementById('headerOverlay');

    const userToggle = document.getElementById('userToggle');
    const userDropdown = document.getElementById('userDropdown');

    /* ================= UTIL ================= */
    function closeAll() {
        dropdownMenu?.classList.remove('open');
        userDropdown?.classList.remove('open');
        overlay?.classList.remove('show');
        dropdownMenu?.setAttribute('aria-hidden', 'true');
    }

    /* ================= MENU TOGGLE ================= */
    menuBtn?.addEventListener('click', (e) => {
        e.stopPropagation();

        const isOpen = dropdownMenu.classList.toggle('open');
        dropdownMenu.setAttribute('aria-hidden', String(!isOpen));

        overlay.classList.toggle('show', isOpen);

        // đóng user dropdown nếu đang mở
        userDropdown?.classList.remove('open');
    });

    /* ================= USER DROPDOWN ================= */
    userToggle?.addEventListener('click', (e) => {
        e.stopPropagation();

        userDropdown.classList.toggle('open');

        // đóng menu category nếu đang mở
        dropdownMenu?.classList.remove('open');
        overlay?.classList.remove('show');
    });

    /* ================= OVERLAY ================= */
    overlay?.addEventListener('click', closeAll);

    /* ================= CLICK OUTSIDE ================= */
    document.addEventListener('click', (e) => {
        if (
            !dropdownMenu?.contains(e.target) &&
            !menuBtn?.contains(e.target) &&
            !userDropdown?.contains(e.target) &&
            !userToggle?.contains(e.target)
        ) {
            closeAll();
        }
    });

    /* ================= DROPDOWN MENU LINK ================= */
    document.querySelectorAll('.dropdown-menu a').forEach(link => {
        link.addEventListener('click', () => closeAll());
    });

    /* ================= MENU BAR SCROLL ================= */
    document.querySelectorAll('.menu-bar a').forEach(link => {
        link.addEventListener('click', (e) => {
            const targetId = link.getAttribute('href');
            if (targetId?.startsWith('#')) {
                const target = document.querySelector(targetId);
                if (target) {
                    e.preventDefault();
                    target.scrollIntoView({ behavior: 'smooth', block: 'start' });
                }
            }
        });
    });

});
