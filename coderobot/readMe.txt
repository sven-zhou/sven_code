一、使用说明：
	1、修改 src 目录下 config.properties 文件内容(里面有提示)，主要是 jdbc信息、代码路径、包名即可
	2、目前只测试使用于 数据库oracle
	3、工程需使用mybatis spring 框架
	4、创建你自己的项目工程，把生成的代码拷贝到相应的位置即可
	5、如果是maven项目，用到的依赖如下拷贝即可
二、注意事项：
	1、程序会把数据库表名转化为java实体类（去掉表名下划线，并把其后首字母大写）
		举例： 表名称 tbl_order 
			-->实体类：         tblOrderEntity
			-->dao:     tblOrderDao
			-->service: tblOrderService
	
	2、数据库中每个表最好建立主键，程序会生成根据主键查询的方法
	3、程序会生成juint单元测试的部分代码，请补充完整后运行单元测试
	4、程序会在service类加注解 @Transactional 标记事务
	5、生成 application_context_test.xml 的spring 配置文件，可以修改使用之
三、项目依赖
 1、maven项目依赖（部分pom文件），不包括oracle jdbc jar包
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<org.springframework.version>3.1.2.RELEASE</org.springframework.version>
		<org.slf4j.version>1.7.2</org.slf4j.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.7</version>
			<scope>test</scope>
		</dependency>

		<!-- mybatis -->
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis</artifactId>
			<version>3.1.1</version>
		</dependency>
		<dependency>
			<groupId>org.mybatis</groupId>
			<artifactId>mybatis-spring</artifactId>
			<version>1.1.1</version>
		</dependency>

		<!-- spring -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context-support</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-orm</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjrt</artifactId>
			<version>1.6.1</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-expression</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-web</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-asm</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jms</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${org.springframework.version}</version>
		</dependency>

		<dependency>
			<groupId>cglib</groupId>
			<artifactId>cglib</artifactId>
			<version>2.2.2</version>
		</dependency>

		<dependency>
			<groupId>aopalliance</groupId>
			<artifactId>aopalliance</artifactId>
			<version>1.0</version>
		</dependency>

		<dependency>
			<groupId>commons-lang</groupId>
			<artifactId>commons-lang</artifactId>
			<version>2.5</version>
		</dependency>
		<dependency>
			<groupId>commons-beanutils</groupId>
			<artifactId>commons-beanutils</artifactId>
			<version>1.8.3</version>
		</dependency>
		<dependency>
			<groupId>commons-collections</groupId>
			<artifactId>commons-collections</artifactId>
			<version>3.2.1</version>
		</dependency>
		<dependency>
			<groupId>commons-dbcp</groupId>
			<artifactId>commons-dbcp</artifactId>
			<version>1.4</version>
		</dependency>
		
		<dependency>
			<groupId>com.oracle</groupId>
			<artifactId>ojdbc6</artifactId>
			<version>ojdbc6</version>
		</dependency>

		<!-- LOGGER -->
		<dependency>
			<groupId>commons-logging</groupId>
			<artifactId>commons-logging</artifactId>
			<version>1.1.1</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>slf4j-api</artifactId>
			<version>${org.slf4j.version}</version>
		</dependency>
		<dependency>
			<groupId>org.slf4j</groupId>
			<artifactId>jcl-over-slf4j</artifactId>
			<version>${org.slf4j.version}</version>
		</dependency>
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-core</artifactId>
			<version>1.0.9</version>
		</dependency>
		<dependency>
			<groupId>ch.qos.logback</groupId>
			<artifactId>logback-classic</artifactId>
			<version>1.0.9</version>
		</dependency>

	</dependencies>
