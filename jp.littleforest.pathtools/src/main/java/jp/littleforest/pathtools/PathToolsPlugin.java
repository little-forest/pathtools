/******************************************************************************
 * Copyright (c) 2011-2012 Yusuke Komori. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *****************************************************************************/
package jp.littleforest.pathtools;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import jp.littleforest.pathtools.util.EclipseFontUtil;

/**
 * The activator class controls the plug-in life cycle
 *
 * 本プラグインでは、visibleWhen 判定をハンドラで独自実装しており、プラグインを遅延起動させないため org.eclipse.ui.startup
 * 拡張を使用しています。(遅延起動にすると、ポップアップメニューの初回表示時にハンドラの isEnabled() メソッドが呼びされないため)
 *
 * @author y-komori
 */
public class PathToolsPlugin extends AbstractUIPlugin implements IStartup {

    // The plug-in ID
    public static final String PLUGIN_ID = "jp.littleforest.pathtools"; //$NON-NLS-1$

    // The shared instance
    private static PathToolsPlugin plugin;

    /**
     * {@link PathToolsPlugin} を構築します。<br />
     */
    public PathToolsPlugin() {
    }

    /*
    * (non-Javadoc)
    * @see
    org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
    */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
        logInfo(PLUGIN_ID + " bundle started.");
    }

    /*
    * (non-Javadoc)
    * @see
    org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
    */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
        logInfo(PLUGIN_ID + " bundle stopped.");
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static PathToolsPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     *
     * @param path
     *        the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    /**
     * アクティブな {@link IWorkbenchPartSite} を返します。<br />
     *
     * @return {@link IWorkbenchPartSite}、見つからなかった場合は {@code null}
     */
    public IWorkbenchPartSite getActiveSite() {
        IWorkbenchWindow window = plugin.getWorkbench().getActiveWorkbenchWindow();
        if (window != null) {
            IWorkbenchPage activePage = window.getActivePage();
            if (activePage != null) {
                IWorkbenchPart activePart = activePage.getActivePart();
                if (activePart != null) {
                    return activePart.getSite();
                }
            }
        }
        return null;
    }

    /* (non-Javadoc)
    * @see org.eclipse.ui.IStartup#earlyStartup()
    */
    public void earlyStartup() {
        logInfo("Eary startup ok.");
        EclipseFontUtil.resetFont();
    }

    public static void logError(String message) {
        log(IStatus.ERROR, message, null);
    }

    public static void logError(String message, Throwable ex) {
        log(IStatus.ERROR, message, ex);
    }

    public static void logError(Throwable ex) {
        log(IStatus.ERROR, ex.getLocalizedMessage(), ex);
    }

    public static void logInfo(String msg) {
        log(IStatus.INFO, msg, null);
    }

    public static void log(int severity, String message, Throwable throwable) {
        ILog log = PathToolsPlugin.getDefault().getLog();
        Status status = new Status(severity, PLUGIN_ID, message, throwable);
        log.log(status);
    }

}
