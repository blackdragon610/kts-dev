package jp.co.kts.ui.sale;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.action.ActionForward;

import jp.co.keyaki.cleave.common.util.DateUtil;
import jp.co.keyaki.cleave.fw.dao.DaoException;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppActionMapping;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseAction;
import jp.co.keyaki.cleave.fw.ui.web.struts.AppBaseForm;
import jp.co.keyaki.cleave.fw.ui.web.struts.StrutsBaseConst;
import jp.co.kts.app.extendCommon.entity.ExtendSalesItemDTO;
import jp.co.kts.app.extendCommon.entity.ExtendSalesSlipDTO;
import jp.co.kts.app.output.entity.ErrorDTO;
import jp.co.kts.app.output.entity.ErrorObject;
import jp.co.kts.app.output.entity.SaleListTotalDTO;
import jp.co.kts.service.common.ServiceConst;
import jp.co.kts.service.fileExport.ExportExcelSalesService;
import jp.co.kts.service.fileExport.ExportPickListService;
import jp.co.kts.service.fileExport.ExportSaleListService;
import jp.co.kts.service.fileExport.ExportSaleSummalyService;
import jp.co.kts.service.mst.ChannelService;
import jp.co.kts.service.mst.CorporationService;
import jp.co.kts.service.mst.UserService;
import jp.co.kts.service.sale.SaleDisplayService;
import jp.co.kts.ui.web.struts.WebConst;
import net.arnx.jsonic.JSON;


public class SaleAction extends AppBaseAction{

	protected ActionForward doExecute(AppActionMapping appMapping,
			AppBaseForm appForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SaleForm form = (SaleForm)appForm;

		if ("/initSaleList".equals(appMapping.getPath())) {
			return initSaleList(appMapping, form, request);
		} else if("/initSaleSummary".equals(appMapping.getPath())) {
			return initSaleSummary(appMapping, form, request);
		} else if("/detailSale".equals(appMapping.getPath())) {
			return detailSale(appMapping, form, request);
		} else if("/updateDetailSale".equals(appMapping.getPath())) {
			return updateDetailSale(appMapping, form, request);
		} else if("/searchSaleList".equals(appMapping.getPath())) {
			return searchSaleList(appMapping, form, request);
		} else if("/saleListPageNo".equals(appMapping.getPath())) {
			return saleListPageNo(appMapping, form, request);
		} else if("/initRegistrySales".equals(appMapping.getPath())) {
			return initRegistrySales(appMapping, form, request);
		} else if("/registrySales".equals(appMapping.getPath())) {
			return registrySales(appMapping, form, request);
		} else if("/initLeaveStock".equals(appMapping.getPath())) {
			return initLeaveStock(appMapping, form, request);
		} else if ("/leaveStock".equals(appMapping.getPath())) {
			return leaveStock(appMapping, form, request);
		} else if ("/exportPickList".equals(appMapping.getPath())) {
			return exportPickList(appMapping, form, request, response);
		} else if ("/pickListPrintOutFile".equals(appMapping.getPath())) {
			return pickListPrintOutFile(response);

		} else if ("/exportTotalPickList".equals(appMapping.getPath())) {
			return exportTotalPickList(appMapping, form, request, response);
		} else if ("/totalPickListPrintOutFile".equals(appMapping.getPath())) {
			return totalPickListPrintOutFile(response);

		} else if ("/initReturnSales".equals(appMapping.getPath())) {
			return initReturnSales(appMapping, form, request, response);
		} else if ("/returnSales".equals(appMapping.getPath())) {
			return returnSales(appMapping, form, request, response);
		} else if ("/deleteSales".equals(appMapping.getPath())) {
			return deleteSales(appMapping, form, request, response);
		} else if ("/lumpUpdateSales".equals(appMapping.getPath())) {
			return lumpUpdateSales(appMapping, form, request, response);
		} else if ("/initCopySlip".equals(appMapping.getPath())) {
			return initCopySlip(appMapping, form, request, response);
		} else if ("/saleListDownLoad".equals(appMapping.getPath())) {
			return saleListDownLoad(appMapping, form, request, response);
		} else if ("/saleSummaryDownLoad".equals(appMapping.getPath())) {
			return saleSummaryDownLoad(appMapping, form, request, response);
		}

		return appMapping.findForward(StrutsBaseConst.GLOBAL_FORWARD_ERROR);
	}

	protected ActionForward initSaleList(AppActionMapping appMapping, SaleForm form,
	            HttpServletRequest request) throws Exception {

		form.setSalesSlipList(new ArrayList<ExtendSalesSlipDTO>());

		CorporationService corporationService = new CorporationService();
		form.setCorporationList(corporationService.getCorporationList());

		ChannelService channelService = new ChannelService();
		form.setChannelList(channelService.getChannelList());

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		form.setSaleSearchDTO(saleDisplayService.initSaleSearchDTO());
		form.setPickoutputFlg("0");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	protected ActionForward initSaleSummary(AppActionMapping appMapping, SaleForm form,
	           HttpServletRequest request) throws Exception {

		 form.setSalesSlipList(new ArrayList<ExtendSalesSlipDTO>());

		 CorporationService corporationService = new CorporationService();
		 form.setCorporationList(corporationService.getCorporationList());

		 ChannelService channelService = new ChannelService();
		 form.setChannelList(channelService.getChannelList());

		 SaleDisplayService saleDisplayService = new SaleDisplayService();
		 form.setSaleSearchDTO(saleDisplayService.initSaleSearchDTOSummaly());

		 return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward initRegistrySales(AppActionMapping appMapping,
				SaleForm form, HttpServletRequest request) throws Exception {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		form.setSalesSlipDTO(saleDisplayService.initSalesSlipDTO());

		form.setAddSalesItemList(saleDisplayService.initAddSalesItemList());
		form.setSalesItemList(new ArrayList<ExtendSalesItemDTO>());

		form.setChannelList(new ChannelService().getChannelList());
		form.setCorporationList(new CorporationService().getCorporationList());

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward registrySales(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request) throws Exception {

		//????????????????????????
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		ErrorDTO errorMessageDTO = new ErrorDTO();

		//BONCRE-1992 ??????????????????????????????
		errorMessageDTO = saleDisplayService.salesSlipValidate(form.getSalesSlipDTO());

		// ?????????????????????????????????
		if (!errorMessageDTO.getErrorMessage().equals("")) {
			form.setErrorDTO(errorMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		//????????????
//		SaleDisplayService.setFlags(form.getSalesSlipDTO());
//		SaleDisplayService.setEtcPrice(form.getSalesSlipDTO());
		saleDisplayService.newRegistrySalesSlip(form.getSalesSlipDTO());

		//????????????
		saleDisplayService.registrySaleItemList(form.getAddSalesItemList(), form.getSalesSlipDTO().getSysSalesSlipId(), form.getSalesSlipDTO());

		form.setSysSalesSlipId(form.getSalesSlipDTO().getSysSalesSlipId());

		form.setAlertType(WebConst.ALERT_TYPE_REGIST);

		form.setSaleSearchDTO(saleDisplayService.initSaleSearchDTO());

		//token?????????
		saveToken(request);

		return detailSale(appMapping, form, request);
	}

	protected ActionForward detailSale(AppActionMapping appMapping, SaleForm form,
			HttpServletRequest request) throws Exception {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		//????????????
		form.setSalesSlipDTO(saleDisplayService.getSalesSlip(form.getSysSalesSlipId()));
		if (form.getSalesSlipDTO() == null) {

			form.setSalesSlipDTO(saleDisplayService.initSalesSlipDTO());
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}
		if (form.getSalesSlipDTO() != null) {
			UserService userService = new UserService();
			form.setMstUserDTO(userService.getUserName(form.getSalesSlipDTO().getUpdateUserId()));
		}
//		SaleDisplayService.setEtcPrice(form.getSalesSlipDTO());
//		SaleDisplayService.setFlags(form.getSalesSlipDTO());

		//????????????
		form.setSalesItemList(saleDisplayService.getSalesItemList(form.getSysSalesSlipId(), form.getSalesSlipDTO().getSysCorporationId()));
		form.setAddSalesItemList(saleDisplayService.initAddSalesItemList());

		//O??????????????????
		form.setChannelList(new ChannelService().getChannelList());
		//O??????
		form.setCorporationList(new CorporationService().getCorporationList());

		//??????????????????????????????????????????????????????????????????????????????????????????
		form.getSalesSlipDTO().setGrossMargin(
				saleDisplayService.getGrossMargin(form.getSalesSlipDTO(), form.getSalesItemList(), form.getSaleSearchDTO()));

		//???????????????????????????????????????
		form.getSalesSlipDTO().setOriginOrderNo(form.getSalesSlipDTO().getOrderNo());
		form.setReturnButtonFlg("1");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward updateDetailSale(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request) throws Exception {

		//?????????????????????
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		ErrorDTO errorMessageDTO = new ErrorDTO();

		//BONCER-1992 ??????????????????????????????
		errorMessageDTO = saleDisplayService.salesSlipValidate(form.getSalesSlipDTO());

		// ?????????????????????????????????
		if (!errorMessageDTO.getErrorMessage().equals("")) {
			form.setErrorDTO(errorMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//????????????
//		SaleDisplayService.setFlags(form.getSalesSlipDTO());
//		SaleDisplayService.setEtcPrice(form.getSalesSlipDTO());
		saleDisplayService.updateSalesSlip(form.getSalesSlipDTO());

		//????????????
		saleDisplayService.updateSalesItemList(form.getSalesItemList());
		saleDisplayService.registrySaleItemList(form.getAddSalesItemList(), form.getSalesSlipDTO().getSysSalesSlipId(), form.getSalesSlipDTO());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		//token?????????
		saveToken(request);

		return detailSale(appMapping, form, request);
//		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ????????????
	 *
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws DaoException
	 */
	private ActionForward searchSaleList(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request) throws DaoException {

		HttpSession session = request.getSession(false);	
		
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		
		saleDisplayService.setFlags(form.getSaleSearchDTO());
		form.setSysSalesSlipIdList(saleDisplayService.getSysSalesSlipIdList(session , form.getSaleSearchDTO()));
		saleDisplayService.setFlags(form.getSaleSearchDTO());

		// ????????????????????????????????????
		if (form.getSysSalesSlipIdList() == null || form.getSysSalesSlipIdList().size() <= 0) {

			ErrorDTO errorMessageDTO = new ErrorDTO();
			errorMessageDTO.setSuccess(false);
			errorMessageDTO.setErrorMessage("?????????????????????????????????????????????");
			form.setErrorDTO(errorMessageDTO);

			form.setSysSalesSlipIdListSize(0);
			form.setSalesSlipList(new ArrayList<ExtendSalesSlipDTO>());

			form.setSaleListTotalDTO(new SaleListTotalDTO());

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setSaleListTotalDTO(saleDisplayService.getSaleListTotalDTO(
				form.getSysSalesSlipIdList()
				,form.getSaleSearchDTO().getGrossProfitCalc()
				,form.getSaleSearchDTO().getSumDispFlg()));

		form.setSysSalesSlipIdListSize(form.getSysSalesSlipIdList().size());

		form.setPageIdx(0);
		form.setSalesSlipList(saleDisplayService.getSalesSlipList(form.getSysSalesSlipIdList(), form.getPageIdx(), form.getSaleSearchDTO()));
		form.setSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSaleSearchDTO().getListPageMax()));
//		form.setSalesSlipList(SaleDisplayService.getSalesSlipList(form.getSaleSearchDTO()));

		//?????????????????????????????????????????????
//		form.setSessionSaleSearchDTO(form.getSaleSearchDTO());
		
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward saleListPageNo(AppActionMapping appMapping, SaleForm form, HttpServletRequest request) throws DaoException {

		if (form.getSysSalesSlipIdList() == null || form.getSysSalesSlipIdList().size() <= 0) {

			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		form.setSalesSlipList(new SaleDisplayService().getSalesSlipList(form.getSysSalesSlipIdList(), form.getPageIdx(), form.getSaleSearchDTO()));
		form.setSaleListPageMax(WebConst.LIST_PAGE_MAX_MAP.get(form.getSaleSearchDTO().getListPageMax()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected ActionForward initLeaveStock(AppActionMapping appMapping, SaleForm form,
			HttpServletRequest request) throws Exception {

		if (form.getSalesSlipList() == null || form.getSalesSlipList().size() <= 0) {
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		form.setLeaveStockList(saleDisplayService.getLeaveStockList(form.getSalesSlipList()));
		form.setLeaveStockList(saleDisplayService.sortLeaveStockList(form.getLeaveStockList()));
		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private ActionForward leaveStock(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request) throws Exception {
		//????????????????????????
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		ErrorObject<List<ExtendSalesItemDTO>> checkedResult = new ErrorObject<>();
		//Brembo???????????????????????????????????????
		List<ExtendSalesItemDTO> bremboItemList = new ArrayList<>();

		//??????????????????????????????????????????
		checkedResult = saleDisplayService.checkLeaveStock(form.getLeaveStockList());
		//??????????????????????????????
		form.getErrorDTO().setErrorMessageList(checkedResult.getErrorMessageList());
		//????????????
		saleDisplayService.leaveStock(checkedResult.getResultObject());
		//????????????????????????Brembo??????????????????????????????
		bremboItemList = saleDisplayService.checkTargetExistsForBremboItem(checkedResult.getResultObject());

		//Brembo??????????????????????????????Brembo????????????????????????????????????
		if (!(bremboItemList == null || bremboItemList.isEmpty())) {
			saleDisplayService.createBremboCorpSaleSlip(bremboItemList);
		}

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);
		initLeaveStock(appMapping, form, request);

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????????????????????????????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	protected ActionForward exportPickList(AppActionMapping appMapping, SaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getSalesSlipList() == null || form.getSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		try{
			ExportPickListService exportPickListService = new ExportPickListService();

			exportPickListService.pickList(request, response,form.getSalesSlipList());

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.setCharacterEncoding("UTF-8");
			PrintWriter printWriter = response.getWriter();
			int[] slipCountArray = new int[2];
			printWriter.print(JSON.encode(slipCountArray));
			return null;
		}
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		saleDisplayService.updatePickFlg(form.getSalesSlipList());
		return null;
	}

	protected ActionForward exportTotalPickList(AppActionMapping appMapping, SaleForm form,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (form.getSalesSlipList() == null || form.getSalesSlipList().size() <= 0) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		try{
			ExportPickListService exportPickListService = new ExportPickListService();


			exportPickListService.totalPickList(response,form.getSalesSlipList());

		}catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}

		return null;
	}



	protected ActionForward pickListPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "pickList.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "????????????????????????????????????" + fileNmTimeFormat.format(date) + ".pdf";

		ExportPickListService exportPickListService = new ExportPickListService();

		try {
			exportPickListService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}

	protected ActionForward totalPickListPrintOutFile(
			HttpServletResponse response) throws ServletException,
			IOException {

		String filePath = "totalPickList.pdf";
		Date date = new Date();
		SimpleDateFormat fileNmTimeFormat = new SimpleDateFormat(
				"yyyyMMdd_HHmmss");
		String fname = "????????????????????????????????????" + fileNmTimeFormat.format(date) + ".pdf";

		ExportPickListService exportPickListService = new ExportPickListService();

		try {
			exportPickListService.outPut(response,filePath,fname);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return null;
		}
		return null;
	}



	private ActionForward initReturnSales(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		form.setSalesItemList(saleDisplayService.initReturnSalesItem(form.getSalesItemList()));
		form.setSalesSlipDTO(saleDisplayService.initReturnSalesSlip(form.getSalesSlipDTO(), form.getSalesItemList()));

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward returnSales(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//????????????????????????
		SaleDisplayService saleDisplayService = new SaleDisplayService();
		ErrorDTO errorMessageDTO = new ErrorDTO();

		//BONCER-1992 ??????????????????????????????
		form.getSalesSlipDTO().setOriginOrderNo("");
		errorMessageDTO = saleDisplayService.salesSlipValidate(form.getSalesSlipDTO());

		// ?????????????????????????????????
		if (!errorMessageDTO.getErrorMessage().equals("")) {
			form.setErrorDTO(errorMessageDTO);
			return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_FAILURE);
		}

		//20140322?????????????????????????????????????????????????????????????????????????????????
//		SaleDisplayService saleDisplayService = new SaleDisplayService();
//		SaleDisplayService.registryReturnSalesSlip(form.getSalesSlipDTO());
//		SaleDisplayService.registryReturnSalesItem(form.getSalesItemList(), form.getSalesSlipDTO().getSysSalesSlipId());

		saleDisplayService.registryReturnSalesSlip(form.getSalesSlipDTO());
		saleDisplayService.registryReturnSalesItem(form.getSalesItemList(), form.getSalesSlipDTO(), form.getSalesSlipDTO());

		form.setSysSalesSlipId(form.getSalesSlipDTO().getSysSalesSlipId());

		form.setAlertType(WebConst.ALERT_TYPE_REGIST);
		form.setReturnButtonFlg("1");

		return detailSale(appMapping, form, request);
	}

	private ActionForward deleteSales(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		saleDisplayService.deleteSalesSlip(form.getSalesSlipDTO().getSysSalesSlipId());

		saleDisplayService.deleteSalesItem(form.getSalesItemList(), form.getSalesSlipDTO().getSysSalesSlipId());

		form.setReturnButtonFlg("3");
		form.setAlertType(WebConst.ALERT_TYPE_DELETE);

		return initRegistrySales(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward lumpUpdateSales(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		saleDisplayService.updateSalesList(form.getSalesSlipList());

		form.setAlertType(WebConst.ALERT_TYPE_UPDATE);

		return saleListPageNo(appMapping, form, request);

		//return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	/**
	 * ??????
	 * @param appMapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws DaoException
	 */
	private ActionForward initCopySlip(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws DaoException {

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		//form.setAddSalesItemList(SaleDisplayService.initAddSalesItemList());
		form.setAddSalesItemList(saleDisplayService.initCopySlipItem(form.getSalesItemList()));
		form.setSalesItemList(new ArrayList<ExtendSalesItemDTO>());
		form.setSalesSlipDTO(saleDisplayService.initCopySlip(form.getSalesSlipDTO(), form.getAddSalesItemList()));
		//BONCER-1992 ?????????????????????????????????????????????????????????????????????
		form.getSalesSlipDTO().setOriginOrderNo("");

		return appMapping.findForward(StrutsBaseConst.FORWARD_NAME_SUCCESS);
	}

	private ActionForward saleListDownLoad(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.SALE_LIST_TEMPLATE_PATH);

		// ????????????????????????????????????
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ??????????????????????????????????????????
		HSSFWorkbook workBook = new HSSFWorkbook(filein);

		//??????????????????????????????????????????
		ExportExcelSalesService exportSaleList = new ExportSaleListService();

		SaleDisplayService saleDisplayService = new SaleDisplayService();
		saleDisplayService.setFlags(form.getSaleSearchDTO());
		
		HttpSession session = request.getSession(false);
		
		workBook = exportSaleList.getFileExportSales(session, form.getSaleSearchDTO(), workBook);
		saleDisplayService.setFlags(form.getSaleSearchDTO());

		// ?????????????????????.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "?????????_" + date + ".xls";
		// ???????????????????????????????????????????????????????????????????????????????????????.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// ??????????????????????????????
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		fileOutHssf.close();

		return null;
	}

	private ActionForward saleSummaryDownLoad(AppActionMapping appMapping,
			SaleForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String filePath = this.getServlet().getServletContext().getRealPath(ServiceConst.SALE_SUMMALY_TEMPLATE_PATH);

		// ????????????????????????????????????
		POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(filePath));
		// ??????????????????????????????????????????
		HSSFWorkbook workBook = new HSSFWorkbook(filein);

		//??????????????????????????????????????????
		ExportExcelSalesService exportExcelSalesService = new ExportSaleSummalyService();

		workBook = exportExcelSalesService.getFileExportSales(form.getSaleSearchDTO(), workBook);

		// ?????????????????????.
		String date = DateUtil.dateToString("yyyyMMdd");
		String fname = "???????????????_" + date + ".xls";
		// ???????????????????????????????????????????????????????????????????????????????????????.
		byte[] sJis = fname.getBytes("Shift_JIS");
		fname = new String(sJis, "ISO8859_1");

		// ??????????????????????????????
		response.setContentType("application/octet-stream; charset=Windows-31J");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);
		ServletOutputStream fileOutHssf = response.getOutputStream();
		workBook.write(fileOutHssf);
		fileOutHssf.close();

		return null;
	}

}
