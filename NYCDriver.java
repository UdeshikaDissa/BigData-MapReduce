/*
 * --------------------------------------------------------------------
 * Developer Name 	: Udeshika Dissanayake
 * Subject			: COSC2637 Big Data Processing
 * Assignment		: Assignment 1 - Semester 2, 2019
 * Student Number	: s3400652
 * Date				: 12/10/2019 * 
 *--------------------------------------------------------------------
 */

package edu.rmit.cosc2637.s3400652.Assignment;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.io.FloatWritable;



public class NYCDriver 
{
    
	public static void main( String[] args ) throws IOException, ClassNotFoundException, InterruptedException
	{
    
		/*//for hardcording data and output paths
		Path dataPath = new Path("/user/s3400652/green_tripdata_2019-01_sample.csv");
		Path outputDir = new Path("/user/s3400652/OutputNYC");
		*/
        
				//Define configuration File for MapReduce Drive
    			Configuration conf = new Configuration();
    			Job job = Job.getInstance(conf, "NYC Taxi Analysis");
    			
    			job.setJarByClass(NYCDriver.class);
    			
    			//*****Selection of Mapper Class. [comment only one line out of below two lines]*****
    			//job.setMapperClass(NYCMapper.class);  //Uncomment this for standard Mapper; Comment the below line
    			job.setMapperClass(NYCMapper_IMC.class); //Uncomment this for In-Mapper Combiner; comment the above line
    			//**********************************************************************************
    			
    			//*****Selection of Combiner Class. [uncomment only if standard combiner is used]
    			//job.setCombinerClass(NYCReducer.class); //Uncomment this for standard Combiner
    			//**********************************************************************************
    			
    			//*****Selection of Reducer Class;
    			job.setReducerClass(NYCReducer.class);
    			//***************************************
    			
    			job.setOutputKeyClass(Text.class);
    			//job.setOutputValueClass(IntWritable.class); // for PULocation counter
    			job.setOutputValueClass(FloatWritable.class); //for Total fare 
    			job.setMapOutputKeyClass(Text.class);
    			//job.setMapOutputValueClass(IntWritable.class); // for PULocation counter
    			job.setMapOutputValueClass(FloatWritable.class); //for Total fare 
    			
    			//setting arguments for input and output paths
    			FileInputFormat.addInputPath(job, new Path(args[0]));
    			FileOutputFormat.setOutputPath(job, new Path(args[1]));
    			
    			System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
