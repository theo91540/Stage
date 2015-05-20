package mirelac;

public class Memory extends Composant
{

	public Memory(String id)
	{
		super(id);
	}

	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		sys.addSysChan("urgent chan lock_"+this.getName()+";\nurgent chan unlock_"+this.getName()+";\n");
		sys.addSysTemplate(this.getName()+" = "+this.getName()+"_t(lock_"+this.getName()+", unlock_"+this.getName()+");\n");

		codexml += "<template>\n<name>"+this.getName()+"_t</name>\n";
		codexml += "<parameter>urgent chan &amp;lock_"+this.getName()+", urgent chan &amp;unlock_"+this.getName()+"</parameter>\n<declaration></declaration>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">W</name></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"></location>\n";
		codexml += "<init ref=\""+init+"\"/>\n";
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"-20\">lock_"+this.getName()+"?</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+init+"\"/><label kind=\"synchronisation\" x=\"50\" y=\"80\">unlock_"+this.getName()+"?</label><nail x=\"150\" y=\"100\"/><nail x=\"0\" y=\"100\"/></transition>\n</template>\n";
			
		return codexml;
	}
}