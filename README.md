# Project Description: Change Your Life

## Project Goal  
Developing an application for job searching with features like filtering, adding jobs to favorites, and viewing job details.  

## Key Features  
1. **Job Listings**  
   - Paginated list of job vacancies.  
   - Ability to load more jobs as the user scrolls down.  
   
2. **Job Filtering**  
   - Search by regions and industries.  
   - Select a country, region, or city to refine filter parameters.  
   - Save filter settings for future use.  

3. **Favorites**  
   - Add jobs to favorites for quick access.  
   - Manage favorite jobs: add, remove, and view.  

4. **Job Details View**  
   - Display detailed information about a job (title, description, salary, requirements).  
   - Fetch data from the network with a fallback to the local database in case of an error.  
   - Handle errors, including removed jobs (404).  

5. **Integration with the HeadHunter API**  
   - Fetch data on jobs, countries, regions, and cities.  
   - Support REST API using the Retrofit library.  

## Tech Stack  
- **Programming Language:** Kotlin  
- **Architecture:** MVVM  
- **Network Requests:** Retrofit  
- **Database Management:** Room  
- **Asynchronous Operations:** Kotlin Coroutines  
- **Image Loading:** Glide  

## Additional Features  
- Local data caching for offline access.  
- A user-friendly and intuitive interface with support for light and dark themes.  

## Planned Improvements  
- Transitioning from XML layouts to Jetpack Compose for UI development.  
- Refactoring the project to improve dependency injection using Dagger 2. 
