package mirelac;

/**
 * <b>Capteur du type Periodic d'un systeme MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public class Periodic extends Capteur
{

 	/**
	 * Constructeur Periodic
	 * @param id nom du composant
     */
	public Periodic(String id)
	{
		super(id);
		this.decalage = 1;
	}

	/**
	 * Construit le xml du composant Periodic
	 * @param sys systeme du composant
	 */
	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();
		int id_temp = sys.getIdLocation() + 2;

		codexml += "<template>\n";

		//parametres systeme
		parameters(sys);

		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">E</name><label kind=\"invariant\" x=\"-50\" y=\"20\">x&lt;"+this.getMaxStart()+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(3*echelle)+"\" y=\"0\"><name x=\""+((3*echelle)-20)+"\" y=\"-25\">T</name><label kind=\"invariant\" x=\""+(2*echelle)+"\" y=\"20\">x&lt;"+this.getMax()[0]+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(6*echelle)+"\" y=\"0\"><name x=\""+((36*echelle)-20)+"\" y=\"-25\">S</name></location>\n";

		//locations sortie
		locationsSortie(sys);

		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";

		//transitions entree
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"guard\" x=\""+(echelle)+"\" y=\"-20\">x&gt;="+this.getMinStart()+"</label><label kind=\"assignment\" x=\""+(echelle)+"\" y=\"0\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+(4*echelle)+"\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";

		//transitions sortie
		transitionsSortie(sys, init, id_temp);

		codexml += "</template>\n";

		return codexml;
	}
}