#flyway管理DB命令
flyway: manager database
mvn flyway:migrate
mvn flyway:clean

#dbunit创建数据
dbunit:manager data
mvn dbunit:operation

#maven执行install跳过测试命令
mvn install -Dmaven.test.skip=true