# Moove
A simple application to display Movies from the Movie DB API

### App Architecture:
MVVM Architecture was used because of the following reasons;
- No tight coupling between view and ViewModel unlike the mvp architecture.
- The ViewModel has no reference to the view unlike the presenter in mvp and this prevents cases of memory leaks.
- There is a 0ne-to-many relationship between view and ViewModel.
- Easy to test since the business logic is separated from the UI.

### Libraries Used for project:

The following Libraries were used for this project;

- Retrofit 2 (for network calls)
- Okhttp3 (logging Interceptor)
- Hilt (for Dependency Injection)
- Glide
- Coroutine
