package fpp;

public class Box3D23Enumerator
{
	private static String _name = "3D-Box 2-3";
	
	public static void run(Instance instance, Pool pool)
	{
		pool.createFamily(_name);
		for(Codeword a: instance.get())
		{
			for(int i=0; i<instance.codewordSize(); ++i)
			for(int j=i+1; j<instance.codewordSize(); ++j)
			for(int k=0; k<instance.codewordSize(); ++k) if( k != i && k != j )
			for(int bi=0; bi<3; ++bi) if( bi != a.get(i) )
			for(int bj=0; bj<3; ++bj) if( bj != a.get(j) )
			for(int bk=0; bk<3; ++bk) if( bk != a.get(k) )
			{
				Codeword b = a.clone(i, bi, j, bj);
				Codeword c = a.clone(i, bi);
				Codeword d = a.clone(j, bj);
				Codeword ap = a.clone(k, bk);
				Codeword bp = a.clone(i, bi, j, bj, k, bk);
				Codeword cp = a.clone(i, bi, k, bk);
				Codeword dp = a.clone(j, bj, k, bk);
				
				Inequality dv = new Inequality();
				dv.set(ap, 3);
				dv.set(bp, 3);
				dv.set(d, 3);
				dv.set(dp, 3);
				dv.set(a, 2);
				dv.set(b, 2);
				dv.set(c, 2);
				dv.set(cp, 2);
				
				Codeword[] B = {a, ap, cp, bp, b, d};
				for(Codeword v: instance.get()) if( neighbor(v,B) == true && !v.equals(c) && !v.equals(dp) )
					dv.set(v, 1);
				
				dv.rhs(5);
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
