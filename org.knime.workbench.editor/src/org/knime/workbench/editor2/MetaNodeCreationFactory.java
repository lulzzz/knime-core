/*
 * ------------------------------------------------------------------------
 *
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
 * ---------------------------------------------------------------------
 *
 * History
 *   24.03.2015 (tibuch): created
 */
package org.knime.workbench.editor2;

import org.eclipse.gef.requests.CreationFactory;
import org.knime.core.node.workflow.NodeID;
import org.knime.core.node.workflow.WorkflowCopyContent;
import org.knime.core.node.workflow.WorkflowManager;
import org.knime.core.node.workflow.WorkflowPersistor;
import org.knime.workbench.repository.model.MetaNodeTemplate;

/**
 * A Factory to create metanodes.
 * @author Tim-Oliver Buchholz, KNIME.com AG, Zurich, Switzerland
 */
public class MetaNodeCreationFactory implements CreationFactory {

    private MetaNodeTemplate m_template = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getNewObject() {
        NodeID id = m_template.getManager().getID();
        WorkflowManager sourceManager = WorkflowManager.META_NODE_ROOT;
        WorkflowCopyContent content = new WorkflowCopyContent();
        content.setNodeIDs(id);
        return sourceManager.copy(content);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getObjectType() {
        return WorkflowPersistor.class;
    }

    /**
     * @return the metanode template
     */
    public MetaNodeTemplate getMetaNodeTemplate() {
        return m_template;
    }

    /**
     * @param template the metanode template to set
     */
    public void setMetaNodeTemplate(final MetaNodeTemplate template) {
        m_template = template;
    }

}
