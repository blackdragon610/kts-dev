package jp.co.kts.service.mst;

import java.util.List;

import jp.co.kts.app.common.entity.MstMakerDTO;
import jp.co.kts.dao.common.SequenceDAO;
import jp.co.kts.dao.mst.MakerDAO;
import jp.co.kts.service.common.Result;
import jp.co.kts.service.common.ServiceValidator;

public class MakerService {

	/**
	 * メーカー情報全件取得
	 * DELETE_FALG：1以外
	 * @return List<MstMakerDTO>
	 * @throws Exception
	 */
	public List<MstMakerDTO> getMakerInfo() throws Exception {
		MakerDAO dao = new MakerDAO();
		return dao.getMakerInfoList();
	}

	/**
	 * メーカー情報一件取得
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public MstMakerDTO getMakerDetail(MstMakerDTO dto) throws Exception {
		MakerDAO dao = new MakerDAO();
		return dao.getMakerDetailInfo(dto);
	}

	/**
	 * メーカー名とメーカー名ｶﾅの入力チェック
	 * @param makerNm
	 * @param makerNmKana
	 * @return result
	 */
	public Result<MstMakerDTO> validate(String makerNm, String makerNmKana) {

		Result<MstMakerDTO> result = new Result<MstMakerDTO>();

		//入力チェック(必須・文字数)
		ServiceValidator.inputChecker(result, makerNm, "メーカー名", 30, true);
		ServiceValidator.inputChecker(result, makerNmKana, "メーカー名ｶﾅ", 60, true);

		return result;
	}

	/**
	 * メーカー情報登録
	 * @param makerNm
	 * @param makerNmKana
	 * @return result
	 */
	public int registryMaker(MstMakerDTO dto)throws Exception {
		int result = 0;
		MakerDAO dao = new MakerDAO();
		dto.setSysMakerId(new SequenceDAO().getMaxSysMakerId() + 1);

		result = dao.registoryMaker(dto);
		return result;
	}

	/**
	 * メーカー情報更新
	 * @param makerNm
	 * @param makerNmKana
	 * @return result
	 */
	public int updateMaker(MstMakerDTO dto)throws Exception {
		int result = 0;
		MakerDAO dao = new MakerDAO();
		result = dao.updateMaker(dto);
		return result;
	}

	/**
	 * メーカー情報削除
	 * @param makerNm
	 * @param makerNmKana
	 * @return result
	 */
	public int deleteMaker(MstMakerDTO dto)throws Exception {
		int result = 0;
		MakerDAO dao = new MakerDAO();
		result = dao.deleteMaker(dto);
		return result;
	}


}
