package mirelac;

public class Priority extends UniteTraitement
{

	public Priority(String id)
	{
		super(id);
	}

	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		codexml += "<template>\n";

		//parameters
		parameters(sys);

		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"><name x=\"130\" y=\"-25\">P</name><label kind=\"invariant\" x=\"100\" y=\"20\">x&lt;"+this.getMax()[0]+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"-100\"><name x=\"-20\" y=\"-125\">W2</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"-100\"><name x=\"130\" y=\"-125\">P2</name><label kind=\"invariant\" x=\"100\" y=\"-80\">x&lt;"+this.getMax()[1]+"</label></location>\n";
		int id_temp = sys.getIdLocation();
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"></location>\n";
		
		//locations sortie
		locationsSortie(sys, 50);
		
		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";

		//transitions entree
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-20\">k_"+this.getSource()[0]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"50\" y=\"0\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+2)+"\"/><label kind=\"synchronisation\" x=\"-60\" y=\"-60\">k_"+this.getSource()[1]+"_"+this.getName()+"?</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+2)+"\"/><target ref=\""+(init+3)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-120\">k_"+this.getSource()[0]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"50\" y=\"-100\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"200\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+3)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"200\" y=\"-120\">x&gt;="+this.getMin()[1]+"</label><nail x=\"300\" y=\"-100\"/></transition>\n";

		//transitions sortie
		transitionsSortie(sys, 50, 0, init, id_temp);

		codexml += "</template>\n";

		return codexml;
	}
	
}