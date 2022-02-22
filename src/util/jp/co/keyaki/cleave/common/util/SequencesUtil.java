package jp.co.keyaki.cleave.common.util;




/**
 * 採番ユーティリティクラス
 *
 */
public class SequencesUtil {

    /**
     * プレフィックス情報ごとに採番コードを
     * 返却する
     *
     * @param prefix     プレフィックス
     * @param padLength        採番桁数
     * @return           各コード
     * @throws Exception 例外発生時
     */
//    public static int getNextNo(
//            String prefix) throws Exception {

        //StringBuilder sb = new StringBuilder();
//        SysSequencesDAO dao = new SysSequencesDAO();
//        SysSequencesDTO dto = dao.selectLock(prefix);
//
//        int nextVal = dto.getCurrentValue() + 1;
//        dto.setCurrentValue(nextVal);
//
//        // 採番テーブルの更新
//        dao.update(dto);

    	// sb.append(StringUtils.leftPad(String.valueOf(nextVal), padLength, "0"));

//        return nextVal;
//    }

//    /**
//     * プレフィックス情報ごとにログ管理番号を
//     * 返却する
//     *
//     * @param prefix     プレフィックス
//     * @param len        採番桁数
//     * @param connection コネクション
//     * @return           各コード
//     * @throws Exception 例外発生時
//     */
//    public static int getNextLogManagementNo() throws Exception {
//
//        SysSequencesDAO dao = new SysSequencesDAO();
//        SysSequencesDTO dto = dao.selectLock(EntityConst.PREFIX_SYS_ACCESS_LOG);
//
//        // 採番コード生成
//        int nextVal = dto.getCurrentValue() + 1;
//        dto.setCurrentValue(nextVal);
//
//
//        // 採番テーブルの更新
//        dao.update(dto);
//
//        return nextVal;
//    }
}
