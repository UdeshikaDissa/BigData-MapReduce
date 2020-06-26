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

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//Mapper Class

//------Data Pattern of Green Taxi data
//2,21/12/2018 15:17,21/12/2018 15:18,N,1,264,264,5,0,3,0.5,0.5,0,0,,0.3,4.3,2,1,
//6th Column = PickUp Location; 7th Column = Drop Off Location
//17th Column = Total fare; 4th Column = Number of Passengers

//------Data Pattern of Yellow Taxi data
//1	01-02-19 0:59	01-02-19 1:07	1	2.1	1	N	48	234	1	9	0.5	0.5	2	0	0.3	12.3	0
//8th Column = PickUp Location; 9th Column = Drop Off Location
//17th Column = Total fare; 8th Column = Number of Passengers

//public class NYCMapper extends Mapper<LongWritable, Text, Text, IntWritable> //for number of Pick Up Locations
public class NYCMapper extends Mapper<LongWritable, Text, Text, FloatWritable> //for total fare
{
	private final static IntWritable one = new IntWritable(1);	
	private FloatWritable Total_fare = new FloatWritable();
	
	// ----mapper to count number of Pick Up Locations-----
	// Out - (PULoc_264, 1)
	/*
	@Override
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException
	{
		String line = value.toString();
		String[] fields = line.split(",");
		
		if(fields.length > 6)
		{
			Text PULocation = new Text("PULoc_"+fields[5]);
			context.write(PULocation, one);
		}
		
		
	}
	*/
	
	// ----mapper to get total fare for each Pick Up Locations-----
	// out - (PULoc_264, 4.3)
	@Override
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException
	{
		String line = value.toString();
		String[] fields = line.split(",");
			
		if(fields.length > 16)
		{
			Text PULocation = new Text("PULoc_"+fields[7]);
			if(fields[16].matches("\\d+.+")) //check whether its numeric
			{
				float f = Float.parseFloat(fields[16]);
				Total_fare.set(f);
			}
			context.write(PULocation, Total_fare);
		}
			
			
	}
	
}


