# StudyBuddy

StudyBuddy is a collaborative platform developed to facilitate the exchange of experiences and questions among students and between students and teachers. It aims to enhance learning through community engagement and direct communication. This repository focuses on the chat functionality, which was my primary responsibility in the team project.

## Key Features and Responsibilities

- **Authentication Process:** Implemented a robust authentication mechanism using Google's Firebase Firestore. This process includes user registration and login, ensuring secure access to the platform.

<p align="center">
  <img src="https://github.com/goge1221/Firebase-Messaging-App/assets/75140192/b932aac6-fe74-42e7-bf76-72adb9834fc8" alt="First Image" width="20%">
  <img src="https://github.com/goge1221/Firebase-Messaging-App/assets/75140192/e62fd5a5-64ad-4bc4-8acc-66957dab90d9" alt="Second Image" width="20%">
</p>

- **Chat Functionality:** Designed to enable seamless communication within the StudyBuddy platform, allowing users to exchange messages, share experiences, and ask questions in real-time.

<p align="center">
  <img src="https://github.com/goge1221/Firebase-Messaging-App/assets/75140192/0d9f47a8-d549-42e2-afe1-b4fb703f5300" alt="First Image" width="20%">
  <img src="https://github.com/goge1221/Firebase-Messaging-App/assets/75140192/7da7bb37-2527-4035-bec2-0da235d7c91d" alt="Second Image" width="20%">
  <img src="https://github.com/goge1221/Firebase-Messaging-App/assets/75140192/e89345a9-8cc1-40f4-bd8d-e1efe82a9f48" alt="Second Image" width="20%">
</p>

### Technology Stack

- **Programming Language:** Java.
- **Database and Authentication:** Firebase Firestore for managing user accounts and chat sessions.

### Chat System Design

- **Unique Chat IDs:** Each chat session is assigned a unique ID, along with designated sender and receiver information, ensuring messages are accurately routed.

- **Message Ordering:** Messages within a chat are organized chronologically, based on the time they were sent, facilitating a natural and intuitive conversation flow.

### Authentication and User Management

- **Registration Validation:** Incorporated checks during the registration process to verify the completeness and correctness of user inputs, including email format validation. Notifying of the users in form of a Toast message on the screen. 

- **Firebase Integration:** Utilized Firebase Firestore for storing user credentials, enabling subsequent logins with the same credentials.

- **Persistent Login State:** Used shared preferences to maintain user login status across app sessions, with the option to clear this state upon user logout.

### User Interface and Real-time Communication

- **RecyclerViews:** Leveraged RecyclerViews for displaying conversations and user lists, optimizing the UI for efficient interaction and navigation.

- **Real-time Messaging:** Integrated Firestore's snapshot listeners for the chat functionality, ensuring immediate update and display of messages as they are sent and received.
