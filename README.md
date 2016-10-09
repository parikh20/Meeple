# Meeple
Contributors: Ananth Kuchibhotla, Jaineek Parikh

NOTE: This project is still in progress. Keep checking for updates!

## About
Meeple is an Android application about connecting to and interacting with your local community. Users can freely post events or meet-ups, which can be seen by all other users within a certain geographic range. We created Meeple initially to provide an efficient and interesting user interface to our friend group's activities. 

## How It Works
To ensure security, we use an an account authentication service to store user account information. To store user data (post information, geographic location, etc), we use Google Firebase, a cloud-based NoSQL type database. For more information, please visit the Firebase website for official documentation: https://firebase.google.com/

Efficiently querying the database for each user's "local" events was difficult. We now use Geofire to query the realtime databse for events within a specified geographic range of the user. Geofire selectively loads only the data near certain locations, keeping any queries fast. Please refer to the documentation for more information: https://github.com/firebase/geofire-java

## Current Features
Meeple scrapes your geographic location and lists all the events (posted by other users) happening in your area. Users can select that they are going, adding the event to the "Your Events" tab. To view local events, Meeple gives you multiple views: users see a list of posts or can choose to view a map, which displays their set geographic region and any posts in the area. 
 
 ![Alt text](screenshots/resized/myFeed1.png?raw=true) ![Alt text](screenshots/resized/map1.png?raw=true)


 

Selecting the map markers pulls up a summary of each post, and clicking on each summary will bring users to the event's page. On the event page, eventgoers will see more information (the date, time, location, etc.), can RSVP, and can easily find directions from their current location!

  ![Alt text](screenshots/resized/map2.png?raw=true) ![Alt text](screenshots/resized/viewPost1.png?raw=true)

## Upcoming Features

1. **Groups** - Each post will now be associated with a group: Public or a named Private group. Users will be able to create groups, add  other user's to it, and request to join groups. When finding events for a particular user, Meeple will display posts only of that user's groups and Public posts. This will make event hosting more personal and efficient for each user, eliminating the hassle of looking through all the local posts for interesting events.

2. **Comments** - Users will be able to leave comments on event pages, allowing for discussion between eventgoers. This will make it easier to connect with other attendees and allow event-hosters to make mass anouncements.

3. **Autologin** - We are working on saving encrypted user information to prevent the user from having to login each time he/she opens Meeple.

4. **Chat** - We are implementing a chatting interface between users using Google Firebase's cloud-based notification system. Users will be able to private message other users and eventgoers.  However, this feature is still tentative and in development. 







