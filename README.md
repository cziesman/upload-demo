# Building the WAR file

You will need to have Maven installed to build the WAR file for deployment.

Use the following command to generate the WAR file:

    mvn clean install

The file `upload-demo` will be created in the `target` directory, and can be deployed to EAP from there.
