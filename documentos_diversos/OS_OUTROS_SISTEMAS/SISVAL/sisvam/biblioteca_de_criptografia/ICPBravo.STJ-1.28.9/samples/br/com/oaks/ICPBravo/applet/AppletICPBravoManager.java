package br.com.oaks.ICPBravo.applet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.security.AccessController;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.com.oaks.ICPBravo.algorithm.SignatureAlgorithm;
import br.com.oaks.ICPBravo.algorithm.asymmetric.RSA;
import br.com.oaks.ICPBravo.algorithm.digest.DigestAlgorithm;
import br.com.oaks.ICPBravo.algorithm.digest.SHA1;
import br.com.oaks.ICPBravo.algorithm.symmetric.SymmetricAlgorithm;
import br.com.oaks.ICPBravo.applet.ConfiguracaoApplet.ConfiguracaoAlteradaListener;
import br.com.oaks.ICPBravo.asn1.x509.KeyPurposeId;
import br.com.oaks.ICPBravo.asn1.x509.KeyUsage;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificate;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificateGenerator;
import br.com.oaks.ICPBravo.certs.ICPBravoCertificationRequest;
import br.com.oaks.ICPBravo.cms.CMSEnvelopedData;
import br.com.oaks.ICPBravo.cms.CMSSignedData;
import br.com.oaks.ICPBravo.cms.CMSSignerInformation;
import br.com.oaks.ICPBravo.cms.content.Base64Content;
import br.com.oaks.ICPBravo.cms.content.BytesContent;
import br.com.oaks.ICPBravo.cms.content.Content;
import br.com.oaks.ICPBravo.cms.content.FileContent;
import br.com.oaks.ICPBravo.cms.content.HexContent;
import br.com.oaks.ICPBravo.cms.content.ISO_8859_1_Content;
import br.com.oaks.ICPBravo.cms.content.UrlAndBase64Content;
import br.com.oaks.ICPBravo.cms.pkcs9.CommitmentTypeIndicationAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.DocumentDescriptionAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.FriendlyNameAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.SignedAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.SignerLocationAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.UnsignedAttribute;
import br.com.oaks.ICPBravo.cms.pkcs9.UnstructuredNameAttribute;
import br.com.oaks.ICPBravo.cms.policies.CMSPolicy;
import br.com.oaks.ICPBravo.cms.policies.Policy;
import br.com.oaks.ICPBravo.exceptions.AcessControlException;
import br.com.oaks.ICPBravo.exceptions.FileDoesNotExistsException;
import br.com.oaks.ICPBravo.exceptions.HttpErrorException;
import br.com.oaks.ICPBravo.exceptions.IOErrorException;
import br.com.oaks.ICPBravo.exceptions.InvalidReadFileException;
import br.com.oaks.ICPBravo.exceptions.InvalidURLException;
import br.com.oaks.ICPBravo.exceptions.NotSuportedAlgorithmException;
import br.com.oaks.ICPBravo.exceptions.StreamOperationException;
import br.com.oaks.ICPBravo.manager.ICPBravoManager;
import br.com.oaks.ICPBravo.manager.PKCS12Manager;
import br.com.oaks.ICPBravo.manager.WindowsMYManager;
import br.com.oaks.ICPBravo.util.HttpProtocol;
import br.com.oaks.ICPBravo.util.ICPBravoUtil;
import br.com.oaks.ICPBravo.util.PDFSignedData;

/**
 * Applet padr�o para aplica��es WEB.
 * 
 * @author Ubiratan
 *
 */
public class AppletICPBravoManager extends AppletICPBravoBase {
	private static final long serialVersionUID = 1L;
	private String _managerName;
	private ICPBravoManager _manager = ICPBravoManager.getDefaultManager();
	private Policy _policy = null;
	private boolean _callBackMode = true;
	
	/**
	 * Indica se o Applet funcionar� em modo Callback, onde todos os retornos se dar�o por meio de callback ou n�o
	 * @param mode - true (default) - retorno por meio de callback, false - retorno direto pelo m�todo
	 */
	public void setCallBackMode(boolean mode) {
		_callBackMode = mode;
	}
	
	public static boolean _isRunnig = false;
	public boolean isRunning() {
		return _isRunnig;
	}
	
	private Policy getPolicy() throws InvalidURLException, HttpErrorException, StreamOperationException, AcessControlException, IOException, InvalidReadFileException, IOErrorException, FileDoesNotExistsException {
		if (_policy == null)
			_policy = _currentConfiguration.getConf().getPolicy();
		if (_policy == null)
			_policy = new CMSPolicy();

		if (!(tstUrl == null || tstUrl.isEmpty())) {
			_policy.setTSTUrl(tstUrl);
		} else if (! _currentConfiguration.getConf().getURLTst().isEmpty()) {
			_policy.setTSTUrl(_currentConfiguration.getConf().getURLTst());
		}
		
		return _policy;
	}
	
	private void addSignedAttribute(SignedAttribute attribute) throws InvalidURLException, HttpErrorException, StreamOperationException, AcessControlException, IOException, InvalidReadFileException, IOErrorException, FileDoesNotExistsException {
		Policy policy = getPolicy();
		policy.addSignedAttribute(attribute);
	}
	
	private void addUnsignedAttribute(UnsignedAttribute attribute) throws InvalidURLException, HttpErrorException, StreamOperationException, AcessControlException, IOException, InvalidReadFileException, IOErrorException, FileDoesNotExistsException {
		Policy policy = getPolicy();
		policy.addUnsignedAttribute(attribute);
	}
	
	/**
	 * Indica um nome amig�vel para as pr�ximas assinaturas realizadas
	 * 
	 * @param name
	 */
	public void addUnstructureParameter(String name) {
		try {
			addUnsignedAttribute(new UnstructuredNameAttribute(name));
		} catch (Exception e) {
			showError(e, "Invalid friendly name: "+e.getMessage());
		}
	}
	/**
	 * Indica um nome amig�vel para as pr�ximas assinaturas realizadas
	 * 
	 * @param name
	 */
	public void setFriendlyName(String name) {
		try {
			addUnsignedAttribute(new FriendlyNameAttribute(name));
		} catch (Exception e) {
			showError(e, "Invalid friendly name: "+e.getMessage());
		}
	}
	/**
	 * Indica o tipo de compromisso para as pr�ximas assinaturas realizadas
	 * 
	 * @param type
	 */
	public void setCommitmentType(String type) {
		try {
			addSignedAttribute(new CommitmentTypeIndicationAttribute(type));
		} catch (Exception e) {
			showError(e, "Invalid commitment type: "+e.getMessage());
		}
	}

	/**
	 * Indica uma descri��o de documento para as pr�ximas assinaturas realizadas
	 * 
	 * @param description
	 */
	public void setDocumentDescription(String description) {
		try {
			addSignedAttribute(new DocumentDescriptionAttribute(description));
		} catch (Exception e) {
			showError(e, "Invalid document description: "+e.getMessage());
		}
	}

	/**
	 * Indica a localiza��o do signat�rio para as pr�ximas assinaturas realizadas
	 * 
	 * @param countryName
	 * @param localityName
	 * @param postalAddress
	 */
	public void setSignerLocation(String countryName, String localityName, String postalAddress) {
		try {
			addSignedAttribute(new SignerLocationAttribute(countryName, localityName, postalAddress));
		} catch (Exception e) {
			showError(e, "Invalid signer location: "+e.getMessage());
		}
	}
	
	/**
	 * Construtor do applet
	 */
	public AppletICPBravoManager () {
	}

	/**
	 * Indica se ser�o ou n�o apresentadas as mensagens de erro em forma de dialog do applet.
	 * Caso seja indicado que n�o deve apresentar as mensagens, utilize o m�todo setFinalizeOperationCallback(...) do javascript
	 * fornecido junto a API para indicar o m�todo javascript que ser� utilizado para o tratamento da mensagem de erro.
	 *  
	 * @param apresentaDialogsErro
	 */
	public void apresentaDialogsErro(final boolean apresentaDialogsErro) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				_currentConfiguration._conf.setApresentaDialogsErro(apresentaDialogsErro);
				return null;
			}
		});
	}
	
	/**
	 * Indica a pol�tica utilizada nas pr�ximas assinaturas, que pode ser: AD-CP, AD-A, AD-D, AD-R, AD-T ou o valor default CMS. 
	 * 
	 * @param policyName
	 */
	public void setPolicy(final String policyName) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					_policy = ConfiguracaoApplet.getPolicy(policyName, tstUrl);
				} catch (Exception e) {
					showError(e, "Invalid policy: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Salva um certificado, codificado em base64, em seu gerenciador com um determinado alias.
	 *  
	 * @param managerName
	 * @param alias
	 * @param certificateB64
	 */
	public void saveCertificate(final String managerName, final String alias, final String certificateB64, final String csrB64) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					
					if (managerName != null) {
						_manager = ICPBravoManager.getManagerInstance(managerName);
						_manager.reload();
						_managerName = managerName;
					}
					
					ICPBravoCertificationRequest csr = null;
					if (csrB64 != null)
						csr = new ICPBravoCertificationRequest(Base64Content.fromBase64(csrB64));

					PrivateKey privateKey = null;
					if (_managerName.equals(WindowsMYManager.ManagerName)) {
						privateKey = _manager.getPrivateKey(alias);
						_manager = new WindowsMYManager();
						File pkcs12Temp = new File(getPkcs12Temporary());
						pkcs12Temp.delete();
					}
					
					_manager.install(alias, csr, privateKey, certificateB64);
					_manager.saveTofile();
					callJavaScript("operationOk();");
				} catch (Exception e) {
					showError(e, "Erro ao salvar certificado: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	/**
	 * Encerra a sess�o com o dispositivo conectado
	 */
	public void logout() {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					if (_manager != null)
						_manager.logout();
				} catch (Exception e) {
					showError(e, "Erro ao salvar certificado: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Verifica uma assinatura
	 * @param pkcs7
	 */
	public void verifySignature(final String pkcs7) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					String ret = "";
					CMSSignedData cms = new CMSSignedData(new Base64Content(ICPBravoUtil.readFromUrlAndBase64(pkcs7)));
					try {
						cms.verifyICPBrasilSignature();
						ret += "Assinatura ICP-Brasil OK";
					} catch (Exception e) {
						ret += "Assinatura ICP-Brasil n�o OK";
					}
					
					List<CMSSignerInformation> signers = cms.getSigners();
					for (CMSSignerInformation si : signers) {
						ret += ";Assinado por "+si.getCertificate().getName()+" em "+si.getSignatureDateTime().toString();
					}
					callJavaScript("initializeValue();", "addValuePart", "finalizeValue(\"ok\");", ret);
				} catch (Exception e) {
					showError(e, "Erro ao gerar CSR: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	/**
	 * Solicita a gera��o de um CSR (Certificate Signed Request), bem como seu respectivo par de chaves em um determinado menager. 
	 * 
	 * @param managerName
	 * @param alias - Alias para cria��o da chave no manager
	 * @param cn - Common Name
	 * @param validade - Per�odo de validade do certificado (em dias)
	 */
	public void generateCSR(final String managerName, final String alias, final String cn, final long validade) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					System.out.println("M: "+managerName+", A:"+alias+", CN:"+cn+", D:"+validade);
					_managerName = managerName;	
					if (managerName.equals(WindowsMYManager.ManagerName)) {
						File pkcs12Temp = new File(getPkcs12Temporary());
						if (pkcs12Temp.exists())
							pkcs12Temp.delete();
						_manager = new PKCS12Manager(getPkcs12Temporary(), "IcpBravoSecretKey!!".toCharArray());
					} else
						_manager = ICPBravoManager.getManagerInstance(managerName);
					SignatureAlgorithm alg = new SignatureAlgorithm(new SHA1(), new RSA(_manager.getProvider()));
					ICPBravoCertificationRequest request = _manager.generateCSR(alias, cn, alg, validade * 24 * 3600);
					byte [] csr = request.getEncoded();
					_manager.saveTofile();
					callJavaScriptB64("initializeValue('CSR');", "addValuePart", "finalizeValue(\"ok\");", csr);
				} catch (Exception e) {
					showError(e, "Erro ao gerar CSR: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	private String getPkcs12Temporary() {
		return ICPBravoUtil.getTempFilesLocation()+"/Pkcs12Temp.p12";
	}
	
	/**
	 * Carrega os certificados de um determinado gerenciador, invocando a fun��o de callback indicada no m�todo javascript "setAddCertificate(...)".
	 * 
	 * @param managerName
	 */
	public String [][] loadCertificates(final String managerName) {
		return AccessController.doPrivileged(new PrivilegedAction<String [][]>() {
			@Override
			public String [][] run() {
/*
				Runnable action = new Runnable() {
					@Override
					public void run() {
						try {
							System.out.println("Carregando de "+managerName);
//							loadFromManager(managerName);
//							for (int a=0; a<certificates.length; a++) {
//								callJavaScript("addCertificate("+alias2Javascript(certificates[a].getAlias())+",'"+certificates[a].getName()+"');");
//							}
							if (managerName.equals("fromFile")) {
								_manager = ICPBravoManager.getDefaultManager();
								_manager.reload();
								ICPBravoCertificate cert = PainelMostraCertificados.carregarCertificadoDoArquivo(null, _currentConfiguration);
								_manager.addCertificate(cert.getAlias(), cert);
								callJavaScript("addCertificate("+alias2Javascript(cert.getAlias())+",'"+cert.getName()+"');");
							} else {
								_manager = ICPBravoManager.getManagerInstance(managerName);
								_manager.reload();
								
								for (ICPBravoCertificate cert : _manager.getCertificates()) {
									callJavaScript("addCertificate("+alias2Javascript(cert.getAlias())+",'"+cert.getName()+"');");
								}
							}
							callJavaScript("certificatesLoaded();");
						} catch (Exception e) {
							showError(e, "Erro na carga: "+e.getMessage());
						}
					}
				};
				
				Thread thread = new Thread(action, "Carga do dispositivo");
				if (!thread.isAlive())
					thread.start();
*/
				
				String toRet[][] = null;
				try {
					System.out.println("Carregando de "+managerName);
					if (managerName.equals("fromFile")) {
						_manager = ICPBravoManager.getDefaultManager();
						_manager.reload();
						ICPBravoCertificate cert = PainelMostraCertificados.carregarCertificadoDoArquivo(null, _currentConfiguration);
						_manager.addCertificate(cert.getAlias(), cert);
						callJavaScript("addCertificate("+alias2Javascript(cert.getAlias())+",'"+cert.getName()+"');");
					} else {
						_manager = ICPBravoManager.getManagerInstance(managerName);
						_manager.reload();
						
						int b=0;
						toRet = new String[_manager.getCertificates().size()][];
						for (ICPBravoCertificate cert : _manager.getCertificates()) {
							String subjectDN = (cert.getSubjectDN().getName()+(cert.getCPF() == null ? "":(",cpf="+cert.getCPF()))+(cert.getCNPJ() == null ? "":(",cnpj="+cert.getCNPJ()))).toLowerCase();
							
							boolean valid = true;
							if (filter != null && filter.length() > 0) {
								String peaces [] = filter.toLowerCase().split(",");
								for (int a=0; a<peaces.length; a++) {
									if (subjectDN.indexOf(peaces[a].trim()) < 0) {
										valid = false;
										break;
									}
								}
							}
							if (valid && cert.getPrivateKey() != null) {
								callJavaScript("addCertificate("+alias2Javascript(cert.getAlias())+",'"+cert.getName()+"');");
								toRet[b] = new String[2]; 
								toRet[b][0] = cert.getAlias();
								toRet[b][1] = cert.getName();
								b++;
							}
						}
					}
					callJavaScript("certificatesLoaded();");
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				
				return toRet;
			}
		});
	}
	
	private ICPBravoCertificate [] certificates = null;
	private void loadFromManager(String managerName) throws Exception {
		if (managerName.equals("fromFile")) {
			_manager = ICPBravoManager.getDefaultManager();
			_manager.reload();
			ICPBravoCertificate cert = PainelMostraCertificados.carregarCertificadoDoArquivo(null, _currentConfiguration);
			_manager.addCertificate(cert.getAlias(), cert);
			certificates = new ICPBravoCertificate[] {cert};
		} else {
			_manager = ICPBravoManager.getManagerInstance(managerName);
			_manager.reload();
			
			List<ICPBravoCertificate> certsOk = new ArrayList<ICPBravoCertificate>();
			for (ICPBravoCertificate cert : _manager.getCertificates()) {
				if (cert.getPrivateKey() != null) {
					String subjectDN = (cert.getSubjectDN().getName()+(cert.getCPF() == null ? "":(",cpf="+cert.getCPF()))+(cert.getCNPJ() == null ? "":(",cnpj="+cert.getCNPJ()))).toLowerCase();
					
					boolean valid = true;
					if (filter != null && filter.length() > 0) {
						String peaces [] = filter.toLowerCase().split(",");
						for (int a=0; a<peaces.length; a++) {
							if (subjectDN.indexOf(peaces[a].trim()) < 0) {
								valid = false;
								break;
							}
						}
					}
					if (valid)
						certsOk.add(cert);
				}
			}
			
			
			certificates = new ICPBravoCertificate[certsOk.size()];
			int a = 0;
			for (ICPBravoCertificate cert : certsOk) {
				certificates[a++] = cert;
			}
		}
	}
	
	private void showError(Exception e, String title) {
		if (_currentConfiguration._conf.getApresentaDialogsErro()) {
			JOptionPane.showMessageDialog(null, title, "ERRO", javax.swing.JOptionPane.ERROR_MESSAGE);
		}
		try {
			if (e != null) {
				e.printStackTrace();
				callJavaScript("operationError('"+e.getMessage()+"');");
			} else {
				System.out.println(title);
				callJavaScript("operationError('"+title.replaceAll("\n", ", ")+"');");
			}
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}

	private byte [] signSimpleBytes(ICPBravoCertificate cert, String toSign) throws Exception {
		Content sig = new Base64Content(toSign);
		return cert.sign(sig.getContent());
//		SignatureAlgorithm alg = new SignatureAlgorithm(new SHA1(), new RSA(cert.getManager().getProvider()));
//		return alg.sign(sig.getContent(), cert);
	}

	/**
	 * Assina os bytes com a chave privativa do certificado indicado.
	 * Trata-se de assinatura de algoritimo e n�o da gera��o de pacote de assinatura.
	 * O resultado da assinatura ser� devolvido em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 *  
	 * @param alias - Refer�ncia ao certificado selecionado
	 * @param toSign - bytes a serem assinados, codificados em base64.
	 */
	public String signSimpleBytes(final int alias, final String toSign) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					ICPBravoCertificate cert = _manager.getCertificate(javascript2Alias(alias));
					byte [] sign = signSimpleBytes(cert, toSign);
					if (_callBackMode)
						if (sign != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", sign);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(sign);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina os bytes com a chave privativa do certificado indicado.
	 * Trata-se de assinatura de algoritimo e n�o da gera��o de pacote de assinatura.
	 * O resultado da assinatura ser� devolvido em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param toSign
	 * @param separator
	 */
	public String signMultiplesBytes(final int alias, final String toSign, final String separator) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					ICPBravoCertificate cert = _manager.getCertificate(javascript2Alias(alias));
					String [] values = toSign.split(separator);
					String signs = "";
					for (String value : values) {
						if (signs.length() > 0)
							signs += separator;
						signs += new BytesContent(signSimpleBytes(cert, value)).getBase64Encoded();
					}
					if (_callBackMode)
						callJavaScript("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", signs);
					return signs;
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um arquivo local com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em base64, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param file
	 * @param signature
	 * @param encapsulate
	 */
	public String signFile(final int alias, final String file, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new FileContent(file), sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um stream de bytes produzidos pelo endere�o de URL fornecido com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em base64, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param url
	 * @param signature
	 * @param encapsulate
	 */
	public String signURL(final int alias, final String url, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new BytesContent(new HttpProtocol(url, _currentConfiguration.getConf().getProxyInfo()).read()),
							sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um stream de bytes produzidos pelo endere�o de URL fornecido com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em hexadecimal, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param url
	 * @param signature
	 * @param encapsulate
	 */
	public String signURLHex(final int alias, final String url, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new BytesContent(new HttpProtocol(url, _currentConfiguration.getConf().getProxyInfo()).read()), sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptHex("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return HexContent.encode(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um conjunto de bytes, indicado na mensagem, com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em base64, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param message
	 * @param signature
	 * @param encapsulate
	 */
	public String signMessage(final int alias, final String message, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new ISO_8859_1_Content(message), sig, encapsulate);
					if (_callBackMode) {
						if (pkcs7 != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					}
					return Base64Content.toBase64(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um conjunto de bytes, indicado na mensagem, com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em hexadecimal, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param message
	 * @param signature
	 * @param encapsulate
	 */
	public String signMessageToHex(final int alias, final String message, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new ISO_8859_1_Content(message), sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptHex("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return HexContent.encode(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um conjunto de bytes, indicado na mensagem em base64, com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em base64, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param message
	 * @param signature
	 * @param encapsulate
	 */
	public String signMessageB64(final int alias, final String message, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new Base64Content(signature);
					byte [] pkcs7 = signMessage(alias, new Base64Content(message), sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	public String coSignMessageB64(final int alias, final String signature) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new UrlAndBase64Content(signature);
					byte [] pkcs7 = signMessage(alias, null, sig, true);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	public String signPDFUrl(final int alias, final String url, final String reason, final String location) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					byte [] pdf = signPDFMessage(alias, new BytesContent(new HttpProtocol(url, _currentConfiguration.getConf().getProxyInfo()).read()), reason, location);
					if (_callBackMode)
						if (pdf != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pdf);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pdf);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	public String signPDFUrlToHex(final int alias, final String url, final String reason, final String location) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					byte [] pdf = signPDFMessage(alias, new BytesContent(new HttpProtocol(url, _currentConfiguration.getConf().getProxyInfo()).read()), reason, location);
					if (_callBackMode)
						if (pdf != null)
							callJavaScriptHex("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pdf);
						else
							showError(null, "Certificado inv�lido");
					return HexContent.encode(pdf);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	public String signPDFMessageB64(final int alias, final String message, final String reason, final String location) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					byte [] pdf = signPDFMessage(alias, new Base64Content(message), reason, location);
					if (_callBackMode)
						if (pdf != null)
							callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pdf);
						else
							showError(null, "Certificado inv�lido");
					return Base64Content.toBase64(pdf);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * Assina um conjunto de bytes, indicado na mensagem em hexadecimal, com o certificado indicado.
	 * O resultado da assinatura ser� devolvido em hexadecimal, em fun��o de callback indicado no m�todo "setCMSCallback(...)". 
	 * 
	 * @param alias
	 * @param message
	 * @param signature
	 * @param encapsulate
	 */
	public String signMessageHex(final int alias, final String message, final String signature, final boolean encapsulate) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				try {
					Content sig = null;
					if (signature != null)
						sig = new HexContent(signature);
					byte [] pkcs7 = signMessage(alias, new HexContent(message), sig, encapsulate);
					if (_callBackMode)
						if (pkcs7 != null)
							callJavaScriptHex("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", pkcs7);
						else
							showError(null, "Certificado inv�lido");
					return HexContent.encode(pkcs7);
				} catch (Exception e) {
					showError(e, "Erro na carga: "+e.getMessage());
				}
				return null;
			}
		});
	}

	/**
	 * @return retorna o algoritimo de digest setado no applet
	 */
	public String getDigestAlgorithm() {
		return _currentConfiguration.getConf().getNomeAlgoritmoDigest();
	}

	/**
	 * Retorna o hash de uma determinada mensagem 
	 * O resultado do hash ser� devolvido em base64, em fun��o de callback indicado no m�todo "setHASHCallback(...)". 
	 * 
	 * @param message
	 * @param hashAlgorithm
	 */
	public void getDocumentHash(final String message, final String hashAlgorithm) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					DigestAlgorithm alg = DigestAlgorithm.getInstance(hashAlgorithm);
					callJavaScriptB64("initializeHASH();", "addHASHPart", "finalizeHASH(\"ok\");", alg.digest(new ISO_8859_1_Content(message).getContent()));
				} catch (Exception e) {
					showError(e, "Erro no processamento: "+e.getMessage());
				}
				return null;
			}
		});		
	}
	
	public void setCRLIgnore(boolean ignoraCRL) {
		_currentConfiguration.getConf().setCRLIgnore(ignoraCRL);
	}

	private byte [] signMessage(final int alias, final Content message, final Content signature, final boolean encapsulate) throws Exception {
		ICPBravoCertificate cert;
		if (alias < 0) {
			int pos = getCertificateSelected("Windows-MY");
			cert = certificates[pos];
		} else
			cert = _manager.getCertificate(javascript2Alias(alias));
		if (cert == null)
			System.out.println("cert null");
		else
			System.out.println("cert "+cert.getName());
		if (_currentConfiguration.getConf().podeAssinar(cert)) {
			CMSSignedData cmsSignature = null;
			Policy policy = getPolicy();
			policy.setCrlIgnore(_currentConfiguration.getConf().isCRLIgnore());
			try { // Se j� tem uma assinatura
				cmsSignature = new CMSSignedData(signature);
			} catch (Exception e) { // Se � uma assinatura nova
				cmsSignature = new CMSSignedData(policy);
			}
			if (message != null)
				cmsSignature.setData(message);
			cmsSignature.setEncapsulate(encapsulate);
			cmsSignature.addSigner(cert, /*
					new SignatureAlgorithm( 
							_currentConfiguration.getConf().getAlgoritmoDigest(), 
							_currentConfiguration.getConf().getAlgoritmoAssinatura(cert.getManager().getProvider())), */
							policy); 
			return cmsSignature.getASN1Encoded();
		} else {
			String menssage = _currentConfiguration.getConf().mensagemSignatario(cert);
			showError(null, "certificado n�o permite assinar: "+menssage);
			return null;
		}
	}

	private byte [] signPDFMessage(final int alias, final Content message, final String reason, final String location) throws Exception {
		ICPBravoCertificate cert = _manager.getCertificate(javascript2Alias(alias));
		if (cert == null)
			System.out.println("cert null");
		else
			System.out.println("cert: "+cert.getName());
		if (_currentConfiguration.getConf().podeAssinar(cert)) {
			PDFSignedData pdfSignature = null;
			System.out.println("Lendo PDF");
			pdfSignature = new PDFSignedData(message.getContent());
			System.out.println("PDF Lido");

//			if (!(tstUrl == null || tstUrl.isEmpty())) {
//				pdfSignature.setTSTUrl(tstUrl);
//			} else if (! _currentConfiguration.getConf().getURLTst().isEmpty()) {
//				pdfSignature.setTSTUrl(_currentConfiguration.getConf().getURLTst());
//			}
			pdfSignature.addSigner(cert,
					new SignatureAlgorithm( 
							_currentConfiguration.getConf().getAlgoritmoDigest(), 
							_currentConfiguration.getConf().getAlgoritmoAssinatura(cert.getManager().getProvider())),
						getPolicy(),
//						"ICPBravo",
					new Date(), reason, location);
			return pdfSignature.getPdfEncoded();
		} else {
			String menssage = _currentConfiguration.getConf().mensagemSignatario(cert);
			showError(null, "certificado n�o permite assinar: "+menssage);
			return null;
		}
	}
	
	private int type;
	private String tstUrl;
	private String alias;
	private BigInteger serialNumber;
	private String subject;
	private String ou;
	private String o=null;
	private String c=null;
	private int aliasIssuer;
	private int keyUsage = 0;
	private List<KeyPurposeId> extendedKeyUsage = new ArrayList<KeyPurposeId>();
	private String crlUrl;
	private String policiesOid;
	private String policiesUrl;
	private Date from;
	private Date to;
	private SignatureAlgorithm sign;
	private String email;
	private String responsavel;
	private Date nascimento;
	private String cpf;
	private String nis;
	private String rg;
	private String oe;
	private String te;
	private String zona;
	private String sessao;
	private String municipioUf;
	private String inss;
	private String cnpj;
	private ICPBravoCertificate generated;

	public KeyPurposeId [] getExtendedKeyUsage() {
		KeyPurposeId [] toReturn = new KeyPurposeId [extendedKeyUsage.size()];
		for (int a=0; a<extendedKeyUsage.size(); a++)
			toReturn[a] = extendedKeyUsage.get(a);
		return toReturn;
	}
	
	public String createCertificate(final String managerName) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				System.out.println("gerando em "+managerName);
				try {
					ICPBravoCertificate certIssuer = null;
					if (aliasIssuer > 0)
						certIssuer = _manager.getCertificate(javascript2Alias(aliasIssuer));

					ICPBravoManager outMgr = ICPBravoManager.getManagerInstance(managerName);
					outMgr.reload();
					
					ICPBravoCertificateGenerator gen;
					if (type == 0) { // AC
						gen = new ICPBravoCertificateGenerator(
								outMgr.getProvider(), alias, serialNumber, subject, certIssuer, keyUsage, getExtendedKeyUsage() , crlUrl, 
								policiesOid, policiesUrl, ou, o, c,
								from, to, sign, email);
					} else if (type == 1) { // PF
						gen = new ICPBravoCertificateGenerator(
								outMgr.getProvider(), alias, serialNumber, subject, certIssuer, keyUsage, getExtendedKeyUsage(), crlUrl, 
								policiesOid, policiesUrl, ou, o, c,
								from, to, sign, email, 
								nascimento, cpf, nis, rg, oe, te, zona, sessao, municipioUf, inss);
					} else { // PJ
						gen = new ICPBravoCertificateGenerator(
								outMgr.getProvider(), alias, serialNumber, subject, certIssuer, keyUsage, getExtendedKeyUsage(), crlUrl, 
								policiesOid, policiesUrl, ou, o, c,
								from, to, sign, email, 
								responsavel, nascimento, cpf, nis, rg, oe, cnpj, inss);
					}
					generated = gen.generate(outMgr, true);
					outMgr.saveTofile();
					
					byte [] encoded = generated.getEncoded();
					if (_callBackMode)
						callJavaScriptB64("initializeCertificate();", "addCertificatePart", "finalizeCertificate(\"ok\");", encoded);
					return Base64Content.toBase64(encoded);
				} catch (Exception e) {
					showError(e, "Certificate not created: "+e.getMessage());
				}
				return null;
			}
		});
	}

	private Date dateParser(String date, String format) {
		System.out.println(format+" - "+date);
		try {
			return ICPBravoUtil.string2Date(date, format);
		} catch (Exception e) {
			return new Date();
		}
	}

	public void setType(int type) {
		System.out.println("type: "+type);
		this.type = type;
	}

	public void setTstUrl(String tstUrl) {
		System.out.println("urlTST: "+tstUrl);
		this.tstUrl = tstUrl;
		_currentConfiguration.getConf().setURLTst(tstUrl);
	}
	
	public void setAlias(String alias) {
		System.out.println("alias: "+alias);
		this.alias = alias;
	}

	public void setSerialNumber(long serialNumber) {
		System.out.println("serialNumber: "+serialNumber);
		this.serialNumber = BigInteger.valueOf(serialNumber);
	}

	public void setSubject(String subject) {
		System.out.println("subject: "+subject);
		this.subject = subject;
	}

	public void setOu(String ou) {
		System.out.println("ou: "+ou);
		this.ou = ou;
	}

	public void setAliasIssuer(int aliasIssuer) {
		System.out.println("aliasIssuer: "+aliasIssuer);
		this.aliasIssuer = aliasIssuer;
	}

	public void setCrlUrl(String crlUrl) {
		System.out.println("crlUrl: "+crlUrl);
		this.crlUrl = crlUrl;
	}

	public void setPoliciesOid(String policiesOid) {
		System.out.println("policiesOid: "+policiesOid);
		this.policiesOid = policiesOid;
	}

	public void setPoliciesUrl(String policiesUrl) {
		System.out.println("policiesUrl: "+policiesUrl);
		this.policiesUrl = policiesUrl;
	}

	public void setFrom(String from, String format) {
		System.out.println("from: "+from);
		this.from = dateParser(from, format);
	}

	public void setTo(String to, String format) {
		System.out.println("to: "+to);
		this.to = dateParser(to, format);
	}

	public void setSign0(String sign, String hash) {
		System.out.println("sign: "+sign);
		try {
			this.sign = SignatureAlgorithm.getInstance(_manager.getProvider(), sign);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NotSuportedAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void setEmail(String email) {
		System.out.println("email: "+email);
		this.email = email;
	}

	public void setResponsavel(String responsavel) {
		System.out.println("responsavel: "+responsavel);
		this.responsavel = responsavel;
	}

	public void setNascimento(String nascimento, String format) {
		System.out.println("nascimento: "+nascimento);
		this.nascimento = dateParser(nascimento, format);
	}

	public void setCpf(String cpf) {
		System.out.println("cpf: "+cpf);
		this.cpf = cpf;
	}

	public void setNis(String nis) {
		System.out.println("nis: "+nis);
		this.nis = nis;
	}

	public void setRg(String rg) {
		System.out.println("rg: "+rg);
		this.rg = rg;
	}

	public void setOe(String oe) {
		System.out.println("oe: "+oe);
		this.oe = oe;
	}

	public void setTe(String te) {
		System.out.println("te: "+te);
		this.te = te;
	}

	public void setZona(String zona) {
		System.out.println("zona: "+zona);
		this.zona = zona;
	}

	public void setSessao(String sessao) {
		System.out.println("sessao: "+sessao);
		this.sessao = sessao;
	}

	public void setMunicipioUf(String municipioUf) {
		System.out.println("municipioUf: "+municipioUf);
		this.municipioUf = municipioUf;
	}

	public void setInss(String inss) {
		System.out.println("inss: "+inss);
		this.inss = inss;
	}

	public void setCnpj(String cnpj) {
		System.out.println("cnpj: "+cnpj);
		this.cnpj = cnpj;
	}

	public void setDigitalSignature(boolean value) { if (value) keyUsage |= KeyUsage.digitalSignature;}
	public void setNonRepudiation(boolean value) { if (value) keyUsage |= KeyUsage.nonRepudiation;}
	public void setKeyEncipherment(boolean value) { if (value) keyUsage |= KeyUsage.keyEncipherment;}
	public void setDataEncipherment(boolean value) { if (value) keyUsage |= KeyUsage.dataEncipherment;}
	public void setKeyAgreement(boolean value) { if (value) keyUsage |= KeyUsage.keyAgreement;}
	public void setKeyCertiSign(boolean value) { if (value) keyUsage |= KeyUsage.keyCertSign;}
	public void setCrlSign(boolean value) { if (value) keyUsage |= KeyUsage.cRLSign;}
	public void setEncipherOnly(boolean value) { if (value) keyUsage |= KeyUsage.encipherOnly;}
	public void setDecipherOnly(boolean value) { if (value) keyUsage |= KeyUsage.decipherOnly;}

	public void setServerAuth(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_serverAuth);}
	public void setClientAuth(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_clientAuth);}
	public void setCodeSigning(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_codeSigning);}
	public void setEmailProtection(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_emailProtection);}
	public void setIpsecEndSystem(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_ipsecEndSystem);}
	public void setIpsecTunnel(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_ipsecTunnel);}
	public void setIpsecUser(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_ipsecUser);}
	public void setTimeStamping(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_timeStamping);}
	public void setOCSPSigning(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_OCSPSigning);}
	public void setDvcs(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_dvcs);}
	public void setSbgpCertAAServerAuth(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_sbgpCertAAServerAuth);}
	public void setScvp_responder(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_scvp_responder);}
	public void setEapOverPPP(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_eapOverPPP);}
	public void setEapOverLAN(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_eapOverLAN);}
	public void setScvpServer(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_scvpServer);}
	public void setScvpClient(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_scvpClient);}
	public void setIpsecIKE(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_ipsecIKE);}
	public void setCapwapAC(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_capwapAC);}
	public void setCapwapWTP(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_capwapWTP);}
	public void setSmartcardlogon(boolean value) { if (value) extendedKeyUsage.add(KeyPurposeId.id_kp_smartcardlogon);}

	private CMSEnvelopedData _envelopedData;

	public void initializeEnvelopedData(final String algorithm, final String input, final String envelopedData) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				System.out.println("Iniciando envelopedData");
				try {
					if (envelopedData != null) {
						_envelopedData = new CMSEnvelopedData(new Base64Content(envelopedData));
					} else {
						_envelopedData = new CMSEnvelopedData(SymmetricAlgorithm.getInstance(algorithm));
					}
					_envelopedData.setData(input.getBytes());
				} catch (Exception e) {
					showError(e, "Error on enveloped data: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	public void addTarget(final int alias) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				System.out.println("Adicionando destinat�rio "+alias);
				try {
					ICPBravoCertificate target = _manager.getCertificate(javascript2Alias(alias));
					_envelopedData.addKeyTransRecipient(target);
				} catch (Exception e) {
					showError(e, "Error on enveloped data: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	public void decrypEnvelopedData(final String envelopedData, final int alias) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				System.out.println("Decriptografando com "+alias);
				try {
					ICPBravoCertificate target = _manager.getCertificate(javascript2Alias(alias));
					_envelopedData = new CMSEnvelopedData(new Base64Content(envelopedData));
					String decryptData = new BytesContent(_envelopedData.getContent(target)).toString();
					callJavaScript("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", decryptData);
				} catch (Exception e) {
					showError(e, "Erro ao decriptar dados: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	public void generateEnvelopedData() {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				System.out.println("Gerando o envelope");
				try {
					callJavaScriptB64("initializeCMS();", "addCMSPart", "finalizeCMS(\"ok\");", _envelopedData.getASN1Encoded());
				} catch (Exception e) {
					showError(e, "Error on enveloped data: "+e.getMessage());
				}
				return null;
			}
		});
	}
	
	public void configureApplet() {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				_currentConfiguration.configura(new ConfiguracaoAlteradaListener() {
					public void onAlterarConfiguracao() {
						try {
							callJavaScript("appletConfigured();");
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}});
				return null;
			}
		});
	}
	
	public void onInit() {
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));
		this.getContentPane().add(new JLabel("ICP-Bravo"));
		this.getContentPane().setPreferredSize(new Dimension(80,15));
		this._currentConfiguration = ConfiguracaoApplet.carregaConfiguracaoApplet("ICPBravo", this);
		String filter = getParameter("filter");
		if (filter != null) {
			setFilter(filter);
		}
	}

	public void onStart() {
		_isRunnig = true;
		try {
			callJavaScript("appletLoaded();");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	}

	private Map<Integer, String> aliasMap = new HashMap<Integer, String>();
	private int nextMap = 1;
	
	private String javascript2Alias(int alias) {
		String toReturn = aliasMap.get(alias);
		System.out.println("get - "+alias+" "+toReturn);
		return toReturn;
	}

	String filter;
	public void setFilter(String filter) {
		this.filter = filter;
	}

	private int alias2Javascript(String alias) {
		int next = nextMap++;
		aliasMap.put(next, alias);
		System.out.println("set - "+next+" "+alias);
		return next;
	}

	/**
	 * Retorna caracter�sticas de um certificado.
	 * O resultado ser� devolvido em fun��o de callback indicado no m�todo "setOnSetCertificateProperties(...)". 
	 * 
	 * @param alias
	 * @param prop
	 */
	public String getCertificateProperties(final int alias, final String prop) {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				String toRet = null;
				try {
					ICPBravoCertificate cert = _manager.getCertificate(javascript2Alias(alias));
					try {
						String value = null;
						if (prop.equals("Id"))
							value = cert.getSerialNumber().toString();
						else if (prop.equals("Issuer"))
							value = cert.getIssuerNameDN();
						else if (prop.equals("DocumentNumber"))
							value = cert.getDocumentNumber();
						else if (prop.equals("Name"))
							value = cert.getName();
						else if (prop.equals("CPF"))
							value = cert.getCPF();
						else if (prop.equals("CNPJ"))
							value = cert.getCNPJ();
						else if (prop.equals("RIC"))
							value = cert.getRIC();
						else if (prop.equals("NotAfter"))
							value = cert.getNotAfter().toString();
						else if (prop.equals("NotBefore"))
							value = cert.getNotBefore().toString();
						else if (prop.equals("IsICPBrasil"))
							value = cert.isICPBrasil()?"true":"false";
						else if (prop.equals("Encoded"))
							value = Base64Content.toBase64(cert.getEncoded());
						else if (prop.equals("PEM"))
							value = cert.getPEMAndChain();
						else if (prop.equals("P7B"))
							value = cert.getP7BPEM();
						System.out.println("Prop: "+prop+", Value:"+value);
						if (_callBackMode)
							callJavaScript("initializeValue();", "addValuePart", "finalizeValue(\"ok\");", value);
						toRet = value;
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
				}
				return toRet;
			}
		});
	}

	/**
	 * Retorna o identificado �nico de um certificado.
	 * O resultado ser� devolvido em fun��o de callback indicado no m�todo "setOnSetCertificateProperties(...)". 
	 * 
	 * @param alias
	 */
	public void getCertificateId(final int alias) {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					ICPBravoCertificate cert = _manager.getCertificate(javascript2Alias(alias));
					try {
						String certId = cert.getUniqueID();
						callJavaScript("_setCertificateID('"+certId+"');");
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
				}
				return null;
			}
		});
	}
	
	
	private String message = "Autorizar";
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	private int getCertificateSelected(final String manager) throws Exception {
		if (certificates == null)
			loadFromManager(manager);
		final JComboBox certs = new JComboBox(certificates);
		certs.setToolTipText("Selecione o certificado que dever� ser utilizado para a opera��o");
		final JButton refresh = new JButton("Recarregar");
		refresh.setToolTipText("Recarregar os certificados do token ou smartCard");
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					certs.removeAllItems();
					loadFromManager(manager);
					for (ICPBravoCertificate option : certificates) {
						certs.addItem(option.getName());
					}
				} catch (Exception e) {
				}
			}
		});
		
		JComponent[] components = new JComponent[2];
		components[0] = certs;
		components[1] = refresh;
		Object[] options = {message, "Cancelar"};
		JOptionPane jop = new JOptionPane(components, JOptionPane.QUESTION_MESSAGE,  JOptionPane.OK_CANCEL_OPTION, null, options, options[0]);
		
		JDialog dialog = jop.createDialog("Selecione o Certificado");
		dialog.addComponentListener(new ComponentAdapter(){
				@Override
				public void componentShown(ComponentEvent e){
					certs.requestFocusInWindow(); 
				}
			});
		dialog.setVisible(true);
		
		if (jop.getValue().equals(options[0]))
			return certs.getSelectedIndex();
		return -1;
	}
}