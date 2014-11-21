package br.jus.stj.sigeven.view.validator;

import javax.faces.validator.Validator;

import br.jus.stj.commons.util.MensagemUtil;

public abstract class GenericValidator implements Validator {
	
	public String getMensagem(String bundleName, String key) {
    	return MensagemUtil.getMensagem(bundleName, key);
    }
	
}
