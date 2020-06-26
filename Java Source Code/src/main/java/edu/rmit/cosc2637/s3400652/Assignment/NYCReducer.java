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

import java.util.Iterator;
import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;

//public class NYCReducer extends Reducer<Text, IntWritable, Text, IntWritable> //for sum PULocation 
public class NYCReducer extends Reducer<Text, FloatWritable, Text, FloatWritable> //to sum total fare for each location
{
	//for PULocation count
	/*
	@Override
	public void reduce(Text key, Iterable<IntWritable> value, Context context) 
			throws IOException, InterruptedException
	{
		int sum = 0;
		for (IntWritable val : value)
		{
			sum += val.get();
		}
		
		context.write(key, new IntWritable(sum));
	}
	*/
	
	//for Total Fare calc for each locatoin
	@Override
	public void reduce(Text key, Iterable<FloatWritable> value, Context context) 
			throws IOException, InterruptedException
	{
		float sum = 0;
		float sum_average =0;
		int counter = 0;
		
		for (FloatWritable val : value)
		{
			counter +=1;
			sum += val.get();
		}
		
		sum_average = sum/counter;
		
		context.write(key, new FloatWritable(sum));
	}

}
