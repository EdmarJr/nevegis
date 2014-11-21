package br.jus.stj.sigeven.view.controller.participantes;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import br.jus.stj.sigeven.view.controller.ProgressController;

@ManagedBean
@ViewScoped
public class ProgressBarView implements Serializable {

	@Inject
	private ProgressController progressController;

	public ProgressController getProgressController() {
		return progressController;
	}


	public void testando() {

		int i = 0;
		while (i < 10) {
			i++;
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			getProgressController().setProgress(
					getProgressController().getProgress() + 10);
		}
	}
}