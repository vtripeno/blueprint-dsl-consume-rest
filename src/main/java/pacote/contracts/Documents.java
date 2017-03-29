package pacote.contracts;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Documents {
	private String cpf;
	private String rg;
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getRg() {
		return rg;
	}
	public void setRg(String rg) {
		this.rg = rg;
	}
}
