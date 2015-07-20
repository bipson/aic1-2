# Intro #

Hi, there. Here is a short guide about how to setup the maven plugin for eclipse. (Which was not 100% included in the Sample WS)

There may but must not occure some problems during the setup, but I tried to include all that I came accross, so it should be a smooth process for you.

Also, this is rather general guide so if some example path or something doesn't exactly match this VM don't get scared :) .

If you wanna use this working environment you need to read a bit about maven lifecycle, for example here:

http://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html

# Goals #

Install eclipse, m2eclipse plugin, import maven project and package it.

# Tools #

Eclipse: 		Juno (4.2) SR1 (Eclipse IDE for Java EE Developers )

m2eclipse plugin :	Maven Integration for Eclipse, 1.2, org.eclipse.m2e.feature

# Manual #

1.1 		Download and unzip **eclipse**.

1.2		In eclipse Help/Eclipse Marketplace.../ install Maven **Integration for Eclipse** (not WTP)



2.1 		**Import** project as Existing Maven Projects

2.2		If using a maven plugin which execution is not covered by m2eclipse (e.g. cxf-codegen-plugin) you MUST use **lifecycle-mapping** as shown below.

> (for additional info. : http://wiki.eclipse.org/M2E_plugin_execution_not_covered)


```
<build>
    ...
    <pluginManagement>
	    <plugins>
	        <plugin>
	            <groupId>org.eclipse.m2e</groupId>
	            <artifactId>lifecycle-mapping</artifactId>
	            <version>1.0.0</version>
	            <configuration>
	                <lifecycleMappingMetadata>
	                    <pluginExecutions>
	                        <pluginExecution>
	                                <pluginExecutionFilter>
	                                    <groupId>org.apache.cxf</groupId>
	                                    <artifactId>cxf-codegen-plugin</artifactId>
	                                    <versionRange>[2.6.0,)</versionRange>
	                                    <goals>
	                                        <goal>wsdl2java</goal>
	                                    </goals>
	                                </pluginExecutionFilter>
	                                <action>
	                                    <execute>
	                                        <runOnIncremental>false</runOnIncremental>
	                                    </execute>
	                                </action>
	                            </pluginExecution>
	                    </pluginExecutions>                         
	                </lifecycleMappingMetadata>
	            </configuration>
	        </plugin>
	    </plugins>
	</pluginManagement>
	...
</build>
```

2.3		Make sure you are useing the JRE included in JDK, as there may occure an error if using the standalone version. (eg. use **C:\Program Files\Java\jdk1.6.0\_35\jre**, NOT C:\Program Files\Java\jre6)

2.4 		If the **generated resources** were not included into Java Build Path of the project, you need to do so manually, in project/Properties/Java Build Pathe/Source -> Add Folder... and choose target/generated-sources

2.5		If the **generated client** class should cause errors on lines: super(wsdlLocation, serviceName, feature); the cause is the incompatibility with JAX-WS API. One solution is to generate JAX-WS 2.1 client rather than JAX-WS 2.2. Do this by adding the following int cxf-codegen-plugin configuration in pom.xml

> (for additional info. : https://issues.apache.org/jira/browse/CXF-3268)
```
		
		<configuration>
                <defaultOptions>
                    <extraargs>
                        <extraarg>-frontend</extraarg>
                        <extraarg>jaxws21</extraarg>
                        ... 
                    </extraargs>
                </defaultOptions>
                 ...
            	</configuration>
```


