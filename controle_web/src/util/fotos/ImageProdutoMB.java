package util.fotos;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import ejb.ProdutoBO;
import model.ProdutoPO;

@ManagedBean(name="imageProdutoMB")
@ApplicationScoped
public class ImageProdutoMB {

    @EJB
    private ProdutoBO service;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            return new DefaultStreamedContent();
        }
        else {
            String id = context.getExternalContext().getRequestParameterMap().get("id");
            ProdutoPO produtoPO = service.find(new Long(id));
            if (produtoPO != null && produtoPO.getFoto() != null){
            	byte[] foto = service.find(new Long(id)).getFoto();
                return new DefaultStreamedContent(new ByteArrayInputStream(foto));
            } else {
            	return new DefaultStreamedContent(new ByteArrayInputStream(getFotoDefault()));
            } 
        }
    }
    
    
    
    
    private static byte[] getFotoDefault() {

		BufferedImage imagem = null;
		try {
			
			
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance()
		            .getExternalContext().getContext();
		   
			String realPath = ctx.getRealPath("/");
			realPath = realPath + "\\imagens\\default.jpg";
			
			imagem = ImageIO.read(new File(realPath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		BufferedImage bi = new BufferedImage(imagem.getWidth(null),imagem.getHeight(null),BufferedImage.TYPE_INT_RGB);  
		Graphics bg = bi.getGraphics();  
		bg.drawImage(imagem, 0, 0, null);  
		bg.dispose();  

		ByteArrayOutputStream buff = new ByteArrayOutputStream();         
		try {    
			ImageIO.write(bi, "JPG", buff);    
		} catch (IOException e) {    
			e.printStackTrace();    
		}    
		return buff.toByteArray();        

	}

    

}
