package Nmap.model.menu;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name="menu")
public class Menu {
	
	private List<Submenu> submenus = new ArrayList<>();

	@XmlElement(name="submenu")
	public List<Submenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<Submenu> submenus) {
		this.submenus = submenus;
	}

}
