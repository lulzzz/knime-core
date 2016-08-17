/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME GmbH, Konstanz, Germany
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
 * ------------------------------------------------------------------------
 *
 * History
 *   Mar 19, 2012 (hornm): created
 */
package org.knime.core.node;

import java.io.InputStream;

import org.knime.node2012.KnimeNodeDocument;

/**
 * A node factory to create nodes dynamically. It essentially creates the node description (usually given in the
 * XXXNodeFactory.xml) dynamically.
 *
 * @author Dominik Morent, KNIME.com, Zurich, Switzerland
 * @author Martin Horn, University of Konstanz
 * @author Thorsten Meinl, KNIME.com, Zurich, Switzerland
 * @param <T> the node model of the factory
 * @since 2.6
 */
public abstract class DynamicNodeFactory<T extends NodeModel> extends NodeFactory<T> {
    /**
     * Creates a new dynamic node factory.
     */
    public DynamicNodeFactory() {
        super(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Deprecated
    protected final InputStream getPropertiesInputStream() {
        return super.getPropertiesInputStream();
    }

    /**
     * Subclasses should add the node description elements. The {@link KnimeNodeDocument} reflects the structure given
     * in http://www.knime.org/Node.dtd and allows one to create the node description document more easily (generated by
     * XMLBeans (see http://xmlbeans.apache.org/)
     *
     * @param doc the document to add the description to
     * @deprecated override {@link #createNodeDescription()} instead
     */
    @Deprecated
    protected void addNodeDescription(final KnimeNodeDocument doc) {
        // empty on purpose, subclasses should not be force to override this deprecated method
    }

    /**
     * {@inheritDoc}
     *
     * In case of dynamic nodes the procedure is roughly as follows:
     * <ol>
     * <li>Create a KnimeNodeDocument in the desired version (preferably the latest, e.g.
     * {@link org.knime.node.v28.KnimeNodeDocument})</li>
     * <li>Populate the document</li>
     * <li>Use one of the node description proxies (e.g. {@link NodeDescription28Proxy}) in order to return the
     * {@link NodeDescription}</li>
     * </ol>
     */
    @SuppressWarnings("deprecation")
    @Override
    protected NodeDescription createNodeDescription() {
        KnimeNodeDocument doc = KnimeNodeDocument.Factory.newInstance();
        addNodeDescription(doc);
        return new NodeDescription27Proxy(doc);
    }

    /** Calls {@link NodeFactory#createNodeDescription()}, which parses the corresponding xml file named after
     * this factory class (same package). If that fails it logs it to the NodeLogger and returns an empty
     * {@link NoDescriptionProxy}.
     * @return The result of that call.
     * @since 2.10
     */
    protected NodeDescription parseNodeDescriptionFromFile() {
        try {
            return super.createNodeDescription();
        } catch (Exception e) {
            NodeLogger.getLogger(getClass()).error(e);
            return new NoDescriptionProxy(getClass());
        }
    }
}
