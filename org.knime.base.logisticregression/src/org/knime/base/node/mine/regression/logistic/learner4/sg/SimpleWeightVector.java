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
 *   20.03.2017 (Adrian): created
 */
package org.knime.base.node.mine.regression.logistic.learner4.sg;

import org.knime.base.node.mine.regression.logistic.learner4.TrainingRow;
import org.knime.base.node.mine.regression.logistic.learner4.sg.IndexCache.IndexIterator;

/**
 * Simple implementation of a weight vector that directly stores and manipulates the weights.
 *
 * @author Adrian Nembach, KNIME.com
 */
class SimpleWeightVector <T extends TrainingRow> extends AbstractWeightVector<T> {

    public SimpleWeightVector(final int nFets, final int nCats, final boolean fitIntercept) {
        super(nFets, nCats, fitIntercept);
    }

    @Override
    public void scale(final double scaleFactor) {
        update((val, c, i) -> val * scaleFactor, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void normalize() {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getScale() {
        // this simple implementation does not use a scalar scale
        return 1.0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] predict(final T row) {
        double[] prediction = new double[m_data.length];
        for (int c = 0; c < m_data.length; c++) {
            double p = 0.0;
            for (int i = 0; i < m_data[c].length; i++) {
                p += m_data[c][i] * row.getFeature(i);
            }
            prediction[c] = p;
        }
        return prediction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] predict(final T row, final int[] nonZeroIndices) {
        double[] prediction = new double[m_data.length];
        for (int c = 0; c < m_data.length; c++) {
            double p = 0.0;
            for (int i = 0; i < nonZeroIndices.length; i++) {
                int idx = nonZeroIndices[i];
                if (idx == -1) {
                    break;
                }
                p += m_data[c][idx] * row.getFeature(idx);
            }
            prediction[c] = p;
        }
        return prediction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double[] predict(final T row, final IndexCache indexCache) {
        double[] prediction = new double[m_data.length];
        for (int c = 0; c < m_data.length; c++) {
            double p = 0.0;
            for (IndexIterator iter = indexCache.getIterator(); iter.hasNext();) {
                int i = iter.next();
                p += m_data[c][i] * row.getFeature(i);
            }
            prediction[c] = p;
        }
        return prediction;
    }
}
