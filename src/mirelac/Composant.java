package mirelac;

/**
 * <b>Composant est un composant d'un systeme MIRELA.</b>
 * <p>
 * Il existe different types de composants MIRELA :
 * <ul>
 * <li>Periodic</li>
 * <li>Aperiodic</li>
 * <li>Rendering</li>
 * <li>Memory</li>
 * <li>First</li>
 * <li>Both</li>
 * <li>Priority</li>
 * </ul>
 * </p>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public abstract class Composant
{

	protected String[] Source;
	protected String[] Target;
	protected int MinStart;
	protected int MaxStart;
	protected int[] Min;
	protected int[] Max;
	protected String Name;
	protected String codexml = "";

	public Composant(String id)
	{
		Source=new String[0];
		Target=new String[0];
		MinStart=0;
		MaxStart=0;
		Min=new int[0];
		Max=new int[0];
		Name=id;
	}

	public int maxMem(Composant c, String s)
	{
		for(int i=0; c.getSource().length>i ;i++)
			if(s.equals(c.getSource()[i])) 
				return c.getMax()[i];
		
		return -1;
	}

	public int minMem(Composant c, String s)
	{
		for(int i=0; c.getSource().length>i; i++)
			if(s.equals(c.getSource()[i])) 
				return c.getMin()[i];

		return -1;
	}

	//Getters et Setters 

	public String[] getSource() {
		return Source;
	}

	public void setSource(String[] source) {
		this.Source = source;
	}

	public String[] getTarget() {
		return Target;
	}

	public void setTarget(String[] target) {
		this.Target = target;
	}

	public int getMinStart() {
		return MinStart;
	}

	public void setMinStart(int minstart) {
		this.MinStart = minstart;
	}

	public int getMaxStart() {
		return MaxStart;
	}

	public void setMaxStart(int maxstart) {
		this.MaxStart = maxstart;
	}

	public int[] getMin() {
		return Min;
	}

	public void setMin(int[] min) {
		this.Min = min;
	}

	public int[] getMax() {
		return Max;
	}

	public void setMax(int[] max) {
		this.Max = max;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		this.Name = name;
	}

	public abstract String toXML(Systeme sys);
}

