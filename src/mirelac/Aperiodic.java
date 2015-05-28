package mirelac;

/**
 * <b>Capteur du type Aperiodic d'un systeme MIRELA.</b>
 * 
 * @author Johan Arcile, Theo Chelim
 * @version 1.1
 */
public class Aperiodic extends Capteur
{

 	/**
	 * Constructeur Aperiodic
	 * @param id nom du composant
     */
	public Aperiodic(String id)
	{
		super(id);
		this.decalage = 0;
	}

	/**
	 * Construit le xml du composant Aperiodic
	 * @param sys systeme du composant
	 */
	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();
		int id_temp = sys.getIdLocation() + 1;

		codexml += "<template>\n";

		//parametres systeme
		parameters(sys);
		
		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">S</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(echelle*3)+"\" y=\"0\"><name x=\""+(echelle*3-20)+"\" y=\"-25\">T</name></location>\n";

		//locations sortie
		locationsSortie(sys);

		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";

		//transitions entree
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+echelle+"\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";
		
		//transitions sortie
		transitionsSortie(sys, init, id_temp);

		codexml += "</template>\n";
		
		return codexml;
	}
}