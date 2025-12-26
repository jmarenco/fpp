package fpp;

public class InnerDiamondEnumerator
{
	private static String _name = "Inner Diamond";
	
	public static void run(Instance instance, Pool pool)
	{
		pool.createFamily(_name);
		for(Codeword a: instance.get())
		{
			for(int i=0; i<instance.codewordSize(); ++i)
			for(int j=0; j<instance.codewordSize(); ++j) if( i != j )
			for(int bi=0; bi<3; ++bi) if( bi != a.get(i) )
			for(int bj=0; bj<3; ++bj) if( bj != a.get(j) )
			for(int ci=0; ci<3; ++ci) if( ci != a.get(i) && ci != bi )
			for(int cj=0; cj<3; ++cj) if( cj != a.get(j) && cj != bj )
			{
				Codeword b = a.clone(i, bi, j, bj);
				Codeword c = a.clone(i, bi);
				Codeword d = a.clone(j, bj);
				Codeword e = a.clone(i, ci);
				Codeword f = a.clone(i, bi, j, cj);
				Codeword g = a.clone(j, cj);
				Codeword h = a.clone(i, ci, j, bj);
				
				Inequality dv = new Inequality();
				dv.set(a, 1);
				dv.set(b, 1);
				dv.set(c, 1);
				dv.set(d, 1);
				dv.set(e, 1);
				dv.set(f, 1);
				dv.set(g, 1);
				dv.set(h, 1);
				
				Codeword[] B = {a, f, h};
				Codeword[] E = {a, b, c, d, e, f, g, h};
				for(Codeword v: instance.get()) if( neighbor(v,B,E) == true )
					dv.set(v, 1);
				
				dv.rhs(2);
				pool.add(_name, dv);
			}
		}
		
		System.out.println(_name + ": " + pool.inequalities(_name).size());
	}
	
	private static boolean neighbor(Codeword v, Codeword[] ws, Codeword[] except)
	{
		for(int i=0; i<ws.length; ++i) if( ws[i].equals(v) )
			return false;

		for(int i=0; i<except.length; ++i) if( except[i].equals(v) )
			return false;

		for(int i=0; i<ws.length; ++i) if( ws[i].distance(v) == 1 )
			return true;
		
		return false;
	}
}
