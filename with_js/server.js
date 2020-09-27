// Express library
const express = require('express');
const mongoose = require('mongoose');
const ShortUrl = require('./models/shortUrl');
const app = express();

mongoose.connect('mongodb://localhost/pandaUrlShortener', {
    useNewUrlParser: true, useUnifiedTopology: true
})

app.set('view engine', 'ejs');
app.use(express.urlencoded({ extended: false }));

/* Routes */
app.get('/', async (req, res) => {
    // Get all the urls inside the ShortUrl's table
    const shortUrls = await ShortUrl.find();
    res.render('index', { shortUrls:shortUrls });
});

app.post('/shortUrls', async (req, res) => {
    console.log("Pressed the shorten button");
    
    // Connect to my database and save a new URLs
    await ShortUrl.create({ full: req.body.fullUrl });
    res.redirect('/');
});

// Route for the short urls,saved in a parameter named "shortUrl"
app.get('/:shortUrl', async (req, res) => {
    const shortUrl = await ShortUrl.findOne({ short: req.params.shortUrl });
    if (shortUrl == null) return res.sendStatus(404);

    shortUrl.clicks++; // Adding clicks attribute
    shortUrl.save();

    // Redirect to the actual full site
    res.redirect(shortUrl.full);
});

// When deploy the site, I can set the port environment variable
// https://stackoverflow.com/questions/18864677/what-is-process-env-port-in-node-js
app.listen(process.env.PORT || 5000);