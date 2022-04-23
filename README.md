# IMDB API Rest Game

## Screen

![alt text](https://github.com/rafarocha/imdb/blob/master/screenshots/screen1.png?raw=true)

## How to Use

1. Run Spring Project and enter http://localhost:8080/game/new
2. Follow the instructions at the message JSON
3. Review 2 movies an each card
4. Click on poster URL to see cover film
5. Click on link commandVote attribute to cards 1 or 2
6. You have 5 steps and then you will see your score
7. Explore code

## Setup
- API Swagger http://localhost:8080/swagger-ui.html
- H2 Console http://localhost:8080/h2-console
- Integration API Reference https://www.omdbapi.com/
  - Generate your own API key to use. Limit at 1K/day. 
  - Insert on application.properties `app.imdb.apikey=[your-key]`
- Improvements or Bugs in tickets e todolist files

## Disclaimer
- This project is not a reference or with good performance.
- It was purposely developed as a monolith.
- This monolith will be the base project for transition to microservices.
- Additionally, some performance improvement analyzes will be addressed.