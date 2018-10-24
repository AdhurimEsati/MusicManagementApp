Music Management App
---
This is a simple android app which I made as a test task. It's a Music Management App based on the LastFM API.

The app starts on MainActivity, where Locally saved albums are displayed on a list, clicking on an item on the list opens Detailed View of the album which displays Album image, artist name and album title, also displays a list of that album's tracks, and clicking on one of the tracks opens that track url in your web browser.
Then if you go to the drawer menu by clicking the 3 bars on top left or swiping right, you can go to Search Page where you can search for artists, for example if you search for Rita, you get a list of matches (artists) and if you click any item on the list it will go to TopAlbums activity where the top albums of that artist are displayed, also when clicking items on TopAlbum, it also opens the Detailed view of the album.

Features
---
* You can save/remove your favorite artist's albums and view them in the main screen (albums are saved locally).
* Search for artists - view all artists' songs
* Search for albums - Detailed view about the album which displays information including all the songs
* View Artists' top albums
* Redirect to the song's or artist's LastFM Page

* The album information that are retrieved from the LastFM API are: 
  * Name
  * Artist
  * Image
  * Tracks

Library and Source used
---
* GSON - https://github.com/google/gson

* LastFM API - https://www.last.fm/api - you need to use your API Key. Example: *http://ws.audioscrobbler.com/2.0/?method=tag.gettoptracks&tag=disco&api_key=**YOUR_API_KEY**&format=json*

Screenshots
---

![screen1](https://i.imgur.com/zcZ83fl.jpg)

![screen2](https://i.imgur.com/PIEQEn0.jpg)

![screen3](https://i.imgur.com/Ps9LvEk.jpg)

![screen4](https://i.imgur.com/sWvSv9z.jpg)
