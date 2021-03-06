/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers.path;

import static jp.littleforest.pathtools.Constants.*;
import jp.littleforest.pathtools.PathToolsPlugin;

/**
 * フォルダを開くコマンドのためのハンドラです。<br />
 * 
 * @author y-komori
 */
public class OpenFolderHandler extends AbstractOpenHandler {

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.AbstractOpenHandler#getTargetSiteIds()
     */
    @Override
    protected String[] getTargetSiteIds() {
        return TARGET_SITE_IDS;
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.AbstractOpenHandler#getCommandLine()
     */
    @Override
    protected String getCommandLine() {
        return PathToolsPlugin.getDefault().getPreferenceStore().getString(P_OPEN_FOLDER_CMD);
    }
}
