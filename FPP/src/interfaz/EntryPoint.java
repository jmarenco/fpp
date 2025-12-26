package interfaz;

import fpp.Codeword;
import fpp.Instance;

public class EntryPoint
{
	public static void main(String[] args)
	{
		Instance instance = new Instance(5);
		for(Codeword c: instance.get())
			System.out.println(c);
	}
}
