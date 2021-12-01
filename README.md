# Swovie App

## Setup
  - Connect Firebase Realtime Database
    - Drop ``google-services.json`` into the ``app`` folder
  - Add TMDB API key
    - Make a copy of ``tmdbapikey.properties.example`` in app's root folder and rename it to ``tmdbapikey.properties``
    - Put the API key within double quotes
 
## Structure
  - api : contains necessary functions to pull data from TMDB
  - data: mainly object classes are located here, they contain explicit data, or functions that get data from a source
  - database: Classes in here take care of the communication with firebase
  - models: TODO
  - start: contains all the Classes of Fragments located in the first navigation graph and their direct helper Classes
  - ui: directory for the GenresViewAdapter TODO
