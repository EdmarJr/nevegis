package br.jus.stj.sigeven.view.controller.participantes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class ParticipanteFotoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String file;

	byte[] foto;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public void upload(FileUploadEvent event) {
		byte[] bytes = null;
		try {
			bytes = obterBytes(event.getFile().getInputstream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.getComponent().getResourceBundleMap().clear();
		setFoto(bytes);
	}

	private byte[] obterBytes(InputStream in) {
		byte[] retorno = null;
		try {

			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int read = 0;

			while ((read = in.read()) != -1) {
				buffer.write(read);
			}
			in.close();
			in = null;
			buffer.flush();
			retorno = buffer.toByteArray();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return retorno;
	}

	public StreamedContent getImage() {
		//
		if (getFoto() == null || getFoto().length == 0) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			return new DefaultStreamedContent(new ByteArrayInputStream(
					getFoto()));
		}
	}

	public void testando() {
		if (file != null) {
			System.out.println("teste");
		}
	}

}
