package fpp;

public class ReinforcedBoxEnumerator
{
	private static String _name = "Reinforced Box";
	
	public static void run(Instance instance, Pool pool)
	{
		pool.createFamily(_name);
		for(Codeword a: instance.get())
		{
			for(int i=0; i<instance.codewordSize(); ++i)
			for(int j=i+1; j<instance.codewordSize(); ++j)
			for(int bi=0; bi<3; ++bi) if( bi != a.get(i) )
			for(int bj=0; bj<3; ++bj) if( bj != a.get(j) )
			{
				Codeword b = a.clone(i, bi, j, bj);
				Codeword c = a.clone(i, bi);
				Codeword d = a.clone(j, bj);
				
				Inequality dv = new Inequality();
				dv.set(a, 1);
				dv.set(b, 1);
				dv.set(c, 1);
				dv.set(d, 2);
				
				Codeword[] B = {a, b, d};
				for(Codeword v: instance.get()) if( neighbor(v,B) == true && !v.equals(c) )
					dv.set(v, 1);
				
				dv.rhs(2);
				pool.add(_name, dv);
			}
		}
		
		System.out.println(_name + ": " + pool.inequalities(_name).size());
	}
	
	private static boolean neighbor(Codeword v, Codeword[] ws)
	{
		for(int i=0; i<ws.length; ++i) if( ws[i].equals(v) )
			return false;

		for(int i=0; i<ws.length; ++i) if( ws[i].distance(v) == 1 )
			return true;
		
		return false;
	}
}
