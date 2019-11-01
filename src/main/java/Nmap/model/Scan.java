package Nmap.model;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "nmaprun")
public class Scan {
	
	private List<Host> hosts;
	private List<Link> linkTraceroute=new ArrayList<Link>();

	@XmlElement(name="host")
	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}

	@Override
	public String toString() {
		return "Scan [Hosts:" + getHosts() + "]";
	}
	
	
	public void setLinkTraceroute(List<Link> linkTraceroute) {
		this.linkTraceroute = linkTraceroute;
	}

	public List<Link> getLinkTraceroute(){
		ArrayList <Link> list = new ArrayList<Link>();
		if(linkTraceroute!=null && linkTraceroute.isEmpty() && hosts!=null){
			Host you = new Host();
			you.getStatus().setState(Status.State.you);
			you.setAddress(new Address("You"));
			for(Host host : hosts){
			   if(host.getTrace()==null || host.getTrace().size()<=1)
				   list.add(new Link(you,host));
			   else {
				   Host prev = you;
				   for(Hop hop: host.getTrace()){
					   Host next = hop.toHost();
					   next= hosts.contains(next) ? hosts.get(hosts.indexOf(next)) : next;
					   list.add(new Link(prev, next));
					   prev=next;
				   }
			   }
			}
			setLinkTraceroute(list);
		}
		return linkTraceroute;
	}
	
	

}
