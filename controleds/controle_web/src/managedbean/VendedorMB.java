package managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import ejb.VendedorBO;
import model.VendedorPO;




@ManagedBean(name="vendedorMB")
@ViewScoped
public class VendedorMB extends AbstractMB implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private VendedorBO vendedorBO;
	
	private VendedorPO vendedorPO = new VendedorPO();
	private List<VendedorPO> vendedoresList = new ArrayList<>();
	
	@PostConstruct
	public void ini(){
		vendedorPO = new VendedorPO();
		vendedoresList = vendedorBO.findAll();
	}
	
	
	public void salvar(){
		try {
			vendedorBO.save(vendedorPO);
			ini();
			menssagemSucesso("Vendedor Salvo com Sucesso.");
		} catch (Exception e) {
			menssagemErro(e.getMessage());
		}
		
	}
	
	
	public void handleFileUpload(FileUploadEvent event) {
		vendedorPO.setFoto(event.getFile().getContents());
		FacesMessage message = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void selectVend(VendedorPO vendedorPO){
		this.vendedorPO = vendedorPO;
	}


	public VendedorBO getVendedorBO() {
		return vendedorBO;
	}


	public void setVendedorBO(VendedorBO vendedorBO) {
		this.vendedorBO = vendedorBO;
	}


	public VendedorPO getVendedorPO() {
		return vendedorPO;
	}


	public void setVendedorPO(VendedorPO vendedorPO) {
		this.vendedorPO = vendedorPO;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public List<VendedorPO> getVendedoresList() {
		return vendedoresList;
	}


	public void setVendedoresList(List<VendedorPO> vendedoresList) {
		this.vendedoresList = vendedoresList;
	}

	
}
