package Spooding.Spooder;

import java.io.IOException;

public interface DataMovement {
	public abstract void exportExcel() throws IOException;
	public abstract void importExcel();
}
