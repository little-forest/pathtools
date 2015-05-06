/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;

/**
 * ワークスペースを開くコマンドのためのハンドラです。<br />
 * 
 * @author y-komori
 */
public class OpenWorkspaceHandler extends OpenFolderHandler {
    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.SingleDynamicHandler#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        // 常にメニューを有効にする
        return true;
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.AbstractOpenHandler#isEnabled(org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected boolean isEnabled(IAdaptable adaptable) {
        // 常にメニューを有効にする
        return true;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
        String command = getCommandLine();
        execRuntime(command, workspaceRoot.getLocation().toOSString());
        return null;
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#getTargetSiteIds()
     */
    @Override
    protected String[] getTargetSiteIds() {
        return new String[] {};
    }
}
