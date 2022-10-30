package com.way2automation.misc;

import java.util.Date;

public class TestTimeStamp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Date d = new Date();
		String screenshotName = d.toString().replace(":", "_").replace(" ", "_")+".jpg";
		
		String x = "900";
		x.concat(".0");
		System.out.println(x);

	}

}
