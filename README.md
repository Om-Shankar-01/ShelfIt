# ShelfIt 

*An Android application for discovering and browsing books, powered by the Google Books API.*

It provides a clean and intuitive interface for users to search for any book and view its details like page count, average rating, authors, language and more. The app is designed with a focus on solid architecture and a smooth user experience.

## Screenshots
<table align="center">
  <tr>
    <td align="center">
      <img src="/screenshots/screenshot_1.jpg" alt="Main search screen" width="300"/>
      <br />
      <sub><b>Main Search Screen</b></sub>
    </td>
    <td align="center">
      <img src="/screenshots/screenshot_2.jpg" alt="Results screen" width="300"/>
      <br />
      <sub><b>Results Screen</b></sub>
    </td>
    <td align="center">
      <img src="/screenshots/screenshot_3.jpg" alt="Book details screen" width="300"/>
      <br />
      <sub><b>Book Details Screen</b></sub>
    </td>
    <td align="center">
      <img src="/screenshots/screenshot_4.jpg" alt="Book details screen" width="300"/>
      <br />
      <sub><b>Book Details Screen</b></sub>
    </td>
  </tr>
</table>

## Features

- **Book Search**: Search for books by title, author, or any keyword.  
- **Results Display**: View search results in a clean, scrollable list with book thumbnails.  
- **Detailed View**: Get comprehensive details for any book, including its description, authors, publisher, ratings, and more.  
- **Dynamic UI**: Fully built with Jetpack Compose, offering a modern and reactive user experience.  
- **State Handling**: Gracefully manages loading, error, and empty search result states to keep the user informed.  


## Architecture & Tech Stack

ShelfIt follows the **MVVM (Model-View-ViewModel)** architecture pattern for a clean separation of concerns and scalability.  

- **UI Layer**: Built entirely with Jetpack Compose for a declarative, modern UI.  
- **Networking**: Retrofit + OkHttp for efficient network requests to the Google Books API.  
- **Asynchronous Operations**: Kotlin Coroutines for smooth background thread management.  
- **Dependency Injection**: Manual DI approach via an `AppContainer`.  
- **Image Loading**: Coil for efficient image loading and caching in Compose.  


## Installation Guide

Follow these steps to build and run the app locally:

### 1. Clone the repository
```bash
git clone https://github.com/your-username/ShelfIt.git
```

### 2. Open in Android Studio

Open the cloned repository in the latest stable version of **Android Studio**.

### 3. Get a Google Books API Key

This project requires a Google Books API key. You can obtain one from the [Google Cloud Console](https://console.cloud.google.com/).

* Create a new project.
* Go to **APIs & Services > Library** and enable the **Google Books API**.
* Go to **APIs & Services > Credentials** and create a new API key.

### 4. Add the API Key

Create a `local.properties` file in the root of the project (if it doesn't already exist) and add your key:

```properties
BOOKS_API_KEY="YOUR_API_KEY"
```

The project is set up to automatically read this key.

### 5. Build and Run

Sync the Gradle files and run the app on an Android Emulator or a physical device.

