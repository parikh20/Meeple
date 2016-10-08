# Meeple
Contributors: Ananth Kuchibhotla, Jaineek Parikh

NOTE: This project is still in progress. Keep checking for updates!

## About
Meeple is an Android application about connecting to and interacting with your local community. Users can freely post events or meet-ups, which can be seen by all other users within a certain geographic range. We created Meeple initially to provide an efficient and interesting user interface to our friend group's activities. 

## How It Works
To ensure security, we use an an account authentication service to store user account information. To store user data (post information, geographic location, etc), we use Google Firebase, a cloud-based NoSQL type database. For more information, please visit the Firebase website for official documentation: https://firebase.google.com/

Efficiently querying the database for each user's "local" events was difficult. We now use Geofire to query the realtime databse for events within a specified geographic range of the user. Geofire selectively loads only the data near certain locations, keeping any queries fast. Please refer to the documentation for more information: https://github.com/firebase/geofire-java

## See Local Events
![Alt text](screenshots/myfeed1small.png?raw=true) ![Alt text](screenshots/map1small.png?raw=true)

