package mirelac;

public class First extends UniteTraitement
{

	public First(String id)
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
		
		for(int j=0;this.getSource().length>j;j++)
		{
			codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\""+(-100*j)+"\"><name x=\"130\" y=\""+((-100*j)-25)+"\">P"+j+"</name><label kind=\"invariant\" x=\"150\" y=\""+((-100*j)+20)+"\">x&lt;"+this.getMax()[j]+"</label></location>\n";
		}

		int id_temp = sys.getIdLocation();
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"></location>\n";
		
		//locations sortie
		locationsSortie(sys, 50);

		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";

		//transitions entree
		for(int j=0;this.getSource().length>j;j++){
			codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+j+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\""+((-100*j)-20)+"\">k_"+this.getSource()[j]+"_"+this.getName()+"?</label><label kind=\"assignment\" x=\"50\" y=\""+((-100*j))+"\">x:=0</label>";
			if(j>0){codexml += "<nail x=\"0\" y=\""+(-100*j)+"\"/>";}
			codexml += "</transition>\n";

		}
		for(int j=0;this.getSource().length>j;j++){
			codexml += "<transition><source ref=\""+(init+j+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"200\" y=\""+((-100*j)-20)+"\">x&gt;="+this.getMin()[j]+"</label>";
			if(j>0){codexml += "<nail x=\"300\" y=\""+(-100*j)+"\"/>";}
			codexml += "</transition>\n";
		}


		//transitions sortie
		transitionsSortie(sys, 50, 0, init, id_temp);
		
		codexml += "</template>\n";

		return codexml;
	}
}