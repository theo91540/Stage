public class Periodic extends Capteur
{

	public Periodic(String id)
	{
		super(id);
	}

	public String toXML(Systeme sys)
	{
		int init = sys.getIdLocation();

		parameters(sys);
		
		//locations entree
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"0\" y=\"0\"><name x=\"-20\" y=\"-25\">E</name><label kind=\"invariant\" x=\"-50\" y=\"20\">x&lt;"+this.getMaxStart()+"</label></location>\n";
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"150\" y=\"0\"><name x=\"130\" y=\"-25\">T</name><label kind=\"invariant\" x=\"100\" y=\"20\">x&lt;"+this.getMax()[0]+"</label></location>\n";
		int id_temp = sys.getIdLocation();
		codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\"300\" y=\"0\"><name x=\"280\" y=\"-25\">S</name></location>\n";
		//locations sortie
		int nbLoc = sys.nbOutLoc(this.getTarget());
		int n=nbLoc;
		for(int j=0;n>0;j++){
			if(sys.toComp(this.getTarget()[j]) instanceof Memory){
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*(n+1))+"\" y=\"100\"><label kind=\"invariant\" x=\""+((150*(n+1))-50)+"\" y=\"120\">x&lt;"+maxMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></location>\n";
				n--;
				codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*(n+1))+"\" y=\"100\"></location>\n";
				n--;
				if(n>0){
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*(n+1))+"\" y=\"100\"></location>\n";
					n--;
				}
			}
			else{
				if(n>0){
					codexml += "<location id=\""+(sys.setIdLocation())+"\" x=\""+(150*(n+1))+"\" y=\"100\"></location>\n";
					n--;
				}
			}
		}
		//location initiale
		codexml += "<init ref=\""+init+"\"/>\n";
		//transitions entree
		codexml += "<transition><source ref=\""+init+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"guard\" x=\"50\" y=\"-20\">x&gt;="+this.getMinStart()+"</label><label kind=\"assignment\" x=\"50\" y=\"10\">x=0</label></transition>\n";
		codexml += "<transition><source ref=\""+(init+1)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\"200\" y=\"-20\">x&gt;="+this.getMin()[0]+"</label></transition>\n";
		//transitions sortie
		n = nbLoc;
		for(int j=0;this.getTarget().length>j;j++){
			if(sys.toComp(this.getTarget()[j]) instanceof Memory){
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*(n+1))+50)+"\" y=\"80\">lock_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*(n+1))+50)+"\" y=\"110\">x=0</label>";
				if(n==nbLoc){codexml += "<nail x=\""+Math.max((150*(n+2)),300)+"\" y=\"0\"/><nail x=\""+Math.max((150*(n+2)),300)+"\" y=\"100\"/>";}
				codexml += "</transition>\n";
				n--;
				codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"guard\" x=\""+((150*(n+1))+50)+"\" y=\"80\">x&gt;="+minMem(sys.toComp(this.getTarget()[j]),this.getName())+"</label></transition>\n";
				n--;
				if(n==0){
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"200\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label><nail x=\"150\" y=\"100\"/></transition>\n";
				}
				else{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*(n+1))+50)+"\" y=\"80\">unlock_"+this.getTarget()[j]+"!</label></transition>\n";
					n--;
				}
			}
			else{
				if(n==0){
					codexml += "<transition><source ref=\""+id_temp+"\"/><target ref=\""+(init+1)+"\"/><label kind=\"synchronisation\" x=\"200\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\"200\" y=\"110\">x=0</label>";
					if(n==nbLoc){codexml += "<nail x=\"300\" y=\"100\"/>";}
					codexml += "<nail x=\"150\" y=\"100\"/></transition>\n";
				}
				else{
					codexml += "<transition><source ref=\""+(id_temp++)+"\"/><target ref=\""+id_temp+"\"/><label kind=\"synchronisation\" x=\""+((150*(n+1))+50)+"\" y=\"80\">k_"+this.getName()+"_"+this.getTarget()[j]+"!</label><label kind=\"assignment\" x=\""+((150*(n+1))+50)+"\" y=\"110\">x=0</label>";
					if(n==nbLoc){codexml += "<nail x=\""+Math.max((150*(n+2)),300)+"\" y=\"0\"/><nail x=\""+Math.max((150*(n+2)),300)+"\" y=\"100\"/>";}
					codexml += "</transition>\n";
					n--;
				}
			}
		}
		codexml += "</template>\n";

		return codexml;
	}
}