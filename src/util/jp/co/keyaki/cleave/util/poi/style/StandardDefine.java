/*
 *　開発システム　プロジェクト管理システム
 *　著作権　　　　Copyright 2007 Cleave
 */
package jp.co.keyaki.cleave.util.poi.style;

import jp.co.keyaki.cleave.util.poi.CellStyleDecorator;
import jp.co.keyaki.cleave.util.poi.CompositeCellStyleDecorator;

public class StandardDefine {

	public static final CellStyleDecorator STRING_CELL;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(FontSetting.DEFAULT);
		composite.add(ValueHorizontalAlignment.NORMAL);
		composite.add(ValueVerticalAlignment.MIDDLE);
		composite.setUnmodifiable();
		STRING_CELL = composite;
	}

	public static final CellStyleDecorator STRING_CENTER_MIDDLE_CELL;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(FontSetting.DEFAULT);
		composite.add(ValueHorizontalAlignment.CENTER);
		composite.add(ValueVerticalAlignment.MIDDLE);
		composite.setUnmodifiable();
		STRING_CENTER_MIDDLE_CELL = composite;
	}

	public static final CellStyleDecorator INTEGER_CELL;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(FontSetting.DEFAULT);
		composite.add(ValueHorizontalAlignment.RIGHT);
		composite.add(ValueVerticalAlignment.MIDDLE);
		composite.add(DisplayFormat.INTEGER_COMMA);
		composite.setUnmodifiable();
		INTEGER_CELL = composite;
	}

	public static final CellStyleDecorator PERCENT_SCALE1_CELL;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(FontSetting.DEFAULT);
		composite.add(ValueHorizontalAlignment.RIGHT);
		composite.add(ValueVerticalAlignment.MIDDLE);
		composite.add(DisplayFormat.PERCENT_SCALE1);
		composite.setUnmodifiable();
		PERCENT_SCALE1_CELL = composite;
	}

    public static final CellStyleDecorator DATE_YYYY_MM_DD_CELL;
    static {
        CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
        composite.add(FontSetting.DEFAULT);
        composite.add(ValueHorizontalAlignment.RIGHT);
        composite.add(ValueVerticalAlignment.MIDDLE);
        composite.add(DisplayFormat.DATE_YYYY_MM_DD);
        composite.setUnmodifiable();
        DATE_YYYY_MM_DD_CELL = composite;
    }

    public static final CellStyleDecorator DATE_YYYY_MM_CELL;
    static {
        CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
        composite.add(FontSetting.DEFAULT);
        composite.add(ValueHorizontalAlignment.RIGHT);
        composite.add(ValueVerticalAlignment.MIDDLE);
        composite.add(DisplayFormat.DATE_YYYY_MM);
        composite.setUnmodifiable();
        DATE_YYYY_MM_CELL = composite;
    }

    public static final CellStyleDecorator TIMESTAMP_YYYY_MM_DD_HH_MM_SS_CELL;
    static {
        CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
        composite.add(FontSetting.DEFAULT);
        composite.add(ValueHorizontalAlignment.RIGHT);
        composite.add(ValueVerticalAlignment.MIDDLE);
        composite.add(DisplayFormat.TIMESTAMP_YYYY_MM_DD_HH_MM_SS);
        composite.setUnmodifiable();
        TIMESTAMP_YYYY_MM_DD_HH_MM_SS_CELL = composite;
    }

	/**
	 * 全罫線レイアウト。
	 */
	public static final CellStyleDecorator FILL_BORDER;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(TopBorder.THIN);
		composite.add(BottomBorder.THIN);
		composite.add(LeftBorder.THIN);
		composite.add(RightBorder.THIN);
		composite.add(CenterBorder.THIN);
		composite.add(MiddleBorder.THIN);
		composite.setUnmodifiable();
		FILL_BORDER = composite;
	}
	/**
	 * 外枠罫線レイアウト。
	 */
	public static final CellStyleDecorator SQUARE_BORDER;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(TopBorder.THIN);
		composite.add(BottomBorder.THIN);
		composite.add(LeftBorder.THIN);
		composite.add(RightBorder.THIN);
		composite.add(CenterBorder.NONE);
		composite.add(MiddleBorder.NONE);
		composite.setUnmodifiable();
		SQUARE_BORDER = composite;
	}
	/**
	 * 外枠罫線（点線）レイアウト。
	 */
	public static final CellStyleDecorator SQUARE_DOTTED_GREY_50_BORDER;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(TopBorder.DOTTED_GREY_50);
		composite.add(BottomBorder.DOTTED_GREY_50);
		composite.add(LeftBorder.DOTTED_GREY_50);
		composite.add(RightBorder.DOTTED_GREY_50);
		composite.add(CenterBorder.NONE);
		composite.add(MiddleBorder.NONE);
		composite.setUnmodifiable();
		SQUARE_DOTTED_GREY_50_BORDER = composite;
	}

	/**
	 * 外枠罫線＋中央縦線あり罫線レイアウト。
	 */
	public static final CellStyleDecorator SQUARE_CENTER_BORDER;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(SQUARE_BORDER);
		composite.add(CenterBorder.THIN);
		composite.setUnmodifiable();
		SQUARE_CENTER_BORDER = composite;
	}

	/**
	 * 外枠罫線（点線）＋中央縦線（点線）あり罫線レイアウト。
	 */
	public static final CellStyleDecorator SQUARE_CENTER_DOTTED_GREY_50_BORDER;
	static {
		CompositeCellStyleDecorator composite = new CompositeCellStyleDecorator();
		composite.add(SQUARE_DOTTED_GREY_50_BORDER);
		composite.add(CenterBorder.DOTTED_GREY_50);
		composite.setUnmodifiable();
		SQUARE_CENTER_DOTTED_GREY_50_BORDER = composite;
	}


	public static final CellStyleDecorator TOP_BORDER_NONE = TopBorder.NONE;
	public static final CellStyleDecorator BOTTOM_BORDER_THIN = BottomBorder.THIN;
	public static final CellStyleDecorator BOTTOM_BORDER_NONE = BottomBorder.NONE;
	public static final CellStyleDecorator LEFT_BORDER_THIN = LeftBorder.THIN;
	public static final CellStyleDecorator LEFT_BORDER_NONE = LeftBorder.NONE;
	public static final CellStyleDecorator RIGHT_BORDER_NONE = RightBorder.NONE;
	public static final CellStyleDecorator MIDDLE_BORDER_NONE = MiddleBorder.NONE;

}
