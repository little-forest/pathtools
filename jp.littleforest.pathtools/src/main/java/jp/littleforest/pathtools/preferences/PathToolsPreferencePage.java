/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools.preferences;

import static jp.littleforest.pathtools.Constants.*;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import jp.littleforest.pathtools.Messages;
import jp.littleforest.pathtools.PathToolsPlugin;

/**
 * 本プラグインのプリファレンスページです。<br />
 *
 * @author y-komori
 */
public class PathToolsPreferencePage extends FieldEditorPreferencePage implements
IWorkbenchPreferencePage {

    /**
     * {@link PathToolsPreferencePage} を構築します。<br />
     */
    public PathToolsPreferencePage() {
        super(GRID);
        IPreferenceStore store = PathToolsPlugin.getDefault().getPreferenceStore();
        setPreferenceStore(store);
        setDescription(""); //$NON-NLS-1$
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
        addField(new BooleanFieldEditor(P_ENABLE_OPEN_CMD, Messages.PATHTOOLS_DISPLAY_OPEN_IN_MENU, getFieldEditorParent()));
        addField(new StringFieldEditor(P_OPEN_FOLDER_CMD, Messages.PATHTOOLS_OPEN_FOLDER_CMD, getFieldEditorParent()));
        addField(new StringFieldEditor(P_OPEN_PROMPT_CMD, Messages.PATHTOOLS_OPEN_PROMPT_CMD,
                getFieldEditorParent()));
    }

}
