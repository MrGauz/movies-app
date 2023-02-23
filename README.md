# 🍿 Swovie App

This is the final project of TUM's Kotlin practical course. Essentially, it was a 10 day hackathon.

Unfortunately, the website is down, but you can have a look at [this detailed report](https://github.com/MrGauz/movies-app/blob/main/docs/Kotlin%20Practical%20Course%20Report.pdf) to see how the app used to look.

## ⚙️ Setup

- Connect Firebase Realtime Database
    - Drop ``google-services.json`` into the ``app`` folder
- Add TMDB API key
    - Make a copy of ``tmdbapikey.properties.example`` in app's root folder and rename it
      to ``tmdbapikey.properties``
    - Put the API key within double quotes

## 🧩 Structure

- api: TMDB API logic
- data: Firebase logic
- database: Classes in here take care of the communication with firebase
- models: objects for (de-)serializing
- start: contains all classes of Fragments located in the first navigation graph
- swipe: contains all classes of Fragments located in the second navigation graph
- ui: UI helper classes
