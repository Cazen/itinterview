#이 문서는 서버가 폭파되었을 때 사용할 문서로 개발에 필요한 환경과 배포를 위한 환경 설정을 포함한다

1. Server Instance Environment
- main instance : 2CPU 8GB RAM + EIP
- DB : Saving the cost + Postgre SQL(Discourse와 DB를 맞추기 위함)

2. Firewall setting
- main instance -> DB 5432 Port(for Postgre SQL)

# Init main instance setting

Install git
```
sudo yum -y install git
```

Install npm
```
rpm -Uvh https://dl.fedoraproject.org/pub/epel/epel-release-latest-7.noarch.rpm
sudo yum -y install npm
```

Install nodejs and related resources
```
sudo yum -y install nodejs
npm install extract
npm install bzip2
sudo yum -y install bzip2
npm install phantomjs

```

Install JDK 1.8
```
sudo yum -y install wget
wget --header "Cookie: oraclelicense=accept-securebackup-cookie" http://download.oracle.com/otn-pub/java/jdk/8u102-b14/jdk-8u102-linux-x64.rpm
sudo yum -y localinstall jdk-8u102-linux-x64.rpm
rm jdk-8u102-linux-x64.rpm
```

Install Docker & Docker compose
```
sudo yum -y update
sudo tee /etc/yum.repos.d/docker.repo <<-'EOF'
[dockerrepo]
name=Docker Repository
baseurl=https://yum.dockerproject.org/repo/main/centos/7/
enabled=1
gpgcheck=1
gpgkey=https://yum.dockerproject.org/gpg
EOF
sudo yum -y install docker-engine
sudo systemctl enable docker.service
sudo systemctl start docker
sudo docker run --rm hello-world
sudo curl -L "https://github.com/docker/compose/releases/download/1.8.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version
sudo groupadd docker
sudo gpasswd -a ${USER} docker
export DOCKER_HOST=tcp://127.0.0.1:2375
vi /usr/lib/systemd/system/docker.service
And edit this rule to expose the API :
ExecStart=/usr/bin/docker daemon -H unix:// -H tcp://localhost:2375
sudo service docker restart
```

Install nginxProxy
```
docker run -d -p 80:80 -v /var/run/docker.sock:/tmp/docker.sock:ro jwilder/nginx-proxy
```


Install Jhipster & relative items
```
sudo npm install -g yo &
sudo npm install -g bower &
sudo npm install -g gulp-cli &
sudo npm install -g generator-jhipster &
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
