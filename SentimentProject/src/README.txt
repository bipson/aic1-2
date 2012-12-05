
===============================================
		Sentiment Analysis Test Project
===============================================



This directory contains all sources and dependencies (jar-files) needed 
to run the project. 

In eclipse, select the Java-view and do File/New -> Java Project, put the files
into the newly created source folder and add the libraries under the lib directory
to the build path. Then run it as Java Application. 

I took it from the sentiment analysis project and adjusted it to test 
single tweets. After initialization the program will ask for a text (tweet)
and a target polarity (0 for bad, 4 for good), and show the results for the 
different classification models. 
If an algorithm's output does not coincide with the target polarity, it will
be weighted less important in successive iterations. The target polarity could 
also be set to the most common polarity among the results. This way the 
algorithms would be rated according to the assumed correctness of their results. 


