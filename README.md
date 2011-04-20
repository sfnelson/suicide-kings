Suicide Kings
=============

Webapp for suicide kings loot distribution in World of Warcraft.

User interface implemented in GWT. Runs on Google App Engine.

Eclipse + Maven
---------------

Based on the Eclipse/Maven instructions from [GWT Expense Example](http://code.google.com/p/google-web-toolkit/source/browse/trunk/samples/expenses/README-MAVEN.txt).

Install the following into Eclipse:
* Google Plugin for Eclipse [(instructions)](http://code.google.com/eclipse/)
* m2eclipse Core
* Maven Integration for WTP (in m2eclipse extras)
  Instructions for installing the maven plugins can be found here:
  (http://m2eclipse.sonatype.org/installing-m2eclipse.html)

Ensure Eclipse is configured to use Java 1.6 (required by AppEngine and GWT).

In Eclipse, go to the File menu and choose:
* File -> Import... -> Existing Maven Projects into Workspace.
* Browse to the directory containing this file, select "Expenses".
* Click Finish.

You can now browse the project in Eclipse.

To launch GWT development mode:
* Go to the Run menu item and select Run -> Run as -> Web Application.

