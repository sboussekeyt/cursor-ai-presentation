// Keyboard Navigation
document.addEventListener('keydown', (e) => {
    const currentPage = window.location.pathname.split('/').pop();

    const pages = [
        'index.html',
        'slide1.html',
        'slide2.html',
        'slide3.html',
        'slide4.html',
        'slide5.html'
    ];

    const currentIndex = pages.indexOf(currentPage);

    // Right arrow or Space - next slide
    if ((e.key === 'ArrowRight' || e.key === ' ') && currentIndex < pages.length - 1) {
        e.preventDefault();
        window.location.href = pages[currentIndex + 1];
    }

    // Left arrow - previous slide
    if (e.key === 'ArrowLeft' && currentIndex > 0) {
        e.preventDefault();
        window.location.href = pages[currentIndex - 1];
    }

    // Home key - go to start
    if (e.key === 'Home') {
        e.preventDefault();
        window.location.href = 'index.html';
    }

    // End key - go to last slide
    if (e.key === 'End') {
        e.preventDefault();
        window.location.href = pages[pages.length - 1];
    }
});

// Function for button navigation (used in index.html)
function nextSlide() {
    window.location.href = 'slide1.html';
}

// Smooth fade-in animation on page load
window.addEventListener('load', () => {
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 10);
});

// Print mode detection
window.matchMedia('print').addListener((mql) => {
    if (mql.matches) {
        // Add print-specific styles
        document.body.classList.add('print-mode');
    }
});

// Touch swipe support for mobile
let touchStartX = 0;
let touchEndX = 0;

document.addEventListener('touchstart', (e) => {
    touchStartX = e.changedTouches[0].screenX;
});

document.addEventListener('touchend', (e) => {
    touchEndX = e.changedTouches[0].screenX;
    handleSwipe();
});

function handleSwipe() {
    const swipeThreshold = 50;
    const currentPage = window.location.pathname.split('/').pop();

    const pages = [
        'index.html',
        'slide1.html',
        'slide2.html',
        'slide3.html',
        'slide4.html',
        'slide5.html'
    ];

    const currentIndex = pages.indexOf(currentPage);

    // Swipe left - next slide
    if (touchStartX - touchEndX > swipeThreshold && currentIndex < pages.length - 1) {
        window.location.href = pages[currentIndex + 1];
    }

    // Swipe right - previous slide
    if (touchEndX - touchStartX > swipeThreshold && currentIndex > 0) {
        window.location.href = pages[currentIndex - 1];
    }
}

// Progress bar animation
const progressFill = document.querySelector('.progress-fill');
if (progressFill) {
    setTimeout(() => {
        progressFill.style.transition = 'width 0.5s ease';
    }, 100);
}

// Add accessibility announcements
const slideCounter = document.querySelector('.slide-counter');
if (slideCounter) {
    const announcement = document.createElement('div');
    announcement.setAttribute('role', 'status');
    announcement.setAttribute('aria-live', 'polite');
    announcement.className = 'sr-only';
    announcement.textContent = slideCounter.textContent;
    document.body.appendChild(announcement);
}

// Add screen reader only class
const style = document.createElement('style');
style.textContent = `
    .sr-only {
        position: absolute;
        width: 1px;
        height: 1px;
        padding: 0;
        margin: -1px;
        overflow: hidden;
        clip: rect(0, 0, 0, 0);
        white-space: nowrap;
        border-width: 0;
    }

    .print-mode nav,
    .print-mode footer,
    .print-mode .slide-navigation {
        display: none !important;
    }

    @media print {
        body {
            background: white !important;
        }

        .slide-container {
            page-break-inside: avoid;
        }

        nav, footer, .slide-navigation, .progress-bar {
            display: none !important;
        }
    }
`;
document.head.appendChild(style);

console.log('🎨 Cursor & IA Presentation - Navigation Loaded');
console.log('💡 Keyboard shortcuts:');
console.log('   → / Space : Next slide');
console.log('   ← : Previous slide');
console.log('   Home : First slide');
console.log('   End : Last slide');
