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

import java.io.File;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;

import jp.littleforest.pathtools.PathToolsPlugin;
import jp.littleforest.pathtools.util.IResourceUtil;

/**
 * ツリーで選択された要素を開くコマンドハンドラの基底クラスです。<br />
 *
 * @author y-komori
 */
public abstract class AbstractOpenHandler extends SingleDynamicHandler {

    private static final String PATH_HOLDER = "{path}";

    /**
     * 指定されたコマンドを実行します。<br />
     *
     * @param command
     *            コマンド
     * @param path
     *            {path} と置換するパス
     */
    protected void execRuntime(String command, String path) {
        try {
            if (path != null) {
                command = command.replace(PATH_HOLDER, path);
            }
            Runtime.getRuntime().exec(command);
            PathToolsPlugin.logInfo("Command executed. : " + command);
        } catch (Throwable ex) {
            PathToolsPlugin.logError("Unable to run command. : " + command, ex);
        }
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#isEnabled(org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected boolean isEnabled(IAdaptable adaptable) {
        // disable if specified in preference page.
        boolean enableOpenCmd = PathToolsPlugin.getDefault().getPreferenceStore().getBoolean(P_ENABLE_OPEN_CMD);
        if(!enableOpenCmd){
            return false;
        }

        boolean enabled = false;
        if (adaptable instanceof IResource) {
            this.selected = adaptable;
            enabled = true;
        } else if (adaptable instanceof IPackageFragment) {
            IPackageFragment ipf = (IPackageFragment) adaptable;
            if (ipf.getJavaProject() instanceof IJavaProject) {
                // ライブラリ中のJarの場合
                this.selected = getJarFile(ipf);
                enabled = true;
            }
        } else if (adaptable instanceof IJavaElement) {
            this.selected = getJarFile(adaptable);
            enabled = true;
        } else {
            this.selected = adaptable.getAdapter(IResource.class);
            enabled = true;
        }
        return enabled;
    }

    /**
     * JarPackageFragmentRoot から Jar を取得します。<br />
     *
     * @param adaptable
     *            JarPackageFragmentRoot を表す {@link IAdaptable}
     * @return Jar に対応する {@link File}
     */
    protected File getJarFile(IAdaptable adaptable) {//JarPackageFragmentRoot
        IJavaElement jpfr = (IJavaElement) adaptable;
        File selected = jpfr.getPath().makeAbsolute().toFile();
        if (!(selected).exists()) {
            File projectFile = new File(jpfr.getJavaProject().getProject().getLocation()
                    .toOSString());
            selected = new File(projectFile.getParent() + selected.toString());
        }
        return selected;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        if (selected == null || selectedClass == null) {
            return null;
        }

        File directory = null;

        if (selected instanceof IContainer) {
            directory = new File(IResourceUtil.toOSPath((IContainer) selected));
        } else if (selected instanceof IFile) {
            IContainer parent = ((IFile) selected).getParent();
            directory = new File(IResourceUtil.toOSPath(parent));
        } else if (selected instanceof File) {
            File file = (File) selected;
            if (!file.isDirectory()) {
                directory = file.getParentFile();
            } else {
                directory = file;
            }
        }

        if (directory != null) {
            String command = getCommandLine();
            execRuntime(command, directory.getAbsolutePath());
        } else {
            PathToolsPlugin.logError("Directory could'nt find. : " + selected.toString());
        }

        return null;
    }

    /**
     * 実行用コマンドラインを返します。<br />
     * サブクラスで実装してください。<br />
     *
     * @return コマンドライン
     */
    abstract protected String getCommandLine();

}
