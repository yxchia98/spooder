package Spooding.Spooder;

import java.io.IOException;
/**
 * Exportable Interface which decides what methods certain classes must contain
 */
public interface Exportable {
	/**
	 * Method to export data in to a .csv file type
	 * @throws IOException
	 */
	public abstract void exportExcel() throws IOException;
	/**
	 * Method to export data into MongoDB
	 */
	public abstract void exportMongo();

}
