package fpp;

public class Codeword
{
	private int[] x;
	
	public Codeword(int size)
	{
		x = new int[size];
	}
	
	public int size()
	{
		return x.length;
	}
	
	public int get(int i)
	{
		return x[i];
	}
	
	public void set(int i, int value)
	{
		if( value < 0 || value > 2 )
			throw new RuntimeException("Invalid set value for codeword: " + value);
		
		x[i] = value;
	}
	
	public void inc(int i)
	{
		this.set(i, this.get(i)+1);
	}
	
	public int distance(Codeword other)
	{
		if( this.size() != other.size() )
			throw new RuntimeException("Invalid codeword comparison");

		int ret = 0;
		for(int i=0; i<this.size(); ++i) if( this.get(i) != other.get(i) )
			++ret;
		
		return ret;
	}
	
	public Codeword clone()
	{
		Codeword ret = new Codeword(this.size());
		
		for(int i=0; i<this.size(); ++i)
			ret.set(i, this.get(i));
		
		return ret;
	}
	
	@Override public String toString()
	{
		String ret = "";
		for(int i=0; i<this.size(); ++i)
		{
			if( this.get(i) == 0 )
				ret = ret + "0";

			if( this.get(i) == 1 )
				ret = ret + "1";
			
			if( this.get(i) == 2 )
				ret = ret + "2";
		}
		
		return ret;
	}
}
