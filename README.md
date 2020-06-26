# BigData-MapReduce
This BigData study intends to identify the most revenue generating Taxi zones in New York City for year 2019. Three MapReduce algorithms were developed and their performance were analyzed on different size of input datasets and on different size clusters in EMR.

## Steps
### Source Java Code
================
The zip file containing Java source code package is located at
~~\Package - s3400652\Java Source Code\Assignment.zip

Load this zip file to eclipse workspace.


### Build standalone .jar file from Java MapReduce project
======================================================
- The dependancies (.pom) have correclty been set in the provided Java application package
- Follow the standard procedure to build standalone .jar file in eclipse-workspace through Maven build

[** - .jar files have been provided in the "Java Souce File" folder]


### Upload standalone .jar file from local computer to HDFS file system through HUE
=================================================================================
Drag and drop the .jar file to HDFS (location - Home/user/s3400652) through HUE web interface.


[** - destination location has to be correctly set]


### Copy .jar from HDFS to cluster master node through EMR CLI
===============================================================
hadoop fs -copyToLocal /user/s3400652/Assignment-0.0.1-SNAPSHOT_yellow.jar /home/hadoop/
hadoop fs -copyToLocal /user/s3400652/Assignment-0.0.1-SNAPSHOT_yellow_combiner.jar /home/hadoop/
hadoop fs -copyToLocal /user/s3400652/Assignment-0.0.1-SNAPSHOT_yellow_IMC.jar /home/hadoop/

[** - source location (/user/s3400652) has to be correctly set]

Experiment were conducted on NYC TLC Yellow Taxi Trip Data .csv for year 2019. [make sure the .csv column format is the same if older year files 
are used in for analysis]

### Fetch Yellow Trip Data files from AWS S3 bucket to HDFS file system through EMR command prompt (six files were copied)
==========================================================================================================================
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-01.csv /user/s3400652/
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-02.csv /user/s3400652/
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-03.csv /user/s3400652/
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-04.csv /user/s3400652/
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-05.csv /user/s3400652/
hadoop distcp s3a://nyc-tlc/"trip data"/yellow_tripdata_2019-06.csv /user/s3400652/

[** - make sure destination "/user/s3400652/" is correctly set accordingly]

### Deploy MapReduce .jar files in Hadoop and Run
=============================================
hadoop jar Assignment-0.0.1-SNAPSHOT_yellow.jar /user/s3400652/*.csv /user/s3400652/outputNYC_sixfile
hadoop jar Assignment-0.0.1-SNAPSHOT_yellow_combiner.jar /user/s3400652/*.csv /user/s3400652/outputNYC_sixfile_combiner
hadoop jar Assignment-0.0.1-SNAPSHOT_yellow_IMC.jar /user/s3400652/*.csv /user/s3400652/outputNYC_sixfile_IMC

[** - all the csv files in HDFC location "/user/s3400652/" have been taken as inputs; output files are saved at "/user/s3400652"
under provided output folder name]


### Changing the cluster node size - run below shell script in jumphost CLI
=======================================================================
sh  boost_cluster.sh

[** - this will increase the number of nodes in the EMR cluster by 2]


### Sort and view output files - below command will sort and show top most 10 results for given output file system
==============================================================================================================
hadoop fs -cat /user/s3400652/outputNYC_sixfile_combiner/part-r-0000* | sort -g -k2 -r | head -n10

[** - output file path has to be correcty set; in above example the output files path is "/user/s3400652/outputNYC_sixfile_combiner"]




