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
import static jp.littleforest.pathtools.util.IResourceUtil.*;

import java.util.List;

import jp.littleforest.pathtools.util.SWTUtil;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;

/**
 * パスをコピーするハンドラの基底クラスです。<br />
 * 
 * @author y-komori
 */
public abstract class AbstractPathHandler extends MultiDynamicHandler<IResource> {
    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#isEnabled(org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected boolean isEnabled(IAdaptable adaptable) {
        IResource resource = getAdaptable(adaptable, IResource.class);
        if (resource != null) {
            selectedElements.add(resource);
            selectedClass = IResource.class;
            return true;
        } else {
            return false;
        }
    }

    /**
     * ハンドラの実行メソッドです。<br />
     * サブクラスの振る舞い応じてパスを取得してクリップボードにコピーします。
     * 
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        String path = getPath(selectedElements);
        if(path != null && path.length() > 0){
            SWTUtil.copyToClipboard(path);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#getTargetSiteIds()
     */
    @Override
    protected String[] getTargetSiteIds() {
        return TARGET_SITE_IDS;
    }

    /**
     * 指定されたリソースからパスを取り出します。<br />
     * 
     * @param resources
     *            リソースのリスト
     * @return パス文字列
     */
    abstract protected String getPath(List<IResource> resources);
}
