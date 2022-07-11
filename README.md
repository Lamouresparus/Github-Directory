# GreyAssessment
GreyAssessment is an Android application that searches for Github Repositories and Users from the GitHub API (Application Programming Interface). The app has four screens Home, Repository, Users, and UserDetail.
This application was an assessment for an Android Developer position at Grey.

## Screenshot
<img width="306" alt="Screenshot 2022-07-11 at 05 37 10" src="https://user-images.githubusercontent.com/34775925/178189480-3581f815-60f2-4fd8-8397-570e24b3fc7f.png"> <img width="306" alt="Screenshot 2022-07-11 at 05 36 46" src="https://user-images.githubusercontent.com/34775925/178189443-95eefe7c-f57c-4c96-abc7-124ce94f8ddd.png"> <img width="306" alt="Screenshot 2022-07-11 at 05 40 43" src="https://user-images.githubusercontent.com/34775925/178189820-eaa198e4-c728-4599-ada4-9fc1181ec6d1.png">
<img width="306" alt="Screenshot 2022-07-11 at 05 39 05" src="https://user-images.githubusercontent.com/34775925/178189646-97d2f129-de1c-496c-9e3f-c974767ec5e0.png"> <img width="306" alt="Screenshot 2022-07-11 at 05 47 34" src="https://user-images.githubusercontent.com/34775925/178190626-d74a507c-edfd-4ed4-93a2-0bd910a4b815.png"> <img width="306" alt="Screenshot 2022-07-11 at 05 48 06" src="https://user-images.githubusercontent.com/34775925/178190684-d443cb39-3051-48f2-94c5-655ff25b9513.png">

## Libraries used:
-  Kotlin
-  XML - UI
-  Hilt  -  dependency injection
-  Retrofit - HTTP Request
-  Kotlin Coroutines  - for asynchronous tasks
-  Coil - image loading
-  MockK - creating mocks for tests
-  Turbine - testing coroutines Flow


## Installation
1. Download the file or clone the repo and
2. Open it in Android Studio

### Note 
Making multiple sucessive API calls may lead to a 403 error. This is caused by Github’s rate limiting. Authenticated requests get a higher rate limit. Check out the documentation for more details https://docs.github.com/rest/overview/resources-in-the-rest-api#rate-limiting .


## Architecture and Design
I used Model View View-Model (MVVM), repository pattern, and clean architecture. I used all 3 together to achieve a more structured code and less tight coupling between components. At a high level, I have 3 layers in the project which I have implemented as packages, all in one module. These layers include:
1. `Data Layer:` Responsible for getting data from any source, whether the local source or remote source. For this project, I implemented only the remote source(GitHub API) with Retrofit.
2. `Domain layer:` Houses the core business logic of the app, interacting with the data layer via an abstraction(Repository Interface), afterward returning data to the UI layer.
3. `UI layer:` Responsible for displaying content on the screen. The View-Model in this layer is responsible for interacting with the domain layer via a use-case class to send and retrieve data, based on user actions.



## Improvements
1. Local Source using Room: If I had more time, Aadding a local source would improve the User Experience(UX) of the application. Users can always have content displayed when they use the app for a second time or maybe when they have no internet connection.
2. Multi Modules: Currently there is only one module that manages everything. We can add more modules that would help extract all the layers and features into their separate module. While this might be cumbersome, it has huge benefits such as increased build times, separation of concerns, etc.
3. UX: The app can capture more user content and notify user's internet availability
4. Unit Test: A higher test coverage would be nice to have
5. Pagination: Currently, the user can only scroll through 30 items on the list. Implementing pagination would load more items to the screen when the user scrolls to the end of the current list.



## Resources
- https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/
- https://github.com/cashapp/turbine
- https://codingwithmohit.com/coroutines/learning-shared-and-state-flows-with-tests/
- https://fabiosanto.medium.com/unit-testing-coroutines-state-flow-c6e6de580027
