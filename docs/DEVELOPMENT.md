#이 문서는 서버가 폭파되었을 때 사용할 문서로 개발에 필요한 환경과 배포를 위한 환경 설정을 포함한다

1. Server Instance Environment
- main instance : 2CPU 8GB RAM + EIP
- DB : Saving the cost + Postgre SQL(Discourse와 DB를 맞추기 위함)

2. Firewall setting
- main instance -> DB 5432 Port(for Postgre SQL)

# Init main instance setting

Install npm
```
sudo apt-get update
sudo apt-get -y install nodejs
sudo apt-get -y install npm
sudo apt-get install nodejs-legacy
npm install extract
npm install bzip2
npm install phantomjs

```

Install JDK 1.8
```
sudo apt-get purge openjdk*
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

```

Install Docker & Docker compose
```
wget -qO- https://get.docker.com/ | sh
```

Install nginxProxy
```
sudo docker run -d -p 80:80 -v /var/run/docker.sock:/tmp/docker.sock:ro jwilder/nginx-proxy
```


Install Jhipster & relative items
```
sudo npm install -g yo &
sudo npm install -g bower &
sudo npm install -g gulp-cli &
sudo npm install -g generator-jhipster &
bower install ngInfiniteScroll
sudo npm install --save ng-infinite-scroll
bower update

```

clone the git repo
```
git clone https://Cazen@bitbucket.org/Cazen/itinterview.git
cd itinterview
rm -rf node_modules/
npm install
./gradlew installGulp
```

Checking the local server(dev env)
```
./gradlew
```

Checking the docker server(prd env)
```
./gradlew bootRepackage -Pprod buildDocker
docker-compose -f src/main/docker/app.yml up
```
