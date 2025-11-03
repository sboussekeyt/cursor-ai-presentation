# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **static HTML presentation website** about Cursor and AI-assisted coding. The presentation consists of 10 interconnected slides with keyboard navigation and a shared navigation bar. All content is in French.

**Key Characteristic**: This is a pure static site with no build process, no package manager, and no compilation step. Files are deployed directly to GitHub Pages.

## Project Architecture

### Multi-Page Static Navigation System

The site uses a **coordinated multi-file approach** where:

1. **Each slide is a standalone HTML file** (`docs/*.html`)
2. **Shared navigation state** is managed by `docs/js/script.js`
3. **A slides manifest** is hardcoded in `script.js:2-13` defining the slide order

**Critical Architecture Detail**: The slides array in `script.js` MUST stay synchronized with:
- The actual HTML files in `docs/`
- The navigation links in each HTML file's `<nav>` section
- The slide counter display logic

Example slides array structure:
```javascript
const slides = [
    { id: 'title', title: 'L\'Intelligence Artificielle...', file: 'index.html' },
    { id: 'contexte', title: 'Du Complètion de Code...', file: 'contexte.html' },
    // ... 8 more entries
];
```

### Slide Navigation Flow

1. User lands on any `.html` file
2. `script.js` executes on `DOMContentLoaded`
3. `getCurrentSlideIndex()` parses `window.location.pathname` to find current position
4. Updates active nav link styling and slide counter
5. Keyboard events (ArrowLeft/ArrowRight) trigger `prevSlide()`/`nextSlide()`
6. Navigation executes via `window.location.href` (full page reload)

**Why this matters**: When adding/removing slides, you must update:
- Create/delete the HTML file in `docs/`
- Update the `slides` array in `script.js`
- Update all `<nav>` sections across all HTML files (if adding to nav bar)

### Styling Architecture

**Single CSS file** (`docs/css/style.css`) defines:
- CSS custom properties for theming (colors, spacing)
- Shared layout components (nav, footer, slide-wrapper)
- Typography system (Inter for body, Poppins for headings)
- Responsive breakpoints

**Important**: Each HTML file contains **inline styles** for slide-specific layouts. This is intentional for per-slide customization without creating component classes.

## Development Workflow

### Running Locally

**No build step required**. Open HTML files directly:

```bash
# Option 1: Direct file opening
open docs/index.html

# Option 2: Simple HTTP server (recommended for accurate path testing)
cd docs
python3 -m http.server 8000
# Visit http://localhost:8000
```

**Why a server matters**: Local file protocol (`file://`) doesn't handle relative paths identically to HTTP servers. Use HTTP server to test navigation.

### Making Content Changes

**Editing slide content**:
1. Open the specific `docs/*.html` file
2. Edit content inside `<main>` section
3. Refresh browser (no compilation needed)

**Adding a new slide**:
1. Duplicate an existing HTML file (e.g., `cp docs/cursor.html docs/newslide.html`)
2. Update the content in `newslide.html`
3. Add entry to `slides` array in `docs/js/script.js`
4. Update `<nav>` sections in ALL HTML files to include link to new slide
5. Test keyboard navigation flows correctly

**Modifying styles**:
- Global changes: Edit `docs/css/style.css`
- Slide-specific layouts: Edit inline styles in individual HTML files

### Deployment

**GitHub Pages** is the deployment target. Configuration:
- Source: `main` branch, `/root` directory (NOT `/docs`)
- The `/docs` folder is recognized automatically by GitHub Pages

**Deployment command**:
```bash
git add .
git commit -m "Update: [description]"
git push origin main
# GitHub Pages auto-deploys in ~2-5 minutes
```

**Deployment URL pattern**: `https://[username].github.io/cursor-ai-presentation/`

### Common Issues

**Navigation not updating after adding slide**:
- Verify `slides` array in `script.js` includes new entry
- Check `file` property matches actual filename
- Ensure new HTML file is in `docs/` directory

**Styles not applying**:
- Check CSS file path is `css/style.css` (relative from HTML location)
- Clear browser cache (Cmd+Shift+R / Ctrl+Shift+R)
- Verify CSS custom properties are defined in `:root` selector

**Images not loading on GitHub Pages**:
- Paths must be relative: `images/filename.png` (NOT `/images/...`)
- Verify images are committed to git and pushed
- Check file extensions match exactly (case-sensitive on Linux servers)

## Important File Locations

**Never modify**:
- `.git/` - Git repository metadata
- `.gitignore` - Already properly configured

**Core files**:
- `docs/js/script.js` - Navigation logic and slides manifest
- `docs/css/style.css` - Global styles and design system
- `docs/*.html` - Individual slide pages

**Documentation**:
- `README.md` - User-facing documentation
- `DEPLOYMENT_GUIDE.md` - GitHub Pages deployment instructions
- `claudedocs/` - Claude Code analysis reports

## Design System

**Color palette** (defined as CSS variables):
- `--color-purple`: Primary gradient start
- `--color-blue`: Primary gradient end
- `--color-charcoal`: Text color
- `--color-white`: Background
- `--color-light-purple/blue`: Accent backgrounds

**Typography scale**:
- Headings: Poppins, weights 600-800
- Body: Inter, weights 400-700
- Gradient text effect applied to `.gradient-text` class

**Layout patterns**:
- `.slide-wrapper` → `.slide-container` → content sections
- `.title-section` / `.image-section` for two-column layouts
- `.content-box` for card-style content blocks

## Testing Before Commits

**Manual verification checklist**:
1. ✅ All HTML files load without 404 errors
2. ✅ Navigation links work in both directions
3. ✅ Keyboard navigation (←/→) works on all slides
4. ✅ Slide counter displays correct numbers
5. ✅ Images load correctly
6. ✅ No console errors in browser DevTools

**Navigation integrity test**:
```bash
# Verify slides array matches actual files
cd docs
ls -1 *.html | wc -l  # Should match slides.length in script.js
```

## Git Workflow

**Current branch**: `main`
**Remote**: `origin` → `https://github.com/Sboussekeyt/cursor-ai-presentation.git`

**Standard workflow**:
```bash
git status                    # Check what's changed
git add docs/                 # Stage presentation files
git commit -m "Update: [description]"
git push origin main          # Deploy to GitHub Pages
```

**When adding new files**: Ensure they're not in `.gitignore` and are inside committed directories.

## Content Language

All content is in **French**. When generating or editing content:
- Use French for slide titles, descriptions, and UI labels
- Maintain formal/professional tone ("vous" not "tu")
- Technical terms (like "Cursor", "AI", "LLM") remain in English
