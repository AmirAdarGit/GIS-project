package Coords;

import Geom.Point3D;

public class myCoords implements coords_converter{

	private final int earth_radius_in_meter = 6371000;
	
	@Override
	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		Point3D ans = meterToGps(local_vector_in_meter);
		ans.add(gps);
		return ans;
	}

	/*
	 * helper method to convert a point from meter to degrees (gps)
	 */
	public Point3D meterToGps(Point3D vector_in_meter){
		
		double latitude = Math.asin(vector_in_meter.y() / earth_radius_in_meter) * 180 / Math.PI;
		double lon_norm = Math.cos(latitude * Math.PI / 180);
		double longitude = Math.asin(vector_in_meter.x() /( earth_radius_in_meter * lon_norm)) * 180 / Math.PI;
		
		Point3D p = new Point3D(longitude,latitude,vector_in_meter.z());
		return p;
		
	}
	/*
	 * helper method to convert a point from degress (gps) to meter.
	 */
	public Point3D gpsToMeter(Point3D gps){	
		
		double lon_norm = Math.cos(gps.y() * Math.PI / 180);
		double x = Math.sin(gps.x() * Math.PI / 180) * earth_radius_in_meter * lon_norm;
		double y = Math.sin(gps.y() * Math.PI / 180) * earth_radius_in_meter;
		
		Point3D p = new Point3D(x,y,gps.z());
		
		return p;
	}
	
	@Override
	public double distance3d(Point3D gps0, Point3D gps1) {
		Point3D p1 = gpsToMeter(gps0);
		Point3D p2 = gpsToMeter(gps1);
		double distance = p1.distance3D(p2);
		return distance;
	}

	@Override
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		Point3D p1 = gpsToMeter(gps0);
		Point3D p2 = gpsToMeter(gps1);
		
		Point3D vector = new Point3D(p2.x() - p1.x(), p2.y() - p1.y(), p2.z() - p1.z());
		return vector;
	}

	@Override
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		// TODO Auto-generated method stub
		double[] ans = new double[3];
		ans[0] = gps0.angleXY_2PI(gps1);
		ans[1] = gps0.angleZ(gps1);
		ans[2] = gps0.distance3D(gps1);
		return ans;
	}

	
	/**
	 * return true iff this point is a valid lat, lon , lat coordinate: [-180,+180],[-90,+90],[-450, +inf]
	 * @param p
	 * @return
	 */
	@Override
	public boolean isValid_GPS_Point(Point3D p) {
		double x = p.x();
		double y = p.y();
		double z = p.z();
		
		boolean answer = false;
		
		if(x >= -180 && x <= 180 && y >= -90 && y <= 90 && z >= -450 && z <= Integer.MAX_VALUE)
			answer = true;
		
		return answer;
	}
	
	
}
