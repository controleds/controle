package managedbean;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public abstract class AbstractMB implements Serializable {

	
	private static final long serialVersionUID = 7605993745409947150L;

	public void menssagemSucesso(String msg){
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_INFO,msg, ""));
	}
	
	public void menssagemErro(String msg){
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_ERROR,msg, ""));
	}
	
	public void menssagemAlerta(String msg){
		FacesContext.getCurrentInstance().addMessage(null,	new FacesMessage(FacesMessage.SEVERITY_WARN,msg, ""));
	}
	
	
	public void imprimeRelatorio(List<?> list, HashMap<String, Object> parameters, String relatorio) {
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.responseComplete();
			ServletContext scontext = (ServletContext) facesContext.getExternalContext().getContext();
			JasperReport report = JasperCompileManager
					.compileReport(scontext.getRealPath("report/" + relatorio + ".jrxml"));
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();
			imprimePdf(facesContext, baos);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void imprimePdf(FacesContext facesContext, ByteArrayOutputStream baos) throws IOException {
		byte[] bytes = baos.toByteArray();
		if (bytes != null && bytes.length > 0) {
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "attachment; filename=\"relatorioPorData.pdf\"");
			response.setContentLength(bytes.length);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(bytes, 0, bytes.length);
			outputStream.flush();
			outputStream.close();
		}
	}
}
