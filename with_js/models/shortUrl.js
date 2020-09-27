const mongoose = require('mongoose');
const shortId = require('shortid'); // npm i shortid

/** Schema */
const shortUrlSchema = new mongoose.Schema({
    // All different columns for database
    full: {
        type: String,
        require: true
    },
    short: {
        type: String,
        require: true,
        default: shortId.generate // automatically fire the function
    },
    clicks: {
        type: Number,
        required: true,
        default: 0
    }
});

module.exports = mongoose.model('ShortUrl', shortUrlSchema);