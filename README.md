# nbj-mybatis-generator-plugins

基于mybatis-generator-maven-plugin规范，实现其插件功能，包括分页查询、增加RPC注解等功能

## 背景

在一个项目里，有多人进行开发，但是生成的mybatis文件（实体类、实体类Example、Mapper.java、Mapper.xml）各不相同。

其原因在于，团队成员所使用的**IDE**（IDEA、Eclipse）、**版本类型**（旗舰版、破解版）、**版本号**（2018.1/2018.2/2019.1/2019.2/2019.3...）、**生成工具**（better-mybatis-generator、free-xxx、maven-xxx）等等均可能不相同。

## 解决方案

1. 使用与个人开发环境无关的配置（maven pom配置）
2. 使用易获取、可拓展的插件工具（org.mybatis.generator:mybatis-generator-maven-plugin）
3. **实现插件工具的业务定制拓展功能（本项目的存在意义）**

## 拓展功能支持（mybatis-generator-plugins）

1. **分页功能**：nbj.maven.mybatis.generator.plugins.MBGLimitPlugin。官方插件有弊端，为查全表再分页，完全没用到 limit 关键字，不适用表数据量大的应用。
2. **重命名WithBlobs方法**：nbj.maven.mybatis.generator.plugins.MBGRenameBlobsPlugin。为了减少使用WithoutBlobs和WithBlobs两种实体类，如 User和UserWithBlobs之间的操作出错可能，将WithBlobs相关方法名（实体类可以通过设置defaultModelType="flat"来合并）进行重命名。
3. **实体类增加无参注解**：nbj.maven.mybatis.generator.plugins.MBGDomainAnnotationPlugin。为了减少人工手动手动追加注解的麻烦，拓展该功能。如追加 @lombok.Data @lombok.NoArgsConstructor @lombok.AllArgsConstructor @lombok.Builder

## 使用方法（Quick Start）

1. 安装依赖（暂时还没有发布到公共maven仓库，需要手动安装）

   ```shell
   git clone https://github.com/nextbin/nbj-mybatis-generator-plugins.git
   git checkot v0.0.1
   mvn clean install
   ```

2. 修改 pom.xml，新增 build.plugins.plugin 配置插件 org.mybatis.generator:mybatis-generator-maven-plugin，如已配置，则需新增依赖 github.nextbin.maven:nbj-mybatis-generator-plugins。

  ```xml
  <build>
    <plugins>
      <plugin>
        <groupId>org.mybatis.generator</groupId>
        <artifactId>mybatis-generator-maven-plugin</artifactId>
        <version>1.4.0</version>
        <configuration>
          <configurationFile>${basedir}/src/main/resources/mybatis/generatorConfig.xml</configurationFile>
          <overwrite>true</overwrite>
          <verbose>true</verbose>
        </configuration>
        <dependencies>
          <dependency>
            <groupId>github.nextbin.maven</groupId>
            <artifactId>nbj-mybatis-generator-plugins</artifactId>
            <version>0.0.1</version>
          </dependency>
          <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.19</version>
          </dependency>
        </dependencies>
      </plugin>
    </plugins>
  </build>
  ```

2. 新增 src/main/resources/mybatis/generatorConfig.xml，如已配置，则需新增拓展插件。

   ```xml
   <!DOCTYPE generatorConfiguration PUBLIC
       "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
       "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
   <generatorConfiguration>
     <context id="myBatis3" targetRuntime="MyBatis3" defaultModelType="flat">
   
       <property name="javaFileEncoding" value="UTF-8"/>
       <property name="javaFormatter" value="org.mybatis.generator.api.dom.DefaultJavaFormatter"/>
       <property name="xmlFormatter" value="org.mybatis.generator.api.dom.DefaultXmlFormatter"/>
   
       
       <!--  nbj定制插件：分页  -->
       <plugin type="nbj.maven.mybatis.generator.plugins.MBGLimitPlugin"/>
       <!--  nbj定制插件：重命名查询方法的 WithBLOBs  -->
       <plugin type="nbj.maven.mybatis.generator.plugins.MBGRenameBlobsPlugin"/>
       <!--  nbj定制插件：实体类追加注解  -->
       <plugin type="nbj.maven.mybatis.generator.plugins.MBGDomainAnnotationPlugin">
         <property name="@lombok.Data" value=""/>
         <property name="@lombok.NoArgsConstructor" value=""/>
         <property name="@lombok.AllArgsConstructor" value=""/>
         <property name="@lombok.Builder" value=""/>
         <property name="nbj.comment.gen" value="1"/>
         <property name="nbj.comment.created" value="plugins"/>
         <property name="nbj.comment.author" value="__user"/>
       </plugin>
       
       
       <!--  官方插件：equals && hashCode  -->
       <plugin type="org.mybatis.generator.plugins.EqualsHashCodePlugin">
         <property name="useEqualsHashCodeFromRoot" value="true"/>
       </plugin>
       <!--  官方插件：实体类 with-builder -->
       <plugin type="org.mybatis.generator.plugins.FluentBuilderMethodsPlugin"/>
       
       
       
       <!--  注释生成器  -->
       <commentGenerator>
         <property name="suppressAllComments" value="true"/>
       </commentGenerator>
   
       
       <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
               connectionURL="jdbc:mysql://localhost:3306/test"
               userId="root" password="root">
         <!-- http://mybatis.org/generator/usage/mysql.html -->
         <property name="nullCatalogMeansCurrent" value="true"/>
       </jdbcConnection>
   
       
       <javaModelGenerator targetPackage="nbj.maven.mybatis.generator.example.domain" targetProject="src/main/java"/>
       <sqlMapGenerator targetPackage="nbj.maven.mybatis.generator.example.mapper.sqlmap" targetProject="src/main/java"/>
       <javaClientGenerator targetPackage="nbj.maven.mybatis.generator.example.mapper" targetProject="src/main/java"
                  type="XMLMAPPER"/>
   
       
       <table tableName="tbl_nbj_mybatis_plugins_test1" domainObjectName="TestDomain1">
         <columnOverride column="remark" javaType="java.lang.String" jdbcType="VARCHAR"/>
       </table>
   
     </context>
   </generatorConfiguration>
   ```

3. 在IDEA侧边栏执行 generate 操作

## 常见问题

1. 找不到maven插件

   ```
   Could not find artifact github.nextbin.maven:nbj-mybatis-generator-plugins:jar:0.0.1
   ```

   检查是否已完成本地手动安装，见使用方法1.安装依赖

## 迭代计划

v0.0.2

1. 新增 MBGThriftPlugin：实体类新增 thrift 相关注解

v0.1.x

1. 发布到公共仓库，无需手动安装

## 迭代记录

v0.0.1

1. 新增分页功能：nbj.maven.mybatis.generator.plugins.MBGLimitPlugin
2. 新增重命名WithBlobs方法：nbj.maven.mybatis.generator.plugins.MBGRenameBlobsPlugin
3. 新增实体类增加无参注解：nbj.maven.mybatis.generator.plugins.MBGDomainAnnotationPlugin

## 其他

如有其它拓展需求，可以提issue，都会尽量安排上

## 参考资料

1.  https://mybatis.org/generator/quickstart.html


