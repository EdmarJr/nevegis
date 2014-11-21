package br.jus.stj.sigeven.view.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;

@FacesValidator("selectOneMenuValidator")
public class SelectOneMenuValidator extends GenericValidator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)
			throws ValidatorException {
		
		if (value == null) {
			FacesMessage msg = new FacesMessage(getMensagem("msg",
					"msg.campo.obrigatorio"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

		return;
	}

	
}
	