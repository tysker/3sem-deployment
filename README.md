1. copy jar file to server
   https://www.cyberciti.biz/faq/how-to-copy-and-transfer-files-remotely-on-linux-using-scp-and-rsync/
   scp filename username@destination-remote-host:/path/to/dir
2. Install maven and java on server
   - apt install openjdk-17-jre-headless
   - apt install maven
3. Run postgresql on server

```yaml
services:
  postgres:
    image: postgres:latest
    container_name: postgres_container
    restart: unless-stopped
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    volumes:
      - ./data:/var/lib/postgresql/data/
    ports:
      - "5432:5432"
```

4. create database inside docker container
```bash  
docker exec -it postgres_container psql -U postgres
CREATE DATABASE mydatabase;
```

5. enable ufw on server to allow port 7070
```bash
ufw allow 7070
```

6. Run jar file on server
```bash
java -jar filename.jar
```

7. Use a http file or postman to test the api
```bash
http://server-ip:7070/api/v1/xxx
```