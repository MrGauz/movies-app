# Swovie App

## Setup

- Connect Firebase Realtime Database
    - Drop ``google-services.json`` into the ``app`` folder
- Add TMDB API key
    - Make a copy of ``tmdbapikey.properties.example`` in app's root folder and rename it
      to ``tmdbapikey.properties``
    - Put the API key within double quotes

## Structure

- api: TMDB API logic
- data: Firebase logic
- database: Classes in here take care of the communication with firebase
- models: objects for (de-)serializing
- start: contains all classes of Fragments located in the first navigation graph
- swipe: contains all classes of Fragments located in the second navigation graph
- ui: UI helper classes
