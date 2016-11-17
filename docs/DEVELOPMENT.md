#이 문서는 개발을 위해 작성된 문서로 개발에 필요한 환경과 배포를 위한 환경 설정을 포함한다

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

Install nodejs
```
sudo yum -y install nodejs
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
```



