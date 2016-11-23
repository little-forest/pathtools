/******************************************************************************
 * Copyright (c) 2011-2015 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/

package jp.littleforest.pathtools.util;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import jp.littleforest.pathtools.Constants;
import jp.littleforest.pathtools.PathToolsPlugin;

/**
 * @author y-komori
 *
 */
public class EclipseFontUtil {
    public static void increaseFont() {
        int changeBy = 1;
        saveChangeHistory(changeBy);
        changeFont(changeBy);
    }

    public static void decreaseFont() {
        int changeBy = -1;
        saveChangeHistory(changeBy);
        changeFont(changeBy);
    }

    public static void resetFont() {
        IPreferenceStore store = PathToolsPlugin.getDefault()
                .getPreferenceStore();
        int history = store.getInt(Constants.P_FONT_SIZE_CHANGE_HISTORY);
        PathToolsPlugin.logInfo(String.format("current history : %d", history));
        changeFont(history * -1);
        store.setValue(Constants.P_FONT_SIZE_CHANGE_HISTORY, 0);
    }

    private static void saveChangeHistory(int changeBy) {
        IPreferenceStore store = PathToolsPlugin.getDefault()
                .getPreferenceStore();
        int history = store.getInt(Constants.P_FONT_SIZE_CHANGE_HISTORY);
        store.setValue(Constants.P_FONT_SIZE_CHANGE_HISTORY, history + changeBy);
        PathToolsPlugin.logInfo(String.format("saved history : %d -> %d", history, history + changeBy));

    }

    private static void changeFont(int changeBy) {
        changeFont("org.eclipse.ui.workbench", "org.eclipse.jdt.ui.editors.textfont", changeBy);
        changeFont("org.eclipse.ui.workbench", "org.eclipse.jface.textfont", changeBy);
    }

    private static void changeFont(String qualifier, String key, int changeBy) {
        IPreferencesService preferencesService = Platform.getPreferencesService();
        String value = preferencesService.getString(qualifier, key, null, null);
        FontData[] fontDatas = PreferenceConverter.basicGetFontData(value);

        FontData fontData = fontDatas[0];
        fontData.setHeight(fontData.getHeight() + changeBy);
        Preferences prefs = preferencesService.getRootNode()
                .node("/instance/" + qualifier);
        prefs.put(key, fontData.toString());
        try {
            prefs.flush();
            PathToolsPlugin.logInfo("font changed : " + fontData.getHeight());
        } catch (BackingStoreException e) {
            PathToolsPlugin.logError(e);
        }
    }

    private static void changeFont2(int changeBy) {
        IWorkbenchPartSite site = getActiveSite();

        PathToolsPlugin.logInfo("Activesite : " + site.getClass()
                .toString());

        //        if(!(site instanceof PartSite)){
        //            return;
        //        }
        //        PartSite partSite = (PartSite)site;
        //        partSite.getPage().get
    }

    private static IWorkbenchPartSite getActiveSite() {
        IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow();
        IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
        IWorkbenchPart activePart = activePage.getActivePart();
        IWorkbenchPartSite site = activePart.getSite();
        return site;
    }
}
