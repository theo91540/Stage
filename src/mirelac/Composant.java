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
	/**
	 *	Nom du composant
	 */
	protected String name;

	/**
	 *	Tableau des composants sources du composant
	 */
	protected String[] source;

	/**
	 *	Tableau des composants cibles du composant
	 */
	protected String[] target;

	/**
	 *	min debut
	 */
	protected int minStart;

	/**
	 *	max debut
	 */
	protected int maxStart;

	/**
	 *	tableau de duree min
	 */
	protected int[] min;

	/**
	 *	tableau de duree max
	 */
	protected int[] max;

	/**
	 *	code xml du composant
	 */
	protected String codexml;

	/**
	 *	echelle de construction du composant
	 */
	protected int echelle;

	/**
	 *	decalage des locations de sortie
	 */
	protected int decalage;

	/**
	 *	Constructeur de composant
	 *	@param id nom du composant
	 */
	public Composant(String id)
	{
		this.name = id;
		this.source = new String[0];
		this.target = new String[0];
		this.minStart = 0;
		this.maxStart = 0;
		this.min = new int[0];
		this.max = new int[0];
		this.codexml = new String();
		this.echelle = 50;
	}
	
	/**
	 *	Construit le xml correspondant 
	 *	@param sys systeme du composant
	 */
	public abstract String toXML(Systeme sys);

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

	////////////////////////
	// Getters et Setters //
	////////////////////////

	public String[] getSource() {
		return source;
	}

	public void setSource(String[] source) {
		this.source = source;
	}

	public String[] getTarget() {
		return target;
	}

	public void setTarget(String[] target) {
		this.target = target;
	}

	public int getMinStart() {
		return minStart;
	}

	public void setMinStart(int minstart) {
		this.minStart = minstart;
	}

	public int getMaxStart() {
		return maxStart;
	}

	public void setMaxStart(int maxstart) {
		this.maxStart = maxstart;
	}

	public int[] getMin() {
		return min;
	}

	public void setMin(int[] min) {
		this.min = min;
	}

	public int[] getMax() {
		return max;
	}

	public void setMax(int[] max) {
		this.max = max;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

