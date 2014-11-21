package br.jus.stj.sigeven.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.jus.stj.sigeven.vo.ParticipanteVO;

public class LeitorExcel {
	
	
	private enum Coluna {
		TRATAMENTO(0), TITULO(1), NOME(2), CARGO(3), ORGAO(4), ENDERECO(5), ESTADO(6), CIDADE(7), CEP(8), ETIQUETA(9);
		
		private int valor;
		
		private Coluna(int valor) {
			this.valor = valor;
		}
		
		public int getValor() {
			return this.valor;
		}
	}
	
	public List<ParticipanteVO> lerStream(InputStream is, String tipoArquivo) throws IOException {
		List<ParticipanteVO> lista = new ArrayList<ParticipanteVO>();
		
		Workbook workbook = null;
		if (ConstantesSigeven.TIPO_ARQUIVO_XLS.equals(tipoArquivo)){
			workbook = new HSSFWorkbook(is);
		}else if(ConstantesSigeven.TIPO_ARQUIVO_XLSX.equals(tipoArquivo)){
			workbook = new XSSFWorkbook(is);
		}else{
			
		}
		int numSheets = workbook.getNumberOfSheets();
		System.out.println("numSheets: " + numSheets);
		
		Sheet sheet = workbook.getSheetAt(0);
		
		sheet.removeRow(sheet.getRow(0));
		
		Iterator<Row> rowIterator = sheet.iterator();
		while(rowIterator.hasNext()) {
				ParticipanteVO participante = new ParticipanteVO();
				Row row = rowIterator.next();
				for (Coluna coluna : Coluna.values()) {
					String valor = null;
					Cell cell = row.getCell(coluna.getValor());
					if(cell==null){
						cell = row.createCell(0);
						cell.setCellValue("");
					}
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						valor = cell.getStringCellValue().trim();
						break;
					case Cell.CELL_TYPE_NUMERIC:
						valor = Double.toString(cell.getNumericCellValue());
						break;
					default:
						break;
					}
					
					
					switch (coluna) {
					case TRATAMENTO:
						participante.setTratamento(valor);
						break;
					case TITULO:
						participante.setTitulo(valor);
						break;
					case NOME:
						participante.setNome(valor);
						break;
					case CARGO:
						participante.setCargo(valor);
						break;
					case ORGAO:
						participante.setOrgao(valor);
						break;
					case ENDERECO:
						participante.setEndereco(valor);
						break;
					case ESTADO:
						participante.setEstado(valor);
						break;
					case CIDADE:
						participante.setCidade(valor);
						break;
					case CEP:
						participante.setCep(valor);
						break;
					default:
						break;
					}
				}
				lista.add(participante);
		}	
		return lista;
	}
	
	public void outLista(List<ParticipanteVO> lista) {
		for (ParticipanteVO part : lista) {
			StringBuilder sb = new StringBuilder();
			sb.append("Tratamento:");
			sb.append(part.getTratamento());
			sb.append(" - ");
			sb.append("Titulo:");
			sb.append(part.getTitulo());
			sb.append(" - ");
			sb.append("Nome:");
			sb.append(part.getNome());
			sb.append(" - ");
			sb.append("Endere�o:");
			sb.append(part.getEndereco());
			sb.append(" - ");
			sb.append("Estado:");
			sb.append(part.getEstado());
			sb.append(" - ");
			sb.append("CEP:");
			sb.append(part.getCep());
			sb.append(" - ");
			sb.append("Cartão:");
			sb.append(part.getCidade());
			System.out.println(sb.toString());
		}
	}

}
