模板工程安装
===========================================================

安装命令
------------------
参考 http://maven.apache.org/archetype/maven-archetype-plugin/examples/create-with-property-file.html
    
    mvn clean:clean archetype:create-from-project -Darchetype.properties=./archetype.properties
    
    cd target\generated-sources\archetype
    
    mvn install
  
IDEA添加原型
------------------
    <groupId>com.softfn.dev</groupId>
    <artifactId>softfn-dubbo-archetype</artifactId>
    <version>1.0.0-SNAPSHOT</version>

IDEA删除原型
------------------
    JetBrains\IntelliJ IDEA 14.1.3\.IntelliJIdea\system\Maven\Indices\UserArchetypes.xml
    
    