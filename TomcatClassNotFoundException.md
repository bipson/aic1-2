# Introduction #

If you cannot start Tomcat because of a `ClassNotFoundException` that says `CXFServlet` is missing, follow the steps below to fix this.

# Fixing Tomcat #
## Locate the WTP publish directory ##
Start Eclipse, open the Servers view, click on Tomcat to mark it and press F3 to open the Overview.

Click the "Open launch configuration" link and select the Arguments tab. In the "VM arguments" look for the argument called "wtp.deploy". The value of the argument is a folder.

This is the place when you edit a Java file and save it, WTP publishes the changes for Tomcat to load.

## Fix missing libraries ##
Open this directory in Windows Explorer.

In there should be a BaseServices directory. If not, open Eclipse, select the `BaseServices` project, and press Alt+Shift+X, R. Then click Finish.

Navigate to BaseServices/WEB-INF/lib. If the lib directory is missing or doesn't contain cxf-2.7.0.jar (in my case it holds 72 files), jump over [here](http://code.google.com/p/aic1-2/downloads/detail?name=Tomcat%20WAR%20Libraries.rar) to get the libraries. Place all the contained .jar files inside the lib directory. Tomcat should be fine now.