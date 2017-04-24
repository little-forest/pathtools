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

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import jp.littleforest.pathtools.PathToolsPlugin;

/**
 * 本プラグインのプリファレンスイニシャライザです。<br />
 *
 * @author y-komori
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /* (non-Javadoc)
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = PathToolsPlugin.getDefault().getPreferenceStore();

        store.setDefault(P_ENABLE_OPEN_CMD, P_ENABLE_OPEN_CMD_DEFAULT);

        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.indexOf("windows") != -1) {
            store.setDefault(P_OPEN_FOLDER_CMD, P_OPEN_FOLDER_CMD_DEFAULT_WIN);
            store.setDefault(P_OPEN_PROMPT_CMD, P_OPEN_PROMPT_CMD_DEFAULT_WIN);
        } else {
            store.setDefault(P_OPEN_FOLDER_CMD, "");
            store.setDefault(P_OPEN_PROMPT_CMD, "");
        }

        store.setDefault(P_FONT_SIZE_CHANGE_HISTORY, 0);
    }
}
