package mirelac;

public class Rendering extends Composant
{

	public Rendering(String id)
	{
		super(id);
	}

	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(lock_"+this.getSource()[0]+", unlock_"+this.getSource()[0]+");\n");

		codexml += "<template>\n";

		//parameters
		codexml += "<name>"+this.getName()+"_t</name>\n";
		codexml += "<parameter>urgent chan &amp;lock_"+this.getSource()[0]+", urgent chan &amp;unlock_"+this.getSource()[0]+"</parameter>\n";
		codexml += "<declaration>clock x;</declaration>\n";

		//locations
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"><name x=\"130\" y=\"-25\">M</name><label kind=\"invariant\" x=\"100\" y=\"20\">x&lt;"+this.getMax()[0]+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"450\" y=\"0\"><name x=\"430\" y=\"-25\">S</name><label kind=\"invariant\" x=\"400\" y=\"20\">x&lt;"+this.getMaxStart()+"</label></location>\n";
		
		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";	

		//transitions
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-20\">lock_"+this.getSource()[0]+"!</label><label kind=\"assignment\" x=\"50\" y=\"0\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+(init+2)+"\"/><label kind=\"guard\" x=\"200\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+2)+"\"/><target ref=\""+(init+3)+"\"/><label kind=\"synchronisation\" x=\"350\" y=\"-20\">unlock_"+this.getSource()[0]+"!</label><label kind=\"assignment\" x=\"350\" y=\"0\">x:=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+3)+"\"/><target ref=\""+init+"\"/><label kind=\"guard\" x=\"200\" y=\"80\">x&gt;="+this.getMinStart()+"</label><nail x=\"450\" y=\"100\"/><nail x=\"0\" y=\"100\"/></transition>\n";
		
		codexml += "</template>\n";

		return codexml;
	}
}
