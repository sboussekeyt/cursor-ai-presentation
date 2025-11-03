# 🚀 Quick Start Guide

## Test Locally (Right Now!)

### Option 1: Double-Click (Easiest)
```bash
# Just open the file in your browser
open docs/index.html
# or on Windows: start docs/index.html
# or on Linux: xdg-open docs/index.html
```

### Option 2: Local Server (Recommended)
```bash
# Python 3
cd docs
python3 -m http.server 8000
# Then open: http://localhost:8000

# Or with Node.js
npx serve docs
# Then open the URL shown
```

## 🎮 How to Navigate

### Keyboard
- `→` or `Space` : Next slide
- `←` : Previous slide
- `Home` : Go to start
- `End` : Go to last slide

### Mobile
- Swipe left : Next slide
- Swipe right : Previous slide

### Interface
- Click navigation buttons at bottom
- Use the menu at top
- Progress bar shows your position

## ✅ Quick Test Checklist

1. **Open** [docs/index.html](docs/index.html) in browser
2. **Test navigation** : Try arrow keys → ←
3. **Check responsive** : Resize browser window
4. **Test mobile** : Open DevTools (F12) → Toggle device toolbar
5. **Print preview** : Ctrl/Cmd + P (should hide nav/footer)

## 📤 Deploy to GitHub Pages

### Step 1: Push to GitHub
```bash
git add docs/
git commit -m "Add AI presentation slides"
git push origin main
```

### Step 2: Enable GitHub Pages
1. Go to your repository on GitHub
2. Click **Settings**
3. Scroll to **Pages** section
4. Under **Source**:
   - Branch: `main`
   - Folder: `/docs`
5. Click **Save**

### Step 3: Access Your Site
- Wait 1-2 minutes
- URL: `https://[your-username].github.io/cursor-ai-presentation`
- Share the link!

## 🎨 Quick Customization

### Change Colors
Edit [docs/css/style.css](docs/css/style.css):
```css
:root {
    --color-purple: #8B5CF6;  /* Change this */
    --color-blue: #3B82F6;     /* Change this */
}
```

### Add Your Name
Edit each slide's footer in HTML files:
```html
<footer>
    <p>&copy; 2025 Votre Nom - Présentation Cursor & IA</p>
</footer>
```

### Change Slide Content
Just edit the HTML files in `docs/`:
- `slide1.html` - Cursor & Tools
- `slide2.html` - LLM Models
- `slide3.html` - Context 1
- `slide4.html` - Context 2
- `slide5.html` - Context 3

## 📊 What You Got

### Files Created
- ✅ 6 HTML pages (index + 5 slides)
- ✅ 2 CSS files (style + slides)
- ✅ 1 JS file (navigation)
- ✅ README + Documentation

### Features Implemented
- ✅ Keyboard navigation
- ✅ Touch/swipe support
- ✅ Progress bar
- ✅ Responsive design
- ✅ Accessible (WCAG AA)
- ✅ Print-friendly
- ✅ Beautiful modern design

## 🔍 Troubleshooting

### Navigation not working?
- Open browser console (F12)
- Should see: "Navigation Loaded"
- If not, check `navigation.js` is loading

### Styles broken?
- Check paths to CSS files
- Clear browser cache (Ctrl+Shift+R)

### Mobile not responsive?
- Test with real device or Chrome DevTools
- Check viewport meta tag is present

## 📱 Test on Mobile

1. Deploy to GitHub Pages (see above)
2. Open on phone: `https://[your-username].github.io/cursor-ai-presentation`
3. Test swipe gestures
4. Check readability

## 🎯 Next Steps

### Immediate
1. ✅ Test locally (5 min)
2. ✅ Deploy to GitHub Pages (5 min)
3. ✅ Share with colleagues!

### Optional Enhancements
- [ ] Add screenshots of Cursor
- [ ] Add tool logos (PNG/SVG)
- [ ] Customize colors to your brand
- [ ] Add more slides if needed
- [ ] Record presentation video

## 📚 Documentation

- **Full README**: [docs/README.md](docs/README.md)
- **Implementation Details**: [IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md)
- **Improved Prompt**: [prompts/slides_improved.md](prompts/slides_improved.md)

## 🆘 Need Help?

### Common Issues

**Q: How do I add a new slide?**
A: Copy `slide5.html` → `slide6.html`, edit content, update `navigation.js`

**Q: Can I use this offline?**
A: Yes! Just open `index.html` in any browser

**Q: How do I export to PDF?**
A: Open in Chrome → Print (Ctrl+P) → Save as PDF

**Q: Can I customize the design?**
A: Yes! Edit CSS files in `docs/css/`

## 🎉 You're Ready!

Your presentation is ready to use:
- 🖥️ **Local**: Open `docs/index.html`
- 🌐 **Online**: Deploy to GitHub Pages
- 📱 **Mobile**: Works on all devices
- 🖨️ **Print**: Export to PDF anytime

**Enjoy your presentation!** 🚀

---

**Quick Links:**
- [Open Presentation](docs/index.html)
- [View README](docs/README.md)
- [Deploy Guide](#deploy-to-github-pages)
