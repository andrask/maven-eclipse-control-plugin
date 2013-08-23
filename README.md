maven-eclipse-control-plugin
============================

This repo contains a single Eclipse plugin that enables to trigger a workspace refresh from outside Eclipse. 
The `refresher` uses a socket listening on port 43155 as the trigger. If a connection is attempted at the
socket, then a workspace refresh is started. The trigger can be implemented in the command line as

    telnet localhost 43155
    
Or anything similar that connects to the given port. The connection is immediately closed by the plugin after acception.

To use this plugin from maven, simply add the following to your `pom.xml`

    <project>
      ...
      <build>
        <plugins>
          <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>1.2.1</version>
            <executions>
              <execution>
                ...
                <goals>
                  <goal>exec</goal>
                </goals>
              </execution>
            </executions>
            <configuration>
              <executable>telnet</executable>
              <arguments>
                <argument>localhost</argument>
                <argument>43155</argument>
              </arguments>
            </configuration>
          </plugin>
        </plugins>
      </build>
       ...
    </project>

Or use it from command line args

    mvn exec:exec -Dexec.executable="telnet" -Dexec.args="localhost 43155"

Usage
-----

**Deployed from source**

1. Import the plugin project in Eclipse.
2. Run the plugin as an Eclipse Application. The plugin will be active in the new workspace after startup.

**Deployed as plugin**

1. Export the `refresher` project as deployable plugin.
2. Copy the plugin jar to the destination eclipse's plugins folder.

Caution
-------
The plugin uses a fixed port for listening, therefore, a single instance of eclipse is supported at one time.
