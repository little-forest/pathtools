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

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.Signature;

import jp.littleforest.pathtools.util.SVNUtil;
import jp.littleforest.pathtools.util.SWTUtil;
import jp.littleforest.util.lang.StringUtil;

/**
 * 修飾名をクリップボードにコピーするためのハンドラです。<br />
 *
 * @author y-komori
 */
public class CopyQualifiedNameHandler extends MultiDynamicHandler<IJavaElement> {

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.MultiDynamicHandler#isEnabled(org.eclipse.core.runtime.IAdaptable)
     */
    @Override
    protected boolean isEnabled(IAdaptable adaptable) {
        IJavaElement javaElement = getAdaptable(adaptable, IJavaElement.class);
        if (javaElement != null) {
            selectedElements.add(javaElement);
            return true;
        } else {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {
        StringBuilder buf = new StringBuilder(128);
        for (IJavaElement e : selectedElements) {
            String fqcn = getQualifiedName(e);
            buf.append(SVNUtil.getRevisionString(e.getResource()));
            buf.append(fqcn);
            buf.append(SEP);
        }
        if (buf.length() > 0) {
            SWTUtil.copyToClipboard(buf.toString());
        }
        return null;
    }

    protected String getQualifiedName(IJavaElement e) {
        String qn = "";
        switch (e.getElementType()) {
        case IJavaElement.ANNOTATION:
            break;
        case IJavaElement.CLASS_FILE:
            qn = getQualifiedClassName((IClassFile) e);
            break;
        case IJavaElement.COMPILATION_UNIT:
            qn = getQualifiedClassName((ICompilationUnit) e);
            break;
        case IJavaElement.FIELD:
            qn = getQualifiedFieldName((IField) e);
            break;
        case IJavaElement.IMPORT_CONTAINER:
            break;
        case IJavaElement.IMPORT_DECLARATION:
            break;
        case IJavaElement.INITIALIZER:
            break;
        case IJavaElement.JAVA_MODEL:
            break;
        case IJavaElement.JAVA_PROJECT:
            break;
        case IJavaElement.LOCAL_VARIABLE:
            break;
        case IJavaElement.METHOD:
            qn = getQualifiedMethodName((IMethod) e);
            break;
        case IJavaElement.PACKAGE_DECLARATION:
            break;
        case IJavaElement.PACKAGE_FRAGMENT:
            qn = getQualifiedPackageName(e);
            break;
        case IJavaElement.TYPE:
            qn = getQualifiedClassName((IType) e);
            break;
        case IJavaElement.TYPE_PARAMETER:
            break;
        default:
            break;
        }
        return qn;
    }

    protected String getQualifiedClassName(ICompilationUnit cu) {
        String fqcn = StringUtil.trimSuffix(cu.getElementName(), ".java");
        IJavaElement parent = cu.getParent();
        if (parent == null) {
            return fqcn;
        }
        if (parent.getElementType() == IJavaElement.PACKAGE_FRAGMENT) {
            String parentQn = getQualifiedPackageName(parent);
            if (StringUtil.isNotEmpty(parentQn)) {
                fqcn = getQualifiedPackageName(parent) + "." + fqcn;
            }
        }
        return fqcn;
    }

    protected String getQualifiedClassName(IType type) {
        String fqcn = type.getElementName();
        IJavaElement parent = type.getParent();
        if (parent == null) {
            return fqcn;
        }
        int parentType = parent.getElementType();
        if (parentType == IJavaElement.TYPE) {
            // 内部クラスの場合
            String enclosingQn = getQualifiedName(parent);
            fqcn = enclosingQn + "$" + fqcn;
        } else if (parentType == IJavaElement.COMPILATION_UNIT) {
            // クラスの場合
            fqcn = getQualifiedClassName((ICompilationUnit) parent);
        } else if (parentType == IJavaElement.CLASS_FILE) {
            // Jar内のクラスの場合
            fqcn = getQualifiedClassName((IClassFile) parent);
        } else if (parentType == IJavaElement.PACKAGE_FRAGMENT) {
            fqcn = getQualifiedPackageName(parent) + "." + fqcn;
        }
        return fqcn;
    }

    protected String getQualifiedClassName(IClassFile classFile) {
        String fqcn = StringUtil.trimSuffix(classFile.getElementName(), ".class");
        IJavaElement parent = classFile.getParent();
        if (parent == null) {
            return fqcn;
        }
        int parentType = parent.getElementType();
        if (parentType == IJavaElement.TYPE) {
            // 内部クラスの場合
            String enclosingQn = getQualifiedName(parent);
            fqcn = enclosingQn + "$" + fqcn;
        } else if (parentType == IJavaElement.COMPILATION_UNIT) {
            // クラスの場合
            fqcn = getQualifiedClassName((ICompilationUnit) parent);
        } else if (parentType == IJavaElement.PACKAGE_FRAGMENT) {
            fqcn = getQualifiedPackageName(parent) + "." + fqcn;
        }
        return fqcn;
    }

    protected String getQualifiedPackageName(IJavaElement e) {
        if (e instanceof IPackageFragment) {
            return ((IPackageFragment) e).getElementName();
        } else {
            return e.getElementName();
        }
    }

    protected String getQualifiedFieldName(IField e) {
        IJavaElement parent = e.getParent();
        int parentType = parent.getElementType();
        String parentQn = "";
        if (parentType == IJavaElement.TYPE) {
            parentQn = getQualifiedClassName((IType) parent);
        } else if (parentType == IJavaElement.CLASS_FILE) {
            parentQn = getQualifiedClassName((IClassFile) parent);
        }
        return parentQn + "#" + e.getElementName();
    }

    protected String getQualifiedMethodName(IMethod e) {
        IJavaElement parent = e.getParent();
        StringBuilder qn = new StringBuilder(48);
        qn.append(getQualifiedClassName((IType) parent));
        qn.append("#");
        qn.append(e.getElementName());
        qn.append("(");
        boolean isFirst = true;
        for (String paramType : e.getParameterTypes()) {
            if (!isFirst) {
                qn.append(", ");
            } else {
                isFirst = false;
            }
            qn.append(Signature.toString(paramType));
        }
        qn.append(")");
        return qn.toString();
    }

    /* (non-Javadoc)
     * @see jp.littleforest.pathtools.handlers.DynamicHandler#getTargetSiteIds()
     */
    @Override
    protected String[] getTargetSiteIds() {
        return TARGET_SITE_IDS;
    }
}
