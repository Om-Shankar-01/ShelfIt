# ShelfIt
ShelfIt App is a simple Android application built using **Jetpack Compose** and the **Google Books API**.  

It allows users to search for books by entering a query term and displays the results in an interactive grid layout with thumbnails. Users can tap on a book to view its details.

## Features
•	**Search Functionality**: Type in a query to search for books using the Google Books API.  
•	**Dynamic Results**: Displays search results as a grid with thumbnail images fetched dynamically.  
•	**Book Details**: View more information about a book, such as title, author, and description.  


## Installation
> Follow these steps to set up and run the Bookshelf App on your local machine:

1. **Clone the Repository**  
   Open your terminal and run the following command to clone the repository:  
   ```bash
   git clone https://github.com/{yourusername}/bookshelf-app.git  
   ```
2.	**Open in Android Studio**  
	•	Launch Android Studio.  
	•	Open the cloned project by selecting File > Open and navigating to the project folder.  

3.	**Add Your Google Books API Key**  
	•	Obtain your API key from the Google Books API Console.  
	•	Open the gradle.properties file in the project.  
	•	Add the following line, replacing your_api_key_here with your actual API key:  
    ```
    apiKey = your_api_key_here
    ```

4.	**Sync the Project**  
	•	In Android Studio, click on File > Sync Project with Gradle Files to download dependencies.

5.	**Run the App**  
	•	Connect your physical Android device or start an emulator.  
	•	Click the green Run button in Android Studio to build and launch the app.


## Key Components
1.	UI Layer  
	•	Built using Jetpack Compose.  
	•	Features composables for search, grid layout, and book details.  

2.	ViewModel  
	•	Handles user interactions and business logic.  
	•	Fetches data from the repository and exposes state to the UI using StateFlow.

3.	Repository  
	•	Manages data operations, including network requests to the Google Books API.  
    
4.	API Integration  
	•	Uses Retrofit for API calls to fetch book data and details.