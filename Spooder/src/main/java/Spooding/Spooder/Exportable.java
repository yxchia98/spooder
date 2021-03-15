package Spooding.Spooder;

import java.io.IOException;
/**
 * Exportable Interface
 */
public interface Exportable {
	/**
	 * Method to export data in to a .csv file type
	 * @throws IOException Throws exception is related to Input and Output operations
	 */
	public abstract void exportExcel() throws IOException;
	/**
	 * Method to export data into MongoDB
	 */
	public abstract void exportMongo();

}
