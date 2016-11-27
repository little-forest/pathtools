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
import jp.littleforest.pathtools.util.SWTUtil;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;

/**
 * パッケージ名やクラス名をクリップボードにコピーするためのハンドラです。<br />
 * 
 * @author y-komori
 */
public class CopyNameHandler extends MultiDynamicHandler<IAdaptable> {
    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#isEnabled(org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected boolean isEnabled(IAdaptable adaptable) {
        IProject project = getAdaptable(adaptable, IProject.class);
        if (project != null) {
            selectedElements.add(project);
            return true;
        }

        IResource resource = getAdaptable(adaptable, IResource.class);
        if (resource != null) {
            selectedElements.add(resource);
            return true;
        }

        IJavaElement javaElement = getAdaptable(adaptable, IJavaElement.class);
        if (javaElement != null) {
            selectedElements.add(javaElement);
            return true;
        }

        IStorage storage = getAdaptable(adaptable, IStorage.class);
        if (storage != null) {
            selectedElements.add(storage);
            return true;
        }

        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        StringBuilder buf = new StringBuilder(128);
        for (Object e : selectedElements) {
            String name;
            if (e instanceof IProject) {
                // プロジェクト名
                name = ((IProject) e).getName();
            } else if (e instanceof IPackageFragmentRoot) {
                // パッケージルート
                name = ((IPackageFragmentRoot) e).getElementName();
            } else if (e instanceof IPackageFragment) {
                // パッケージ名
                name = ((IPackageFragment) e).getElementName();
            } else if (e instanceof ICompilationUnit) {
                // クラス名(.java)
                ICompilationUnit icu = (ICompilationUnit) e;
                name = concatJavaName(icu.getParent().getElementName(), icu.getElementName());
            } else if (e instanceof IClassFile) {
                // クラスファイル(.class)
                IClassFile classFile = (IClassFile) e;
                name = classFile.getType().getFullyQualifiedName();
            } else if (e instanceof IType) {
                // クラス・アノテーション名
                IType java = (IType) e;
                name = java.getFullyQualifiedName();
            } else if (e instanceof IMethod) {
                // メソッド名
                IMethod method = (IMethod) e;
                IType type = method.getDeclaringType();
                name = type.getFullyQualifiedName() + "#" + method.getElementName() + "()";
            } else if (e instanceof IField) {
                // フィールド名
                IField field = (IField) e;
                IType type = field.getDeclaringType();
                name = type.getFullyQualifiedName() + "#" + field.getElementName();
            } else if (e instanceof IStorage) {
                name = ((IStorage) e).getName();
            } else if (e instanceof IResource) {
                name = ((IResource) e).getName();
            } else {
                // その他
                name = e.toString();
            }
            buf.append(name);
            buf.append(SEP);
        }
        if (buf.length() > 0) {
            SWTUtil.copyToClipboard(buf.toString());
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

    private String concatJavaName(String parent, String child) {
        String name;
        if (parent == null || "".equals(parent)) {
            name = child;
        } else {
            name = parent + "." + child;
        }
        if (name.endsWith(".java")) {
            name = name.substring(0, name.length() - ".java".length());
        }
        return name;
    }
}
