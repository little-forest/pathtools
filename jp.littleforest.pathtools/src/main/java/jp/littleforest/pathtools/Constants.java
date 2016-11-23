/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools;

/**
 * 本プラグインのための定数インターフェースです。<br />
 * 
 * @author y-komori
 */
public interface Constants {
    // メニュー表示対象サイトのID
    public static final String[] TARGET_SITE_IDS = { "org.eclipse.ui.navigator.ProjectExplorer",
    "org.eclipse.jdt.ui.PackageExplorer" };

    // 改行文字
    public static final String SEP = System.getProperty("line.separator");

    public static final String P_OPEN_FOLDER_CMD = "jp.littleforest.pathtools.preps.P_OPEN_FOLDER_CMD";

    public static final String P_OPEN_FOLDER_CMD_DEFAULT_WIN = "explorer.exe /e ,/root, \"{path}\"";

    public static final String P_OPEN_PROMPT_CMD = "jp.littleforest.pathtools.preps.P_OPEN_PROMPT_CMD";

    public static final String P_OPEN_PROMPT_CMD_DEFAULT_WIN = "cmd /K start cd /D \"{path}\"";

    public static final String P_FONT_SIZE_CHANGE_HISTORY = "jp.littleforest.pathtools.preps.P_FONT_SIZE_CHANGE_HISTORY";
}
