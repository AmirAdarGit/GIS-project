package GIS;

import java.io.*;
import java.util.*;

import Geom.Geom_element;
import Geom.Point3D;
import File_format.*;

public class MultiCSV {

	private static ArrayList<String> csv_files = new ArrayList<String>();


	/*
	 * Primary function to build a Project by receiving a folder pathname.
	 */
	public static Project buildProject(String folder_path){
		Project project = new Project();

		new MultiCSV().listFolder(new File(folder_path));
		//#2 find csv file
		//#3 build layer from csv	
		//#4 insert layer into project
		for (String string : csv_files) {
			Layer layer = readFromCSV(string);
			project.add(layer);
		}
		return project; //#5 return project.
	}

	/*
	 * recursively traverse a folder and insert any pathname.csv into
	 * an ArrayList.
	 */
	private void listFolder(File dir) 
	{
		File[] subDirs = dir.listFiles(new FileFilter()
		{

			public boolean accept(File pathname)
			{
				if(pathname.toString().endsWith(".csv"))
				{
					csv_files.add(pathname.toString());	
					//System.out.println(pathname);
				}
				return pathname.isDirectory();
			}
		});
		for(File folder:subDirs)
		{
			listFolder(folder);
		}
	}  

	/*
	 * Build a Layer by reading a *.csv file.
	 * note: we skip the first 2 lines because we presume those are not an element.
	 */
	public static Layer readFromCSV(String pathname){ 

		BufferedReader br = null;
		String line = "";
		String csvSplitBy = ",";

		Layer layer = new Layer();

		try {

			br = new BufferedReader(new FileReader(pathname));
			br.readLine(); //read the first line, its not necessary
			br.readLine(); //read the second line, its not necessary           
			while ((line = br.readLine()) != null ) { //read parameters starting from 3rd line.
				// use comma as separator
				String[] string = line.split(csvSplitBy);
				Element element = new Element(string);
				layer.add(element);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
					return layer;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;

	}

	/*
	 * Build projectKML.kml file containing all the information inside a Porject.
	 */
	public void projectToKML(Project project) throws IOException{
		new Csv2kml();
		String output = "projectKML.kml";	
		File file = new File(output);

		try{

			if(!file.createNewFile()){ //if the file exists, delete it and start over.
				file.delete();
				Csv2kml.writeToKmlStart(output);
			} else { //file created and start normaly.
				Csv2kml.writeToKmlStart(output);
			}

			Iterator<GIS_layer> iter1 = project.iterator();
			while(iter1.hasNext()){
				Iterator<GIS_element> iter2 = iter1.next().iterator();
				while(iter2.hasNext()){
					Element element = (Element) iter2.next();
					Data data = (Data) element.getData();
					kmlElementFromData(data, output);
				}
			}
			Csv2kml.writeToKmlEnd(output);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Helper function to build an element (placemark) from a Data object.
	 */
	private void kmlElementFromData(Data data,String path) throws IOException{	
		String kmlelement = "<Placemark>\n" +
				"<name>" + "<![CDATA[" + data.getValue("SSID") + "]]></name>\n" +
				"<description>" + "<![CDATA[BSSID: <b>"  + data.getValue("MAC") + "</b><br/>Capabilities: <b>" + data.getValue("AuthMode") + "</b><br/>Channel: <b>" + data.getValue("Channel") + "</b><br/>Timestamp: <b>" + data.getValue("FirstSeen") + "</b>]]></description><styleUrl>#red</styleUrl>\n" +
				"<TimeStamp>" + data.getValue("FirstSeen") +"</TimeStamp>\n" + "<Point>\n" +
				"<coordinates>" + data.getValue("Longitude") + "," + data.getValue("Latitude") + "</coordinates>\n" +
				"</Point>\n" +
				"</Placemark>\n";

		BufferedWriter writer = new BufferedWriter(new FileWriter(path,true));
		writer.write(kmlelement);
		writer.close();
	}

	public static void main(String[] args) throws IOException{
		Project project = buildProject("C:\\Users\\amir adar\\eclipse-workspace\\amir&ido matala 2.0\\csv");


		//System.out.println(project.toString());
		//System.out.println(project.iterator().next().toString());
		new MultiCSV().projectToKML(project);

		//new MultiCSV().listFolder(new File("C:\\Users\\lenovo\\Desktop\\University\\ObjectOriented\\matala 2\\Ex2\\data"));
	}

}
