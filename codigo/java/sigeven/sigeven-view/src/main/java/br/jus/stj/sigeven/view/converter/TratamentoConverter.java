package br.jus.stj.sigeven.view.converter;

import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.jus.stj.sigeven.entity.db2.maladir.TratamentoMDIR;

@FacesConverter(value = "tratamentoConverter", forClass = TratamentoMDIR.class)
public class TratamentoConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		 if (arg2 != null) {
	            return this.getAttributesFrom(arg1).get(arg2);
	        }
	        return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		 if (arg2 != null && ! "".equals(arg2)) {
	            TratamentoMDIR entity = (TratamentoMDIR) arg2;
	 
	            if (entity.getId() != null) {
	                this.addAttribute(arg1, entity);
	                return entity.getId().toString();
	 
	            }
	        }
	        return "";
	}
	
	private void addAttribute(UIComponent component, TratamentoMDIR o) {
        this.getAttributesFrom(component).put(o.getId().toString(), o);
    }
 
    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
