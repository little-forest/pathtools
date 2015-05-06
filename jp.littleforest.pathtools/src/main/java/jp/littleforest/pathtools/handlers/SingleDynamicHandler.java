/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers;

import jp.littleforest.pathtools.Activator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * メニューを動的に表示するハンドラの基底クラスです。<br />
 * 
 * @author y-komori
 */
public abstract class SingleDynamicHandler extends DynamicHandler {

    protected Object selected = null;

    protected Class<?> selectedClass = null;

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.AbstractHandler#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        IWorkbenchPartSite activeSite = Activator.getDefault().getActiveSite();

        this.selected = null;
        this.selectedClass = null;
        if (activeSite == null) {
            return false;
        }

        if (!checkSiteId(activeSite, getTargetSiteIds())) {
            return false;
        }

        boolean enabled = false;

        // 選択要素の型からコマンド表示対象かどうかを判断する
        ISelection selection = activeSite.getSelectionProvider().getSelection();
        if (selection != null && selection instanceof IStructuredSelection) {
            IStructuredSelection stSelection = (IStructuredSelection) selection;
            if (stSelection.size() == 1) {
                IAdaptable adaptable = (IAdaptable) stSelection.getFirstElement();
                // 実際の判定はサブクラスで行う
                enabled = isEnabled(adaptable);
                if (enabled) {
                    this.selectedClass = adaptable.getClass();
                }
            }
        }
        return enabled;
    }

    protected boolean isEnabled(IAdaptable adaptable) {
        return true;
    }
}
