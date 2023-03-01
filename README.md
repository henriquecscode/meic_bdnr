# Orient DB

### Container 

Instructions on running orientDb in a docker container.
The docker documentation can be found here: https://hub.docker.com/_/orientdb

#### How to create and run the image:

First create a database folder where you are running the docker container

```
docker run -d --name orientdb -p 2424:2424 -p 2480:2480 \
    -v database:/orientdb/databases \
    -e ORIENTDB_ROOT_PASSWORD=rootpwd \
    -e ORIENTDB_NODE_NAME=odb1 \
    orientdb
```

#### How to run the console:
```
docker run --rm -it orientdb /orientdb/bin/console.sh
```
Alternatively, go to the docker terminal to start the console inside the container
```
cd bin
./console.sh
```


#### How to run the ETL:
```
docker run  --rm -it -v <config_path>:/orientdb/config orientdb /orientdb/bin/oetl.sh ../config/oetl-config.json
```

### Operation

After Orient is running you should be able to access it on 
localhost:2480 (unless you have changed the base configurations)

First however, you need to create a database to which we can add data to and operate on.

Before all open the console.

Next connect to a server instance. Remember, since OrientDB can be distributed, we can perform operations from any of the available servers
```
CONNECT remote:localhost root rootpwd
```

Create a database. Choose any name. In this case we chose `mydb`
```
create database plocal:/orientdb/databases/mydb
```
The default credentials will be admin admin. 
The path chosen is also the path you must use when you connect to the database in the next steps

Connect to the database
```
connect remote:localhost/orientdb/databases/mydb admin admin
connect remote:localhost/orientdb/databases/test1 admin admin
```