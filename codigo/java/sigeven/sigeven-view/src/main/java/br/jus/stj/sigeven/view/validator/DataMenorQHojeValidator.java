package br.jus.stj.sigeven.view.validator;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("dataMenorQHojeValidator")
public class DataMenorQHojeValidator extends GenericValidator{
	
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
    	if(value!=null){
    		Date dataParametro = (Date)value;
    		
			if(!((UIInput) component).getSubmittedValue().equals(dateToString((Date)value)) || dataParametro.before(stringToDate(dateToString(new Date())))){
				FacesMessage msg = new FacesMessage(getMensagem("msg", "msg.msg026.datafimmenordatainicio"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			}
    	}
    }
    

    protected String dateToString(Date data){
    	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
    	String dataSaida = formatter.format(data);
    	return dataSaida;
    }
    
    protected Date stringToDate(String data){
    	SimpleDateFormat  formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date dataSaida = null;
		try {
			dataSaida = formatter.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
    	return dataSaida;
    }

}
