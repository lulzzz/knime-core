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
 *   2010 03 28 (ohl): created
 */
package org.knime.workbench.ui.layout.commands;

import java.util.Map;

import org.eclipse.gef.commands.Command;
import org.knime.core.node.workflow.ConnectionContainer;
import org.knime.core.node.workflow.ConnectionID;
import org.knime.core.node.workflow.ConnectionUIInformation;
import org.knime.core.node.workflow.NodeContainer;
import org.knime.core.node.workflow.NodeID;
import org.knime.core.node.workflow.NodeUIInformation;
import org.knime.core.node.workflow.WorkflowManager;
import org.knime.workbench.editor2.editparts.NodeContainerEditPart;
import org.knime.workbench.ui.layout.align.HorizAlignManager;

/**
 *
 * @author ohl, KNIME.com, Zurich, Switzerland
 */
public class HorizAlignCommand extends Command {

    private final WorkflowManager m_wfm;

    private HorizAlignManager m_alignMgr;

    private final NodeContainerEditPart[] m_nodes;

    /**
     * @param wfm the workflow manager holding the nodes
     * @param nodes the nodes to align.
     */
    public HorizAlignCommand(final WorkflowManager wfm,
            final NodeContainerEditPart[] nodes) {
        m_wfm = wfm;
        m_nodes = nodes.clone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        m_alignMgr = new HorizAlignManager(m_wfm, m_nodes);
        m_alignMgr.doLayout();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canUndo() {
        return m_alignMgr != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void undo() {
        Map<NodeID, NodeUIInformation> oldPositions =
                m_alignMgr.getOldNodeCoordinates();
        Map<ConnectionID, ConnectionUIInformation> oldBendpoints =
                m_alignMgr.getOldBendpoints();
        // re-position nodes
        for (Map.Entry<NodeID, NodeUIInformation> e : oldPositions.entrySet()) {
            NodeContainer nc = m_wfm.getNodeContainer(e.getKey());
            if (nc == null) {
                continue;
            }
            nc.setUIInformation(e.getValue());
        }
        // re-create bendpoints
        for (Map.Entry<ConnectionID, ConnectionUIInformation> e : oldBendpoints
                .entrySet()) {
            ConnectionContainer cc = m_wfm.getConnection(e.getKey());
            if (cc == null) {
                continue;
            }
            cc.setUIInfo(e.getValue());
        }

    }
}
