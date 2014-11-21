package br.jus.stj.sigeven.view.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dataNascimentoValidator")
public class DataMenorQueHojeMaiorQue1990Validator extends GenericValidator {

	private static final String DATA_MENOR = "01/01/1900";
	private static final String DATA_FORMAT = "dd/MM/yyyy";

	@Override
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		if((value == null) || (value instanceof String && ((String) value).equals(""))){
				return;
		}
		try {
			Date dataTemp = (Date) value;
			Date dataMenor = new SimpleDateFormat(DATA_FORMAT)
					.parse(DATA_MENOR);
			Date dataAtual = new Date(System.currentTimeMillis());			
			
			if (dataTemp.after(dataAtual) || dataTemp.before(dataMenor)) {
				FacesMessage msg = new FacesMessage(getMensagem("msg",
						"msg.msg016.anonascimentoinvalido"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
