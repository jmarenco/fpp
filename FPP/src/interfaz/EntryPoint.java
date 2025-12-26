package interfaz;

import fpp.BoxEnumerator;
import fpp.Instance;
import fpp.Model;
import fpp.Pool;

public class EntryPoint
{
	public static void main(String[] args)
	{
		Instance instance = new Instance(5);

		Pool pool = new Pool();
		BoxEnumerator.run(instance, pool);

//		Model model = new Model(instance);
		Model model = new Model(instance, pool);
		model.solve();
	}
}
