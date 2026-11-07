package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider1
	
	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException{ 
		
		String path=".\\testData\\Opencart_LoginTestData.xlsx";//Taking excel file from test data
		
		ExcelUtility xlutil=new ExcelUtility(path);//Creating an object for excel utility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		 String logindata[][]=new String[totalrows][totalcols];//  created for two dimension array which can store which can store
		 
		 for(int i=1;i<=totalrows;i++) //1 //read data from xl storing in two dimensional array
		 {
			 for(int j=0;j<totalcols;j++) //0
				 logindata[i-1][j]=xlutil.getCellData("Sheet1", i, j); //1,0
		 }
		
		 return logindata; // returning 2D array
		
	}
	
	

}


//DP2

//DP3

//DP4
