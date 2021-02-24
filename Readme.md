# Redis Docker configuration
## Prepare and run Redis container
> docker pull redis

> docker run --name redis -p 6379:6379 -d redis
## Check keys in Redis
> docker exec -it redis /bin/bash
### Inside container:
> redis-cli

> KEYS *
## Remove container
> docker container ls
 
> docker kill [CONTAINER_ID]
 
> docker rm [CONTAINER_ID]