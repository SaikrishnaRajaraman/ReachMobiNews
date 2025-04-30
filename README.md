# NewsMobi

A lightweight, data-driven news application named **NewsMobi**, built as an Android Developer case study for ReachMobi’s Mobile App team. This README outlines the project structure, features, and usage instructions.


## Features

- **Search & Browse**: Fetch and display the latest news headlines from NewsAPI.
- **Pagination**: Efficiently load paginated data from the API using Paging3 library.
- **Manual Refresh**: Manual button to reload data.
- **Detail View**: Tap a headline to view full title, source, publication date, and open the article in a browser.
- **Error Handling**: Gracefully handle network errors and invalid API keys.
- **Favorites**: Save and manage a list of favorite articles. *(Bonus)*
- **Dark Mode**: Support for system-wide dark theme. *(Bonus)*

## Architecture & Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Toolkit**: Jetpack Compose
- **Asynchronous**: Kotlin Coroutines + Flow
- **Navigation**: Compose Navigation
- **State Management**: Android ViewModel
- **Analytics**: Firebase Analytics

## Analytics Events

The following Firebase Analytics events are tracked throughout the app:

```kotlin
const val EVENT_OPEN_ARTICLE = "OPEN_ARTICLE"
const val EVENT_OPEN_ALL_HEADLINES = "OPEN_ALL_HEADLINES"
const val EVENT_OPEN_FAVORITES = "OPEN_FAVORITE"
const val EVENT_ADD_TO_FAVORITE = "ADD_TO_FAVORITE"
const val EVENT_REMOVE_FAVORITE = "REMOVE_FAVORITE"
const val EVENT_REFRESH_FEED = "REFRESH_FEED"
const val EVENT_API_ERROR = "API_ERROR"
const val EVENT_SEARCH_FEED = "SEARCH_FEED"
```

## Usage

- Launch the app to see the latest headlines.
- Use the search bar to filter articles by keyword.
- Tap the refresh icon to reload the news feed.
- Tap a headline to view details and open in browser.
- Save articles to favorites and access them via the favorites screen.

## API Reference

- **NewsAPI Documentation**: [https://newsapi.org/docs](https://newsapi.org/docs)
- **Authentication**: Generate an API key at [https://newsapi.org/register](https://newsapi.org/register)

## Screenshots
<img src="https://github.com/user-attachments/assets/82c86c53-86d8-4667-bc40-51b334b85697" alt="Light mode screenshot 1" width="250"/>
<img src="https://github.com/user-attachments/assets/64e5e267-9c01-4cd8-b2f3-8b6bd81ea4a0" alt="Light mode screenshot 2" width="250"/>
<img src="https://github.com/user-attachments/assets/b5917727-c05e-4f91-b11f-e3adec974bb5" alt="Light mode screenshot 3" width="250" />



### Dark Mode 

<img src="https://github.com/user-attachments/assets/ca461345-85b6-4509-9a66-34a27601efe0" alt="Dark mode screenshot 1" width="250"/>
<img src="https://github.com/user-attachments/assets/a145d284-91ee-4425-9246-6b9ccd788a55" alt="Dark mode screenshot 2" width="250"/>
<img src="https://github.com/user-attachments/assets/b81eab09-133d-413e-bf62-6474dc644496" alt="Dark mode screenshot 3" width="250"/>






## License

This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

---
