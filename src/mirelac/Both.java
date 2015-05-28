package mirelac;

/**
 * <b>UniteTraitement du type Both d'un systeme MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public class Both extends UniteTraitement
{

	 /**
	 * Constructeur Both
	 * @param id nom du composant
     */
	public Both(String id)
	{
		super(id);
		this.decalage = 1;
	}

	/**
	 * Construit le xml du composant Both
	 * @param sys systeme du composant
	 */
	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		codexml += "<template>\n";

		//parameters
		parameters(sys);

		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"><name x=\"280\" y=\"-25\">P</name><label kind=\"invariant\" x=\"240\" y=\"20\">x&lt;"+this.getMax()[0]+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"-100\"></location>\n";
		int id_temp = sys.getIdLocation();
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"450\" y=\"0\"><name x=\"430\" y=\"-25\">S</name></location>\n";
		
		//locations sortie
		locationsSortie(sys);

		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";
		
		//transitions entree
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+2)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-20\">k_"+this.getSource()[0]+"_"+this.getName()+"?</label></transition>\n";
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+3)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-120\">k_"+this.getSource()[1]+"_"+this.getName()+"?</label><nail x=\"0\" y=\"-100\"/></transition>\n";
		codexml += "<transition><source ref=\""+(init+2)+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"200\" y=\"-20\">k_"+this.getSource()[1]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"200\" y=\"0\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+3)+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"200\" y=\"-120\">k_"+this.getSource()[0]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"200\" y=\"-100\">x:=0</label><nail x=\"300\" y=\"-100\"/></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"350\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";

		//transitions sortie
		transitionsSortie(sys, init, id_temp);

		codexml += "</template>\n";

		return codexml;
	}
}