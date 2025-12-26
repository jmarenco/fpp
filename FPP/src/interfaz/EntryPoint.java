package interfaz;

import fpp.Instance;
import fpp.Model;

public class EntryPoint
{
	public static void main(String[] args)
	{
		Instance instance = new Instance(5);
		Model model = new Model(instance);
		model.solve();
	}
}
