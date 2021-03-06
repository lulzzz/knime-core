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
 *   Jan 8, 2012 (wiswedel): created
 */
package org.knime.base.node.mine.treeensemble.data;

import org.knime.base.node.mine.treeensemble.learner.SplitCandidate;
import org.knime.base.node.mine.treeensemble.model.TreeNodeCondition;
import org.knime.base.node.mine.treeensemble.node.learner.TreeEnsembleLearnerConfiguration;

/**
 *
 * @author Bernd Wiswedel, KNIME.com, Zurich, Switzerland
 */
public abstract class TreeAttributeColumnData extends TreeColumnData {

    private final TreeEnsembleLearnerConfiguration m_configuration;

    /**
     * @param metaData
     * @param configuration
     */
    protected TreeAttributeColumnData(final TreeAttributeColumnMetaData metaData,
        final TreeEnsembleLearnerConfiguration configuration) {
        super(metaData);
        m_configuration = configuration;
    }

    /** {@inheritDoc} */
    @Override
    public TreeAttributeColumnMetaData getMetaData() {
        return (TreeAttributeColumnMetaData)super.getMetaData();
    }

    /**
     * @return the configuration
     */
    protected final TreeEnsembleLearnerConfiguration getConfiguration() {
        return m_configuration;
    }

    /**
     * @return an array containing for each index in the column the original index in the table.
     */
    public abstract int[] getOriginalIndicesInColumnList();

    /**
     * Calculates the best split candidate for classification
     *
     * @param membershipController
     * @param rowWeights
     * @param targetPriors
     * @param targetColumn
     * @return best split candidate for classification
     */
    public abstract SplitCandidate calcBestSplitClassification(final TreeNodeMembershipController membershipController,
        final double[] rowWeights, final ClassificationPriors targetPriors,
        final TreeTargetNominalColumnData targetColumn);

    /**
     * Calculates the best split candidate for regression
     *
     * @param membershipController
     * @param rowWeights
     * @param targetPriors
     * @param targetColumn
     * @return best split candidate for regression
     */
    public abstract SplitCandidate calcBestSplitRegression(final TreeNodeMembershipController membershipController,
        final double[] rowWeights, final RegressionPriors targetPriors, final TreeTargetNumericColumnData targetColumn);

    /**
     * @param childCondition
     * @param parentMemberships
     * @param childMembershipsToUpdate
     */
    public abstract void updateChildMemberships(final TreeNodeCondition childCondition,
        final double[] parentMemberships, final double[] childMembershipsToUpdate);

    /**
     * @param childCondition
     * @param parentController
     * @return a child controller containing only indices that satisfy <b>childCondition</b>
     */
    public abstract TreeNodeMembershipController getChildNodeMembershipController(
        final TreeNodeCondition childCondition, final TreeNodeMembershipController parentController);

}
