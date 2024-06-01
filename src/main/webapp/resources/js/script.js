function scrollToDetails() {
    const detailsSection = document.querySelector('.details_section');
    if (detailsSection) {
        detailsSection.scrollIntoView({ behavior: 'smooth' });
    }
}
