# Assignment with below problem statement
Take the path to logfile.txt as an input argument
 Parse the contents of logfile.txt
 Flag any long events that take longer than 4ms
 Write the found event details to file-based HSQLDB (http://hsqldb.org/) in the working folder
 The application should create a new table if necessary and store the following values:
 Event id
 Event duration
 Type and Host if applicable
 Alert (true if the event took longer than 4ms, otherwise false)

1. Read log file using java 8 file reading(newBufferedReader)
2. Created 2 Model classes - LogRecord for all log details from logs.txt and EventData to save validated event details
3. Created Hashset to store Unique event ids
4. Iterate the whole parsed list and found start event and finished event timestamp and calculated difference
5. Save the validated details to EventData

//HSQLDB installation
https://www.tutorialspoint.com/hsqldb/hsqldb_installation.htm
