/*
 * ------------------------------------------------------------------------
 *
 *  Copyright (C) 2003 - 2013
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 * Created on Apr 22, 2013 by Berthold
 */
package org.knime.core.node.interactive;

import org.knime.core.node.NodeModel;

/** Abstract base class for interactive views which are launched on the client side via
 * an integrated browser. They only have indirect access to the NodeModel via get and
 * setViewContent methods and therefore simulate the behaviour of the same view in the
 * WebPortal.
 *
 * @author B. Wiswedel, M. Berthold, Th. Gabriel
 * @param <T> requires a NodeModel implementing InteractiveNode as well
 * @since 2.8
 */
public final class InteractiveWebView<T extends NodeModel & InteractiveNode> extends AbstractInteractiveNodeView<T> {

    /**
     * @param nodeModel the underlying model
     * @param wvt the template to be used for the web view
     */
    protected InteractiveWebView(final T nodeModel, final WebViewTemplate wvt) {
        super(nodeModel);
    }

    /**
     * Load a new ViewContent into the underlying NodeModel.
     *
     * @param vc the new content of the view.
     */
    protected final void loadViewContentIntoNode(final ViewContent vc) {
        getNodeModel().loadViewContent(vc);
    }

    /**
     * @return ViewContent of the underlying NodeModel.
     */
    protected final ViewContent getViewContentFromNode() {
        return getNodeModel().createViewContent();
    }

    /** Set current ViewContent as new default settings of the underlying NodeModel.
     */
    protected final void makeViewContentNewDefault() {
        // TODO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {
        // TODO Auto-generated method stub
    }



    /**
     * {@inheritDoc}
     */
    @Override
    protected final void callOpenView(final String title) {
        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final void callCloseView() {
        // TODO Auto-generated method stub
    }

}