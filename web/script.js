// Slides data
const slides = [
    { id: 'title', title: 'L\'Intelligence Artificielle au Service du Code', file: 'index.html' },
    { id: 'contexte', title: 'Du Complètion de Code à l\'Agent Autonome', file: 'contexte.html' },
    { id: 'cursor', title: 'Cursor : L\'Éditeur de Code Conçu pour l\'IA', file: 'cursor.html' },
    { id: 'features1', title: 'Les Outils d\'IA Intégrés de Cursor', file: 'features1.html' },
    { id: 'features2', title: 'L\'Approche Agentique et Avancée', file: 'features2.html' },
    { id: 'llm', title: 'Les LLM : Le Moteur de la Révolution', file: 'llm.html' },
    { id: 'alternatives', title: 'Le Paysage des Outils d\'IA pour Développeurs', file: 'alternatives.html' },
    { id: 'avantages', title: 'L\'Impact sur la Productivité et la Qualité', file: 'avantages.html' },
    { id: 'limites', title: 'Les Précautions et Défis à Considérer', file: 'limites.html' },
    { id: 'conclusion', title: 'L\'Avenir du Codage est Hybride', file: 'conclusion.html' }
];

// Get current slide index from URL
function getCurrentSlideIndex() {
    const path = window.location.pathname;
    const filename = path.split('/').pop() || 'index.html';
    return slides.findIndex(slide => slide.file === filename);
}

// Initialize navigation
document.addEventListener('DOMContentLoaded', function() {
    const currentIndex = getCurrentSlideIndex();
    
    if (currentIndex !== -1) {
        // Update active nav link
        const navLinks = document.querySelectorAll('nav .nav-links a');
        navLinks.forEach((link, index) => {
            if (index === currentIndex) {
                link.style.color = 'var(--color-blue)';
                link.style.fontWeight = '600';
            }
        });

        // Update slide counter
        const slideCounter = document.querySelector('.slide-counter');
        if (slideCounter) {
            slideCounter.textContent = `${currentIndex + 1} / ${slides.length}`;
        }
    }
});

// Navigation functions
function goToSlide(index) {
    if (index >= 0 && index < slides.length) {
        window.location.href = slides[index].file;
    }
}

function nextSlide() {
    const currentIndex = getCurrentSlideIndex();
    if (currentIndex < slides.length - 1) {
        goToSlide(currentIndex + 1);
    }
}

function prevSlide() {
    const currentIndex = getCurrentSlideIndex();
    if (currentIndex > 0) {
        goToSlide(currentIndex - 1);
    }
}

// Keyboard navigation
document.addEventListener('keydown', function(event) {
    if (event.key === 'ArrowRight') {
        nextSlide();
    } else if (event.key === 'ArrowLeft') {
        prevSlide();
    }
});
