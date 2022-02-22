package jp.co.kts.service.fileExport;

import java.util.Comparator;

import jp.co.kts.app.output.entity.ExportSaleSummalyDTO;

public class CorporateSummalyComparator {

	//コストソート
	final public class CostComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalCost() < summ2.getTotalCost() ? -1:1;
		}
	}

	final public class CostComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalCost() > summ2.getTotalCost() ? -1:1;
		}
	}

	//注文数ソート
	final public class OrderComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalOrderNum() < summ2.getTotalOrderNum() ? -1:1;
		}
	}

	final public class OrderComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalOrderNum() > summ2.getTotalOrderNum() ? -1:1;
		}
	}

	//売上ソート
	final public class PieceRateComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalInTaxPieceRate() < summ2.getTotalInTaxPieceRate() ? -1:1;
		}
	}

	final public class PieceRateComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalInTaxPieceRate() > summ2.getTotalInTaxPieceRate() ? -1:1;
		}
	}

	//粗利ソート
	final public class GrossComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalGrossMargin() < summ2.getTotalGrossMargin() ? -1:1;
		}
	}

	final public class GrossComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getTotalGrossMargin() > summ2.getTotalGrossMargin() ? -1:1;
		}
	}

	//商品コードソート
	final public class CodeComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getItemCode().compareTo(summ2.getItemCode());
		}
	}

	final public class CodeComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ2.getItemCode().compareTo(summ1.getItemCode());
		}
	}

	//商品名ソート
	final public class NmComparatorAsc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ1.getItemNm().compareTo(summ2.getItemNm());
		}
	}

	final public class NmComparatorDesc implements Comparator<ExportSaleSummalyDTO>{
		@Override
		public int compare(ExportSaleSummalyDTO summ1, ExportSaleSummalyDTO summ2){
			return summ2.getItemNm().compareTo(summ1.getItemNm());
		}
	}



	public CostComparatorAsc getCostComparatorAsc() {
		return new CostComparatorAsc();
	}
	public CostComparatorDesc getCostComparatorDesc() {
		return new CostComparatorDesc();
	}

	public OrderComparatorAsc getOrderComparatorAsc() {
		return new OrderComparatorAsc();
	}
	public OrderComparatorDesc getOrderComparatorDesc() {
		return new OrderComparatorDesc();
	}

	public PieceRateComparatorAsc getPieceRateComparatorAsc(){
		return new PieceRateComparatorAsc();
	}
	public PieceRateComparatorDesc getPieceRateComparatorDesc(){
		return new PieceRateComparatorDesc();
	}

	public GrossComparatorAsc getGrossComparatorAsc() {
		return new GrossComparatorAsc();
	}

	public GrossComparatorDesc getGrossComparatorDesc() {
		return new GrossComparatorDesc();
	}

	public CodeComparatorAsc getCodeComparatorAsc() {
		return new CodeComparatorAsc();
	}
	public CodeComparatorDesc getCodeComparatorDesc(){
		return new CodeComparatorDesc();
	}

	public NmComparatorAsc getNmComparatorAsc(){
		return new NmComparatorAsc();
	}
	public NmComparatorDesc getNmComparatorDesc(){
		return new NmComparatorDesc();
	}


}

