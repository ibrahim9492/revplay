### 🎵 RevPlay – Console-Based Music Streaming Application

### 📌 Application Overview

RevPlay is a console-based music streaming application developed using Java that simulates the core functionalities of a real-world music streaming platform.

The application allows users to search and browse songs, artists, albums, playlists, and podcasts, manage favorites, track listening history, and simulate music playback using a text-based interface.

It also supports Musician / Artist roles, enabling artists to upload songs, create albums, manage profiles, and view play statistics.

The system is built using a modular, layered architecture, making it scalable and extendable for future migration into a web or microservices-based application.

### 🚀 Core Features

### 👤 User (Listener) Features

    User registration and login

    Search songs, artists, albums, playlists, and podcasts

    Browse content by genre, artist, and album

    Mark songs as favorites

    View favorite songs list

    Create public or private playlists

    Add / remove songs from playlists

    Update playlist details (name, description, privacy)

    Delete playlists created by the user

    View public playlists created by other users

Simulated music player controls (text-based):

    Play

    Pause

    View recently played songs

    Track complete listening history

### 🎤 Musician / Artist Features

    Register and login as an artist

    Create and manage artist profile

    Upload songs with metadata:

    Title

    Album

    Genre

    Duration

    View uploaded songs and albums

    Track play count and statistics

    View users who have favorite their songs

### Entity Relationship Diagram (ERD)

````
┌─────────────────────────────────────────────────────────────────────────────────────┐
│                               REVPLAY DATABASE ERD                                  │
│                                                                                     │
│  ┌─────────────────┐                ┌─────────────────┐                             │
│  │     USERS       │1             N │   PLAYLISTS     │                             │
│  │  ────────────   │◄───────────────│  ────────────   │                             │
│  │  PK user_id     │                │  PK playlist_id │                             │
│  │     username    │                │     name        │                             │
│  │     email       │                │     user_id (FK)│                             │
│  │     password    │                │     is_public   │                             │
│  └─────────┬───────┘                └─────────┬───────┘                             │
│            │                                  │                                     │
│            │1                                 │1                                    │
│            │                                  │                                     │
│  ┌─────────▼───────┐                  ┌───────▼─────────┐                           │
│  │    ARTISTS      │1               N │ PLAYLIST_SONGS  │                           │
│  │  ────────────   │◄─────────────────│  ────────────   │                           │
│  │  PK artist_id   │                  │  PK playlist_   │                           │
│  │  FK user_id     │                  │      song_id    │                           │
│  │    artist_name  │                  │  FK playlist_id │                           │
│  └─────────┬───────┘                  │  FK song_id     │                           │
│            │                          └─────────┬───────┘                           │
│            │ N                                  │1                                  │
│            │                                    │                                   │
│  ┌─────────▼────────────────────────────────────▼───────┐                           │
│  │                    SONGS                             │                           │
│  │                  ────────────                        │                           │
│  │                  PK song_id                          │                           │
│  │                     title                            │                           │
│  │                     duration                         │                           │
│  │                     genre                            │                           │
│  └──────────────────────┬───────────────────────────────┘                           │
│                         │1                                                          │
│                         │                                                           │
│                         │ N                                                         │
│            ┌────────────▼────────────┐                                              │
│            │     SONG_ARTISTS        │                                              │
│            │    ────────────         │                                              │
│            │    PK song_artist_id    │                                              │
│            │    FK song_id           │                                              │
│            │    FK artist_id         │                                              │
│            │       role              │                                              │
│            └────────────┬────────────┘                                              │
│                         │                                                           │
│                         │1                                                          │
│            ┌────────────▼────────────┐                                              │
│            │        ALBUMS           │                                              │
│            │       ────────────      │                                              │
│            │       PK album_id       │                                              │
│            │         album_name      │                                              │
│            │         artist_id (FK)  │                                              │
│            └────────────┬────────────┘                                              │
│                         │                                                           │
│                         │ N                                                         │
│            ┌────────────▼────────────┐                                              │
│            │      ALBUM_SONGS        │                                              │
│            │     ────────────        │                                              │
│            │     PK album_song_id    │                                              │
│            │     FK album_id         │                                              │
│            │     FK song_id          │                                              │
│            └─────────────────────────┘                                              │
│                                                                                     │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐ │
│  │ USER_FAVORITES  │  │ LISTEN_HISTORY  │  │  PLAYER_STATS   │  │ ARTIST_STATS    │ │
│  │  ────────────   │  │  ────────────   │  │  ────────────   │  │  ────────────   │ │
│  │ PK favorite_id  │  │ PK history_id   │  │ PK stat_id      │  │ PK artist_stat_ │ │
│  │ FK user_id      │  │ FK user_id      │  │ FK song_id      │  │      id         │ │
│  │ FK song_id      │  │ FK song_id      │  │   play_count    │  │ FK artist_id    │ │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  └─────────────────┘ │
│                                                                                     │
└──────────────────────────────────────────────────────────────────────────────────── ┘

````

### 🔐 Standard Functional Scope

Secure authentication for users and artists

Change password functionality

Forgot password recovery using security questions or hints

Secure credential handling using password utilities

### 🏗️ Architecture Overview

The application follows a layered architecture:

App Layer – Application entry point

Service Layer – Business logic

DAO Layer – Database access using JDBC

Model Layer – Entity classes

Utility Layer – Shared utilities

Config Layer – Database configuration

Exception Layer – Centralized exception handling

### 🗄️ Database Setup (IMPORTANT)

## 📌 Database Used

PL/SQL

Accessed using JDBC

## 📌 Step 1: Create Database

CREATE DATABASE revplay_db;

USE revplay_db;

## 📌 Step 2: Create Tables

# -- Users Table

CREATE TABLE users (

user_id INT AUTO_INCREMENT PRIMARY KEY,

username VARCHAR(100) UNIQUE NOT NULL,

email VARCHAR(100) UNIQUE NOT NULL,

password VARCHAR(255) NOT NULL,

role VARCHAR(20) NOT NULL,

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

# -- Songs Table

CREATE TABLE songs (

song_id INT AUTO_INCREMENT PRIMARY KEY,

title VARCHAR(100),

album VARCHAR(100),

genre VARCHAR(50),

duration INT,

release_date DATE,

artist_id INT,

play_count INT DEFAULT 0

);

# -- Playlists Table

CREATE TABLE playlists (

playlist_id INT AUTO_INCREMENT PRIMARY KEY,

user_id INT,

name VARCHAR(100),

description VARCHAR(255),

is_public BOOLEAN,

created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

# -- Playlist Songs Mapping

CREATE TABLE playlist_songs (

playlist_id INT,

song_id INT,

PRIMARY KEY (playlist_id, song_id)

);

# -- Favorites Table

CREATE TABLE favorites (

user_id INT,

song_id INT,

PRIMARY KEY (user_id, song_id)

);

# -- Listening History Table

CREATE TABLE listening_history (

history_id INT AUTO_INCREMENT PRIMARY KEY,

user_id INT,

song_id INT,

listened_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

);

## 📌 Step 3: Configure Database Connection

Update DBConfig.java or DBUtil.java with your database credentials:

private static final String URL = "jdbc:mysql://localhost:3306/revplay_db";

private static final String USERNAME = "root";

private static final String PASSWORD = "your_password";


Ensure MySQL server is running before starting the application.

### 📂 Project Structure

````
revplay/
│
├── .idea/                         # IntelliJ IDEA configuration files
├── .mvn/                          # Maven wrapper files
│
├── docs/                          # Project documentation
│   ├── Architecture.md            # Application architecture documentation
│   └── ERD.md                     # Entity Relationship Diagram documentation
│
├── logs/                          # Log files generated by Log4J
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.revplay/
│   │   │       ├── app/
│   │   │       │   └── RevPlayApplication.java   # Application entry point
│   │   │       │
│   │   │       ├── config/
│   │   │       │   └── DBConfig.java              # Database configuration
│   │   │       │
│   │   │       ├── dao/
│   │   │       │   ├── FavoriteDAO.java
│   │   │       │   ├── ListeningHistoryDAO.java
│   │   │       │   ├── PlaylistDAO.java
│   │   │       │   ├── SongDAO.java
│   │   │       │   └── UserDAO.java
│   │   │       │
│   │   │       ├── exception/
│   │   │       │   └── RevPlayException.java      # Custom exception handling
│   │   │       │
│   │   │       ├── model/
│   │   │       │   ├── Playlist.java
│   │   │       │   ├── Song.java
│   │   │       │   └── User.java
│   │   │       │
│   │   │       ├── service/
│   │   │       │   ├── FavoriteService.java
│   │   │       │   ├── ListeningHistoryService.java
│   │   │       │   ├── MusicService.java
│   │   │       │   ├── PlaylistService.java
│   │   │       │   └── UserService.java
│   │   │       │
│   │   │       └── util/
│   │   │           ├── DBUtil.java                 # JDBC utility methods
│   │   │           └── PasswordUtil.java           # Password encryption utilities
│   │   │
│   │   └── resources/
│   │       └── log4j2.xml                           # Log4J configuration
│   │
│   └── test/
│       └── java/
│           └── com.revplay/
│               ├── AppTest.java
│               ├── MusicServiceTest.java
│               └── UserServiceTest.java
│
├── target/                        # Maven build output directory
│
├── .gitignore
├── pom.xml                        # Maven project configuration
├── README.md                      # Project documentation
└── revplay.iml

````

### 🧪 Testing

Unit tests implemented using JUnit

Test cases cover:

    User services

    Music services

    Core business logic

### 🛠️ Technologies Used

````
| Technology     | Purpose               |
| -------------- | --------------------- |
| Java           | Core application      |
| JDBC           | Database connectivity |
| MySQL / PL/SQL | Relational database   |
| Maven          | Dependency management |
| JUnit          | Unit testing          |
| Log4J          | Logging               |
| Git            | Version control       |

````

### ✅ Definition of Done

Fully working console-based application

All core user and artist features implemented

Database integration completed

Modular and layered architecture

Unit tests included

Logging enabled

### 👨‍💻 Roles & Responsibilities

Designed and developed a console-based music streaming application using Java

Implemented authentication and authorization

Developed playlist, favorites, and listening history features

Enabled artist-specific song and album management

Integrated JDBC with MySQL

Wrote unit tests using JUnit

Configured logging using Log4J

Managed source code using Git

### 🔗 GitHub Repository

👉 Project Link:

https://github.com/ibrahim9492/revplay.git