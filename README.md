An implementation of weather predictor using SPARK MLib in Scala programming language.
	- Using classification& regression model for weather and sensors conditions respectively

    Usage :
       - sbt run
	   
	Folder Structure :
		- src\main\resources\training
		Has the csv file containing the history datas for various countries 
	
		- src\main\scala\com\weatherpredict
		Has the scala source code for weather predictor
		
	Info :
		The program will predict the weather for random countries acorss globally for next few days.
		
		Sample output is as below :
		
               GOA|15.38,73.83,56.10|2017-11-01T22:37:01|Sunny|+27.67|1009.68|70.04
               BNE|-27.42,153.07,6.00|2017-11-02T22:37:01|Sunny|+22.44|1015.71|58.80
               ADL|-34.95,138.53,6.10|2017-11-03T22:37:01|Sunny|+16.39|1018.06|48.10
               PVG|31.40,121.47,4.00|2017-11-04T22:37:01|Rain|+11.46|1012.37|46.80
               DEL|28.57,77.10,236.80|2017-11-05T22:37:01|Rain|+27.67|1007.61|70.91
               HBA|-42.84,147.51,4.00|2017-11-06T22:37:01|Rain|+11.46|1012.43|46.80
               MAA|12.99,80.18,15.80|2017-11-07T22:37:01|Sunny|+26.52|1012.76|74.95			
			
