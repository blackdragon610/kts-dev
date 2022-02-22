package jp.co.kts.service.mst;

import java.util.List;

import jp.co.kts.app.common.entity.MstDeliveryDTO;
import jp.co.kts.app.extendCommon.entity.ExtendMstDeliveryDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.DeliveryDAO;
import jp.co.kts.service.common.Result;

public class DeliveryService {

	public void registryDelivery(MstDeliveryDTO dto) throws Exception {
		DeliveryDAO dao = new DeliveryDAO();
		dto.setSysDeliveryId(new SequenceDAO().getMaxSysDeliveryId() + 1);
		dao.registryDelivery(dto);
	}

	public MstDeliveryDTO getDelivery(long sysDeliveryId) throws Exception {

		DeliveryDAO dao = new DeliveryDAO();
		MstDeliveryDTO dto = new MstDeliveryDTO();
		 dto = dao.getDelivery(sysDeliveryId);
		 return dto;
	}

	public void updateDelivery(MstDeliveryDTO dto) throws Exception {
		DeliveryDAO dao = new DeliveryDAO();
		dao.updateDelivery(dto);
	}

	public void deleteDelivery(long sysDeliveryId) throws Exception {
		DeliveryDAO dao = new DeliveryDAO();
		dao.deleteDelivery(sysDeliveryId);
	}

	public List<ExtendMstDeliveryDTO> getDeliveryList() throws Exception {

		DeliveryDAO dao = new DeliveryDAO();

		return dao.getDeliveryList();
	}

	public List<ExtendMstDeliveryDTO> getDeliveryList(long sysClientId) throws Exception {

		DeliveryDAO dao = new DeliveryDAO();

		return dao.getDeliveryList(sysClientId);
	}

	/**
	 * [概要]入力値の整合性チェックを行う
	 * @param deliveryNm 納入先名
	 * @param sysDeliveryId 法人ID
	 * @param telNo 電話番号
	 * @param validType チェックタイプ
	 * @return result
	 * @throws Exception
	 */
	public Result<MstDeliveryDTO> validate(MstDeliveryDTO deliveryDto, String validType) throws Exception {

		Result<MstDeliveryDTO> result = new Result<MstDeliveryDTO>();
		DeliveryDAO dao = new DeliveryDAO();

		//新規登録の納入先電話番号存在有無チェックを行う
		if (validType.equals("insert")) {
			if(dao.getTelCnt(deliveryDto.getTel(), deliveryDto.getSysClientId()) > 0) {
				result.addErrorMessage("LED00122", "「納入先電話番号」");
			}
		}

		//更新の場合、登録されている得意先番号と違う番号が入力されてたら、入力した得意先番号の重複チェックを行う
		if (validType.equals("update")) {
			MstDeliveryDTO dto = new MstDeliveryDTO();
			dto = dao.getDelivery(deliveryDto.getSysDeliveryId());
			if (!dto.getTel().equals(deliveryDto.getTel())) {
				if(dao.getTelCnt(deliveryDto.getTel(), deliveryDto.getSysClientId()) > 0) {
					result.addErrorMessage("LED00122", "「納入先電話番号」");
				}
			}
		}

		return result;
	}
}
