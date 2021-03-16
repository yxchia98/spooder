package Spooding.Spooder;

public class SentimentData {
	private int dataCount = 0;
	private int positiveCounter = 0;
	private int negativeCounter = 0;
	private int neutralCounter = 0;
	private int veryPositiveCounter = 0;
	private int veryNegativeCounter = 0;
	
	private String positiveString = "Positive";
	private String negativeString = "Negative";
	private String neutralString = "Neutral";
	private String veryPositiveString = "Very positive";
	private String veryNegativeString = "Very negative";
	
	private String filePath = "src/main/java/Spooding/Spooder/";
	
	public SentimentData()
	{
		
	}
	
	public int getDataCount()
	{
		return dataCount;
	}
	
	public int getPositiveCounter()
	{
		return positiveCounter;
	}
	
	public void setPositiveCounter(int positiveCounter)
	{
		this.positiveCounter = positiveCounter;
	}
	
	public int getNegativeCounter()
	{
		return negativeCounter;
	}
	
	public int getVeryPositiveCounter()
	{
		return veryPositiveCounter;
	}
	
	public int getVeryNegativeCounter()
	{
		return veryNegativeCounter;
	}
	
	public void setNegativeCounter(int negativeCounter)
	{
		this.negativeCounter = negativeCounter;
	}
	
	public int getNeutralCounter()
	{
		return neutralCounter;
	}
	
	public void setNeutralCounter(int neutralCounter)
	{
		this.neutralCounter = neutralCounter;
	}
	
	public void setVeryPositiveCounter(int veryPositiveCounter)
	{
		this.veryPositiveCounter = veryPositiveCounter;
	}
	
	public void setVeryNegativeCounter(int veryNegativeCounter)
	{
		this.veryNegativeCounter = veryNegativeCounter;
	}
	
	public String getPositiveString()
	{
		return positiveString;
	}
	
	public String getNegativeString()
	{
		return negativeString;
	}
	
	public String getNeutralString()
	{
		return neutralString;
	}
	
	public String getVeryPositiveString()
	{
		return veryPositiveString;
	}
	
	public String getVeryNegativeString()
	{
		return veryNegativeString;
	}
	
	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
}
