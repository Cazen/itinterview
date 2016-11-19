docker-compose -f src/main/docker/app.yml stop &
./gradlew bootRepackage -Pprod buildDocker
nohup docker-compose -f src/main/docker/app.yml up &
