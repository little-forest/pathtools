/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers.path;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * メニューを動的に表示するハンドラの基底クラスです。<br />
 * 
 * @author y-komori
 */
public abstract class DynamicHandler extends AbstractHandler {

    /**
     * サイトのIDが {@code targetSiteIds} のいずれかであるかどうかをチェックします。<br />
     * 
     * @param site
     *            サイト
     * @param targetSiteIds
     *            チェック対象サイトID
     * @return サイトの ID が チェック対象サイト ID のいずれかであれば {@code true}
     */
    protected boolean checkSiteId(IWorkbenchPartSite site, String[] targetSiteIds) {
        String id = site.getId();
        for (String targetId : targetSiteIds) {
            if (targetId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    protected abstract String[] getTargetSiteIds();

}
