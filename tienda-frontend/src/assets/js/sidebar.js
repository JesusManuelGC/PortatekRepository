function initSidebar() {
  let sidebar = document.querySelector('.sidebar');
  let sidebarOverlay = document.querySelector('.sidebar-overlay');

  function getElements() {
    sidebar = document.querySelector('.sidebar');
    sidebarOverlay = document.querySelector('.sidebar-overlay');
  }

  function openSidebar() {
    getElements();
    if (sidebar) sidebar.classList.add('active');
    if (sidebarOverlay) sidebarOverlay.classList.add('active');
    document.body.style.overflow = 'hidden';
  }

  function closeSidebar() {
    getElements();
    if (sidebar) sidebar.classList.remove('active');
    if (sidebarOverlay) sidebarOverlay.classList.remove('active');
    document.body.style.overflow = '';
  }

  function toggleSidebar() {
    getElements();
    if (sidebar && sidebar.classList.contains('active')) {
      closeSidebar();
    } else {
      openSidebar();
    }
  }

  document.addEventListener('click', function(e) {
    const target = e.target;

    if (target.closest('.sidebar-overlay')) {
      e.preventDefault();
      closeSidebar();
    }

    if (target.closest('.sidebar-header .btn-close')) {
      e.preventDefault();
      closeSidebar();
    }

    if (target.closest('.sidebar-tipo-link')) {
      closeSidebar();
    }
  });

  window.openSidebar = openSidebar;
  window.closeSidebar = closeSidebar;
  window.toggleSidebar = toggleSidebar;
}

if (document.readyState === 'loading') {
  document.addEventListener('DOMContentLoaded', initSidebar);
} else {
  initSidebar();
}

const observer = new MutationObserver(function(mutations) {
  const sidebar = document.querySelector('.sidebar');
  if (sidebar && !window.sidebarInitialized) {
    window.sidebarInitialized = true;
    initSidebar();
  }
});

observer.observe(document.body, {
  childList: true,
  subtree: true
});
