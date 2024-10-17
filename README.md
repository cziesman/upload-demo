# Building the WAR file

You will need to have Maven installed to build the WAR file for deployment.

Use the following command to generate the WAR file:

    mvn clean install

The file `upload-demo.war` will be created in the `target` directory, and can be deployed to EAP from there.

# Adding a module for Primefaces

Create a directory at `$EAP_HOME/modules/org/primefaces/main`

Create the following `module.xml` file

    <?xml version="1.0" encoding="UTF-8"?>
    <module xmlns="urn:jboss:module:1.0" name="org.primefaces">
     <resources>
      <resource-root path="commons-fileupload-1.5.jar"/>
      <resource-root path="commons-io-2.11.0.jar"/>
      <resource-root path="primefaces-8.0.jar"/>
     </resources>
    
     <dependencies>
      <module name="javax.faces.api" />
     </dependencies>
    </module>

Copy the three JAR files listed in `module.xml` to `$EAP_HOME/modules/org/primefaces/main`

Use the command line to add the new module to the EE subsystem.

    ./jboss-cli.sh -c
    
    [standalone@localhost:9990 /] /subsystem=ee:write-attribute(name=global-modules, value=[{"name"=>"org.primefaces","slot"=>"main"}])
    
    {"outcome" => "success"}

Restart the EAP server and deploy the WAR file.
