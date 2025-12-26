package fpp;

import java.util.ArrayList;

public class Instance
{
	private int _codewordSize;
	private ArrayList<Codeword> _all;
	
	public Instance(int codewordSize)
	{
		_codewordSize = codewordSize;
		_all = new ArrayList<Codeword>();
		
		Codeword x = new Codeword(codewordSize);

		int i = 0;
		while( i < codewordSize )
		{
			_all.add(x.clone());
			
			i = 0;
			while( i < codewordSize && x.get(i) == 2 )
				x.set(i++, 0);
			
			if( i < codewordSize )
				x.inc(i);
		}
	}
	
	public ArrayList<Codeword> get()
	{
		return _all;
	}
	
	public int codewordSize()
	{
		return _codewordSize;
	}
}
