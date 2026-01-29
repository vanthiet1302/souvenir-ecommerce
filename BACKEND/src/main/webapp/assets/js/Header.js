document.addEventListener('DOMContentLoaded', () => {

    /* ================= ELEMENTS ================= */
    const menuBtn = document.getElementById('menuBtn');
    const dropdownMenu = document.getElementById('dropdownMenu');
    const overlay = document.getElementById('headerOverlay');

    const userToggle = document.getElementById('userToggle');
    const userDropdown = document.getElementById('userDropdown');

    /* ================= UTIL ================= */
    function closeAll() {
        dropdownMenu?.classList.remove('open');
        userDropdown?.classList.remove('open');
        overlay?.classList.remove('active');

        if (menuBtn) {
            menuBtn.setAttribute('aria-expanded', 'false');
        }
        dropdownMenu?.setAttribute('aria-hidden', 'true');
    }

    /* ================= CATEGORY DROPDOWN ================= */
    menuBtn?.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();

        const isOpen = dropdownMenu.classList.toggle('open');
        overlay?.classList.toggle('active', isOpen);

        // aria
        menuBtn.setAttribute('aria-expanded', String(isOpen));
        dropdownMenu.setAttribute('aria-hidden', String(!isOpen));

        // Không cho user menu mở cùng lúc
        userDropdown?.classList.remove('open');
    });

    /* ================= USER DROPDOWN ================= */
    userToggle?.addEventListener('click', (e) => {
        e.preventDefault();
        e.stopPropagation();

        const isOpen = userDropdown.classList.toggle('open');
        overlay?.classList.toggle('active', isOpen);

        // Không cho category menu mở cùng lúc
        dropdownMenu?.classList.remove('open');
        dropdownMenu?.setAttribute('aria-hidden', 'true');

        if (menuBtn) {
            menuBtn.setAttribute('aria-expanded', 'false');
        }
    });

    /* ================= OVERLAY ================= */
    overlay?.addEventListener('click', (e) => {
        e.stopPropagation();
        closeAll();
    });

    /* ================= CLICK OUTSIDE (GLOBAL) ================= */
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

    /* ================= CATEGORY LINK ================= */
    document.querySelectorAll('.dropdown-menu a').forEach(link => {
        link.addEventListener('click', () => {
            closeAll();
        });
    });

    /* ================= MENU BAR (HOME PAGE ONLY) ================= */
    document.querySelectorAll('.menu-bar a').forEach(link => {
        link.addEventListener('click', (e) => {
            const targetId = link.getAttribute('href');

            if (targetId && targetId.startsWith('#')) {
                const target = document.querySelector(targetId);
                if (target) {
                    e.preventDefault();
                    closeAll();
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            }
        });
    });

});
