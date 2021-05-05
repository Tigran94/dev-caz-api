You can run with the following script ->
mvn clean test -Dthread={count} -DsuiteXmlFile={fileName}

Where 
1. {count} is the count of the threads which runs in parallel by classes
2. {fileName} is the XML file name that you want to run
