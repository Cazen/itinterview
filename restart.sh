docker stop $(docker ps -q --filter ancestor=itinterview )
./gradlew bootRepackage -Pprod buildDocker
docker run -d -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod,swagger" -e "SPRING_DATASOURCE_URL=jdbc:postgresql://10.146.0.5:5432/itinterview" -e "JHIPSTER_SLEEP=10" -e "VIRTUAL_HOST=itinterview.co.kr,www.itinterview.co.kr" itinterview
