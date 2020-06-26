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

import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//Mapper class with In-Mapper Combiner

//------Data Pattern of Green Taxi data
//2,21/12/2018 15:17,21/12/2018 15:18,N,1,264,264,5,0,3,0.5,0.5,0,0,,0.3,4.3,2,1,
//6th Column = PickUp Location; 7th Column = Drop Off Location
//17th Column = Total fare; 4th Column = Number of Passengers

//------Data Pattern of Green Taxi data
//1	01-02-19 0:59	01-02-19 1:07	1	2.1	1	N	48	234	1	9	0.5	0.5	2	0	0.3	12.3	0
//8th Column = PickUp Location; 9th Column = Drop Off Location
//17th Column = Total fare; 8th Column = Number of Passengers

public class NYCMapper_IMC extends Mapper<LongWritable, Text, Text, FloatWritable> //for total fare
{
	private final static IntWritable one = new IntWritable(1);	
	
	//define associative array for holding Mapper outputs and to perform local aggregation
	
	String line;
	Map<Text, FloatWritable> partial_sum_fare;
	
	@Override
	protected void setup(Context context) throws IOException, InterruptedException 
	{
		partial_sum_fare = new HashMap<Text, FloatWritable>();
	}
	
	
	@Override
	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException
	{
		line = value.toString();
		String[] fields = line.split(",");
		
		FloatWritable Total_fare = new FloatWritable();
			
		if(fields.length > 16)
		{
			
			Text PULocation = new Text("PULoc_"+fields[7]); //implemented for yellow taxi data format
			if(fields[16].matches("\\d+.+")) //check whether its numeric
			{
				float f = Float.parseFloat(fields[16]);
							
			
				if(partial_sum_fare.containsKey(PULocation)) 
				{
					FloatWritable temp =  (FloatWritable) partial_sum_fare.get(PULocation); //perform aggregation using temp variable
					float summ = f + temp.get(); 
					Total_fare.set(summ); 
					partial_sum_fare.put(PULocation, Total_fare); 
					
								
				} else {
					Total_fare.set(f); 
					partial_sum_fare.put(PULocation, Total_fare); 
					
				}
			}
			
			
						
		}
			
			
	}
	
	//clean up the variables before next round
	
	@Override
	public void cleanup(Context context) throws IOException, InterruptedException
	{
				
		for (Text key : partial_sum_fare.keySet())
		{
			FloatWritable Total_fare_1 = partial_sum_fare.get(key);
			context.write(key, Total_fare_1);
		}
		
	}
	
	
	
	
	
}




