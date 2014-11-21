package br.jus.stj.sigeven.view.validator;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("horaValidator")
public class HoraValidator extends GenericValidator{
	
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	if(value!=null){
			if(!((UIInput) component).getSubmittedValue().equals(dateToString((Date)value))){
				FacesMessage msg = new FacesMessage(getMensagem("msg", "msg.msg027.horainvalida"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
    	}
    }
    

    protected String dateToString(Date data){
    	Format formatter = new SimpleDateFormat("HH:mm");
    	String dataSaida = formatter.format(data);
    	return dataSaida;
    }

}
