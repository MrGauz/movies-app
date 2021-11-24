# «Movies» App Requirements v1

## Basic functionality
This app helps a group of people to decide which movie to watch together.

User journey through the app should be divided into 2 stages:
- session-creation and -joining
- movie-choosing (through swiping).

In the first stage, a host creates new session, configures it, and shares a join link with other group members.

In the second stage, all people who joined the session swipe movies to the right to accept them as a watching option or to the left to decline. As soon as matches arise, counter shows a number of matches so far. Swiping process continues at longest until 3 matches have been found.

## «Start» screen
The first screen a user sees when opening the app contains 2 buttons:
- a button to start a new session;
- a button to join an existing session.
Start session button leads to the «filter» screen. Join button opens the «join» screen.

## «Filter» screen
This screen allows to define filtering options for shown movies, as well as session’s options. The screen is shown at the moment of session creation; it can also be opened whilst swiping through movies to update session’s configuration.

Options list offers the following movie options:

 - Genre (multiple select with "and" conditions)
 - Release date between years (slider with 2 dots)
 - Director (search field)
 - Minimal IMDB rating (slider; defaults to 4)
 - Movie duration (slider with 2 dots; default 1 to 2 hours)
 - Advanced search

and the following session options:

 - Percentage of votes on a movie out of all votes to count it as a match (slider with a step of 10; minimal value is quorum)
 - Timer to disable join link (slider with a 1 minutes step, might have an incrementing step; min is 2 minutes, max is 30 minutes, defaults to 5 minutes)

Submit button either creates a session and opens the «share» screen or updates current session’s configuration and goes back to the «swipe» screen.

When this window is opened during swiping it also has a button to stop the session which navigates all users to the «start» screen. Session is stopped automatically after 18 hours.

## «Share» screen
This screen’s main purpose is to share created session with others. It shows a copyable session ID, a QR code with an embedded join link and a share button to share the join link. If a user who opens the link does not have the app installed, the link should lead to app installation. Otherwise new user joins session. There is a button at the bottom of the screen to start swiping which leads to the «swipe» screen.

## «Join» screen
The screen contains an input field for the session ID with a submit button and a button to scan a QR code. After submit button has been pressed, QR code scanned, or a join link was opened outside the app, new user is redirected to the «swipe» screen.

## «Swipe» screen
The main element on this screen is a movie card. In the top left corner there is a button shown only to the session’s host that leads to the «filter» screen. In the right top corner there is a counter button that displays the amount of matches so far. A click on the counter opens the «matches» screen.

### Movie card
The card takes up almost the entire screen. It has a movie poster as a background. It displays the following information about the movie:

 - Title
 - Release year
 - Genres
 - Director
 - Poster

A click onto a card opens «details» screen with more information about the movie. Card should have some arrows on it or in any other way show the user how to accept or decline a movie.

## «Matches» screen
This screen is nothing but a list of matches. It can be closed to go back to the «swipe» screen. Matches are shown as a list of movies, showing the same core information about the movie as the movie card on the «swipe» screen. A click onto an element leads to the «details» screen.

## «Details» screen
This screen displays detailed information about the movie, including but not limited to the following:

 - Title
 - Release year
 - Genres
 - Director
 - Screenwriter
 - Main actors
 - Link to IMDB
 - Link to movie trailer on YouTube
