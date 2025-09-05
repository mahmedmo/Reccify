# reccify
# Music Recommendation App

Welcome to Reccify! This Android application, developed using Java on Android Studio, provides personalized music recommendations based on user preferences and Spotify data.

## Features

- **Spotify Integration:** The app checks if Spotify is installed on the user's device. If not, the user will be redirected to the Google Play Store to install it.
- **Spotify Authorization:** Users are required to authorize the app's access to their Spotify data using Spotify's Web API.
- **User Authentication:** Users can create an account or log in. First-time users set their genre preferences during account creation.
- **Personalized Recommendations:** The app uses a custom algorithm that assigns a score to each genre based on user preferences. Songs are recommended using a random probability distribution determined by these scores.
- **Genre Score Management:** Users can like or dislike songs, adjusting genre scores accordingly. Liked songs can be saved directly to the user's Spotify "Liked Songs" playlist.
- **Artist Preferences:** Users can explore more songs from their favorite artists.
- **Statistics:** Users can view statistics related to their preferences, such as favorite genres and saved Spotify songs.

## How It Works

1. **Spotify Check:**
   - The app checks if Spotify is installed on the device.
   - If not, the user is redirected to the Google Play Store to install Spotify.
   
2. **Spotify Authorization:**
   - Users authorize the app to access their Spotify data through Spotify's Web API.
   
3. **User Preferences:**
   - During account creation, users set their genre preferences. Each genre starts with a default score.
   
4. **Recommendation Algorithm:**
   - The app uses a random probability distribution based on genre scores to recommend songs.
   - Liked/disliked songs adjust genre scores, affecting future recommendations.
   
5. **Song Management:**
   - Liked songs can be saved directly to the user's Spotify "Liked Songs" playlist.
   - Users can explore more songs from their favorite artists.
   
6. **Statistics:**
   - Users can view personalized statistics, such as favorite genres and saved songs.

## Getting Started

1. **Prerequisites:**
   - Android Studio
   - Spotify Account

2. **Installation:**
   - Clone the repository.
   - Open the project in Android Studio.
   - Run the app on an Android emulator or physical device.

3. **Spotify Authorization:**
   - Sign in to your Spotify account in the app to authorize access to Spotify data.

4. **Explore and Enjoy:**
   - Set your preferences, discover new music, and enjoy personalized recommendations!

## Screenshots

## Welcome Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/welcome.png "Welcome Page")
## Auth Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/auth.png "Auth Page")
## Login Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/login.png "Login Page")
## Preference Selection Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/pref.png "Preferences Page")
## Home Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/home.png "Home Page")
## Help Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/help.png "Help Page")
## Account Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/account.png "Account Page")
## Saved Songs Page
![alt text](https://github.com/mahmedmo/reccify/blob/master/images/savedsongs.png "Saved Songs Page")


