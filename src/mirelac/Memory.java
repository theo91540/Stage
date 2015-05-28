package mirelac;

/**
 * <b>Composant du type Memory d'un systeme MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public class Memory extends Composant
{

 	/**
	 * Constructeur Memory
	 * @param id nom du composant
     */
	public Memory(String id)
	{
		super(id);
	}

	/**
	 * Construit le xml du composant Memory
	 * @param sys systeme du composant
	 */
	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		sys.addSysChan("urgent chan lock_"+this.getName()+";\nurgent chan unlock_"+this.getName()+";\n");
		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(lock_"+this.getName()+", unlock_"+this.getName()+");\n");

		codexml += "<template>\n";

		//parameters
		codexml += "<name>"+this.getName()+"_t</name>\n";
		codexml += "<parameter>urgent chan &amp;lock_"+this.getName()+", urgent chan &amp;unlock_"+this.getName()+"</parameter>\n";
		codexml += "<declaration></declaration>\n";

		//locations
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"></location>\n";

		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";

		//transitions
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-20\">lock_"+this.getName()+"?</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-120\">unlock_"+this.getName()+"?</label><nail x=\"150\" y=\"-100\"/><nail x=\"0\" y=\"-100\"/></transition>\n";
		
		codexml += "</template>\n";

		return codexml;
	}
}