# IMDB API Rest Game

## Screen

![alt text](https://github.com/rafarocha/imdb/blob/master/screenshots/screen1.png?raw=true)

## How to Use

1. Run Spring Project
2. Create an Account. POST Method without payload on http://localhost:8080/users/{user}/{role}/{pswd}
3. Enter the new game at http://localhost:8080/game/new
4. It takes less than 10 seconds to load the game.
5. Follow the instructions in the message JSON.
6. Review each card's data, one movie for each.
7. Click on the poster URL to see cover film.
8. Then, after deciding your choice, you should click on the card link, in commandVote attribute.
9. You can't miss more than 3 times. There are 5 attempts. At the end, you will see your score.
10. Good luck!

## Setup
- API Swagger http://localhost:8080/swagger-ui.html
- H2 Console http://localhost:8080/h2-console
- Integration API Reference https://www.omdbapi.com/
  - Generate your own API key to use. Limit at 1K/day. 
  - Insert on application.properties `app.imdb.apikey=[your-key]`
- Import insomnia file
- Improvements or Bugs in tickets e todolist files

## Disclaimer
- This project is not a reference or with good performance.
- It was purposely developed as a monolith.
- This monolith will be the base project for transition to microservices.
- Additionally, some performance improvement analyzes will be addressed.
