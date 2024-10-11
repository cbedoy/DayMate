# DayMate

### Description
Personal Assistant App is an Android application designed to simplify and organize daily tasks that require constant tracking and easy access. This project is intended for personal use, aimed at optimizing document management, reminders, shopping lists, and expense projections, including credit card payments.

### Features
- **Management of bureaucratic documents**: Quick and easy access to important documents needed for bureaucratic processes.
- **Keywords and important memories**: Store and search for key information or memories that you need to keep at hand.
- **Constant expense projection**: View your daily, weekly, or monthly expenses clearly and simply.
- **Credit card payment management**: Track your credit card expenses and payments in detail, including future payment projections.
- **Shopping lists**: Create and organize shopping lists for easy access.
- **Daily reminders**: Set reminders for tasks such as practicing musical instruments or other personal activities.

### Tech Stack
- **Kotlin**: Main programming language.
- **Jetpack Compose**: Declarative UI library used to build modern, fast, and fluid user interfaces.
- **MVVM (Model-View-ViewModel)**: Architecture used to separate presentation logic and business logic.
- **Room**: Local data persistence to store documents and expense records offline.
- **Dagger**: Dependency injection to keep the code clean, modular, and easy to test.
- **Coroutines & Flow**: Efficient handling of asynchronous operations for database and network tasks.
- **Retrofit**: To manage API calls, for example, to fetch product quotes or bank rates.
- **Navigation Component**: Safe and modular navigation management between different screens of the app.
- **Data Binding**: Used to bind UI components with the ViewModel and data sources, ensuring the UI reacts to data changes.

### Architecture
The project follows the **MVVM (Model-View-ViewModel)** pattern, allowing a clear separation between business logic and UI, making the project maintainable and scalable.

#### Project Structure:

