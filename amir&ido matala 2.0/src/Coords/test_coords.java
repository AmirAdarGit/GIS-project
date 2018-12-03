package Coords;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Geom.Point3D;
import junit.framework.Assert;

class test_coords {


	@Test
	public void test_add_equal()
	{
		Point3D p1=new Point3D(20,30,40);//polar
		Point3D p2=new Point3D(3,4,5); //meter
		Point3D p3=new Point3D (new myCoords().add(p1,p2));
		assertEquals("20.000026979648176,30.000035972864236,45.0", p3.toString());	
	}
	@Test
	public void test_add_not_equal()
	{
		Point3D p1=new Point3D(20,30,40);//	polar
		Point3D p2=new Point3D(3,4,5); //meter
		p1=new myCoords().gpsToMeter(p1);//change p1 to meter
		Point3D p3=new Point3D (new myCoords().add(p1,p2));
		assertNotEquals("20.000035972864236,30.000026979648176,45.0", p3.toString());	
	}
	@Test
	public void test_meterToGps()
	{
		Point3D p1=new Point3D(10,20,30);//	meter
		Point3D meterTogps = new myCoords().meterToGps(p1);
		assertEquals("8.993216059235313E-5,1.7986432118404153E-4,30.0",meterTogps.toString());
	}
	@Test
	public void test_GpsTometer()
	{
		Point3D p1=new Point3D(10,20,30);//	meter
		Point3D gpsToMeter = new myCoords().gpsToMeter(p1);
		assertEquals("1039593.7300419933,2179010.3331278353,30.0",gpsToMeter.toString());
	}
	@Test
	public void test_distance3D()
	{
		Point3D p1=new Point3D(32.103315,35.209039,670);//build 9
		Point3D p2=new Point3D(32.106352,35.205225,650);// hummus		
		double diss = new myCoords().distance3d(p1, p2);
		assertTrue(502.7290366758166==diss);
	}


	@Test
	public void test_vector3D()
	{
		Point3D p1=new Point3D(32.103315,35.209039,670);//	build 9
		Point3D p2=new Point3D(32.106352,35.205225,650);// hummus		
		Point3D vector = new Point3D (new myCoords().vector3D(p1, p2));
 		assertEquals("363.6774943303317,-346.51863504666835,-20.0", vector.toString());
	}
	@Test
	public void test_isValid_GPS_Point()
	{
		Point3D p1=new Point3D(32.103315,35.209039,670);//	build 9
 		boolean isVal=new myCoords().isValid_GPS_Point(p1);
 		assertEquals(isVal,true);
	}
	

}
