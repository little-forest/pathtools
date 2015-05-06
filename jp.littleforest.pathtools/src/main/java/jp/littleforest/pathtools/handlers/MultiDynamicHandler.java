/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.littleforest.pathtools.Activator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * @author y-komori
 *
 */
public abstract class MultiDynamicHandler<E extends IAdaptable> extends DynamicHandler {

    protected List<E> selectedElements = new ArrayList<E>();

    protected Class<?> selectedClass = null;

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.AbstractHandler#isEnabled()
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean isEnabled() {
        IWorkbenchPartSite activeSite = Activator.getDefault().getActiveSite();

        this.selectedElements.clear();
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
            int size = stSelection.size();
            if (size > 0) {
                Iterator<IAdaptable> itr = stSelection.iterator();
                while (itr.hasNext()) {
                    IAdaptable adaptable = itr.next();
                    // 実際の判定はサブクラスで行う(いずれかが条件を満たせばメニューを表示する)
                    enabled |= isEnabled(adaptable);
                }
            }
        }
        return enabled;
    }

    protected boolean isEnabled(IAdaptable adaptable) {
        return true;
    }
}
