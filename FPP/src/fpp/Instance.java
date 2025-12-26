package fpp;

import java.util.ArrayList;

public class Instance
{
	private ArrayList<Codeword> all;
	
	public Instance(int codewordSize)
	{
		all = new ArrayList<Codeword>();
		Codeword x = new Codeword(codewordSize);

		int i = 0;
		while( i < codewordSize )
		{
			all.add(x.clone());
			
			i = 0;
			while( i < codewordSize && x.get(i) == 2 )
				x.set(i++, 0);
			
			if( i < codewordSize )
				x.inc(i);
		}
	}
	
	public ArrayList<Codeword> get()
	{
		return all;
	}
}
